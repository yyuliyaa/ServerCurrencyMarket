package com.currencymarket.service.impl;

import com.currencymarket.command.Status;
import com.currencymarket.dto.SignUpDto;
import com.currencymarket.dto.clientdto.HomePageDto;
import com.currencymarket.entity.Client;
import com.currencymarket.repository.ClientDao;
import com.currencymarket.repository.RegistrationDao;
import com.currencymarket.repository.impl.ClientDaoImpl;
import com.currencymarket.repository.impl.RegistrationDaoImpl;
import com.currencymarket.service.RegistrationService;

import java.util.Optional;

public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationDao registrationDao;
    private final ClientDao clientDao;

    public RegistrationServiceImpl() {
        this.registrationDao = new RegistrationDaoImpl();
        this.clientDao = new ClientDaoImpl();
    }

    @Override
    public boolean register(SignUpDto signUpRequest) {
        Status status = registrationDao.userIsExist(signUpRequest.getUsername());
        System.out.println(status);
        if(status.equals(Status.USER_ALREADY_EXIST)) {
            return false;
        }
        registrationDao.sign_up(signUpRequest);
        return true;
    }

    @Override
    public HomePageDto sign_in(SignUpDto signUpDto) {
        Optional<Client> client = clientDao.getByUsername(signUpDto.getUsername());
        if(client.isPresent()) {
            HomePageDto homePageDto = new HomePageDto();
            homePageDto.setCash(client.get().getCash());
            homePageDto.setId(client.get().getId());
            homePageDto.setRole(client.get().getRole());
            homePageDto.setUsername(client.get().getUsername());
            return homePageDto;
        }
        return null;
    }

    @Override
    public Status checkUser(SignUpDto signUpDto) {
        HomePageDto homePageDto = registrationDao.sign_in(signUpDto);
        if (homePageDto.getUsername() ==null )
            return Status.INVALID_LOGIN_OR_PASSWORD;
        else if(homePageDto.getActivated().equals("BANNED"))
            return Status.BAN;
        else return Status.ACCEPTED;
    }
}
