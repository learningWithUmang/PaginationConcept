package dev.umang.productservice09april.commons;

import dev.umang.productservice09april.dtos.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationCommons {
    private RestTemplate restTemplate;

    public AuthenticationCommons(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public UserDTO validateToken(String token){
        //What is the type of request am i going to make?
        ResponseEntity<UserDTO> userDTOResponseEntity = restTemplate.postForEntity(
                "http://localhost:8181/users/validate/" + token,
                null,
                UserDTO.class
        );

        return userDTOResponseEntity.getBody();
    }

}
