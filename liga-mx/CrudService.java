package com.fdf.liga_mx.services;

import java.util.List;

public interface CrudService<request,response,entity,id> {

    response save(request request);

    List<entity> findAll();

    List<response> findAllDto();



    entity findById(id id);

    response findDtoById(id id);

    response update(request request, id id);

    void delete(id id);
}
