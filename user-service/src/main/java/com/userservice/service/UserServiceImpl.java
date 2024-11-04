package com.userservice.service;

import com.userservice.dto.UserDto;
import com.userservice.entity.UserEntity;
import com.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(userDto.getPwd());
        userRepository.save(userEntity);

        UserDto resultUserDto = mapper.map(userEntity,UserDto.class);
        return resultUserDto;
    }
}
