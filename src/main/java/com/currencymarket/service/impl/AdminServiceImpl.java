package com.currencymarket.service.impl;

import com.currencymarket.dto.admindto.ClientInformationDto;
import com.currencymarket.repository.AdminDao;
import com.currencymarket.repository.impl.AdminDaoImpl;
import com.currencymarket.service.AdminService;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao;

    public AdminServiceImpl() {
        this.adminDao = new AdminDaoImpl();
    }

    @Override
    public String deleteClient(int id) {
        return adminDao.deleteById(id);
    }

    @Override
    public List<ClientInformationDto> getAll() {
        return adminDao.getAll();
    }
}
