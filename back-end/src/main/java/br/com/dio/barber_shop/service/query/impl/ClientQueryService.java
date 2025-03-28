package br.com.dio.barber_shop.service.query.impl;

import br.com.dio.barber_shop.exception.EmailInUseException;
import br.com.dio.barber_shop.exception.NotFoundException;
import br.com.dio.barber_shop.exception.PhoneInUseException;
import br.com.dio.barber_shop.models.ClientEntity;
import br.com.dio.barber_shop.repository.IClientRepository;
import br.com.dio.barber_shop.service.query.IClientQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ClientQueryService implements IClientQueryService {

    private final IClientRepository repository;

    @Override
    public ClientEntity findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Não foi encontrado o cliente de id: " + id));
    }

    @Override
    public List<ClientEntity> list() {
        return repository.findAll();
    }

    @Override
    public void verifyPhone(String phone) {
        if(repository.existsByPhone(phone)){
            var message = "O telefone " + phone + " já está em uso.";
            throw new PhoneInUseException(message);
        }
    }

    @Override
    public void verifyPhone(long id, String phone) {
        var optional = repository.findByPhone(phone);

        if(optional.isPresent() && !Objects.equals(optional.get().getPhone(), phone)){
            var message = "O telefone " + phone + " já está em uso.";
            throw new PhoneInUseException(message);
        }
    }

    @Override
    public void verifyEmail(String email) {
        if(repository.existsByEmail(email)){
            var message = "O email " + email + " já está em uso.";
            throw new EmailInUseException(message);
        }
    }

    @Override
    public void verifyEmail(long id, String email) {
        var optional = repository.findByEmail(email);

        if(optional.isPresent() && !Objects.equals(optional.get().getPhone(), email)){
            var message = "O email " + email + " já está em uso.";
            throw new EmailInUseException(message);
        }

    }
}
