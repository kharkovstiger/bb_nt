package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DefaultMessageRepository implements MessageRepository {
    
    private final CrudMessageRepository crudMessageRepository;

    @Autowired
    public DefaultMessageRepository(CrudMessageRepository crudMessageRepository) {
        this.crudMessageRepository = crudMessageRepository;
    }

    @Override
    public void save(Message message) {
        crudMessageRepository.save(message);
    }

    @Override
    public List<Message> findUpdates(String author, String opponent, String lastId) {
        return crudMessageRepository.findUpdates(author, opponent, lastId);
    }

    @Override
    public List<Message> findUpdates(String author, LocalDateTime now) {
        return crudMessageRepository.findUpdates(author, now);
    }

    @Override
    public Page<Message> findHistory(String author, String opponent, String lastId, Pageable pageable) {
        return crudMessageRepository.findHistory(author, opponent, lastId, pageable);
    }
}
