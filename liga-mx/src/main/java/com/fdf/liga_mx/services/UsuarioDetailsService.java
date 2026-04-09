package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.entitys.Usuario;
import com.fdf.liga_mx.models.enums.Estados;
import com.fdf.liga_mx.repository.IUsuarioRepository;
import com.fdf.liga_mx.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final IUsuarioRepository usuarioRepo;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if(usuario.getIdStatus().equals(Estados.INACTIVO.getCodigo()))
            throw new UsernameNotFoundException("Usuario inactivo");


        List<GrantedAuthority> roles = new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name())));

        return new CustomUserDetails(usuario.getUsername(), usuario.getPassword(), roles, usuario.getId());
    }
}
