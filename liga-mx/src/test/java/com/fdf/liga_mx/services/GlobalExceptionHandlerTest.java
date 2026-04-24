package com.fdf.liga_mx.services;

import com.fdf.liga_mx.exception.ErrorResponse;
import com.fdf.liga_mx.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.io.IOException;
import java.time.DateTimeException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

     @Mock
     private MessageSource messageSource;

     @Mock
     private WebRequest webRequest;

     @InjectMocks
     private GlobalExceptionHandler globalExceptionHandler;

     @Test
     void handleNoSuchElement_shouldReturnNotFound() {
          // Arrange
          String messageKey = "No encontrado";
          NoSuchElementException ex = new NoSuchElementException(messageKey);
          when(webRequest.getDescription(false)).thenReturn("uri=/api/test");
          when(messageSource.getMessage(eq(messageKey), any(), any())).thenReturn("Traduccion mensaje");

          // Act
          ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNoSuchElement(ex, webRequest);

          // Assert
          assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
          assertEquals(404, response.getBody().getStatus());
          assertEquals("Traduccion mensaje", response.getBody().getMessage());
          assertEquals("/api/test", response.getBody().getPath());
     }

     @Test
     void handleIllegalArgument_shouldReturnBadRequest() {
          // Arrange
          String messageKey = "Argumento invalido";
          IllegalArgumentException ex = new IllegalArgumentException(messageKey);
          when(webRequest.getDescription(false)).thenReturn("uri=/api/test");
          when(messageSource.getMessage(eq(messageKey), any(), any())).thenReturn("Bad Request");

          // Act
          ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgument(ex, webRequest);

          // Assert
          assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
          assertEquals(400, response.getBody().getStatus());
          assertEquals("Bad Request", response.getBody().getMessage());
     }

     @Test
     void handleIllegalState_shouldReturnConflict() {
          // Arrange
          String messageKey = "Estado invalido";
          IllegalStateException ex = new IllegalStateException(messageKey);
          when(webRequest.getDescription(false)).thenReturn("uri=/api/test");
          when(messageSource.getMessage(eq(messageKey), any(), any())).thenReturn("Conflict");

          // Act
          ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalState(ex, webRequest);

          // Assert
          assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
          assertEquals(409, response.getBody().getStatus());
          assertEquals("Conflict", response.getBody().getMessage());
     }

     @Test
     void handleGeneralException_shouldReturnInternalServerError() {
          // Arrange
          Exception ex = new Exception("Random error");
          when(webRequest.getDescription(false)).thenReturn("uri=/api/test");
          when(messageSource.getMessage(contains("error inesperado"), any(), any())).thenReturn("Internal Server Error");

          // Act
          ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(ex, webRequest);

          // Assert
          assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
          assertEquals(500, response.getBody().getStatus());
          assertEquals("Internal Server Error", response.getBody().getMessage());
     }

     @Test
     void getMessage_shouldReturnKey_whenMessageSourceThrowsException() {

          // Arrange
          String messageKey = "key.not.existent";
          NoSuchElementException ex = new NoSuchElementException(messageKey);
          when(webRequest.getDescription(false)).thenReturn("/test");
          when(messageSource.getMessage(anyString(), any(), any())).thenThrow(new org.springframework.context.NoSuchMessageException(messageKey));

          // Act
          ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNoSuchElement(ex, webRequest);

          // Assert
          assertEquals(messageKey, response.getBody().getMessage());
     }

     @Test
     void handleSecurity_shouldReturnForbidden() {
          // Arrange
          SecurityException ex = new SecurityException("Acceso prohibido");
          when(webRequest.getDescription(false)).thenReturn("uri=/api/forbidden");

          // Act
          ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleSecurity(ex, webRequest);

          // Assert
          assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
          assertEquals(ex.getMessage(), response.getBody().getMessage());
     }

     @Test
     void handleAuthenticationException_shouldReturnUnauthorized() {
          // Arrange
          AuthenticationException ex =
                  new BadCredentialsException("Bad creds");
          when(webRequest.getDescription(false)).thenReturn("uri=/api/login");
          when(messageSource.getMessage(eq("Credenciales inválidas"), any(), any())).thenReturn("Credenciales invalidas");

          // Act
          ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleAuthenticationException(ex, webRequest);

          // Assert
          assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
          assertEquals("Credenciales invalidas", response.getBody().getMessage());
     }

     @Test
     void handleIOException_shouldReturnInternalServerError() {
          // Arrange
          IOException ex = new java.io.IOException("Disk full");
          when(webRequest.getDescription(false)).thenReturn("uri=/api/upload");

          // Act
          ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIOException(ex, webRequest);

          // Assert
          assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
          assertTrue(response.getBody().getMessage().contains("Disk full"));
     }

     @Test
     void handleDateTimeException_shouldReturnBadRequest() {
          // Arrange
          DateTimeException ex = new java.time.DateTimeException("Invalid date");
          when(webRequest.getDescription(false)).thenReturn("uri=/api/date");
          when(messageSource.getMessage(eq("Invalid date"), any(), any())).thenReturn("Translated Date Error");

          // Act
          ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDateTimeException(ex, webRequest);

          // Assert
          assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
          assertEquals("Translated Date Error", response.getBody().getMessage());
     }

     @Test
     @SuppressWarnings("unchecked")
     void handleValidationExceptions_shouldReturnMapOfErrors() {
          // Arrange
          MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
          BindingResult bindingResult = mock(BindingResult.class);
          FieldError fieldError = new org.springframework.validation.FieldError("object", "field", "default message");

          when(ex.getBindingResult()).thenReturn(bindingResult);
          when(bindingResult.getAllErrors()).thenReturn(java.util.List.of(fieldError));
          when(webRequest.getDescription(false)).thenReturn("uri=/api/valid");

          // Act
          ResponseEntity<Object> response = globalExceptionHandler.handleValidationExceptions(ex, webRequest);

          // Assert
          assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
          java.util.Map<String, Object> body = (java.util.Map<String, Object>) response.getBody();
          assertNotNull(body);
          java.util.Map<String, String> errors = (java.util.Map<String, String>) body.get("message");
          assertEquals("default message", errors.get("field"));
     }
}

