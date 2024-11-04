package com.userservice.controller;

import com.userservice.dto.Requestuser;
import com.userservice.dto.Responseuser;
import com.userservice.dto.UserDto;
import com.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user-service")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users")
    public ResponseEntity<Responseuser> createUser(@RequestBody Requestuser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        userService.createUser(userDto);

        Responseuser responseuser = mapper.map(userDto, Responseuser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseuser);
    }

}
