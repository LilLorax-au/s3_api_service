package org.example.s3_api_service.models.Entitys;


import jakarta.persistence.Entity;

@Entity
public class User {

    private String name;
    private String email;
    private String role;
    private String api_key;
    private String prefix;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getPrefix() {
        return prefix;
    }
}
