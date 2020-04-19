package com.tiger.bb_nt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Document
@EqualsAndHashCode(of = "id")
public class Post implements Serializable {

    @Id
    private String id;
    private Boolean u21;
    private String title;
    private String text;
    private String author;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate lastUpdate;

    @JsonBackReference
    @DBRef
    private List<Post> comments;

    private Country country;
}
