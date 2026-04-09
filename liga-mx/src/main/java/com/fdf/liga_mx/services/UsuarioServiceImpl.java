package com.fdf.liga_mx.services;

import com.fdf.liga_mx.models.dtos.request.UsuarioRequestDto;
import com.fdf.liga_mx.models.dtos.response.UsuarioResponseDto;
import com.fdf.liga_mx.models.entitys.Usuario;
import com.fdf.liga_mx.models.enums.Roles;
import com.fdf.liga_mx.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements  IUsuarioService{

    private final IUsuarioRepository usuarioRepo;

    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public UsuarioResponseDto save(UsuarioRequestDto usuarioRequestDto) {

        Usuario usuario = Usuario.builder()
                .email(usuarioRequestDto.getEmail().toLowerCase())
                .username(usuarioRequestDto.getUsername().toLowerCase())
                .password(encoder.encode(usuarioRequestDto.getPassword()))
                .role(Roles.BASICO) 
                .build();

        usuarioRepo.save(usuario);


        return UsuarioResponseDto.builder()
                .email(usuario.getEmail())
                .username(usuario.getUsername())
                .role(usuario.getRole())
                .build();
    }

    @Override
    public List<Usuario> findAll() {
        return List.of();
    }

    @Override
    public List<UsuarioResponseDto> findAllDto() {
        return List.of();
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
    }

    @Override
    public UsuarioResponseDto findDtoById(Long aLong) {
        return null;
    }

    @Override
    public UsuarioResponseDto update(UsuarioRequestDto usuarioRequestDto, Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepo.findByUsername(username).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
    }
}
