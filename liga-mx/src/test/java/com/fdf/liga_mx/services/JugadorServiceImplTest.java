package com.fdf.liga_mx.services;


import com.fdf.liga_mx.mappers.*;
import com.fdf.liga_mx.models.dtos.request.JugadorRequest;
import com.fdf.liga_mx.models.dtos.response.JugadorResponseDto;
import com.fdf.liga_mx.models.entitys.Club;
import com.fdf.liga_mx.models.entitys.Jugador;
import com.fdf.liga_mx.models.entitys.Posicion;
import com.fdf.liga_mx.models.enums.Estados;
import com.fdf.liga_mx.repository.JugadorRepository;
import com.fdf.liga_mx.testdata.ClubTestDataBuilder;
import com.fdf.liga_mx.testdata.JugadorRequestTestDataBuilder;
import com.fdf.liga_mx.testdata.PosicionTestDataBuilder;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;
import java.util.NoSuchElementException;

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
        // TODO: Implement test
    }

    @Test
    void findAllDto_mustReturnListOfJugadorResponseDto() {
        // TODO: Implement test
    }

    @Test
    void findById_mustThrowNoSuchElementException_whenJugadorNotFound() {
        // TODO: Implement test
    }

    @Test
    void findById_mustReturnJugador_whenFound() {
        // TODO: Implement test
    }

    @Test
    void findDtoById_mustThrowNoSuchElementException_whenJugadorNotFound() {
        // TODO: Implement test
    }

    @Test
    void findDtoById_mustReturnJugadorResponseDto_whenFound() {
        // TODO: Implement test
    }

    @Test
    void update_mustThrowNoSuchElementException_whenJugadorNotFound() {
        // TODO: Implement test
    }

    @Test
    void update_mustUpdateJugadorSuccessfully_whenAllDataIsDifferentAndValid() {
        // TODO: Implement test
    }

    @Test
    void update_mustNotUpdateClubOrPosicion_whenTheyAreTheSameAsBefore() {
        // TODO: Implement test
    }

    @Test
    void searchJugador_mustReturnPagedJugadorResponseDto() {
        // TODO: Implement test
    }

    @Test
    void obtenerTarjetasJugadorPorTorneoId_mustReturnZeros_whenNoCardsOrFouls() {
        // TODO: Implement test
    }

    @Test
    void obtenerTarjetasJugadorPorTorneoId_mustReturnCorrectStats_whenDataExists() {
        // TODO: Implement test
    }

    @Test
    void updateTarjetasByPartidoId_mustUpdateCardsCorrectly_whenPartidoHasCards() {
        // TODO: Implement test
    }

    @Test
    void saveJugadorWithFile_mustReturnDtoSinClub_whenFileIsEmpty() {
        // TODO: Implement test
    }

    @Test
    void saveJugadorWithFile_mustUploadFileAndReturnDtoSinClub_whenFileIsNotEmpty() {
        // TODO: Implement test
    }

    @Test
    void liberarJugador_mustThrowNoSuchElementException_whenJugadorNotFound() {
        // TODO: Implement test
    }

    @Test
    void liberarJugador_mustThrowIllegalStateException_whenJugadorIsRetirado() {
        // TODO: Implement test
    }

    @Test
    void liberarJugador_mustThrowIllegalStateException_whenJugadorIsAlreadyAgenteLibre() {
        // TODO: Implement test
    }

    @Test
    void liberarJugador_mustLiberarJugadorSuccessfully_whenValid() {
        // TODO: Implement test
    }

}
