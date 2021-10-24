package com.t3h.buoi12.controller;

import com.t3h.buoi12.model.BaseResponse;
import com.t3h.buoi12.model.LoginRequest;
import com.t3h.buoi12.model.RegisterRequest;
import com.t3h.buoi12.model.UserLogin;
import com.t3h.buoi12.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {
//    Autowired: lay cac doi duoi Bean(@Service, @Component, @Repository)
    @Autowired
    private UserProfileService service;

    // link:  {baselink}/{endpoint}
    @GetMapping("/user/all")
    public List<UserLogin> getAllUser() {
        return getAllUserFromDB();
    }

    private List<UserLogin> getAllUserFromDB() {
        List<UserLogin> userLogins = new ArrayList<>();
        userLogins.add(new UserLogin("test1@gmail.com", "12345"));
        userLogins.add(new UserLogin("test2@gmail.com", "12345"));
        userLogins.add(new UserLogin("test3@gmail.com", "12345"));
        userLogins.add(new UserLogin("test4@gmail.com", "12345"));
        userLogins.add(new UserLogin("test5@gmail.com", "12345"));
        userLogins.add(new UserLogin("test6@gmail.com", "12345"));
        return userLogins;
    }

    @GetMapping("/user")
    public UserLogin user() {
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername("ducnd.t3h@gmail.com");
        userLogin.setPassword("123456");
        return userLogin;
    }

    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/auth/register")
    public Object register(
            @RequestBody RegisterRequest request
    ){
        return service.register(request);
    }

    @GetMapping("/api/{userId}")
    public Object getUser(
            @PathVariable("userId") int userId
    ){
        return service.getUser(userId);
    }

    @GetMapping("/ahihi")
    public String ahihi() {
        return "Day la ahihi hahah";
    }
}
