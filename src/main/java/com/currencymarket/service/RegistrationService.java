package com.currencymarket.service;

import com.currencymarket.command.Status;
import com.currencymarket.dto.SignUpDto;
import com.currencymarket.dto.clientdto.HomePageDto;

public interface RegistrationService {
    boolean register(SignUpDto signUpRequest);

    HomePageDto sign_in(SignUpDto signUpDto);

    Status checkUser(SignUpDto signUpDto);
}
