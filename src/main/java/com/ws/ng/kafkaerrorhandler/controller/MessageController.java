package com.ws.ng.kafkaerrorhandler.controller;

import com.ws.ng.kafkaerrorhandler.model.Message;
import com.ws.ng.kafkaerrorhandler.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/message")
public class MessageController {


    @Autowired
    MessageService messageService;

    @GetMapping(path="", produces = "application/json")
    public String getMessage()
    {
        return "OK";
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public Message sendMessage(@RequestBody Message message){
        Message newMessage = new Message(message);
        messageService.sendMessage(newMessage);
        return newMessage;
    }

}
