package com.fdf.liga_mx.models.repositories;

import com.fdf.liga_mx.models.entitys.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusRepository extends CrudRepository<Status, Short> {
}