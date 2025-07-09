package com.example.demo;

import com.example.demo.payload.CricketResponse;
import com.example.demo.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private ChatService chatService;

	@Test
	void contextLoads() throws IOException {
		CricketResponse cricketResponse= chatService.generateCricketResponse("Who is sachin ?");
		CricketResponse cricketResponse1= chatService.generateCricketResponse("Who is sarah palin ?");
		System.out.println(cricketResponse.getContent());
		System.out.println(cricketResponse1);
	}

	@Test
	void testTemplate() throws IOException{
		String s=chatService.loadPromptTemplate("prompts/cricket_bot.txt");
		String prompt=chatService.putValuesInPromptTemplate(s, Map.of("inputText", "what is cricket"));
		System.out.println(prompt);
	}

	@Test
	void testTemplate1() throws IOException{
		String s=chatService.loadPromptTemplate("prompts/image_bot.txt");
		String promptString = chatService.putValuesInPromptTemplate(s, Map.of(
				"numberOfImages", 2+"",
				"description", "A cricket bat",
				"size", "500*500"
		));
		System.out.println(promptString);
	}

}
