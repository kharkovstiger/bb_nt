package com.tiger.bb_nt.model.bb;

import com.tiger.bb_nt.util.SkillInitializer;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document
public class Player extends DefaultPlayer {
    
    private Map<Skill, Integer> skills;

    public Player() {
        super();
        skills= SkillInitializer.initialize();
    }
}
