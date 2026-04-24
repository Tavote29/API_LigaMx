package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.*;
import com.fdf.liga_mx.models.dtos.request.AcontecimientoRequest;
import com.fdf.liga_mx.models.dtos.response.AcontecimientoResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.models.enums.Estados;
import com.fdf.liga_mx.repository.AcontecimientoRepository;
import com.fdf.liga_mx.testdata.AcontecimientoRequestTestDataBuilder;
import com.fdf.liga_mx.testdata.AcontecimientoTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AcontecimientoServiceImplTest {
    private AcontecimientoServiceImpl acontecimientoService;

    @Mock
    private AcontecimientoRepository acontecimientoRepository;
    @Mock
    private IJugadorService jugadorService;
    @Mock
    private IPartidoService partidoService;
    @Mock
    private ICatalogosService catalogosService;
    @Mock
    private ApplicationEventPublisher eventPublisher;


    @BeforeEach
    public void  setUp(){
        StatusMapper statusMapper = new StatusMapper();
        NacionalidadMapper nacionalidadMapper = new NacionalidadMapper();
        PersonaMapper personaMapper = new PersonaMapper(statusMapper, nacionalidadMapper);
        EstadoMapper estadoMapper = new EstadoMapper();
        CiudadMapper ciudadMapper = new CiudadMapper(estadoMapper);
        EstadioMapper estadioMapper = new EstadioMapper(estadoMapper, ciudadMapper);
        DTMapper dtMapper = new DTMapper(personaMapper);
        ClubMapper clubMapper = new ClubMapper(estadoMapper, ciudadMapper, dtMapper, estadioMapper);
        CategoriaArbitroMapper categoriaArbitroMapper = new CategoriaArbitroMapper();
        ArbitroMapper arbitroMapper = new ArbitroMapper(personaMapper, categoriaArbitroMapper);
        TorneoMapper torneoMapper = new TorneoMapper();
        PosicionMapper posicionMapper = new PosicionMapper();
        JugadorMapper jugadorMapper = new JugadorMapper(personaMapper,posicionMapper,clubMapper);
        PartidoMapper partidoMapper = new PartidoMapper(clubMapper, estadioMapper, arbitroMapper, statusMapper, torneoMapper);
        TiposAcontecimientoMapper tiposAcontecimientoMapper = new TiposAcontecimientoMapper();
        AcontecimientoMapper acontecimientoMapper = new AcontecimientoMapper(tiposAcontecimientoMapper,jugadorMapper,partidoMapper);

        acontecimientoService = new AcontecimientoServiceImpl(
            acontecimientoRepository,
            acontecimientoMapper,
            jugadorService,
            partidoService,
            catalogosService,
            eventPublisher
        );
    }

    @Test
    public void saveAcontecimiento_mustSaveAcontecimientoSuccessfully_whenValidDataIsProvided(){
        //Arrange
        AcontecimientoRequest request = new AcontecimientoRequestTestDataBuilder().build();
        Jugador jugador1 = Jugador.builder()
                .idPersona(Persona.builder()
                        .build())
                .idClub(Club.builder()
                        .build())
                .id(request.getIdJugadorPrimario())
                .build();
        Jugador jugador2 = Jugador.builder()
                .idPersona(Persona.builder()
                        .build())
                .idClub(Club.builder()
                        .build())
                .id(request.getIdJugadorSecundario())
                .build();
        Partido partido = Partido.builder()
                .id(request.getIdPartido())
                .idStatus(Status.builder()
                        .id(Estados.ACTIVO.getCodigo())
                        .build())
                .build();
        TiposAcontecimiento tipo = TiposAcontecimiento.builder()
                .id(request.getIdTipo())
                .build();

        when(jugadorService.findById(request.getIdJugadorPrimario())).thenReturn(jugador1);
        when(jugadorService.findById(request.getIdJugadorSecundario())).thenReturn(jugador2);
        when(partidoService.findById(request.getIdPartido())).thenReturn(partido);
        when(catalogosService.findTipoAcontecimientoEntityById(request.getIdTipo())).thenReturn(tipo);

        when(acontecimientoRepository.saveAndFlush(any(Acontecimiento.class))).thenAnswer(
                acon ->{
                    Acontecimiento acontecimiento = acon.getArgument(0);
                    acontecimiento.setId(UUID.randomUUID());
                    return acontecimiento;
                }
        );

        ArgumentCaptor<Acontecimiento> captor = ArgumentCaptor.forClass(Acontecimiento.class);

        //Act
        AcontecimientoResponseDto response = acontecimientoService.save(request);

        //Assert
        assertNotNull(response);
        assertEquals(request.getIdTipo(), response.getIdTipo().getId());
        assertEquals(request.getMinuto(), response.getMinuto());
        assertEquals(request.getIdJugadorPrimario(), response.getIdJugadorPrimario().getId());
        assertEquals(request.getIdJugadorSecundario(), response.getIdJugadorSecundario().getId());
        assertEquals(request.getIdPartido(), response.getIdPartido().getId());

        verify(acontecimientoRepository).saveAndFlush(captor.capture());

        Acontecimiento acontecimientoSaved = captor.getValue();

        assertNotNull(acontecimientoSaved);
        assertSame(jugador1, acontecimientoSaved.getIdJugadorPrimario());
        assertSame(jugador2, acontecimientoSaved.getIdJugadorSecundario());
        assertSame(partido, acontecimientoSaved.getIdPartido());
        assertSame(tipo, acontecimientoSaved.getIdTipo());
        assertSame(request.getMinuto(), acontecimientoSaved.getMinuto());
    }

    @Test
    public void saveAcontecimiento_mustThrowIllegalStateException_whenStatusPartidoIsFinalizado(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        AcontecimientoRequest request = new AcontecimientoRequestTestDataBuilder()
                .withPartido(uuid)
                .build();

        Status finalizado = Status.builder()
                .id(Estados.FINALIZADO.getCodigo())
                .build();
        Partido partido = Partido.builder()
                .id(uuid)
                .idStatus(finalizado)
                .build();

        when(partidoService.findById(request.getIdPartido())).thenReturn(partido);

        //Act
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                ()-> acontecimientoService.save(request));

        //Assert
        assertEquals("Partido finalizado", exception.getMessage());

        verify(partidoService).findById(uuid);
        verifyNoInteractions(acontecimientoRepository);
    }

    @Test
    public void findById_mustReturnAcontecimiento_whenFound(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        Acontecimiento acontecimiento = AcontecimientoTestDataBuilder.anAcontecimiento()
                .withId(uuid)
                .build();

        when(acontecimientoRepository.findById(uuid))
                .thenReturn(Optional.of(acontecimiento));

        //Act
        Acontecimiento result = acontecimientoService.findById(uuid);

        //Assert
        assertNotNull(result);
        assertEquals(acontecimiento.getId(), result.getId());
        assertSame(acontecimiento,result);
    }

    @Test
    public void findDtoById_mustThrowNoSuchElementException_whenAcontecimientoNotFound(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        when(acontecimientoRepository.findById(uuid))
                .thenReturn(Optional.empty());

        //Act
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> acontecimientoService.findDtoById(uuid));

        //Assert
        assertEquals("No se encontro el acontecimiento", exception.getMessage());

    }

    @Test
    public void findAll_mustReturnListOfAcontecimientos(){
        //Arrange
        Acontecimiento acontecimiento1 = AcontecimientoTestDataBuilder.anAcontecimiento().build();
        Acontecimiento acontecimiento2 = AcontecimientoTestDataBuilder.anAcontecimiento().build();
        Acontecimiento acontecimiento3 = AcontecimientoTestDataBuilder.anAcontecimiento().build();

        when(acontecimientoRepository.findAll()).thenReturn(List.of(acontecimiento1,acontecimiento2,acontecimiento3));

        //Act
        List<Acontecimiento> acontecimientos = acontecimientoService.findAll();

        //Assert
        assertNotNull(acontecimientos);
        assertEquals(3,acontecimientos.size());
    }

    @Test
    public void findAllDto_mustReturnListOfAcontecimientoResponseDto(){
        //Arrange
        Acontecimiento acontecimiento1 = AcontecimientoTestDataBuilder.anAcontecimiento().build();
        Acontecimiento acontecimiento2 = AcontecimientoTestDataBuilder.anAcontecimiento().build();
        Acontecimiento acontecimiento3 = AcontecimientoTestDataBuilder.anAcontecimiento().build();

        when(acontecimientoRepository.findAll()).thenReturn(List.of(acontecimiento1,acontecimiento2,acontecimiento3));

        //Act
        List<AcontecimientoResponseDto> acontecimientos = acontecimientoService.findAllDto();

        //Assert
        assertNotNull(acontecimientos);
        assertEquals(3,acontecimientos.size());
    }

    public void update_mustUpdateSuccesfully_whenAllDataIsDifferentAndValid(){

    }
}
