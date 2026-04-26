package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.*;
import com.fdf.liga_mx.models.dtos.request.LesionRequestDto;
import com.fdf.liga_mx.models.dtos.response.LesionResponseDto;
import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Lesion;
import com.fdf.liga_mx.repository.ILesionRepository;
import com.fdf.liga_mx.testdata.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LesionServiceImplTest {
    private LesionServiceImpl lesionService;

    @Mock
    private ILesionRepository lesionRepository;
    @Mock
    private IJugadorService jugadorService;

    private static final Faker faker = new Faker();

    @BeforeEach
    public void setUp(){
        StatusMapper statusMapper = new StatusMapper();
        NacionalidadMapper nacionalidadMapper = new NacionalidadMapper();
        PersonaMapper personaMapper = new PersonaMapper(statusMapper, nacionalidadMapper);
        PosicionMapper posicionMapper = new PosicionMapper();
        EstadoMapper estadoMapper = new EstadoMapper();
        CiudadMapper ciudadMapper = new CiudadMapper(estadoMapper);
        EstadioMapper estadioMapper = new EstadioMapper(estadoMapper,ciudadMapper);
        DTMapper dtMapper = new DTMapper(personaMapper);
        ClubMapper clubMapperReal = new ClubMapper(estadoMapper,ciudadMapper,dtMapper,estadioMapper);
        JugadorMapper jugadorMapper = new JugadorMapper(personaMapper, posicionMapper, clubMapperReal);

        LesionMapper lesionMapper = new LesionMapper(jugadorMapper);

        lesionService = new LesionServiceImpl(
                lesionRepository,
                jugadorService,
                lesionMapper
        );
    }

    @Test
    public void saveLesion_mustSaveLesionSuccessfully_whenValidDataIsProvided(){
        //Arrange
        LesionRequestDto lesionRequest = new LesionRequestTestDataBuilder().build();

        Jugador jugador = new JugadorTestDataBuilder()
                .withId(lesionRequest.getNuiJugador())
                .withIdPersona(PersonaTestDataBuilder.aPersona().build())
                .withIdClub(ClubTestDataBuilder.aClub().build())
                .build();

        when(jugadorService.findById(lesionRequest.getNuiJugador())).thenReturn(jugador);

        when(lesionRepository.saveAndFlush(any(Lesion.class))).thenAnswer(
                l ->{
                    Lesion lesion = l.getArgument(0);
                    lesion.setId(UUID.randomUUID());
                    return lesion;
                }
        );

        ArgumentCaptor<Lesion> captor = ArgumentCaptor.forClass(Lesion.class);

        //Act
        LesionResponseDto result = lesionService.save(lesionRequest);

        //Assert
        assertNotNull(result);
        assertEquals(lesionRequest.getNuiJugador(), result.getJugador().getId());
        assertEquals(lesionRequest.getDescripcionLesion(), result.getDescripcionLesion());
        assertEquals(lesionRequest.getFechaLesion(), result.getFechaLesion());
        assertEquals(lesionRequest.getFechaEstimadaRecuperacion(), result.getFechaRecuperacion());

        verify(lesionRepository).saveAndFlush(captor.capture());

        Lesion lesionSaved = captor.getValue();

        assertNotNull(lesionSaved);
        assertSame(jugador, lesionSaved.getJugador());
        assertEquals(lesionRequest.getDescripcionLesion(), lesionSaved.getDescripcionLesion());
        assertEquals(lesionRequest.getFechaLesion(), lesionSaved.getFechaLesion());
        assertEquals(lesionRequest.getFechaEstimadaRecuperacion(), lesionSaved.getFechaRecuperacion());
    }

    @Test
    public void findById_mustReturnLesion_whenFound(){
        //Arrange
        UUID uuid = UUID.randomUUID();

        Lesion lesion = LesionTestDataBuilder.aLesion()
                .withId(uuid)
                .build();

        when(lesionRepository.findById(uuid))
                .thenReturn(Optional.of(lesion));

        //Act
        Lesion result = lesionService.findById(uuid);

        //Assert
        assertNotNull(result);
        assertEquals(lesion.getId(), result.getId());
        assertSame(lesion, result);
    }

    @Test
    public void findAll_mustReturnListOfLesiones(){
        //Arrange
        Lesion lesion1 = LesionTestDataBuilder.aLesion().build();
        Lesion lesion2 = LesionTestDataBuilder.aLesion().build();
        Lesion lesion3 = LesionTestDataBuilder.aLesion().build();

        when(lesionRepository.findAll()).thenReturn(List.of(lesion1,lesion2,lesion3));

        //Act
        List<Lesion> lesiones = lesionService.findAll();

        //Assert
        assertNotNull(lesiones);
        assertEquals(3,lesiones.size());
    }

    @Test
    public void findAllDto_mustReturnOfLesionResponseDto(){
        //Arrange
        Lesion lesion1 = LesionTestDataBuilder.aLesion().build();
        Lesion lesion2 = LesionTestDataBuilder.aLesion().build();
        Lesion lesion3 = LesionTestDataBuilder.aLesion().build();

        when(lesionRepository.findAll()).thenReturn(List.of(lesion1,lesion2,lesion3));

        //Act
        List<LesionResponseDto> lesiones = lesionService.findAllDto();

        //Assert
        assertNotNull(lesiones);
        assertEquals(3,lesiones.size());
    }
}
