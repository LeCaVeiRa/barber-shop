package br.com.dio.barber_shop.service.impl;

import br.com.dio.barber_shop.models.ScheduleEntity;
import br.com.dio.barber_shop.repository.IScheduleRepository;
import br.com.dio.barber_shop.service.IScheduleService;
import br.com.dio.barber_shop.service.query.IScheduleQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleService implements IScheduleService {

    private final IScheduleRepository repository;
    private final IScheduleQueryService queryService;

    @Override
    public ScheduleEntity save(ScheduleEntity entity) {
        queryService.verifyIfScheduleExists(entity.getStartAt(), entity.getEndAt());
        return repository.save(entity);
    }

    @Override
    public void delete(long id) {
        queryService.findById(id);
        repository.deleteById(id);
    }
}
