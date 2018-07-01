package com.tuoshecx.server.goods.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.common.utils.HtmlUtils;
import com.tuoshecx.server.goods.dao.GoodsDao;
import com.tuoshecx.server.goods.dao.GroupItemDao;
import com.tuoshecx.server.goods.domain.Goods;
import com.tuoshecx.server.goods.domain.GroupItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class GoodsService {
    private final GoodsDao dao;
    private final GroupItemDao groupItemDao;

    @Autowired
    public GoodsService(GoodsDao dao, GroupItemDao groupItemDao) {
        this.dao = dao;
        this.groupItemDao = groupItemDao;
    }

    public Goods get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("商品不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Goods save(Goods t, List<GroupItem> groupItems){
        t.setId(IdGenerators.uuid());
        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(summary(t.getDetail()));
        }
        t.setGroup(isGroup(groupItems));
        dao.insert(t);
        if(isGroup(groupItems)){
            saveGroupItems(t.getId(), t.getShopId(), groupItems);
        }
        return get(t.getId());
    }

    private String summary(String detail){
        return HtmlUtils.text(detail, 150);
    }

    private boolean isGroup(List<GroupItem> groupItems){
        return groupItems != null && !groupItems.isEmpty();
    }

    private void saveGroupItems(String id, String shopId, List<GroupItem> groupItems){
        for(GroupItem item: groupItems){
            Goods goods = get(item.getItemGoodsId());
            validateOfShop(goods, shopId);
            if(goods.getGroup()){
                throw new BaseException("不能添加组合商品");
            }
            if(goods.getDelete()){
                throw new BaseException("商品已经删除");
            }
            item.setId(IdGenerators.uuid());
            item.setGoodsId(id);
            item.setName(goods.getName());
            item.setIcon(goods.getIcon());
            groupItemDao.insert(item);
        }
    }

    private void validateOfShop(Goods t, String shopId){
        if(!StringUtils.equals(t.getShopId(), shopId)){
            throw new BaseException("无操作权限");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Goods update(Goods t, List<GroupItem> groupItems){
        Goods o = get(t.getId());

        if(o.getOpen()){
            throw new BaseException("上架商品不能修改，请先下架");
        }
        validateOfShop(o, t.getShopId());

        boolean isGroup = isGroup(groupItems);
        if(!o.getGroup() && isGroup && groupItemDao.hasItem(o.getId())){
            throw new BaseException("该商品已经组合，不能改成组合商品");
        }

        if(isGroup){
            updateGroupItem(o, groupItems);
        }else{
            groupItemDao.updateItemGoods(t.getId(), t.getName(), t.getIcon());
        }

        t.setGroup(isGroup);
        if(StringUtils.isBlank(t.getSummary())){
            t.setSummary(summary(t.getDetail()));
        }
        dao.update(t);

        return get(t.getId());
    }

    private void updateGroupItem(Goods o, List<GroupItem> groupItems){
        if(o.getGroup()){
            groupItemDao.deleteByGoodsId(o.getId());
        }
        saveGroupItems(o.getId(),o.getShopId(), groupItems);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Goods close(String id, String shopId){
        Goods o = get(id);

        validateOfShop(o, shopId);
        if(!o.getOpen()){
            throw new BaseException("商品已经下架");
        }

        if(!o.getGroup() && isGroupOpen(o.getId())){
            throw new BaseException("还有组合商品未下架");
        }

        dao.updateOpen(id, false);

        return get(id);
    }

    private boolean isGroupOpen(String itemGroupId){
        return groupItemDao.findGoodsIdByItemGoodsId(itemGroupId)
                .stream().map(dao::findOne).anyMatch(Goods::getOpen);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Goods open(String id, String shopId){
        Goods o = get(id);

        validateOfShop(o, shopId);
        if(o.getOpen()){
            throw new BaseException("商品已经上架");
        }

        if(o.getGroup() && !isItemAllOpen(o.getId())){
            throw new BaseException("在组合商品中有未上架的商品");
        }

        dao.updateOpen(id, true);
        return get(id);
    }

    private boolean isItemAllOpen(String goodsId){
        return groupItemDao.findByGoodsId(goodsId).stream()
                .map(e -> get(e.getItemGoodsId())).allMatch(Goods::getOpen);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id, String shopId){
        Goods o = get(id);

        validateOfShop(o, shopId);
        if(o.getOpen()){
            throw new BaseException("上架商品不能删除，请先下架");
        }

        if(!o.getGroup() && isGroupOpen(o.getId())){
            throw new BaseException("还有组合商品未下架");
        }

        return dao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Goods incStock(String id, String shopId, int count){
        Goods o = get(id);

        validateOfShop(o, shopId);
        if(o.getDelete()){
            throw new BaseException("商品不存在");
        }
        if(count < 0 && (o.getStock() + count) < 0){
            throw new BaseException("库存不能小于零");
        }

        if(!dao.incStock(id, count)){
            throw new BaseException("改动库存失败");
        }

        return get(id);
    }

    public long count(String shopId, String catalog, String name, Boolean isOpen){
        return dao.count(shopId, catalog, name, isOpen);
    }

    public List<Goods> query(String shopId, String catalog, String name, Boolean isOpen, int offset, int limit){
        return dao.find(shopId, catalog, name, isOpen, offset, limit);
    }

    public List<GroupItem> queryGroupItems(String id){
        return groupItemDao.findByGoodsId(id);
    }
}
