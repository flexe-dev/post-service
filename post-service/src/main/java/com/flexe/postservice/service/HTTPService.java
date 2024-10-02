package com.flexe.postservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HTTPService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${POST_SERVICE_URL}")
    private String POST_SERVICE_URL;

    @Value("${USER_SERVICE_URL}")
    private String USER_SERVICE_URL;

    @Value("${INTERACTION_SERVICE_URL}")
    private String INTERACTION_SERVICE_URL;

    @Value("${FEED_SERVICE_URL}")
    private String FEED_SERVICE_URL;

    public enum TargetServer{
        POST,
        USER,
        INTERACTION,
        FEED
    }

    public enum TargetController{
        USER,
        POST,
        COMMENT,
        MEDIA,
        TEXT,
        NODE,
        PREFERENCE,
        FEED
    }

    public String getServerURL(TargetServer server){
        return switch(server){
            case POST -> POST_SERVICE_URL;
            case USER -> USER_SERVICE_URL;
            case INTERACTION -> INTERACTION_SERVICE_URL;
            case FEED -> FEED_SERVICE_URL;
        };
    }

    public WebClient generateWebClient(TargetServer server){
        return webClientBuilder.baseUrl(getServerURL(server)).build();
    }

    public String getUriPrefix(TargetController server){
        return switch(server){
            case POST -> "/post";
            case USER -> "/user";
            case NODE -> "/node";
            case COMMENT -> "/comment";
            case MEDIA -> "/media";
            case TEXT -> "/text";
            case FEED -> "/feed";
            case PREFERENCE -> "/preference";
        };
    }

    public <T>ResponseEntity<T> get(WebClient client, String uri, Class<T> responseType){
        return client.get()
                .uri(uri)
                .retrieve()
                .toEntity(responseType)
                .block();
    }

    public <T>ResponseEntity<T> post(WebClient client, String uri, Object body, Class<T> responseType){
        return client.post()
                .uri(uri)
                .bodyValue(body)
                .retrieve()
                .toEntity(responseType)
                .block();
    }

    public <T>ResponseEntity<T> post(WebClient client, String uri, Class<T> responseType){
        return client.post()
                .uri(uri)
                .retrieve()
                .toEntity(responseType)
                .block();
    }

    public <T>ResponseEntity<T> put(WebClient client, String uri, Object body, Class<T> responseType){
        return client.put()
                .uri(uri)
                .bodyValue(body)
                .retrieve()
                .toEntity(responseType)
                .block();
    }

    public <T>ResponseEntity<T> delete(WebClient client, String uri, Class<T> responseType){
        return client.delete()
                .uri(uri)
                .retrieve()
                .toEntity(responseType)
                .block();
    }

    public <T>ResponseEntity<T> delete(WebClient client, String uri, Object body, Class<T> responseType){
        return client.method(HttpMethod.DELETE)
                .uri(uri).bodyValue(body)
                .retrieve()
                .toEntity(responseType)
                .block();
    }



}
