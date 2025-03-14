package com.springboot.blog.service;

import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegistorDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String login(LoginDto loginDto);

    String registor(RegistorDto registorDto);
}
