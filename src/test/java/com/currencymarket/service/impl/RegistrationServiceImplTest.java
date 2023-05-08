package com.currencymarket.service.impl;

import com.currencymarket.command.Status;
import com.currencymarket.dto.SignUpDto;
import com.currencymarket.dto.clientdto.HomePageDto;
import com.currencymarket.entity.Client;
import com.currencymarket.repository.impl.ClientDaoImpl;
import com.currencymarket.repository.impl.RegistrationDaoImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RegistrationServiceImpl.class)
class RegistrationServiceImplTest {

    @Autowired
    private RegistrationServiceImpl registrationService;

    @MockBean
    private RegistrationDaoImpl registrationDao;

    @MockBean
    private ClientDaoImpl clientDao;

    static boolean isRegister;
    static HomePageDto homePageDto;
    static Status status;
    static SignUpDto signUpDto;
    static Optional<Client> client;
    static Status secondStatus;

    @BeforeAll
    static void setUp() {
        client = Optional.of(new Client());
        client.get().setRole("ADMIN");
        client.get().setActive("ACTIVE");
        client.get().setId(1);
        client.get().setCash(123.4);
        client.get().setUsername("123");
        client.get().setPassword("123");

        homePageDto = new HomePageDto();
        homePageDto.setId(1);
        homePageDto.setRole("ADMIN");
        homePageDto.setCash(10.5);
        homePageDto.setActivated("BANNED");
        homePageDto.setUsername("123");
        status = Status.USER_ALREADY_EXIST;

        signUpDto = new SignUpDto();
        signUpDto.setPassword("asdsd");
        signUpDto.setUsername("123");

        isRegister = true;
        secondStatus = Status.INVALID_LOGIN_OR_PASSWORD;
    }

    @Test
    void register() {
        when(registrationDao.userIsExist(any())).thenReturn(status);
        boolean checkResult = registrationService.register(signUpDto);
        assertTrue(checkResult);
    }

    @Test
    void sign_in() {
        when(clientDao.getByUsername(signUpDto.getUsername())).thenReturn(client);
        HomePageDto homePageDto1 = registrationService.sign_in(signUpDto);
        assertNull(homePageDto1);
    }

    @Test
    void checkUser() {
        when(registrationDao.sign_in(any())).thenReturn(homePageDto);
        Status status1 = registrationService.checkUser(signUpDto);
        assertEquals(status1, secondStatus);
    }
}