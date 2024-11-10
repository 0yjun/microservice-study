package com.userservice.controller;

import com.userservice.dto.Requestuser;
import com.userservice.dto.ResponseUser;
import com.userservice.dto.UserDto;
import com.userservice.entity.UserEntity;
import com.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@Slf4j
public class UserController {
    private final UserService userService;
    private final Environment env;

    @Autowired
    public UserController(
            UserService userService
            , Environment env
    ) {
        this.userService = userService;
        this.env = env;
    }

    @GetMapping("/health-check")
    public String status(){
        return  String.format(
                "this port is "+ env.getProperty("local.server.port")
                        + "\n this server port is "+ env.getProperty("server.port")
                        + "\n this secret is "+ env.getProperty("token.secret")
                        + "\n this expiration_time is "+ env.getProperty("token.expiration_time")
        );
    };

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v->{
            result.add(new ModelMapper().map(v,ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUsers(@PathVariable("userId")String userId) throws UsernameNotFoundException {
        UserDto userDto = userService.getUserByUserId(userId);
        List<ResponseUser> result = new ArrayList<>();
        ResponseUser responseUser = new ModelMapper().map(userDto,ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @GetMapping("/users/userId")
    public ResponseEntity<ResponseUser> getUser(@RequestBody Requestuser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        userService.createUser(userDto);

        ResponseUser responseuser = mapper.map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseuser);
    }




    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody Requestuser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        userService.createUser(userDto);

        ResponseUser responseuser = mapper.map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseuser);
    }
}
