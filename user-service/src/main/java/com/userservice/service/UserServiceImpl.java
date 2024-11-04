package com.userservice.service;

import com.userservice.dto.ResponseOrder;
import com.userservice.dto.UserDto;
import com.userservice.entity.UserEntity;
import com.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        userRepository.save(userEntity);

        UserDto resultUserDto = mapper.map(userEntity,UserDto.class);
        return resultUserDto;
    }

    @Override
    public UserDto getUserByUserId(String userId)  {
        UserEntity findUserEntity =  userRepository
                .findByUserId(userId)
                .orElseThrow(NoSuchElementException::new);
        UserDto userDto = new ModelMapper().map(findUserEntity, UserDto.class);
        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);
        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }
}
