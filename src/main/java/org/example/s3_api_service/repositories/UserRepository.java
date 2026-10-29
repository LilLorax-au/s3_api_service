package org.example.s3_api_service.repositories;

import org.example.s3_api_service.models.entitys.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
