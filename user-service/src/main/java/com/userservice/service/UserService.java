package com.userservice.service;

import com.userservice.dto.UserDto;
import com.userservice.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService{
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
