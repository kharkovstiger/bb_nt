package com.tiger.bb_nt.service;

import com.tiger.bb_nt.dao.PlayerRepository;
import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.model.bb.Player;
import com.tiger.bb_nt.model.util.CurrentSession;
import com.tiger.bb_nt.security.AuthorizedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultPlayerService implements PlayerService {

    private final PlayerRepository playerRepository;
    private final BBAPIService bbapiService;
    private final UserService userService;

    @Autowired
    public DefaultPlayerService(PlayerRepository playerRepository, BBAPIService bbapiService, UserService userService) {
        this.playerRepository = playerRepository;
        this.bbapiService = bbapiService;
        this.userService = userService;
    }

    @Override
    public boolean addPlayer(String playerId) {
        return false;
    }

    @Override
    public List<Player> getTeamPlayers() {
        User currentUser=userService.getCurrentUser();
        return bbapiService.getPlayers(currentUser.getLogin(), currentUser.getCode());
    }

    @Override
    public List<Player> getTeamPlayersForCurrentCountry() {
        Country country=CurrentSession.getCountry();
        List<Player> players=getTeamPlayers();
        return players.stream().filter(p -> p.getNationality().equals(country.name())).collect(Collectors.toList());
    }
}
