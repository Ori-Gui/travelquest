package com.ssafy.travelquest.domain.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.travelquest.domain.user.dto.UserClearDungeonResponse;
import com.ssafy.travelquest.domain.user.entity.User;

@Mapper
public interface UserRepository {
    User findById(Long no);
    User findByUserId(String userId);
    int insert(User user);
    void update(User user);
    void delete(int no);
    List<User> findAll();
    User findByUserIdAndNameAndEmail(@Param("userId") String userId,
            @Param("userName") String userName,
            @Param("email") String email);

    List<User> findAllByIds(List<Long> allUserIds);
    List<UserClearDungeonResponse> selectClearedDungeonsByUserId(@Param("userId") Long userId);
}