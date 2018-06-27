package com.linku.server.game.wheel.service;

import com.linku.server.BaseException;
import com.linku.server.common.utils.DateUtils;
import com.linku.server.game.play.domain.Play;
import com.linku.server.game.play.service.PlayService;
import com.linku.server.game.wheel.domain.BigWheel;
import com.linku.server.game.wheel.domain.BigWheelItem;
import com.linku.server.ticket.domain.Pticket;
import com.linku.server.ticket.service.PticketService;
import com.linku.server.user.domain.User;
import com.linku.server.user.service.UserService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BigWheelPrizeService {
    private final BigWheelService service;
    private final UserService userService;
    private final PlayService playService;
    private final PticketService pticketService;

    @Autowired
    public BigWheelPrizeService(BigWheelService service, UserService userService,
                                PlayService playService, PticketService pticketService) {

        this.service = service;
        this.userService = userService;
        this.playService = playService;
        this.pticketService = pticketService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BigWheelPrize prize(String id, String userId){
        BigWheel t = service.get(id);
        User u = userService.get(userId);
        if(!StringUtils.equals(t.getShopId(), u.getShopId())){
            throw new BaseException("大转盘游戏不存在");
        }

        if(t.getState() != BigWheel.State.OPEN){
            throw new BaseException("游戏未开启");
        }

        List<BigWheelItem> items = service.getItems(id);
        List<Integer> ratios = items.stream().sorted(Comparator.comparingInt(BigWheelItem::getIndex))
                .map(BigWheelItem::getRatio).collect(Collectors.toList());

        int prizeIndex = getRand(ratios);

        BigWheelItem prizeItem = null;
        for(BigWheelItem item: items){
            if(item.getIndex() == prizeIndex){
                prizeItem = item;
                break;
            }
        }

        if(prizeItem == null){
            throw new BaseException("抽奖失败");
        }

        Play play = savePlay(t, prizeItem, u);
        int angle = prizeItem.getFromCursor() +
                ((prizeItem.getToCursor() - prizeItem.getFromCursor()) / 2) +
                RandomUtils.nextInt(0, 4);

        return new BigWheelPrize(angle, play);
    }

    private int getRand(List<Integer> ratios){
        int result = -1;
        int sum = ratios.stream().mapToInt(e -> e).sum();
        for(int i = 0; i< ratios.size(); i++){
            int ratio = RandomUtils.nextInt(1, sum);
            if(ratio < ratios.get(i)){
                result = i;
                break;
            }else{
                sum = sum - ratios.get(i);
            }
        }

        return result;
    }

    private Play savePlay(BigWheel t, BigWheelItem item, User user){
        Play play = new Play();

        play.setGameId(t.getId());
        play.setGameName(t.getName());
        play.setName(StringUtils.isBlank(user.getName())? user.getNickname(): user.getName());
        play.setHeadImg(user.getHeadImg());
        play.setPrize(item.getTitle());
        play.setMoney(item.getMoney());
        play.setUserId(user.getId());
        if(item.getMoney() > 0){
            Pticket pticket = savePticket(t.getPticketDays(), item, user);
            play.setPticketId(pticket.getId());
        }else{
            play.setPticketId("-1");
        }

        return playService.save(play);
    }

    private Pticket savePticket(int days, BigWheelItem item, User user){
        Pticket t = new Pticket();

        t.setUserId(user.getId());
        t.setMoney(item.getMoney());
        t.setShopId(user.getShopId());
        t.setFromDate(new Date());
        t.setToDate(DateUtils.plusDays(new Date(), days));
        t.setName("现金券");

        return pticketService.save(t);
    }

    public static class BigWheelPrize{
        private final Integer angle;
        private final Play play;

        public BigWheelPrize(Integer angle, Play play) {
            this.angle = angle;
            this.play = play;
        }

        public Integer getAngle() {
            return angle;
        }

        public Play getPlay() {
            return play;
        }
    }

}
