package com.linku.server.ticket.service;

import com.linku.server.BaseException;
import com.linku.server.base.service.AutoNumberService;
import com.linku.server.common.id.IdGenerators;
import com.linku.server.common.utils.DateUtils;
import com.linku.server.ticket.dao.PticketDao;
import com.linku.server.ticket.domain.Pticket;
import com.linku.server.user.domain.User;
import com.linku.server.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PticketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PticketService.class);

    private static final int MAX_TRY = 5;

    private final PticketDao dao;
    private final UserService userService;
    private final AutoNumberService autoNumberService;

    @Autowired
    public PticketService(PticketDao dao, UserService userService, AutoNumberService autoNumberService) {
        this.dao = dao;
        this.userService = userService;
        this.autoNumberService = autoNumberService;
    }

    public Pticket get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("优惠券不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Pticket save(Pticket t){
        t.setId(IdGenerators.uuid());
        t.setState(Pticket.State.WAIT);
        User user = userService.get(t.getUserId());
        if(!StringUtils.equals(user.getShopId(), t.getShopId())){
            throw new BaseException("不能领取非本店铺优惠券");
        }
        t.setName(StringUtils.isBlank(user.getName())? user.getNickname(): user.getNickname());
        t.setHeadImg(user.getHeadImg());
        t.setCode(genCode(t.getShopId()));

        dao.insert(t);
        return get(t.getId());
    }


    private String genCode(String shopId){
        String shopCode = String.format("%04d", shopId.hashCode() % 10000);
        String now = DateUtils.format("yyyyMMdd", new Date());
        String key = shopId + "@pticket@" + now;
        String code = String.format("%06d", autoNumberService.maxNumber(key));
        return shopCode + now  + code;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Pticket use(String code, String shopId){
        for(int i = 0; i < MAX_TRY; i++){

            Pticket t = getByCode(code);
            if(!StringUtils.equals(t.getShopId(), shopId)){
                throw new BaseException("店铺不存在");
            }

            if(t.getState() == Pticket.State.USE){
                throw new BaseException("优惠券已经使用");
            }

            if(t.getState() == Pticket.State.EXPIRE){
                throw new BaseException("优惠券已经过期");
            }

            if(dao.updateState(code, Pticket.State.USE, t.getVersion())){
                return get(t.getId());
            }
        }

        throw new BaseException("使用优惠券失败");
    }

    private Pticket getByCode(String code){
        try{
            return dao.findOneByCode(code);
        }catch (DataAccessException e){
            throw new BaseException("优惠券不存在");
        }
    }

    public long count(String userId, String shopId, Pticket.State state){
        return dao.count(userId, shopId, state);
    }

    public List<Pticket> query(String userId, String shopId, Pticket.State state, int offset, int rows){
        return dao.find(userId, shopId, state, offset, rows);
    }
}
