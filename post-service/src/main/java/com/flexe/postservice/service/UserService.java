package com.flexe.postservice.service;

import com.flexe.postservice.entity.user.UserDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${USER_SERVICE_URL}")
    private String USER_SERVICE_URL;

    public UserDisplay findUser(String userId){
        WebClient client = webClientBuilder.baseUrl(USER_SERVICE_URL).build();
        return client.get()
                .uri("/user/display/find/" + userId)
                .retrieve()
                .bodyToMono(UserDisplay.class)
                .block();
    }
}
