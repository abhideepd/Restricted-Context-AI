package com.example.demo.controller;


import com.example.demo.payload.CricketResponse;
import com.example.demo.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class ChatController {

    private ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService=chatService;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> generateResponse(@RequestParam(value="inputText", required=true)String inputText){
        String responseText=chatService.generateResponse(inputText);
        return ResponseEntity.ok(responseText);
    }

    @GetMapping("/stream")
    public Flux<String> streamResponse(String inputText){
        Flux<String> response=chatService.streamResponse(inputText);
        return response;
    }

//    @GetMapping("/cricketbot")
//    public ResponseEntity<CricketResponse> getCricketResponse(@RequestParam("inputText") String inputText) throws JsonProcessingException {
//        CricketResponse cricketResponse=chatService.generateCricketResponse(inputText);
//        return ResponseEntity.ok(cricketResponse);
//    }

    @GetMapping("/cricketbot")
    public CricketResponse getCricketResponse(@RequestParam("inputText") String inputText) throws IOException {
        CricketResponse cricketResponse=chatService.generateCricketResponse(inputText);
        return cricketResponse;
    }

    @GetMapping("/images")
    public List<String> generateImages(
            @RequestParam("imageDescription") String imageDesc,
            @RequestParam(value="size", required = false, defaultValue = "512") int size,
            @RequestParam(value="numberOfImages", required = false, defaultValue = "2") int numbers) throws IOException {

        return chatService.generateImages(imageDesc,size,numbers);
    }
}
