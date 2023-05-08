package com.currencymarket.service;

import com.currencymarket.dto.admindto.ClientInformationDto;
import com.currencymarket.dto.clientdto.ChangePasswordDto;
import com.currencymarket.dto.clientdto.PersonalInfoDto;
import com.currencymarket.dto.clientdto.UpdateCashDto;

import java.util.List;

public interface ClientService {
    List<PersonalInfoDto> getPersonalInfo(int id);

    String updateClient(ClientInformationDto clientInformationDto);

    double updateCash(UpdateCashDto updateCashDto);

    boolean changePassword(ChangePasswordDto changePasswordDto);
}
