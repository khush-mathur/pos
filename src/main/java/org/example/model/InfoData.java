package org.example.model;

import org.springframework.stereotype.Component;

@Component
public class InfoData {

//    private String message;

//    public InfoData() {
//        message = "Activity time: " + LocalDateTime.now().toString();
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }

    private static final long serialVersionUID = 1L;

    private String message;
    private String email;

    public InfoData() {
        message = "No message";
        email = "No email";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
