package com.t3h.buoi12.repository;

import com.t3h.buoi12.model.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
    @Query(nativeQuery = true,
    value = "select user_profile.id, user_profile.username, user_profile.email, user_profile.avatar from user_profile " +
            "join " +
            "( " +
            "select friend.sender_id,  friend.receiver_id from  " +
            "friend " +
            "where (friend.sender_id = :userId or friend.receiver_id = :userId) and (friend.status = 'accepted') " +
            ") as temp " +
            "on user_profile.id = temp.sender_id or user_profile.id = temp.receiver_id " +
            "where user_profile.id <> :userId")
    List<Friend> findFriendByUserId(
            @Param("userId") int userId
    );
}
