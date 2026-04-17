package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.*;
import com.fdf.liga_mx.models.dtos.request.PartidoRequest;
import com.fdf.liga_mx.models.dtos.response.PartidoResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.repository.PartidoRepository;
import com.fdf.liga_mx.testdata.PartidoRequestTestDataBuilder;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DateTimeException;
import java.time.Instant;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PartidoServiceImplTest {
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

    @Test
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
        Torneo torneo = Torneo.builder().id(partidoRequest.getIdTorneo()).build();

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
    public void savePartido_mustThrowException_ifDateIsNotValid(){
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


}
