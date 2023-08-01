package com.vc.kanbanProject.domain;

import com.google.api.client.util.Key;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class User {
    private String email;
    private String profile_pic;
    private String username;
}
