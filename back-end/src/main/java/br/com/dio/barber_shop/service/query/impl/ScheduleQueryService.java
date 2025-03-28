package br.com.dio.barber_shop.service.query.impl;

import br.com.dio.barber_shop.exception.NotFoundException;
import br.com.dio.barber_shop.exception.ScheduleInUseException;
import br.com.dio.barber_shop.models.ScheduleEntity;
import br.com.dio.barber_shop.repository.IScheduleRepository;
import br.com.dio.barber_shop.service.query.IScheduleQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleQueryService implements IScheduleQueryService {

    private final IScheduleRepository repository;

    @Override
    public ScheduleEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Agendamento não encontrado."));
    }

    @Override
    public List<ScheduleEntity> findInMonth(OffsetDateTime startAt, OffsetDateTime endAt) {
        return repository.findByStartAtGreaterThanEqualAndEndAtLessThanEqualOrderByStartAtAscEndAtAsc(startAt, endAt);
    }

    @Override
    public void verifyIfScheduleExists(OffsetDateTime startAt, OffsetDateTime endAt) {
        if(repository.existsByStartAtAndEndAt(startAt, endAt)){
            var message = "Já existe um cliente agendado no horário solicitado";
            throw new ScheduleInUseException(message);
        }
    }
}
