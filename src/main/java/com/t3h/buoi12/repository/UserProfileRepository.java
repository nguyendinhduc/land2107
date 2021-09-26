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
}
