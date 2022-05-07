package com.lab3.restaurant.service.user;

import com.lab3.restaurant.dao.UserRepository;
import com.lab3.restaurant.dto.user.UserInfoDto;
import com.lab3.restaurant.dto.user.UserRegisterDto;
import com.lab3.restaurant.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;

    @Lazy
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserInfoDto getByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return new UserInfoDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getId());
    }

    @Override
    public UserEntity save(UserRegisterDto registrationDto) {
        UserEntity.UserRole role = registrationDto.getRole();

        if (role == null) {
            role = UserEntity.UserRole.USER;
        }

        UserEntity user = new UserEntity(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), role);

        System.out.println(user);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}
