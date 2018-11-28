package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository {
    void save(Message message);

    List<Message> findUpdates(String author, String opponent, String lastId);

    List<Message> findUpdates(String author, LocalDateTime now);

    Page<Message> findHistory(String author, String opponent, String lastId, Pageable pageable);
}
