package com.tuoshecx.server.game.play.service;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.common.id.IdGenerators;
import com.tuoshecx.server.game.play.dao.PlayDao;
import com.tuoshecx.server.game.play.domain.Play;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class PlayService {
    private final PlayDao dao;

    @Autowired
    public PlayService(PlayDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Play save(Play t){
        t.setId(IdGenerators.uuid());
        dao.insert(t);
        return dao.findOne(t.getId());
    }

    public Play get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("游戏参与信息不存在");
        }
    }

    public long count(String gameId, String prize){
        return dao.count(gameId, prize);
    }

    public List<Play> find(String gameId, String prize, int offset, int limit){
        return dao.find(gameId, prize, offset, limit);
    }

    public Map<String, Integer> groupPrize(String gameId) {
        return dao.groupPrize(gameId);
    }
}
