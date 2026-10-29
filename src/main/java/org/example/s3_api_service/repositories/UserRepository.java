package org.example.s3_api_service.repositories;

import jakarta.annotation.Nullable;
import org.example.s3_api_service.models.entitys.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Nullable
    User findByApiKey(String apiKey);
}
