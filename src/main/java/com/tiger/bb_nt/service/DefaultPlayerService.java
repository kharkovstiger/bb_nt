package com.tiger.bb_nt.service;

import com.tiger.bb_nt.dao.PlayerRepository;
import com.tiger.bb_nt.model.bb.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultPlayerService implements PlayerService {

    private final PlayerRepository playerRepository;
    private final BBAPIService bbapiService;

    @Autowired
    public DefaultPlayerService(PlayerRepository playerRepository, BBAPIService bbapiService) {
        this.playerRepository = playerRepository;
        this.bbapiService = bbapiService;
    }

    @Override
    public boolean addPlayer(String playerId) {
        return false;
    }
}
