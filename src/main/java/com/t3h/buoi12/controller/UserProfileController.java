package com.t3h.buoi12.controller;

import com.t3h.buoi12.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileController {
    @Autowired
    private UserProfileService service;
    @GetMapping
    public Object getAllUser(){
        return service.getAllUser();
    }

    @GetMapping("/friend/{userId}")
    Object getAllFriend(
            @PathVariable("userId") int userId
    ){
        return service.getAllFriend(userId);
    }
}
