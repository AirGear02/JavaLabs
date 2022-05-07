package com.lab3.restaurant.service.user;

import com.lab3.restaurant.dto.user.UserInfoDto;
import com.lab3.restaurant.dto.user.UserRegisterDto;
import com.lab3.restaurant.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserEntity save(UserRegisterDto registrationDto);
    Iterable<UserEntity> getAllUsers();
    UserInfoDto getByEmail(String email);
}
