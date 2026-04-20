package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.*;
import com.fdf.liga_mx.models.dtos.request.PartidoRequest;
import com.fdf.liga_mx.models.dtos.response.PartidoResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.repository.PartidoRepository;
import com.fdf.liga_mx.testdata.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
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
                estadioService);
    }

    @RepeatedTest(153)
    public void savePartido_mustSavePartidoSuccessfully(){
        //Arrange
        PartidoRequest partidoRequest = new PartidoRequestTestDataBuilder().build();
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
        assertEquals(partidoRequest.getIdCuartoArbitro(), result.getIdCuartoArbitro().getId());;
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
        PartidoRequest partidoRequest = new PartidoRequestTestDataBuilder().withTorneo(torneo).build();
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
        Instant from = Instant.parse("2027-01-01T00:00:00Z");
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
    public void update_mustUpdatePartido_whenDataisValid(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        PartidoRequest request = new PartidoRequestTestDataBuilder().build();

        Club local = Club.builder().id(request.getIdLocal()).build();
        Club visitante = Club.builder().id(request.getIdVisitante()).build();
        Estadio estadio = Estadio.builder().id(request.getIdEstadio()).build();
        Arbitro arbitroCentral = Arbitro.builder().id(request.getIdArbitroCentral()).build();
        Arbitro arbitroAsistente1 = Arbitro.builder().id(request.getIdArbitroAsistente1()).build();
        Arbitro arbitroAsistente2 = Arbitro.builder().id(request.getIdArbitroAsistente2()).build();
        Arbitro cuartoArbitro = Arbitro.builder().id(request.getIdCuartoArbitro()).build();
        Status status = Status.builder().id(request.getIdStatus()).build();
        Torneo torneo = Torneo.builder()
                .id(request.getIdTorneo())
                .status((short) 1)
                .build();

        Partido partido = PartidoTestDataBuilder.aPartido()
                .withId(uuid)
                .withLocal(local)
                .withVisitante(visitante)
                .withEstadio(estadio)
                .withArbitroCentral(arbitroCentral)
                .withArbitroAsistente1(arbitroAsistente1)
                .withArbitroAsistente2(arbitroAsistente2)
                .withCuartoArbitro(cuartoArbitro)
                .withStatus(status)
                .withTorneo(torneo)
                .build();


        //when(clubService.findById(request.getIdLocal())).thenReturn(local);
        //when(clubService.findById(request.getIdVisitante())).thenReturn(visitante);
        //when(estadioService.findById(request.getIdEstadio())).thenReturn(estadio);
        when(arbitroService.findById(request.getIdArbitroCentral())).thenReturn(arbitroCentral);
        when(arbitroService.findById(request.getIdArbitroAsistente1())).thenReturn(arbitroAsistente1);
        //when(arbitroService.findById(request.getIdArbitroAsistente2())).thenReturn(arbitroAsistente2);
        //when(arbitroService.findById(request.getIdCuartoArbitro())).thenReturn(cuartoArbitro);
        //when(catalogosService.findStatusEntityById(request.getIdStatus())).thenReturn(status);
        //when(catalogosService.findTorneoEntityById(request.getIdTorneo())).thenReturn(torneo);

        when(partidoRepository.findById(uuid)).thenReturn(Optional.of(partido));

        when(partidoRepository.saveAndFlush(any(Partido.class)))
                .thenAnswer(match -> match.getArgument(0));

        ArgumentCaptor<Partido> captor = ArgumentCaptor.forClass(Partido.class);

        //Act
        PartidoResponseDto response = partidoService.update(request,uuid);

        //Assert
        assertNotNull(response);
        assertEquals(request.getIdLocal(), response.getIdLocal().getId());
        assertEquals(request.getIdVisitante(), response.getIdVisitante().getId());
        /*
        assertEquals(request.getIdArbitroCentral(), response.getIdArbitroCentral().getId());
        assertEquals(request.getIdArbitroAsistente1(), response.getIdArbitroAsistente1().getId());
        assertEquals(request.getIdArbitroAsistente2(), response.getIdArbitroAsistente2().getId());
        assertEquals(request.getIdCuartoArbitro(), response.getIdCuartoArbitro().getId());
        assertEquals(request.getFecha(), response.getFecha());
        assertEquals(request.getIdStatus(), response.getIdStatus().getId());
        assertEquals(request.getIdTorneo(), response.getIdTorneo().getId());
        */
        verify(partidoRepository).saveAndFlush(captor.capture());
        /*
        verify(clubService).findById(request.getIdLocal());
        verify(clubService).findById(request.getIdVisitante());

         */
        //verify(estadioService).findById(request.getIdEstadio());
        verify(arbitroService).findById(request.getIdArbitroCentral());
        verify(arbitroService).findById(request.getIdArbitroAsistente1());
        //verify(arbitroService).findById(request.getIdArbitroAsistente2());
        //verify(arbitroService).findById(request.getIdCuartoArbitro());
        //verify(catalogosService).findStatusById(request.getIdStatus());
        //verify(catalogosService).findTorneoEntityById(request.getIdTorneo());

    }
}
