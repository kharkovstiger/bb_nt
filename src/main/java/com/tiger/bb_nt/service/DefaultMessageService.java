package com.tiger.bb_nt.service;

import com.tiger.bb_nt.dao.MessageRepository;
import com.tiger.bb_nt.model.Message;
import com.tiger.bb_nt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultMessageService implements MessageService{
    
    private final MessageRepository messageRepository;
    private final UserService userService;

    @Autowired
    public DefaultMessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Override
    public void sendMessage(String opponent, String text) {
        String author=userService.getCurrentUser().getId();
        Message message=new Message(author, opponent, text);
        messageRepository.save(message);
        //TODO notify via telephone
        User userToNotify=userService.getUser(opponent);
    }

    @Override
    public List<Message> getUpdates(String opponent, String lastId) {
        String author=userService.getCurrentUser().getId();
        List<Message> messages=messageRepository.findUpdates(author, opponent, lastId);
        messages.sort(Comparator.comparing(Message::getDate));
        return messages;
    }

    @Override
    public List<String> getUpdates() {
        String author=userService.getCurrentUser().getId();
        List<Message> messages=messageRepository.findUpdates(author, LocalDateTime.now());
        List<String> opponents=messages.stream().map(m -> m.getAuthor().equals(author)?m.getOpponent():m.getAuthor())
                .distinct().collect(Collectors.toList());
        return opponents;
    }

    @Override
    public List<Message> getHistory(String opponent, String lastId, Integer page) {
        String author=userService.getCurrentUser().getId();
        PageRequest pageRequest=PageRequest.of(page, 10, Sort.by("date"));
        return messageRepository.findHistory(author, opponent, lastId, pageRequest).getContent();
    }
}
