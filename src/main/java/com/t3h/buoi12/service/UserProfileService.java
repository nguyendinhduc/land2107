package com.t3h.buoi12.service;

import com.t3h.buoi12.model.BaseResponse;
import com.t3h.buoi12.model.LoginRequest;
import com.t3h.buoi12.model.RegisterRequest;
import com.t3h.buoi12.model.UserLogin;
import com.t3h.buoi12.model.entity.UserProfile;
import com.t3h.buoi12.repository.FriendRepository;
import com.t3h.buoi12.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private FriendRepository friendRepository;

    public Object register(RegisterRequest request){
        //1. kiem tra username/email co trong db chua
        //neu co roi: register khong thanh cong
        //ne khong thi luu vao co so du lieu
        //thanh cong
        List<UserProfile> userProfiles = userProfileRepository.findUserProfileByUserNameOrEmail(
                request.getUsername(), request.getEmail()
        );
        if (userProfiles.size() > 0 ){
            return  new BaseResponse(BaseResponse.FAIL, "username or email is existed");
        }else {
            UserProfile userProfile = new UserProfile();
            userProfile.setSex(request.getSex());
            userProfile.setUsername(request.getUsername());
            String newPassword = new BCryptPasswordEncoder().encode(request.getPassword());
            userProfile.setPassword(newPassword);
            userProfile.setEmail(request.getEmail());
            userProfile.setAvatar(request.getAvatar());
            userProfile.setFullname(request.getFullname());
            userProfile.setBirthday(request.getBirthday());
            //save into database
            userProfile = userProfileRepository.save(userProfile);
            return  new BaseResponse(BaseResponse.SUCCESS, "Register success", userProfile);
        }
    }

    public Object getAllUser(){
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return  new BaseResponse(BaseResponse.SUCCESS, "Register success", userProfiles);
    }

    public Object getUser(int userId) {
        UserProfile userProfile =  userProfileRepository.findByUserId(userId);
       return userProfile;
    }

    public BaseResponse login(LoginRequest request) {
        List<UserProfile> userLogins = userProfileRepository.findByUserUsername(request.getUsername());
        BCryptPasswordEncoder endcod = new BCryptPasswordEncoder();
        for (UserProfile userLogin : userLogins) {
            if (userLogin.getUsername().equals(request.getUsername())) {
                if ( endcod.matches(request.getPassword(), userLogin.getPassword())){
                    //login thanh cong
                    BaseResponse res = new BaseResponse();
                    res.setCode(BaseResponse.SUCCESS);
                    res.setMessage("Login success");
                    return res;
                }
            }
        }
        //login that bai
        BaseResponse res = new BaseResponse();
        res.setCode(BaseResponse.FAIL);
        res.setMessage("Login fail");
        return res;
    }

    public Object getAllFriend(int userId) {
        return friendRepository.findFriendByUserId(userId);
    }
}
