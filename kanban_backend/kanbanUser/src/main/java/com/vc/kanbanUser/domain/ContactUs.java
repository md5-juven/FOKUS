package com.vc.kanbanUser.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.PrimitiveIterator;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Document
public class ContactUs {
    @Id
    private String contact_id;
    private String name;
    private String contact;
    private String email;
    private String message;

    public ContactUs(String contact_id, String name, String email, String message) {
        this.contact_id = contact_id;
        this.name = name;
        this.email = email;
        this.message = message;
    }
}
