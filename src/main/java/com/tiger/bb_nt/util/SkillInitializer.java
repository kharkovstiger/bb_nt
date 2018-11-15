package com.tiger.bb_nt.util;

import com.tiger.bb_nt.model.bb.Skill;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class SkillInitializer {

    public static Map<Skill, Integer> initialize(){
        Map<Skill, Integer> skills=new TreeMap<>(Comparator.comparing(Enum::ordinal));
        for (Skill skill: Skill.values()){
            skills.put(skill, 1);
        }
        return skills;
    }
}
