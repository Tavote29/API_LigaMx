package com.fdf.liga_mx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.fdf.liga_mx.models.entitys.Club;

public interface ClubRepository extends JpaRepository<Club,Short> {

}
