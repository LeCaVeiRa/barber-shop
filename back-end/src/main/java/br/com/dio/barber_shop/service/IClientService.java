package br.com.dio.barber_shop.service;

import br.com.dio.barber_shop.models.ClientEntity;

public interface IClientService {

    ClientEntity save(final ClientEntity entity);

    ClientEntity update(final ClientEntity entity);

    void delete(final long id);
}
