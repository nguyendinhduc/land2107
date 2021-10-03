package com.t3h.buoi12.repository;

import com.t3h.buoi12.model.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

    @Query(nativeQuery = true,
            value = "select * from friend where sender_id = :userId or receiver_id = :userId")
    List<Friend> getAllFriend(
            @Param("userId") int userId
    );

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "update friend set status = :status where " +
                    "(sender_id = :userId and receiver_id = :friendId) " +
                    "or (sender_id = :friendId and receiver_id = :userId)")
    void updateStatusFriend(int userId, int friendId, String status);
}
