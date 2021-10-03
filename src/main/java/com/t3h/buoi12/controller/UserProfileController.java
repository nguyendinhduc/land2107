package com.t3h.buoi12.controller;

import com.t3h.buoi12.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileController {
    @Autowired
    private UserProfileService service;
    @GetMapping
    public Object getAllUser(){
        return service.getAllUser();
    }

    @GetMapping("/api/friend")
    public Object getAllFriend(){
        return service.getAllFriend();
    }

    @GetMapping("/api/user/unfriends")
    public Object getAllUnFriend(
            @RequestParam(value = "content", required = false) String content
    ){
        return service.getAllUnFriend(content);
    }

    @PostMapping("/api/friend")
    public Object makeFriend(
            @RequestParam("friendId") int friendId
    ){
        return service.makeFriend(friendId);
    }

    @PutMapping("/api/friend")
    public Object updateStatus(
            @RequestParam("friendId") int friendId,
            @RequestParam("status") String status
    ){
        return service.updateStatus(friendId, status);
    }

}
