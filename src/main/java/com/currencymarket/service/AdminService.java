package com.currencymarket.service;

import com.currencymarket.dto.admindto.ClientInformationDto;

import java.util.List;

public interface AdminService {

    String deleteClient(int id);

    List<ClientInformationDto> getAll();
}
