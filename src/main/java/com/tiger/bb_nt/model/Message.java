package com.tiger.bb_nt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Message {
    
    @Id
    private String id;
    private String author;
    private String opponent;
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
    private LocalDateTime date;

    public Message() {
    }

    public Message(String author, String opponent, String text) {
        this.author = author;
        this.opponent = opponent;
        this.text = text;
        date=LocalDateTime.now();
    }
}
