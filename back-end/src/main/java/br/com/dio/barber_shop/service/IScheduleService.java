package br.com.dio.barber_shop.service;

import br.com.dio.barber_shop.models.ScheduleEntity;

public interface IScheduleService {

    ScheduleEntity save(final ScheduleEntity entity);

    void delete(final long id);
}
