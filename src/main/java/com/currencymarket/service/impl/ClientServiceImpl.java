package com.currencymarket.service.impl;

import com.currencymarket.dto.admindto.ClientInformationDto;
import com.currencymarket.dto.clientdto.ChangePasswordDto;
import com.currencymarket.dto.clientdto.PersonalInfoDto;
import com.currencymarket.dto.clientdto.UpdateCashDto;
import com.currencymarket.entity.Client;
import com.currencymarket.repository.ClientDao;
import com.currencymarket.repository.impl.ClientDaoImpl;
import com.currencymarket.service.ClientService;
import com.currencymarket.utils.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    public ClientServiceImpl() {
        this.clientDao = new ClientDaoImpl();
    }

    @Override
    public List<PersonalInfoDto> getPersonalInfo(int id) {
        return clientDao.getClientInfo(id);
    }

    @Override
    public String updateClient(ClientInformationDto clientInformationDto) {
        clientDao.update(clientInformationDto);
        return "Клиент успешно обновлен";
    }

    @Override
    public double updateCash(UpdateCashDto updateCashDto) {
        Optional<Client> client = clientDao.getById(updateCashDto.getId());
        if (client.isPresent()) {
            double updateCash = client.get().getCash() + updateCashDto.getCash();
            client.get().setCash(updateCash);
            clientDao.updateClient(client);
            return clientDao.getById(updateCashDto.getId()).get().getCash();
        }
        return client.get().getCash();
    }

    @Override
    public boolean changePassword(ChangePasswordDto changePasswordDto) {
        Optional<Client> client = clientDao.getById(changePasswordDto.getId());
        String encode = PasswordEncoder.encode(changePasswordDto.getOldPassword());
        String encode1 = PasswordEncoder.encode(changePasswordDto.getNewPassword());
        System.out.println(encode);
        System.out.println(encode1);
        if (client.isPresent()) {

            if (client.get().getPassword().equals(encode)) {
                client.get().setPassword(encode1);
                clientDao.updateClient(client);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
