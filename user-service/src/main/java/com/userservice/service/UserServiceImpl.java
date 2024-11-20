package com.userservice.service;

import com.userservice.dto.ResponseOrder;
import com.userservice.dto.UserDto;
import com.userservice.entity.UserEntity;
import com.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;

    private final RestTemplate restTemplate;


    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            Environment env,
            RestTemplate restTemplate
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.restTemplate = restTemplate;
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        userRepository.save(userEntity);

        return mapper.map(userEntity,UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId)  {
        log.info("getUserByUserId {}",userId);
        UserEntity findUserEntity =  userRepository
                .findByUserId(userId)
                .orElseThrow(()->new NoSuchElementException("일치하는 값이 없습니다."));

        log.info(String.valueOf(findUserEntity.toString()));
        UserDto userDto = new ModelMapper().map(findUserEntity, UserDto.class);

        String orderUrl = String.format(Objects.requireNonNull(env.getProperty("order_service.url")), userId);
        ResponseEntity<List<ResponseOrder>> orderListResponse =
                restTemplate.exchange(orderUrl, HttpMethod.GET,null
                        ,new ParameterizedTypeReference<List<ResponseOrder>>(){}
                );
        List<ResponseOrder> orderList = orderListResponse.getBody();

        userDto.setOrders(orderList);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String username) {
        log.info("getUserByUserId {}",username);
        UserEntity findUserEntity =  userRepository
                .findByEmail(username)
                .orElseThrow(()->new NoSuchElementException("일치하는 값이 없습니다."));

        log.info(String.valueOf(findUserEntity.toString()));
        UserDto userDto = new ModelMapper().map(findUserEntity, UserDto.class);
        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);
        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUser by Username called");
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(username);
        if(optionalUserEntity.isEmpty()){
            log.info("email not found");
            throw new UsernameNotFoundException("user not found");
        }
        UserEntity userEntity = optionalUserEntity.get();
        return new User(
                userEntity.getEmail(),
                userEntity.getEncryptedPwd()
                ,new ArrayList<>()
        );
    }
}
