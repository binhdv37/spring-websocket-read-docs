package com.example.springwebsocket.controller;

import com.example.springwebsocket.exception.ApplicationError;
import com.example.springwebsocket.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class GreetingController {

    private SimpMessagingTemplate template;

    @Autowired
    public GreetingController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @RequestMapping(path="/greetings", method=POST)
    public void greet(String greeting) {
        String text = "[" + System.currentTimeMillis() + "]:" + greeting;
        this.template.convertAndSend("/topic/greetings", text); // send message to broker channel from any part of app
    }

    @MessageMapping("/greeting")
    public String handle(String greeting) {
        return "[" + System.currentTimeMillis() + ": " + greeting;
    }

    // handle exception
    @MessageExceptionHandler
    public ApplicationError handleException(MyException exception) {
        // ...
        ApplicationError appError = new ApplicationError();
        return appError;
    }

}
