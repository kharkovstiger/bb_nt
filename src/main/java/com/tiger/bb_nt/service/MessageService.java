package com.tiger.bb_nt.service;

import com.tiger.bb_nt.model.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(String opponent, String text);

    List<Message> getUpdates(String opponent, String lastId);

    List<String> getUpdates();

    List<Message> getHistory(String opponent, String lastId, Integer page);
}
