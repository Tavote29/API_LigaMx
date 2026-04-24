package com.fdf.liga_mx.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdf.liga_mx.mappers.*;
import com.fdf.liga_mx.models.dtos.events.PartidoFinalizadoEvent;
import com.fdf.liga_mx.models.dtos.projection.getMarcadorPartido;
import com.fdf.liga_mx.models.dtos.request.PartidoRequest;
import com.fdf.liga_mx.models.dtos.response.PartidoResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.models.enums.Estados;
import com.fdf.liga_mx.repository.PartidoRepository;
import com.fdf.liga_mx.testdata.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DateTimeException;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class PartidoServiceImplTest {
    private PartidoServiceImpl partidoService;

    @Mock
    private IClubService clubService;
    @Mock
    private ICatalogosService catalogosService;
    @Mock
    private PartidoRepository partidoRepository;
    @Mock
    private IJugadorService jugadorService;
    @Mock
    private IArbitroService arbitroService;
    @Mock
    private IEstadioService estadioService;
    @Mock
    private ITorneoService torneoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Faker faker = new Faker();

    @BeforeEach
    public void setUp() {
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
        PartidoMapper partidoMapper = new PartidoMapper(clubMapper, estadioMapper, arbitroMapper, statusMapper, torneoMapper);

        partidoService = new PartidoServiceImpl(
                partidoRepository,
                partidoMapper,
                catalogosService,
                jugadorService,
                clubService,
                arbitroService,
                estadioService,
                objectMapper);
    }

    @Test
    public void savePartido_mustSavePartidoSuccessfully(){
        //Arrange
        PartidoRequest partidoRequest = new PartidoRequestTestDataBuilder()
            .withFecha(Instant.now().plusSeconds(86400)) // Ensure date is in the future
            .build();
        

        if (partidoRequest.getIdLocal().equals(partidoRequest.getIdVisitante())) {
            partidoRequest.setIdVisitante((short)(partidoRequest.getIdLocal() + 1));
        }
            
        Club local = Club.builder().id(partidoRequest.getIdLocal()).build();
        Club visitante = Club.builder().id(partidoRequest.getIdVisitante()).build();
        Estadio estadio = Estadio.builder().id(partidoRequest.getIdEstadio()).build();
        Arbitro arbitroCentral = Arbitro.builder().id(partidoRequest.getIdArbitroCentral()).build();
        Arbitro arbitroAsistente1 = Arbitro.builder().id(partidoRequest.getIdArbitroAsistente1()).build();
        Arbitro arbitroAsistente2 = Arbitro.builder().id(partidoRequest.getIdArbitroAsistente2()).build();
        Arbitro cuartoArbitro = Arbitro.builder().id(partidoRequest.getIdCuartoArbitro()).build();
        Torneo torneo = Torneo.builder()
                .id(partidoRequest.getIdTorneo())
                .status((short)1)
                .build();

        when(clubService.findById(partidoRequest.getIdLocal())).thenReturn(local);
        when(clubService.findById(partidoRequest.getIdVisitante())).thenReturn(visitante);
        when(estadioService.findById(partidoRequest.getIdEstadio())).thenReturn(estadio);
        when(arbitroService.findById(partidoRequest.getIdArbitroCentral())).thenReturn(arbitroCentral);
        when(arbitroService.findById(partidoRequest.getIdArbitroAsistente1())).thenReturn(arbitroAsistente1);
        when(arbitroService.findById(partidoRequest.getIdArbitroAsistente2())).thenReturn(arbitroAsistente2);
        when(arbitroService.findById(partidoRequest.getIdCuartoArbitro())).thenReturn(cuartoArbitro);
        when(catalogosService.findTorneoEntityById(partidoRequest.getIdTorneo())).thenReturn(torneo);

        when(partidoRepository.saveAndFlush(any(Partido.class))).thenAnswer( match ->{
               Partido partido = match.getArgument(0);
               partido.setId(UUID.randomUUID());
               return partido;
            }
        );


        ArgumentCaptor<Partido> captor = ArgumentCaptor.forClass(Partido.class);

        //Act
        PartidoResponseDto result = partidoService.save(partidoRequest);

        //Assert
        assertNotNull(result);
        assertEquals(partidoRequest.getIdLocal(), result.getIdLocal().getId());
        assertEquals(partidoRequest.getIdVisitante(), result.getIdVisitante().getId());
        assertEquals(partidoRequest.getIdEstadio(), result.getIdEstadio().getId());
        assertEquals(partidoRequest.getIdArbitroCentral(), result.getIdArbitroCentral().getId());
        assertEquals(partidoRequest.getIdArbitroAsistente1(), result.getIdArbitroAsistente1().getId());
        assertEquals(partidoRequest.getIdArbitroAsistente2(), result.getIdArbitroAsistente2().getId());
        assertEquals(partidoRequest.getIdCuartoArbitro(), result.getIdCuartoArbitro().getId());
        assertEquals(partidoRequest.getFecha(), result.getFecha());
        assertEquals(partidoRequest.getIdTorneo(), result.getIdTorneo().getId());

        verify(partidoRepository).saveAndFlush(captor.capture());

        Partido partidoSaved = captor.getValue();

        assertNotNull(partidoSaved);

        assertSame(local,partidoSaved.getIdLocal());
        assertSame(visitante,partidoSaved.getIdVisitante());
        assertSame(estadio, partidoSaved.getIdEstadio());
        assertNotNull(partidoSaved.getId());

        verify(clubService).findById(partidoRequest.getIdLocal());
        verify(clubService).findById(partidoRequest.getIdVisitante());
        verify(estadioService).findById(partidoRequest.getIdEstadio());
        verify(catalogosService).findTorneoEntityById(partidoRequest.getIdTorneo());
    }

    @Test
    public void savePartido_mustThrowNoSuchElementException_ifTorneoNoExists(){
        //Arrange
        Long torneo = 11L;
        PartidoRequest partidoRequest = new PartidoRequestTestDataBuilder()
            .withTorneo(torneo)
            .withFecha(Instant.now().plusSeconds(86400)) // Ensure date is valid
            .build();
            

        if (partidoRequest.getIdLocal().equals(partidoRequest.getIdVisitante())) {
            partidoRequest.setIdVisitante((short)(partidoRequest.getIdLocal() + 1));
        }
            
        Club local = Club.builder().id(partidoRequest.getIdLocal()).build();
        Club visitante = Club.builder().id(partidoRequest.getIdVisitante()).build();
        Estadio estadio = Estadio.builder().id(partidoRequest.getIdEstadio()).build();
        Arbitro arbitroCentral = Arbitro.builder().id(partidoRequest.getIdArbitroCentral()).build();
        Arbitro arbitroAsistente1 = Arbitro.builder().id(partidoRequest.getIdArbitroAsistente1()).build();
        Arbitro arbitroAsistente2 = Arbitro.builder().id(partidoRequest.getIdArbitroAsistente2()).build();
        Arbitro cuartoArbitro = Arbitro.builder().id(partidoRequest.getIdCuartoArbitro()).build();

        when(clubService.findById(partidoRequest.getIdLocal())).thenReturn(local);
        when(clubService.findById(partidoRequest.getIdVisitante())).thenReturn(visitante);
        when(estadioService.findById(partidoRequest.getIdEstadio())).thenReturn(estadio);
        when(arbitroService.findById(partidoRequest.getIdArbitroCentral())).thenReturn(arbitroCentral);
        when(arbitroService.findById(partidoRequest.getIdArbitroAsistente1())).thenReturn(arbitroAsistente1);
        when(arbitroService.findById(partidoRequest.getIdArbitroAsistente2())).thenReturn(arbitroAsistente2);
        when(arbitroService.findById(partidoRequest.getIdCuartoArbitro())).thenReturn(cuartoArbitro);


        when(catalogosService.findTorneoEntityById(torneo)).thenThrow(new NoSuchElementException("No se encontro el torneo"));

        //Act
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                ()-> partidoService.save(partidoRequest));

        //Assert
        assertEquals("No se encontro el torneo", exception.getMessage());

        verify(catalogosService).findTorneoEntityById(torneo);
        verifyNoInteractions(partidoRepository);
    }

    @Test
    public void savePartido_mustThrowDateException_ifDateIsNotValid(){
        //Arrange
        Instant from = Instant.parse("2020-01-01T00:00:00Z");
        PartidoRequest partidoRequest = new PartidoRequestTestDataBuilder().withFecha(from).build();

        //Act
        DateTimeException exception = assertThrows(
                DateTimeException.class,
                () -> partidoService.save(partidoRequest)
        );

        //Assert
        assertEquals("La fecha no es valida", exception.getMessage());

        verifyNoInteractions(partidoRepository);
    }

    @Test
    public void savePartido_mustThrowIllegalArgumentException_ifEquiposAreNotValid(){
        //Arrange
        Instant from = Instant.now().plusSeconds(86400);
        PartidoRequest partidoRequest = new PartidoRequestTestDataBuilder()
                .withFecha(from)
                .withLocal((short)8)
                .withVisitante((short)8)
                .build();

        //Act
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                ()-> partidoService.save(partidoRequest)
        );

        //Assert
        assertEquals("Los clubes no deben ser iguales", illegalArgumentException.getMessage());
        verifyNoInteractions(partidoRepository);
    }

    @Test
    public void findAll_mustReturnListOfPartidos(){
        //Arrange
        Partido partido1 = PartidoTestDataBuilder.aPartido().build();
        Partido partido2 = PartidoTestDataBuilder.aPartido().build();
        Partido partido3 = PartidoTestDataBuilder.aPartido().build();

        when(partidoRepository.findAll()).thenReturn(List.of(partido1,partido2,partido3));

        //Act
        List<Partido> partidoList = partidoService.findAll();

        //Assert
        assertNotNull(partidoList);
        assertEquals(3, partidoList.size());
    }

    @Test
    public void findAllDto_mustReturnListOfPartidos(){
        //Arrange
        Partido partido1 = PartidoTestDataBuilder.aPartido().build();
        Partido partido2 = PartidoTestDataBuilder.aPartido().build();
        Partido partido3 = PartidoTestDataBuilder.aPartido().build();

        when(partidoRepository.findAll()).thenReturn(List.of(partido1,partido2,partido3));

        //Act
        List<PartidoResponseDto> partidoList = partidoService.findAllDto();

        //Assert
        assertNotNull(partidoList);
        assertEquals(3, partidoList.size());
    }

    @Test
    public void findById_mustThrowNoSuchElementException_whenPartidoNotFound(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        when(partidoRepository.findById(uuid))
                .thenReturn(Optional.empty());

        //Act
        NoSuchElementException noSuchElementException = assertThrows(
                NoSuchElementException.class,
                ()-> partidoService.findById(uuid)
        );

        //Assert
        assertEquals("No se encontro el partido",noSuchElementException.getMessage());
    }

    @Test
    public void findById_mustReturnPartido_whenFound(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        Partido partido = PartidoTestDataBuilder.aPartido()
                .withId(uuid)
                .build();

        when(partidoRepository.findById(uuid))
                .thenReturn(Optional.of(partido));

        //Act
        Partido result = partidoService.findById(uuid);

        //Assert
        assertNotNull(result);
        assertEquals(uuid,result.getId());
        assertSame(partido, result);
    }

    @Test
    public void findDtoById_mustReturnPartidoResponseDto_whenFound(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        Partido partido = PartidoTestDataBuilder.aPartido()
                .withId(uuid)
                .build();

        when(partidoRepository.findById(uuid))
                .thenReturn(Optional.of(partido));

        //Act
        PartidoResponseDto responseDto = partidoService.findDtoById(uuid);

        //Assert
        assertNotNull(responseDto);
        assertEquals(uuid, responseDto.getId());
        assertEquals(partido.getIdLocal().getNombreClub(), responseDto.getIdLocal().getNombreClub());
        assertEquals(partido.getIdVisitante().getNombreClub(), responseDto.getIdVisitante().getNombreClub());
        assertEquals(partido.getIdEstadio().getNombreEstadio(), responseDto.getIdEstadio().getNombreEstadio());
        assertEquals(partido.getFecha(),responseDto.getFecha());
        assertEquals(partido.getIdTorneo().getId(), responseDto.getIdTorneo().getId());
    }

    @Test
    public void update_mustThrowNoSuchElementException_whenPartidoNotFound(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        PartidoRequest partidoRequest = new PartidoRequest();

        when(partidoRepository.findById(uuid))
                .thenReturn(Optional.empty());

        //Act
        NoSuchElementException noSuchElementException = assertThrows(
                NoSuchElementException.class,
                ()->partidoService.update(partidoRequest, uuid)
        );

        //Assert
        assertEquals("No se encontro el partido",noSuchElementException.getMessage());
        verifyNoInteractions(clubService);
        verifyNoInteractions(estadioService);
        verifyNoInteractions(arbitroService);
        verifyNoInteractions(catalogosService);
        verify(partidoRepository,never()).saveAndFlush(any());
    }

    @Test
    public void update_mustUpdatePartido_whenDataisValid() {

        // Arrange
        UUID uuid = UUID.randomUUID();
        PartidoRequest request = new PartidoRequestTestDataBuilder().build();


        Estadio estadioNuevo = Estadio.builder().id(request.getIdEstadio()).build();
        Arbitro centralNuevo = Arbitro.builder().id(request.getIdArbitroCentral()).build();
        Arbitro asis1Nuevo = Arbitro.builder().id(request.getIdArbitroAsistente1()).build();
        Arbitro asis2Nuevo = Arbitro.builder().id(request.getIdArbitroAsistente2()).build();
        Arbitro cuartoNuevo = Arbitro.builder().id(request.getIdCuartoArbitro()).build();
        Status statusNuevo = Status.builder().id(request.getIdStatus()).build();


        when(estadioService.findById(request.getIdEstadio())).thenReturn(estadioNuevo);
        when(arbitroService.findById(request.getIdArbitroCentral())).thenReturn(centralNuevo);
        when(arbitroService.findById(request.getIdArbitroAsistente1())).thenReturn(asis1Nuevo);
        when(arbitroService.findById(request.getIdArbitroAsistente2())).thenReturn(asis2Nuevo);
        when(arbitroService.findById(request.getIdCuartoArbitro())).thenReturn(cuartoNuevo);
        when(catalogosService.findStatusEntityById(request.getIdStatus())).thenReturn(statusNuevo);


        Partido partidoEnBD = PartidoTestDataBuilder.aPartido()
                .withId(uuid)
                .withEstadio(Estadio.builder().id((short) (request.getIdEstadio() + 100)).build())
                .withArbitroCentral(Arbitro.builder().id((request.getIdArbitroCentral() + 100)).build())
                .withArbitroAsistente1(Arbitro.builder().id((request.getIdArbitroAsistente1() + 100)).build())
                .withArbitroAsistente2(Arbitro.builder().id( (request.getIdArbitroAsistente2() + 100)).build())
                .withCuartoArbitro(Arbitro.builder().id( (request.getIdCuartoArbitro() + 100)).build())
                .withStatus(Status.builder().id((short) (request.getIdStatus() + 100)).build())
                .withFecha(Instant.now().minusSeconds(86400))
                .build();


        when(partidoRepository.findById(uuid)).thenReturn(Optional.of(partidoEnBD));


        when(partidoRepository.saveAndFlush(any(Partido.class))).thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<Partido> captor = ArgumentCaptor.forClass(Partido.class);


        PartidoResponseDto response = partidoService.update(request, uuid);


        assertNotNull(response);


        verify(partidoRepository).saveAndFlush(captor.capture());
        Partido partidoActualizado = captor.getValue();


        assertEquals(request.getIdEstadio(), partidoActualizado.getIdEstadio().getId());
        assertEquals(request.getIdArbitroCentral(), partidoActualizado.getIdArbitroCentral().getId());
        assertEquals(request.getIdArbitroAsistente1(), partidoActualizado.getIdArbitroAsistente1().getId());
        assertEquals(request.getIdArbitroAsistente2(), partidoActualizado.getIdArbitroAsistente2().getId());
        assertEquals(request.getIdCuartoArbitro(), partidoActualizado.getIdCuartoArbitro().getId());
        assertEquals(request.getIdStatus(), partidoActualizado.getIdStatus().getId());
        assertEquals(request.getFecha(), partidoActualizado.getFecha());


        verify(estadioService).findById(request.getIdEstadio());
        verify(arbitroService).findById(request.getIdArbitroCentral());
        verify(arbitroService).findById(request.getIdArbitroAsistente1());
        verify(arbitroService).findById(request.getIdArbitroAsistente2());
        verify(arbitroService).findById(request.getIdCuartoArbitro());
        verify(catalogosService).findStatusEntityById(request.getIdStatus());
    }

    @Test
    public void obtenerMarcadorPartido_mustReturnMarcadorSucessfully(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        short local = 4;
        short visitante = 2;
        Partido partido = PartidoTestDataBuilder.aPartido()
                .withId(uuid)
                .withLocal(ClubTestDataBuilder.aClub()
                        .withId(local)
                        .build())
                .withVisitante(ClubTestDataBuilder.aClub()
                        .withId(visitante)
                        .build())
                .build();
        getMarcadorPartido marcadorPartidoLocal = mock(getMarcadorPartido.class);
        when(marcadorPartidoLocal.getGoles()).thenReturn((short)5);

        getMarcadorPartido marcadorPartidoVisitante = mock(getMarcadorPartido.class);
        when(marcadorPartidoVisitante.getGoles()).thenReturn((short)1);

        when(partidoRepository.obtenerMarcador(partido.getId()))
                .thenReturn(List.of(marcadorPartidoLocal,marcadorPartidoVisitante));
        //Act
        List<getMarcadorPartido> marcador = partidoService.obtenerMarcadorPartido(partido.getId());

        //Assert
        assertNotNull(marcador);
        assertEquals(2, marcador.size());
        assertEquals(5, marcador.get(0).getGoles().shortValue());
        assertEquals(1,marcador.get(1).getGoles().shortValue());

        verify(partidoRepository).obtenerMarcador(partido.getId());
    }

    @Test
    public void finalizarPartido_mustChangeStatusMarcadorAndTarjetas(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        short local = 1;
        short visitante = 2;
        short estadio = 10;
        short status = 1;
        long torneo = 2;
        PartidoFinalizadoEvent event = new PartidoFinalizadoEvent(uuid);

        Partido partido = PartidoTestDataBuilder.aPartido()
                .withId(uuid)
                .withLocal(ClubTestDataBuilder.aClub()
                        .withId(local)
                        .build())
                .withVisitante(ClubTestDataBuilder.aClub()
                        .withId(visitante)
                        .build())
                .withEstadio(EstadioTestDataBuilder.anEstadio()
                        .withId(estadio)
                        .build())
                .withStatus(StatusTestDataBuilder.aStatus()
                        .withId(status)
                        .build())
                .withTorneo(TorneoTestDataBuilder.aTorneo()
                        .withId(torneo)
                        .build())
                .withArbitroCentral(ArbitroTestDataBuilder.anArbitro()
                        .withId(1L)
                        .build())
                .withArbitroAsistente1(ArbitroTestDataBuilder.anArbitro()
                        .withId(2L)
                        .build())
                .withArbitroAsistente2(ArbitroTestDataBuilder.anArbitro()
                        .withId(3L)
                        .build())
                .withCuartoArbitro(ArbitroTestDataBuilder.anArbitro()
                        .withId(4L)
                        .build())
                .withStatus(StatusTestDataBuilder.aStatus()
                        .withId((short)1)
                        .build())
                .build();

        Status statusFinalizado = StatusTestDataBuilder.aStatus()
                .withId(Estados.FINALIZADO.getCodigo())
                .build();

        when(partidoRepository.findById(uuid)).thenReturn(Optional.of(partido));

        when(catalogosService.findStatusEntityById(Estados.FINALIZADO.getCodigo())).thenReturn(statusFinalizado);

        getMarcadorPartido marcadorLocal = mock(getMarcadorPartido.class);
        when(marcadorLocal.getIdClub()).thenReturn(local);
        when(marcadorLocal.getGoles()).thenReturn((short)2);

        getMarcadorPartido marcadorVisitante = mock(getMarcadorPartido.class);
        when(marcadorVisitante.getIdClub()).thenReturn(visitante);
        when(marcadorVisitante.getGoles()).thenReturn((short)3);


        when(partidoRepository.obtenerMarcador(uuid))
                .thenReturn(List.of(marcadorLocal,marcadorVisitante));

        //Act
        partidoService.finalizarPartido(event);

        //Assert
        assertEquals(Estados.FINALIZADO.getCodigo(), partido.getIdStatus().getId());
        assertEquals(marcadorLocal.getGoles(), partido.getGolesLocal());
        assertEquals(marcadorVisitante.getGoles(), partido.getGolesVisitante());

        verify(jugadorService).updateTarjetasByPartidoId(uuid);
        verify(partidoRepository).saveAndFlush(partido);
    }

    @Test
    public void obtenerMarcadorPartido_mustThrowNoSuchElementException(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        when(partidoRepository.findById(uuid)).thenReturn(Optional.empty());

        //Act
        NoSuchElementException noSuchElementException = assertThrows(
                NoSuchElementException.class,
                ()-> partidoService.findById(uuid));

        //Assert
        assertEquals("No se encontro el partido", noSuchElementException.getMessage());
        verify(partidoRepository).findById(uuid);
        verifyNoMoreInteractions(partidoRepository);
    }


}
