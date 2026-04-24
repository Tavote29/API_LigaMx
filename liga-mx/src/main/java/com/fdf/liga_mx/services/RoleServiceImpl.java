package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.entitys.Role;
import com.fdf.liga_mx.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService{

    private final IRoleRepository roleRepo;


    @Override
    public Role findById(Short id) {
        return roleRepo.findById(id).orElseThrow(() -> new NoSuchElementException("error.role.not_found"));
    }



}
