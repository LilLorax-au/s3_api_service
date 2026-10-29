package org.example.s3_api_service.models.entitys;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    private Integer id;
    private String name;
    private String email;
    private String role;
    private String apiKey;
    private String prefix;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getPrefix() {
        return prefix;
    }
}
