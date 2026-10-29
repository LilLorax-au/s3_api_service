package org.example.s3_api_service.services;


import org.example.s3_api_service.exceptions.AuthException;
import org.example.s3_api_service.models.entitys.User;
import org.example.s3_api_service.repositories.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service is intended to handle authentication
 * @author Isaac Brown
 */
@Service
public class AuthService {

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String getPrefix(String apiKey) throws AuthException{
        User user = this.userRepository.findByApiKey(apiKey);

        if(user != null){
            return user.getPrefix();
        }
        else {
            throw new AuthException("Invalid API key");
        }
    }

    private boolean isKeyValid(String apiKey){
        User user = this.userRepository.findByApiKey(apiKey);

        return user != null;
    }
}
