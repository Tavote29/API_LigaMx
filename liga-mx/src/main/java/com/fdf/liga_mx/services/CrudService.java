package com.fdf.liga_mx.services;

public interface CrudService<request,response,ID> {

    response create(request request);

    response read(ID id);

    response update(request request, ID id);

    void delete(ID id);
}
