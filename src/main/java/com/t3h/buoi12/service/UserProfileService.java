package com.t3h.buoi12.service;

import com.t3h.buoi12.common.JWTUtil;
import com.t3h.buoi12.model.BaseResponse;
import com.t3h.buoi12.model.LoginRequest;
import com.t3h.buoi12.model.RegisterRequest;
import com.t3h.buoi12.model.entity.Friend;
import com.t3h.buoi12.model.entity.UserProfile;
import com.t3h.buoi12.model.response.LoginResponse;
import com.t3h.buoi12.repository.FriendRepository;
import com.t3h.buoi12.repository.FriendResponseRepository;
import com.t3h.buoi12.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private FriendResponseRepository friendResponseRepository;
    @Autowired
    private FriendRepository friendRepository;

    public Object register(RegisterRequest request) {
        //1. kiem tra username/email co trong db chua
        //neu co roi: register khong thanh cong
        //ne khong thi luu vao co so du lieu
        //thanh cong
        List<UserProfile> userProfiles = userProfileRepository.findUserProfileByUserNameOrEmail(
                request.getUsername(), request.getEmail()
        );
        if (userProfiles.size() > 0) {
            return new BaseResponse(BaseResponse.FAIL, "username or email is existed");
        } else {
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
            return new BaseResponse(BaseResponse.SUCCESS, "Register success", userProfile);
        }
    }

    public Object getAllUser() {
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return new BaseResponse(BaseResponse.SUCCESS, "Register success", userProfiles);
    }

    public Object getUser(int userId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        return userProfile;
    }

    public BaseResponse login(LoginRequest request) {
        List<UserProfile> userLogins = userProfileRepository.findByUserUsername(request.getUsername());
        BCryptPasswordEncoder endcod = new BCryptPasswordEncoder();
        for (UserProfile userLogin : userLogins) {
            if (userLogin.getUsername().equals(request.getUsername())) {
                if (endcod.matches(request.getPassword(), userLogin.getPassword())) {
                    //login thanh cong
                    BaseResponse res = new BaseResponse();
                    res.setCode(BaseResponse.SUCCESS);
                    //tra ve chuoi token (jwt) = endcode: userId, email, username
                    res.setMessage("Login success");
                    //sinh token
                    Map<String, Object> par = new HashMap<>();
                    par.put("email", userLogin.getEmail());
                    par.put("username", userLogin.getUsername());
                    par.put("birthday", userLogin.getBirthday());
                    par.put("fullname", userLogin.getFullname());
                    String token = JWTUtil.getJWT(userLogin.getId() + "", par);
                    LoginResponse re = new LoginResponse(userLogin.getId(), userLogin.getUsername(), token);
                    res.setData(re);
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

    public Object getAllFriend() {
        int userId = JWTUtil.getUserLogin();
        return friendResponseRepository.findFriendByUserId(userId);
    }

    public Object getAllUnFriend(String content) {
        int userId = JWTUtil.getUserLogin();
        String newContent;
        boolean isNUll;
        if (content == null || content.equals("")) {
            newContent = "%%";
            isNUll = true;
        } else {
            newContent = "%" + content + "%";
            isNUll = false;
        }
        List<UserProfile> unfriends = userProfileRepository.findUnfriend(
                userId,
                isNUll,
//                (content == null || content.equals(""))  ? null : "%" + content + "%"
                newContent
        );
        return unfriends;
    }

    public Object makeFriend(int friendId) {
        int userId = JWTUtil.getUserLogin();
        List<Friend> friends = friendRepository.getAllFriend(userId);
        boolean isFriend = false;
        for (Friend friend : friends) {
            if (friend.getSenderId() == userId) {
                if (friend.getReceiverId() == friendId) {
                    //vao trong nay
                    isFriend = true;
                    break;
                }
            }
            if (friend.getReceiverId() == userId) {
                if (friend.getSenderId() == friendId) {
                    //vao trong nay
                    isFriend = true;
                    break;
                }
            }
        }
        if (isFriend) {
            return new BaseResponse(400, "friendId " + friendId + " đã kết bạn rồi");
        }
        Friend friend = new Friend();
        friend.setSenderId(userId);
        friend.setReceiverId(friendId);
        friend.setStatus("pending");
        friend = friendRepository.save(friend);
        return new BaseResponse(BaseResponse.SUCCESS, "SUCCESS", friend);
    }

    public Object updateStatus(int friendId, String status) {
        //thieu các bước verify rất nhiều
        int userId = JWTUtil.getUserLogin();
        friendRepository.updateStatusFriend(userId, friendId, status);
        return new BaseResponse(BaseResponse.SUCCESS, "SUCCESS");
    }
}
