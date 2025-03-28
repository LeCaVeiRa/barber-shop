package br.com.dio.barber_shop.service.impl;

import br.com.dio.barber_shop.models.ClientEntity;
import br.com.dio.barber_shop.repository.IClientRepository;
import br.com.dio.barber_shop.service.IClientService;
import br.com.dio.barber_shop.service.query.IClientQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientService implements IClientService {

    private final IClientQueryService queryService;
    private final IClientRepository repository;

    @Override
    public ClientEntity save(ClientEntity entity) {
        queryService.verifyEmail(entity.getEmail());
        queryService.verifyPhone(entity.getPhone());

        return repository.save(entity);
    }

    @Override
    public ClientEntity update(ClientEntity entity) {
        queryService.verifyEmail(entity.getId(), entity.getEmail());
        queryService.verifyPhone(entity.getId(), entity.getPhone());

        var stored = queryService.findById(entity.getId());
        stored.setName(entity.getName());
        stored.setEmail(entity.getEmail());
        stored.setPhone(entity.getPhone());

        return repository.save(stored);
    }

    @Override
    public void delete(long id) {
        queryService.findById(id);
        repository.deleteById(id);
    }
}
