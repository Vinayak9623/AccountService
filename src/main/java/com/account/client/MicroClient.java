package com.account.client;

import com.account.common.ApiResponse;
import com.account.dto.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class MicroClient {

    @Autowired
    private WebClient webClient;

    public UserRequest getUserById(UUID id){
        String url="http://localhost:8080/ecom/user/"+id;
        ApiResponse<UserRequest> response = webClient.get().uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, respone ->
                        Mono.error(new RuntimeException("User not found")))
                .onStatus(HttpStatusCode::is5xxServerError, respons ->
                        Mono.error(new RuntimeException("User service error")))
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserRequest>>() {
                }).block();
        return response!=null?response.getResult():null;
    }
}
