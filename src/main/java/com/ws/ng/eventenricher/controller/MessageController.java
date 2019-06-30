package com.ws.ng.eventenricher.controller;

import com.ws.ng.eventenricher.model.Message;
import com.ws.ng.eventenricher.service.MessageService;
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
