package com.tiger.bb_nt.model.bb;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(of = "id")
public class DefaultPlayer implements Serializable {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String nationality;
    private String team;
    private String manager;
    private String bio;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate lastUpdate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate lastUp;
    
    private Map<String, List<Skill>> lastUps;
    
    private Integer gameShape;
    private Integer potential;
    private Integer salary;
    private Integer age;
    private Integer height;
    private Integer dmi;

    public void addUps(Map<String, List<Skill>> map) {
        if (lastUps==null)
            lastUps=new HashMap<>();
        lastUps.putAll(map);
    }
    
    private boolean inDB;
}
