package com.t3h.buoi12.repository;

import com.t3h.buoi12.model.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    //select * from user_profile where email = :email or username = "username

    @Query(nativeQuery = true,
            value = "select id, username, avatar, password, email, created_time, sex" +
                    " from user_profile where email = :email or username = :test")
    List<UserProfile> findUserProfileByUserNameOrEmail(
            @Param("test") String user,
            @Param("email") String email
    );

    @Query(nativeQuery = true, value = "select * from user_profile")
    List<UserProfile> findAll();

    @Query(nativeQuery = true, value = "SELECT * FROM user_profile where id = :userId")
    UserProfile findByUserId(
            @Param("userId") int userId
    );


    @Query(nativeQuery = true, value = "SELECT * FROM user_profile where username = :username")
    List<UserProfile> findByUserUsername(
            @Param("username") String username
    );

    @Query(nativeQuery = true,
            value = "select * from user_profile " +
                    "where id not in " +
                    "(select user_profile.id from user_profile " +
                    "join " +
                    "(select friend.sender_id, friend.receiver_id from " +
                    "friend " +
                    "where (friend.sender_id = :userId or friend.receiver_id = :userId) " +
                    ") as temp " +
                    "on user_profile.id = temp.sender_id or user_profile.id = temp.receiver_id) " +
                    "and (:isNUll = true or (user_profile.username like :content or user_profile.fullname like :content)) " +
                    "and user_profile.id <> :userId")
    List<UserProfile> findUnfriend(
            @Param("userId") int userId,
            @Param("isNUll") boolean isNUll,
            @Param("content") String content
    );
}
