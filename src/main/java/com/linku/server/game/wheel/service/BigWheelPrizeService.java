package com.linku.server.game.wheel.service;

import com.linku.server.game.play.domain.Play;
import com.linku.server.game.play.service.PlayService;
import com.linku.server.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BigWheelPrizeService {
    private final BigWheelService service;
    private final UserService userService;
    private final PlayService playService;

    @Autowired
    public BigWheelPrizeService(BigWheelService service,
                                UserService userService,
                                PlayService playService) {

        this.service = service;
        this.userService = userService;
        this.playService = playService;
    }

    public Play prize(String id, String userId){
        return null;
    }
}
