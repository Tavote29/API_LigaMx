package com.fdf.liga_mx.controller;

import com.fdf.liga_mx.config.SwaggerResponses;
import com.fdf.liga_mx.config.SwaggerTags;
import com.fdf.liga_mx.models.dtos.request.AuthRequestDto;
import com.fdf.liga_mx.models.dtos.request.UsuarioRequestDto;
import com.fdf.liga_mx.models.dtos.response.UsuarioResponseDto;
import com.fdf.liga_mx.services.IUsuarioService;
import com.fdf.liga_mx.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = SwaggerTags.AUTH_TAG, description = SwaggerTags.AUTH_DESC)
public class AuthController {

    private final AuthenticationManager authManager;

    private final JwtUtil jwtUtil;

    private final IUsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica a un usuario y genera un token JWT para acceder a los endpoints protegidos"
    )
    @SwaggerResponses.CreateApiResponses // Assuming 400 Bad Request, etc. are handled similarly
    @ApiResponse(
        responseCode = "200",
        description = "Autenticación exitosa. Retorna el token JWT.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Map.class)
        )
    )
    @ApiResponse(
        responseCode = "401",
        description = "Credenciales inválidas",
        content = @Content
    )
    public ResponseEntity<Map<String, Serializable>> login(
            @Parameter(
                description = "Credenciales del usuario (username y password)",
                required = true,
                schema = @Schema(implementation = AuthRequestDto.class)
            )
            @RequestBody @Valid AuthRequestDto request) {

        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User principal = (User) Objects.requireNonNull(auth.getPrincipal(), "El principal de autenticación no debe ser nulo");

        String token = jwtUtil.generarToken(principal.getUsername(), principal.getAuthorities());

        return ResponseEntity.ok(Map.of("jwt", token, "expires", jwtUtil.getExpirationToken(token)));

    }

    @PostMapping("/signup")
    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Crea una nueva cuenta de usuario en el sistema"
    )
    @SwaggerResponses.CreateApiResponses
    @ApiResponse(
        responseCode = "201",
        description = "Usuario creado exitosamente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = UsuarioResponseDto.class)
        )
    )
    public ResponseEntity<UsuarioResponseDto> save(
            @Parameter(
                description = "Datos del usuario a registrar",
                required = true,
                schema = @Schema(implementation = UsuarioRequestDto.class)
            )
            @RequestBody @Valid UsuarioRequestDto userDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(userDto));

    }

}
