package com.tiger.bb_nt.service;

import com.tiger.bb_nt.dao.PlayerRepository;
import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.model.bb.Player;
import com.tiger.bb_nt.model.bb.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        User currentUser=userService.getCurrentUser();
        Player player=bbapiService.getPlayer(playerId, currentUser.getLogin(), currentUser.getCode());
        Player existedPlayer=playerRepository.findOne(playerId);
        if (existedPlayer != null){
            player.setLastUpdate(LocalDate.now());
            List<Skill> ups=getSkillDifference(player, existedPlayer);
            if (!ups.isEmpty()) {
                LocalDate date=getLastFridayDate();
                player.setLastUp(date);
                Map<LocalDate, List<Skill>> map=new HashMap<>();
                map.put(date, ups);
                player.addUps(map);
            }
        }
        Player savedPlayer=playerRepository.save(player);
        return savedPlayer != null;
    }

    private LocalDate getLastFridayDate() {
        LocalDate now=LocalDate.now();
        return now.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY));
    }

    private List<Skill> getSkillDifference(Player player, Player existedPlayer) {
        List<Skill> result=new ArrayList<>();
        player.getSkills().forEach((s, v) -> {
            if (!player.getSkills().get(s).equals(existedPlayer.getSkills().get(s)))
                result.add(s);
        });
        return result;
    }

    @Override
    public List<Player> getTeamPlayers() {
        User currentUser=userService.getCurrentUser();
        return bbapiService.getPlayers(currentUser.getLogin(), currentUser.getCode());
    }

    @Override
    public List<Player> getTeamPlayersForCurrentCountry(Country country) {
        List<Player> players=getTeamPlayers();
        return players.stream().filter(p -> p.getNationality().equals(country.name())).collect(Collectors.toList());
    }

    @Override
    public Player addBio(String playerId, String bio) {
        Player player=playerRepository.findOne(playerId);
        if (player!=null){
            player.setBio(bio);
            playerRepository.save(player);
        }
        return player;
    }
}
