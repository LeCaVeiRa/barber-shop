package br.com.dio.barber_shop.controller;

import br.com.dio.barber_shop.controller.request.SaveClientRequest;
import br.com.dio.barber_shop.controller.request.UpdateClientRequest;
import br.com.dio.barber_shop.controller.response.ClientDetailResponse;
import br.com.dio.barber_shop.controller.response.ListClientResponse;
import br.com.dio.barber_shop.controller.response.SaveClientResponse;
import br.com.dio.barber_shop.controller.response.UpdateClientResponse;
import br.com.dio.barber_shop.mapper.IClientMapper;
import br.com.dio.barber_shop.service.IClientService;
import br.com.dio.barber_shop.service.query.IClientQueryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("clients")
@AllArgsConstructor
public class ClientController {
    private final IClientService service;
    private final IClientQueryService queryService;
    private final IClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    SaveClientResponse save(@RequestBody @Valid final SaveClientRequest request){
        var entity = mapper.toEntity(request);
        service.save(entity);

        return mapper.toSaveResponse(entity);
    }

    @PutMapping("{id}")
    UpdateClientResponse update(@PathVariable final Long id, @RequestBody @Valid final UpdateClientRequest request){
        var entity = mapper.toEntity(id, request);
        service.update(entity);

        return mapper.toUpdateResponse(entity);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable final long id){
        service.delete(id);
    }

    @GetMapping("{id}")
    ClientDetailResponse findById(@PathVariable final Long id){
        var entity = queryService.findById(id);

        return mapper.toDetailResponse(entity);
    }

    @GetMapping
    List<ListClientResponse> list(){
        var entities = queryService.list();
        return mapper.toListResponse(entities);
    }
}
