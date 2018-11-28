package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CrudMessageRepository extends MongoRepository<Message, String> {

    @Override
    <S extends Message> S save(S entity);

    @Override
    Optional<Message> findById(String id);

    @Override
    void deleteById(String id);

    @Query(value = "{'$or':[{'author':?0, 'opponent':?1}, {'author':?1, 'opponent':?0}], 'id': {'$gt':?2}}")
    List<Message> findUpdates(String author, String opponent, String lastId);

    @Query(value = "{'$or':[{'author':?0}, {'opponent':?0}], 'date': {'$gt':?1}}")
    List<Message> findUpdates(String author, LocalDateTime now);

//    @Query(value = "{'$or':[{'author':?0, 'opponent':?1}, {'author':?1, 'opponent':?0}], 'id': {'$lt':?2}}")
//    List<Message> findHistory(String author, String opponent, String lastId);

    @Query(value = "{'$or':[{'author':?0, 'opponent':?1}, {'author':?1, 'opponent':?0}], 'id': {'$lt':?2}}")
    Page<Message> findHistory(String author, String opponent, String lastId, Pageable pageable);
}
