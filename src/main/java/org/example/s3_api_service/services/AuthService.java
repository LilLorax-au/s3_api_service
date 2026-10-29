package org.example.s3_api_service.services;


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

    public boolean isKeyValid(String key){
        User user = this.userRepository.findByApiKey(key);

        return user != null;
    }
}
