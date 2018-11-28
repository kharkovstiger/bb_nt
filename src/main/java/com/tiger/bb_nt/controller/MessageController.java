package com.tiger.bb_nt.controller;

import com.tiger.bb_nt.model.Message;
import com.tiger.bb_nt.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = MessageController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class MessageController {
    static final String REST_URL = "api/message";
    
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/getHistory/{opponent}/{lastId}")
    public ResponseEntity getHistory(@PathVariable String opponent, @PathVariable String lastId, @RequestParam("page") Integer page){
        List<Message> messages=messageService.getHistory(opponent, lastId, page);
        return new ResponseEntity(messages, HttpStatus.OK);
    }
    
    @PostMapping(value = "/send/{opponent}")
    public ResponseEntity sendMessage(@PathVariable String opponent, @RequestBody String text){
        messageService.sendMessage(opponent, text);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @GetMapping(value = "/getUpdates/{opponent}/{lastId}")
    public ResponseEntity getUpdates(@PathVariable String opponent, @PathVariable String lastId){
        List<Message> messages=messageService.getUpdates(opponent, lastId);
        return new ResponseEntity(messages, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getUpdates")
    public ResponseEntity getUpdates(){
        List<String> opponents=messageService.getUpdates();
        return new ResponseEntity(opponents, HttpStatus.OK);
    }
}
