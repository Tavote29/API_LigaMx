package com.fdf.liga_mx.services;


import com.fdf.liga_mx.mappers.*;
import com.fdf.liga_mx.models.dtos.projection.TarjetasResumenPorTorneo;
import com.fdf.liga_mx.models.dtos.projection.getTarjetasPorPartido;
import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Nacionalidad;
import com.fdf.liga_mx.models.entitys.Posicion;
import com.fdf.liga_mx.models.enums.Estados;
import com.fdf.liga_mx.repository.JugadorRepository;
import com.fdf.liga_mx.testdata.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class JugadorServiceImplTest {


    private JugadorServiceImpl jugadorService;

    @Mock
    private IClubService clubService;
    @Mock
    private JugadorRepository jugadorRepository;
    @Mock
    private ICatalogosService catalogosService;
    @Mock
    private MediaStorageService mediaService;



    private static final Faker faker = new Faker();

   @BeforeEach
   void setUp() {

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

      jugadorService = new JugadorServiceImpl(
              clubService,
              jugadorRepository,
              jugadorMapper,
              personaMapper,
              catalogosService,
              mediaService
      );
   }


    @Test
    void saveJugador_mustThrowNoSuchElementException_ifClubNoExists(){

        //Arrange

       Short idClub = 99;

       JugadorRequest jugadorRequest = JugadorRequest.builder().id_club(idClub).build();

       when(clubService.findById(idClub))
               .thenThrow(new NoSuchElementException("Club no encontrado"));

        //Act

       NoSuchElementException excepcionCapturada = assertThrows(
               NoSuchElementException.class,
               () -> jugadorService.save(jugadorRequest));


        //Assert
       assertEquals("Club no encontrado", excepcionCapturada.getMessage());

       verify(clubService).findById(idClub);

       verifyNoInteractions(catalogosService);
       verifyNoInteractions(jugadorRepository);





    }

    @Test
    void saveJugador_mustThrowException_ifPosicionNoExists() {
       //Arrange

       JugadorRequest jugadorRequest = new JugadorRequestTestDataBuilder().build();

       Club club = Club.builder().id(jugadorRequest.getId_club()).build();

       when(clubService.findById(jugadorRequest.getId_club()))
               .thenReturn(club);

       when(catalogosService.findPosicionEntityById(jugadorRequest.getId_posicion()))
               .thenThrow(new NoSuchElementException("No se encontro la posicion"));

       //Act

       NoSuchElementException exception = assertThrows(NoSuchElementException.class,
               () -> jugadorService.save(jugadorRequest));


       //Assert

       assertEquals("No se encontro la posicion", exception.getMessage());

       verify(clubService).findById(jugadorRequest.getId_club());

       verifyNoInteractions(jugadorRepository);




    }

    @Test
    void saveJugador_mustSaveJugadorSuccessfully_whenValidDataIsProvided() {
       //Arrange

       JugadorRequest jugadorRequest = new JugadorRequestTestDataBuilder().build();

       Club club = new ClubTestDataBuilder().withId(jugadorRequest.getId_club()).build();

       Posicion posicion = new PosicionTestDataBuilder().withId(jugadorRequest.getId_posicion()).build();

       when(clubService.findById(jugadorRequest.getId_club()))
               .thenReturn(club);

       when(catalogosService.findPosicionEntityById(jugadorRequest.getId_posicion()))
               .thenReturn(posicion);

       when(jugadorRepository.saveAndFlush(any(Jugador.class))).thenAnswer(jug -> {
          Jugador jugador = jug.getArgument(0);

          jugador.setId(faker.number().randomNumber());

          jugador.setStatus(Estados.ACTIVO.getCodigo());

          return jugador;
       });

       ArgumentCaptor<Jugador> captor = ArgumentCaptor.forClass(Jugador.class);

       //Act

       JugadorResponseDto result = jugadorService.save(jugadorRequest);


       //Assert

       assertNotNull(result);

       assertEquals(jugadorRequest.getPersona().getNombre(), result.getIdPersona().getNombre());
       assertEquals(jugadorRequest.getDorsal(), result.getDorsal());
       assertEquals(jugadorRequest.getId_posicion(), result.getIdPosicion().getId());
       assertEquals(jugadorRequest.getId_club(), result.getIdClub().getId());

       verify(jugadorRepository).saveAndFlush(captor.capture());

       Jugador jugadorSaved = captor.getValue();

       assertNotNull(jugadorSaved);

       assertSame(club,jugadorSaved.getIdClub());
       assertSame(posicion,jugadorSaved.getIdPosicion());

       assertEquals(jugadorRequest.getPersona().getNombre(), jugadorSaved.getIdPersona().getNombre());
       assertNotNull(jugadorSaved.getId());
       assertEquals(Estados.ACTIVO.getCodigo(), jugadorSaved.getStatus());


       verify(clubService).findById(jugadorRequest.getId_club());
       verify(catalogosService).findPosicionEntityById(jugadorRequest.getId_posicion());

    }

    @Test
    void findAll_mustReturnListOfJugadores() {
       //Arrange

        Jugador jugador1 = JugadorTestDataBuilder.aJugador().build();

        Jugador jugador2 = JugadorTestDataBuilder.aJugador().build();

        Jugador jugador3 = JugadorTestDataBuilder.aJugador().build();

        when(jugadorRepository.findAll()).thenReturn(List.of(jugador1,jugador2,jugador3));

       //Act

        List<Jugador> jugadorList = jugadorService.findAll();

       //Assert

        assertNotNull(jugadorList);
        assertEquals(3, jugadorList.size());


    }

    @Test
    void findAllDto_mustReturnListOfJugadorResponseDto() {
        //Arrange

        Jugador jugador1 = JugadorTestDataBuilder.aJugador().build();

        Jugador jugador2 = JugadorTestDataBuilder.aJugador().build();

        Jugador jugador3 = JugadorTestDataBuilder.aJugador().build();

        when(jugadorRepository.findAll()).thenReturn(List.of(jugador1,jugador2,jugador3));

        //Act

        List<JugadorResponseDto> jugadorList = jugadorService.findAllDto();

        //Assert

        assertNotNull(jugadorList);
        assertEquals(3, jugadorList.size());
    }

    @Test
    void findById_mustThrowNoSuchElementException_whenJugadorNotFound() {
        //Arrange

        Long id = faker.number().randomNumber();

        when(jugadorRepository.findById(id))
                .thenReturn(Optional.empty());
        //Act

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> jugadorService.findById(id));

        //Assert

        assertEquals("No se encontro el jugador", exception.getMessage());
    }

    @Test
    void findById_mustReturnJugador_whenFound() {
        //Arrange

        Long id = faker.number().randomNumber();

        Jugador jugador = JugadorTestDataBuilder.aJugador().withId(id).build();

        when(jugadorRepository.findById(id))
                .thenReturn(Optional.of(jugador));

        //Act

        Jugador result = jugadorService.findById(id);

        //Assert

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertSame(jugador, result);

    }

    @Test
    void findDtoById_mustThrowNoSuchElementException_whenJugadorNotFound() {
        //Arrange

        Long id = faker.number().randomNumber();

        when(jugadorRepository.findById(id))
                .thenReturn(Optional.empty());
        //Act

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> jugadorService.findDtoById(id));

        //Assert

        assertEquals("No se encontro el jugador", exception.getMessage());
    }

    @Test
    void findDtoById_mustReturnJugadorResponseDto_whenFound() {
        //Arrange

        Long id = faker.number().randomNumber();

        Jugador jugador = JugadorTestDataBuilder.aJugador().withId(id).build();

        when(jugadorRepository.findById(id))
                .thenReturn(Optional.of(jugador));

        //Act

        JugadorResponseDto result = jugadorService.findDtoById(id);

        //Assert

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(jugador.getIdPersona().getNombre(), result.getIdPersona().getNombre());
        assertEquals(jugador.getDorsal(), result.getDorsal());
        assertEquals(jugador.getIdPosicion().getId(), result.getIdPosicion().getId());


    }

    @Test
    void update_mustThrowNoSuchElementException_whenJugadorNotFound() {
        //Arrange

        Long id = faker.number().randomNumber();

        JugadorRequest jugadorRequest = new JugadorRequest();

        when(jugadorRepository.findById(id))
                .thenReturn(Optional.empty());
        //Act

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> jugadorService.update(jugadorRequest,id));

        //Assert

        assertEquals("No se encontro el jugador", exception.getMessage());

        verifyNoInteractions(catalogosService);

        verifyNoInteractions(clubService);

        verify(jugadorRepository,never()).saveAndFlush(any());
    }

    @Test
    void update_mustUpdateJugadorSuccessfully_whenAllDataIsDifferentAndValid() {
        // TODO: Implement test
    }

    @Test
    void update_mustThrowNoSuchElementException_whenNacionalidadNotFound() {
        //Arrange

        Long id = faker.number().randomNumber();

        JugadorRequest jugadorRequest = JugadorRequestTestDataBuilder.aJugadorRequest().build();

        Jugador jugador = new Jugador();

        when(jugadorRepository.findById(id))
                .thenReturn(Optional.of(jugador));

        when(catalogosService.findNacionalidadEntityById(jugadorRequest.getPersona().getIdNacionalidad()))
                .thenThrow(new NoSuchElementException("No se encontro la nacionalidad"));

        //Act

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> jugadorService.update(jugadorRequest,id));

        //Assert

        assertEquals("No se encontro la nacionalidad", exception.getMessage());

        verify(jugadorRepository).findById(id);

        verifyNoInteractions(clubService);

        verify(jugadorRepository,never()).saveAndFlush(any());
    }

    @Test
    void update_mustThrowNoSuchElementException_whenStatusNotFound() {

        //Arrange

        Long id = faker.number().randomNumber();

        JugadorRequest jugadorRequest = JugadorRequestTestDataBuilder.aJugadorRequest().build();

        Jugador jugador = new Jugador();

        Nacionalidad nacionalidad = new Nacionalidad();

        when(jugadorRepository.findById(id))
                .thenReturn(Optional.of(jugador));

        when(catalogosService.findNacionalidadEntityById(jugadorRequest.getPersona().getIdNacionalidad()))
                .thenReturn(nacionalidad);

        when(catalogosService.findStatusEntityById(jugadorRequest.getPersona().getIdStatus()))
                .thenThrow(new NoSuchElementException("No se encontro el status"));

        //Act

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> jugadorService.update(jugadorRequest,id));

        //Assert

        assertEquals("No se encontro el status", exception.getMessage());


        verify(jugadorRepository).findById(id);

        verify(catalogosService).findNacionalidadEntityById(jugadorRequest.getPersona().getIdNacionalidad());

        verifyNoInteractions(clubService);

        verify(jugadorRepository,never()).saveAndFlush(any());

    }

    @Test
    void searchJugador_mustReturnPagedJugadorResponseDto() {
        //Arrange

        int page = 0;

        int size=10;

        String sorts = "nombre_jugador,asc;";

        Short nacionalidadId = 106;


        Nacionalidad nacionalidad = NacionalidadTestDataBuilder.aNacionalidad().withId(nacionalidadId).build();

        Jugador jugador1 = JugadorTestDataBuilder.aJugador()
                            .withIdPersona(PersonaTestDataBuilder
                                    .aPersona()
                                    .withIdNacionalidad(nacionalidad)
                                    .build())
                            .build();

        Jugador jugador2 = JugadorTestDataBuilder.aJugador()
                .withIdPersona(PersonaTestDataBuilder
                        .aPersona()
                        .withIdNacionalidad(nacionalidad)
                        .build())
                .build();

        Jugador jugador3 = JugadorTestDataBuilder.aJugador()
                .withIdPersona(PersonaTestDataBuilder
                        .aPersona()
                        .withIdNacionalidad(nacionalidad)
                        .build())
                .build();

        Page<Jugador> pageJugador = new PageImpl<>(List.of(jugador1,jugador2,jugador3));

        when(jugadorRepository.searchJugador(any(Pageable.class),eq(null),eq(nacionalidadId),eq(null)))
                .thenReturn(pageJugador);


        //Act

        Page<JugadorResponseDto> result = jugadorService.searchJugador(page,size,sorts,null,nacionalidadId,null);


        //Assert
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(jugadorRepository).searchJugador(captor.capture(),eq(null),eq(nacionalidadId),eq(null));

        assertEquals(page,captor.getValue().getPageNumber());
        assertEquals(size,captor.getValue().getPageSize());
        assertEquals(1,captor.getValue().getSort().toList().size());
        assertEquals(jugador1.getIdPersona().getIdNacionalidad().getId(),result.getContent().get(0).getIdPersona().getIdNacionalidad().getId());

    }


    @Test
    void searchJugador_mustReturnIllegalArgumentException_whenSortsAreInvalid() {

       //Arrange

       int page = 0;

       int size=10;

       String invalidSort = "ordenamiento_invalido";

       //Act

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()-> jugadorService.searchJugador(page,size,invalidSort,null,null,null));


        //Assert
        assertEquals("Formato invalido en parámetro de ordenamiento. Se espera 'campo,direccion;'",exception.getMessage());

        verifyNoInteractions(jugadorRepository);


    }

    @Test
    void obtenerTarjetasJugadorPorTorneoId_mustReturnZeros_whenNoCardsOrFouls() {

       //Arrange

       Long jugadorId = faker.number().randomNumber();

       Long torneoId = faker.number().randomNumber();

       ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

       Map<String,Integer> tarjetas = new HashMap<>();

       tarjetas.put("getTarjetas_amarillas",null);
       tarjetas.put("getTarjetas_rojas",null);
       tarjetas.put("getFaltas_cometidas",null);

       TarjetasResumenPorTorneo tarjetasPorTorneo = factory.createProjection(TarjetasResumenPorTorneo.class,tarjetas);

       when(jugadorRepository.obtenerTarjetasJugadorPorTorneoId(jugadorId,torneoId)).thenReturn(tarjetasPorTorneo);

       //Act

        Map<String,Integer> result = jugadorService.obtenerTarjetasJugadorPorTorneoId(jugadorId,torneoId);

        //Assert

        assertNotNull(result);
        assertEquals(0,result.get("tarjetas_amarillas"));
        assertEquals(0,result.get("tarjetas_rojas"));
        assertEquals(0,result.get("faltas_cometidas"));


    }

    @Test
    void obtenerTarjetasJugadorPorTorneoId_mustReturnCorrectStats_whenDataExists() {
        //Arrange

        Long jugadorId = faker.number().randomNumber();

        Long torneoId = faker.number().randomNumber();

        TarjetasResumenPorTorneo tarjetasPorTorneo = mock(TarjetasResumenPorTorneo.class);

        when(tarjetasPorTorneo.getTarjetas_amarillas()).thenReturn(6);
        when(tarjetasPorTorneo.getTarjetas_rojas()).thenReturn(2);
        when(tarjetasPorTorneo.getFaltas_cometidas()).thenReturn(8);


        when(jugadorRepository.obtenerTarjetasJugadorPorTorneoId(jugadorId, torneoId)).thenReturn(tarjetasPorTorneo);

        //Act

        Map<String,Integer> result = jugadorService.obtenerTarjetasJugadorPorTorneoId(jugadorId,torneoId);

        //Assert

        assertNotNull(result);
        assertEquals(6,result.get("tarjetas_amarillas"));
        assertEquals(2,result.get("tarjetas_rojas"));
        assertEquals(8,result.get("faltas_cometidas"));
    }

    @Test
    void updateTarjetasByPartidoId_mustUpdateCardsCorrectly_whenPartidoHasCards() {
        // Arrange

        UUID partidoId = UUID.randomUUID();

        Jugador jugador1 = JugadorTestDataBuilder.aJugador().build();

        Jugador jugador2 = JugadorTestDataBuilder.aJugador().build();

        getTarjetasPorPartido tarjetas1 = mock(getTarjetasPorPartido.class);

        when(tarjetas1.getId_jugador()).thenReturn(jugador1.getId());
        when(tarjetas1.getTarjetas_amarillas()).thenReturn(2);
        when(tarjetas1.getTarjetas_rojas()).thenReturn(1);

        getTarjetasPorPartido tarjetas2 = mock(getTarjetasPorPartido.class);

        when(tarjetas2.getId_jugador()).thenReturn(jugador2.getId());
        when(tarjetas2.getTarjetas_amarillas()).thenReturn(1);
        when(tarjetas2.getTarjetas_rojas()).thenReturn(0);

        List<getTarjetasPorPartido> tarjetas = List.of(tarjetas1, tarjetas2);

        when(jugadorRepository.obtenerTarjetasPorPartidoId(partidoId.toString())).thenReturn(tarjetas);

        when(jugadorRepository.findById(jugador1.getId())).thenReturn(Optional.of(jugador1));
        when(jugadorRepository.findById(jugador2.getId())).thenReturn(Optional.of(jugador2));

        when(jugadorRepository.save(any(Jugador.class))).thenAnswer(inv -> inv.getArgument(0));

        ArgumentCaptor<Jugador> captor = ArgumentCaptor.forClass(Jugador.class);

        // Act

        jugadorService.updateTarjetasByPartidoId(partidoId);


        //Assert

        verify(jugadorRepository,times(2)).save(captor.capture());

        List<Jugador> jugadorList = captor.getAllValues();

        assertEquals((short) 2, jugadorList.get(0).getTarjetasAmarillas());
        assertEquals((short) 1, jugadorList.get(0).getTarjetasRojas());

        assertEquals((short) 1, jugadorList.get(1).getTarjetasAmarillas());
        assertEquals((short) 0, jugadorList.get(1).getTarjetasRojas());



    }

    @Test
    void saveJugadorWithFile_mustReturnDtoSinClub_whenFileIsEmpty() throws IOException {
       //Arrange

        JugadorRequest jugadorRequest = JugadorRequestTestDataBuilder.aJugadorRequest().build();

        MockMultipartFile archivoVacio = new MockMultipartFile(
                "imagen",               // Nombre del parámetro (el form-data key)
                null,         // Nombre original del archivo
                null,         // Tipo de contenido (MIME type)
                new byte[0]           // <--- ESTO HACE QUE isEmpty() DEVUELVA TRUE
        );

        Club club = ClubTestDataBuilder.aClub()
                .withId(jugadorRequest.getId_club())
                .build();

        Posicion posicion = PosicionTestDataBuilder.aPosicion()
                .withId(jugadorRequest.getId_posicion())
                .build();

        when(clubService.findById(jugadorRequest.getId_club())).thenReturn(club);
        when(catalogosService.findPosicionEntityById(jugadorRequest.getId_posicion())).thenReturn(posicion);
        when(jugadorRepository.saveAndFlush(any(Jugador.class))).thenAnswer(inv -> {

            return JugadorTestDataBuilder.aJugador().fromRequest(jugadorRequest).build();
        });

        ArgumentCaptor<Jugador> captor = ArgumentCaptor.forClass(Jugador.class);

        //Act
        JugadorResponseDto result = jugadorService.save(jugadorRequest,archivoVacio);

        //Assert

        assertNotNull(result);
        assertNull(result.getIdClub());
        assertNull(result.getImage());
        assertEquals(jugadorRequest.getId_posicion(),result.getIdPosicion().getId());
        assertEquals(jugadorRequest.getPersona().getNombre(),result.getIdPersona().getNombre());

        verify(jugadorRepository).saveAndFlush(captor.capture());

        Jugador jugadorSaved = captor.getValue();

        assertNotNull(jugadorSaved);
        assertSame(club,jugadorSaved.getIdClub());
        assertSame(posicion,jugadorSaved.getIdPosicion());
        assertEquals(jugadorRequest.getPersona().getNombre(),jugadorSaved.getIdPersona().getNombre());



        verifyNoInteractions(mediaService);
        verify(jugadorRepository,never()).save(any(Jugador.class));

    }

    @Test
    void saveJugadorWithFile_mustUploadFileAndReturnDtoSinClub_whenFileIsNotEmpty() throws IOException {
        //Arrange

        JugadorRequest jugadorRequest = JugadorRequestTestDataBuilder.aJugadorRequest().build();

        MockMultipartFile file = new MockMultipartFile(
                "imagen",               // Nombre del parámetro (el form-data key)
                "imagen.jpg",         // Nombre original del archivo
                "image/jpeg",         // Tipo de contenido (MIME type)
               "EXAMPLE DATA".getBytes()           // <--- ESTO HACE QUE isEmpty() DEVUELVA TRUE
        );

        Club club = ClubTestDataBuilder.aClub()
                .withId(jugadorRequest.getId_club())
                .build();

        Posicion posicion = PosicionTestDataBuilder.aPosicion()
                .withId(jugadorRequest.getId_posicion())
                .build();

        Jugador jugadorCreated = JugadorTestDataBuilder.aJugador().fromRequest(jugadorRequest).build();

        when(clubService.findById(jugadorRequest.getId_club())).thenReturn(club);
        when(catalogosService.findPosicionEntityById(jugadorRequest.getId_posicion())).thenReturn(posicion);
        when(jugadorRepository.saveAndFlush(any(Jugador.class))).thenAnswer(inv -> {

            return jugadorCreated;
        });
        when(jugadorRepository.save(any(Jugador.class))).thenAnswer(inv -> inv.getArgument(0));

        when(mediaService.uploadFile(file,jugadorCreated.getIdPersona().getId().toString()))
                .thenReturn(jugadorCreated.getIdPersona().getId().toString()+".jpg");


        ArgumentCaptor<Jugador> captor = ArgumentCaptor.forClass(Jugador.class);

        //Act
        JugadorResponseDto result = jugadorService.save(jugadorRequest,file);

        //Assert

        assertNotNull(result);
        assertNotNull(result.getImage());
        assertNull(result.getIdClub());
        assertEquals(jugadorRequest.getId_posicion(),result.getIdPosicion().getId());
        assertEquals(jugadorRequest.getPersona().getNombre(),result.getIdPersona().getNombre());

        verify(jugadorRepository).save(captor.capture());

        Jugador jugadorSaved = captor.getValue();

        assertNotNull(jugadorSaved);
        assertEquals(club.getId(),jugadorSaved.getIdClub().getId());
        assertEquals(posicion.getId(),jugadorSaved.getIdPosicion().getId());
        assertEquals(jugadorRequest.getPersona().getNombre(),jugadorSaved.getIdPersona().getNombre());

        verify(clubService).findById(jugadorRequest.getId_club());
        verify(catalogosService).findPosicionEntityById(jugadorRequest.getId_posicion());
        verify(mediaService).uploadFile(file,jugadorSaved.getIdPersona().getId().toString());



    }

    @Test
    void liberarJugador_mustThrowNoSuchElementException_whenJugadorNotFound() {
        // Arrange
        Long jugadorId = 1L;
        when(jugadorRepository.findById(jugadorId)).thenReturn(Optional.empty());

        // Act
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> jugadorService.liberarJugador(jugadorId));

        // Assert
        assertEquals("No se encontro el jugador", exception.getMessage());
        verify(jugadorRepository).findById(jugadorId);
        verify(jugadorRepository, never()).save(any(Jugador.class));
    }

    @Test
    void liberarJugador_mustThrowIllegalStateException_whenJugadorIsRetirado() {
        // Arrange
        Long jugadorId = 1L;
        Jugador jugador = JugadorTestDataBuilder.aJugador().withStatus(Estados.RETIRADO.getCodigo())
                            .withId(jugadorId)
                            .build();

        when(jugadorRepository.findById(jugadorId)).thenReturn(Optional.of(jugador));

        // Act
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> jugadorService.liberarJugador(jugadorId));

        // Assert
        assertEquals("Jugador ya se encuentra libre de competencia", exception.getMessage());
        verify(jugadorRepository).findById(jugadorId);
        verify(jugadorRepository, never()).save(any(Jugador.class));
    }

    @Test
    void liberarJugador_mustThrowIllegalStateException_whenJugadorIsAlreadyAgenteLibre() {
        // Arrange
        Long jugadorId = 1L;
        Jugador jugador = JugadorTestDataBuilder.aJugador().withStatus(Estados.AGENTE_LIBRE.getCodigo())
                            .withId(jugadorId)
                            .build();
        when(jugadorRepository.findById(jugadorId)).thenReturn(Optional.of(jugador));

        // Act
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> jugadorService.liberarJugador(jugadorId));

        // Assert
        assertEquals("Jugador ya se encuentra libre de competencia", exception.getMessage());
        verify(jugadorRepository).findById(jugadorId);
        verify(jugadorRepository, never()).save(any(Jugador.class));
    }

    @Test
    void liberarJugador_mustLiberarJugadorSuccessfully_whenValid() {
        // Arrange
        Long jugadorId = 1L;
        Jugador jugador = JugadorTestDataBuilder.aJugador()
                .withStatus(Estados.ACTIVO.getCodigo())
                .withTarjetasAmarillas((short) 5)
                .withId(jugadorId)
                .withTarjetasRojas((short) 1).build();

        when(jugadorRepository.findById(jugadorId)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(any(Jugador.class))).thenAnswer(inv -> inv.getArgument(0));

        ArgumentCaptor<Jugador> captor = ArgumentCaptor.forClass(Jugador.class);

        // Act
        jugadorService.liberarJugador(jugadorId);

        // Assert
        verify(jugadorRepository).findById(jugadorId);
        verify(jugadorRepository).save(captor.capture());

        Jugador jugadorSaved = captor.getValue();
        assertEquals(Estados.AGENTE_LIBRE.getCodigo(), jugadorSaved.getStatus());
        assertEquals((short) 0, jugadorSaved.getTarjetasAmarillas());
        assertEquals((short) 0, jugadorSaved.getTarjetasRojas());
        assertNull(jugadorSaved.getIdClub());
    }

}