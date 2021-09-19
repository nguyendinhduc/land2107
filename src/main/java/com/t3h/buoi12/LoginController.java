package com.t3h.buoi12;

import com.t3h.buoi12.model.BaseResponse;
import com.t3h.buoi12.model.LoginRequest;
import com.t3h.buoi12.model.UserLogin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {
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

    @PostMapping("/user")
    public BaseResponse login(@RequestBody LoginRequest request) {
        List<UserLogin> userLogins = getAllUserFromDB();
        for (UserLogin userLogin : userLogins) {
            if (userLogin.getUsername().equals(request.getUsername()) &&
                    userLogin.getPassword().equals(request.getPassword())) {
                //login thanh cong
                BaseResponse res = new BaseResponse();
                res.setCode(BaseResponse.SUCCESS);
                res.setMessage("Login success");
                return res;
            }
        }
        //login that bai
        BaseResponse res = new BaseResponse();
        res.setCode(BaseResponse.FAIL);
        res.setMessage("Login fail");
        return res;
    }

    @GetMapping("/ahihi")
    public String ahihi() {
        return "Day la ahihi hahah";
    }
}
