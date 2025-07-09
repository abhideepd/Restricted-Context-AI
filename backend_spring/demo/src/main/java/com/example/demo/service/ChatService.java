package com.example.demo.service;

import com.example.demo.payload.CricketResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

import javax.imageio.ImageReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChatService {

    private ChatModel chatModel;
    private ImageModel imageModel;

    public ChatService(ChatModel chatModel, ImageModel imageModel){
        this.chatModel=chatModel;
        this.imageModel = imageModel;
    }

    public String generateResponse(String inputText){
        String response=chatModel.call(inputText);
        return response;
    }

    public Flux<String> streamResponse(String inputText){
        Flux<String> response=chatModel.stream(inputText);
        return response;
    }

    public CricketResponse generateCricketResponse(String inputText) throws IOException {
        /*String promptString="As a cricket expert. Answer this question: "+inputText+". "+
                "If the above question is not related to cricket, generate one joke saying this question is out of context. Give a JSON response that must contain 'content' as a key and your response as value";*/
        String promptString = loadPromptTemplate("prompts/cricket_bot.txt");
        String prompt = putValuesInPromptTemplate(promptString, Map.of("inputText", inputText));;
        ChatResponse cricketResponse = chatModel.call(new Prompt(prompt));
        String responseString=cricketResponse.getResult().getOutput().getText();
        ObjectMapper mapper=new ObjectMapper();
        CricketResponse cricketResponse1=mapper.readValue(responseString, CricketResponse.class);
        System.out.println(cricketResponse1);
        return  cricketResponse1;
    }

    public String loadPromptTemplate(String fileName) throws IOException {
        Path filePath= new ClassPathResource(fileName).getFile().toPath();
        return  Files.readString(filePath);
    }

    public String putValuesInPromptTemplate(String template, Map<String, String> variables){
        for(Map.Entry<String, String> entry:variables.entrySet()){
            template=template.replace("{"+entry.getKey()+"}", entry.getValue());
        }
        return template;
    }

    public List<String> generateImages(String imageDesc, int size, int numbers) throws IOException {
        String template = this.loadPromptTemplate("prompts/image_bot.txt");
        String promptString = this.putValuesInPromptTemplate(template, Map.of(
           "description", imageDesc
        ));
        ImageResponse imageResponse=imageModel.call(new ImagePrompt(promptString,
                OpenAiImageOptions.builder().model("dall-e-2").N(numbers).height(size).width(size).build()));
        List<String> imageUrls=imageResponse.getResults().stream().map(generation->generation.getOutput().getUrl()).collect(Collectors.toList());
        return imageUrls;
    }
}
