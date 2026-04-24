package com.fdf.liga_mx.models.repositories;

import com.fdf.liga_mx.models.entitys.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusRepository extends JpaRepository<Status, Short> {
}