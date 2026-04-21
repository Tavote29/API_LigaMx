package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.*;
import com.fdf.liga_mx.models.dtos.request.ClubRequest;
import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.request.EstadioRequestDto;
import com.fdf.liga_mx.models.dtos.response.ClubResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.models.enums.Estados;
import com.fdf.liga_mx.repository.IClubRepository;
import com.fdf.liga_mx.repository.JugadorRepository;
import com.fdf.liga_mx.testdata.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class ClubServiceImpTest {

    private ClubServiceImpl clubService;

    @Mock
    private IClubRepository clubRepo;

    @Mock
    private ICatalogosService catalogosService;

    @Mock
    private IDTService dtService;

    @Mock
    private IEstadioService estadioService;

    @Mock
    private MediaStorageService storageService;
    @Mock
    private JugadorRepository jugadorRepository;

    private static final Faker faker = new Faker();

    @BeforeEach
    void setUp() {

        EstadoMapper estadoMapper = new EstadoMapper();
        CiudadMapper ciudadMapper = new CiudadMapper(estadoMapper);
        StatusMapper statusMapper = new StatusMapper();
        NacionalidadMapper nacionalidadMapper = new NacionalidadMapper();
        PersonaMapper personaMapper = new PersonaMapper(statusMapper, nacionalidadMapper);
        DTMapper dtMapper = new DTMapper(personaMapper);
        EstadioMapper estadioMapper = new EstadioMapper(estadoMapper, ciudadMapper);
        ClubMapper clubMapper = new ClubMapper(estadoMapper, ciudadMapper, dtMapper, estadioMapper);

        clubService = new ClubServiceImpl(
                clubRepo,
                clubMapper,
                catalogosService,
                dtService,
                estadioService,
                storageService,
                jugadorRepository
        );
    }

    @Test
    void changeStadium_mustThrowNoSuchElementException_whenClubNotFound() {
        // Arrange
        Short idClub = (short) faker.number().numberBetween(1,18);
        EstadioRequestDto request = EstadioRequestDto.builder().build();

        when(clubRepo.findByIdAndStatusIs(idClub, Estados.ACTIVO.getCodigo())).thenReturn(Optional.empty());

        // Act
        assertThrows(NoSuchElementException.class, () -> clubService.changeStadium(request, idClub));


        //Assert
        verify(clubRepo).findByIdAndStatusIs(idClub, Estados.ACTIVO.getCodigo());
        verifyNoInteractions(estadioService);
        verify(clubRepo,never()).save(any(Club.class));
    }

    @Test
    void changeStadium_mustAssignExistingStadiumToClub_whenEstadioIdIsProvided() {
       //Arrange

        Short idClub = (short) faker.number().numberBetween(1,18);

        Club club = ClubTestDataBuilder.aClub().withId(idClub).build();

        EstadioRequestDto estadioRequestDto = EstadioRequestDtoTestDataBuilder.anEstadioRequestDto()
                .withId((short)faker.number().randomDigit())
                .build();

        Estadio estadio = EstadioTestDataBuilder.anEstadio().fromRequest(estadioRequestDto).build();

        when(clubRepo.findByIdAndStatusIs(idClub, Estados.ACTIVO.getCodigo())).thenReturn(Optional.of(club));

        when(estadioService.findById(estadioRequestDto.getId())).thenReturn(estadio);

        when(clubRepo.saveAndFlush(any(Club.class))).thenAnswer(i -> i.getArgument(0));

        //Act
        ClubResponseDto clubResponseDto = clubService.changeStadium(estadioRequestDto,idClub);

        //Assert

        assertNotNull(clubResponseDto);
        assertEquals(estadio.getId(),clubResponseDto.getIdEstadio().getId());
        assertEquals(estadio.getNombreEstadio(),clubResponseDto.getIdEstadio().getNombreEstadio());



        verify(clubRepo).saveAndFlush(any(Club.class));
        verify(clubRepo).findByIdAndStatusIs(idClub, Estados.ACTIVO.getCodigo());
        verify(estadioService,never()).save(any(EstadioRequestDto.class));

    }

    @Test
    void changeStadium_mustCreateNewStadiumAndAssignToClub_whenEstadioIdIsNotProvided() {
        //Arrange

        Short idClub = (short) faker.number().numberBetween(1,18);

        Club club = ClubTestDataBuilder.aClub().withId(idClub).build();

        EstadioRequestDto estadioRequestDto = EstadioRequestDtoTestDataBuilder.anEstadioRequestDto()
                .build();

        Estadio estadio = EstadioTestDataBuilder.anEstadio().fromRequest(estadioRequestDto).build();

        short newIdEstadio = (short)faker.number().randomDigit();

        when(clubRepo.findByIdAndStatusIs(idClub, Estados.ACTIVO.getCodigo())).thenReturn(Optional.of(club));

        when(estadioService.save(any(EstadioRequestDto.class))).thenAnswer(i -> {

            estadio.setId(newIdEstadio);

            return EstadioResponseDtoTestDataBuilder
                    .anEstadioResponseDto()
                    .fromEntity(estadio)
                    .build();

        });

        when(estadioService.findById(newIdEstadio)).thenReturn(estadio);

        when(clubRepo.save(any(Club.class))).thenAnswer(i -> i.getArgument(0));

        //Act
        ClubResponseDto clubResponseDto = clubService.changeStadium(estadioRequestDto,idClub);



        //Assert

        assertNotNull(clubResponseDto);
        assertNotNull(clubResponseDto.getIdEstadio());

        assertEquals(estadioRequestDto.getNombreEstadio(),clubResponseDto.getIdEstadio().getNombreEstadio());
        assertEquals(estadioRequestDto.getDireccion(),clubResponseDto.getIdEstadio().getDireccion());
        assertEquals(estadioRequestDto.getCapacidad(),clubResponseDto.getIdEstadio().getCapacidad());

        verify(estadioService).save(any(EstadioRequestDto.class));

        verify(estadioService).findById(newIdEstadio);

        verify(clubRepo,never()).saveAndFlush(any(Club.class));
        verify(clubRepo).findByIdAndStatusIs(idClub, Estados.ACTIVO.getCodigo());
    }

    @Test
    void updateEscudo_mustThrowNoSuchElementException_whenClubNotFound() {
        // Arrange
        Short idClub = (short) faker.number().numberBetween(1,18);
        MultipartFile file = mock(MultipartFile.class);

        when(clubRepo.findById(idClub)).thenReturn(Optional.empty());

        // Act
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> clubService.updateEscudo(file, idClub));

        //Assert

        assertEquals("Club no encontrado",ex.getMessage());

        verify(clubRepo).findById(idClub);
        verifyNoInteractions(storageService);
    }

    @Test
    void updateEscudo_mustReplaceExistingFile_whenClubAlreadyHasImage() throws IOException {
        //Arrange
        short idClub = (short)faker.number().numberBetween(1,18);

        Club club = ClubTestDataBuilder.aClub().withId(idClub).build();

        MockMultipartFile file = new MockMultipartFile(
                "imagen",               // Nombre del parámetro (el form-data key)
                "imagen.jpg",         // Nombre original del archivo
                "image/jpeg",         // Tipo de contenido (MIME type)
                "EXAMPLE DATA".getBytes()           // <--- ESTO HACE QUE isEmpty() DEVUELVA TRUE
        );

        String newStorageKey = UUID.randomUUID() +".jpg";

        when(clubRepo.findById(idClub)).thenReturn(Optional.of(club));
        when(storageService.replaceFile(anyString(),anyString(),any(MultipartFile.class))).thenReturn(newStorageKey);
        when(clubRepo.save(any(Club.class))).thenAnswer(i -> i.getArgument(0));

        //Act
        ClubResponseDto clubResponseDto = clubService.updateEscudo(file, idClub);


        //Assert

        assertNotNull(clubResponseDto);
        assertEquals(idClub,clubResponseDto.getId());
        assertEquals(club.getNombreClub(),clubResponseDto.getNombreClub());

        assertEquals(newStorageKey,clubResponseDto.getImageUrl());

        verify(storageService).replaceFile(anyString(),anyString(),any(MultipartFile.class));

        verify(storageService,never()).uploadFile(any(MultipartFile.class),anyString());


    }

    @Test
    void updateEscudo_mustUploadNewFile_whenClubHasNoImage() throws IOException {
        //Arrange
        short idClub = (short)faker.number().numberBetween(1,18);

        Club club = ClubTestDataBuilder.aClub().withId(idClub)
                .withImageUrl(null)
                .build();

        MockMultipartFile file = new MockMultipartFile(
                "imagen",               // Nombre del parámetro (el form-data key)
                "imagen.jpg",         // Nombre original del archivo
                "image/jpeg",         // Tipo de contenido (MIME type)
                "EXAMPLE DATA".getBytes()           // <--- ESTO HACE QUE isEmpty() DEVUELVA TRUE
        );

        String newStorageKey = UUID.randomUUID() +".jpg";

        when(clubRepo.findById(idClub)).thenReturn(Optional.of(club));

        when(storageService.uploadFile(any(MultipartFile.class),anyString())).thenReturn(newStorageKey);

        when(clubRepo.save(any(Club.class))).thenAnswer(i -> i.getArgument(0));

        //Act
        ClubResponseDto clubResponseDto = clubService.updateEscudo(file, idClub);


        //Assert

        assertNotNull(clubResponseDto);
        assertEquals(idClub,clubResponseDto.getId());
        assertEquals(club.getNombreClub(),clubResponseDto.getNombreClub());

        assertEquals(newStorageKey,clubResponseDto.getImageUrl());

        verify(storageService).uploadFile(any(MultipartFile.class),anyString());

        verify(storageService,never()).replaceFile(anyString(),anyString(),any(MultipartFile.class));
    }

    @Test
    void assignDT_mustThrowNoSuchElementException_whenClubNotFound() {
        // Arrange
        short idClub = (short) faker.number().numberBetween(1,18);

        DTRequest dtRequest = DTRequest.builder().build();

        when(clubRepo.findById(idClub))
                .thenReturn(Optional.empty());

        // Act
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> clubService.assignDT(dtRequest, idClub));

        //Assert
        assertEquals("Club no encontrado", exception.getMessage());
        verify(clubRepo).findById(idClub);
        verify(clubRepo, never()).save(any(Club.class));
        verifyNoInteractions(dtService);
    }

    @Test
    void assignDT_mustAssignExistingDTToClub_whenDTNUI_IsProvided() {
        //Arrange

        short idClub = (short) faker.number().numberBetween(1,18);

        Club club = Club.builder().id(idClub).build();

        DTRequest dtRequest = DTRequestTestDataBuilder.aDTRequest()
                .withIdClub(null)
                .build();

        DT dt = DTTestDataBuilder.aDT()
                .withClub(null)
                .fromRequest(dtRequest).build();



        when(clubRepo.findById(idClub)).thenReturn(Optional.of(club));

        when(dtService.findById(dtRequest.getNUI_DT())).thenReturn(dt);

        when(clubRepo.save(any(Club.class))).thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<Club> captor = ArgumentCaptor.forClass(Club.class);

        //Act
        clubService.assignDT(dtRequest, idClub);

        //Assert
        verify(clubRepo).save(captor.capture());

        Club clubUpdated = captor.getValue();

        assertNotNull(clubUpdated);
        assertNotNull(clubUpdated.getIdDt());

        assertSame(dt,clubUpdated.getIdDt());

        assertEquals(dtRequest.getNUI_DT(),clubUpdated.getIdDt().getId());

        assertEquals(dtRequest.getPersona().getNombre(),clubUpdated.getIdDt().getPersona().getNombre());
        assertEquals(dtRequest.getPersona().getFechaNacimiento(),clubUpdated.getIdDt().getPersona().getFechaNacimiento());
        assertEquals(dtRequest.getPersona().getLugarNacimiento(),clubUpdated.getIdDt().getPersona().getLugarNacimiento());
        assertEquals(dtRequest.getPersona().getEstatura(),clubUpdated.getIdDt().getPersona().getEstatura());
        assertEquals(dtRequest.getPersona().getPeso(),clubUpdated.getIdDt().getPersona().getPeso());



        verify(clubRepo).findById(idClub);

        verify(dtService,never()).save(any(DTRequest.class));

        verify(dtService).findById(dtRequest.getNUI_DT());

        verify(clubRepo).save(any(Club.class));
    }

    @Test
    void assignDT_mustThrowIllegalStateException_whenDTStatusIsNotActive() {
        //Arrange

        short idClub = (short) faker.number().numberBetween(1,18);

        Club club = Club.builder().id(idClub).build();

        DTRequest dtRequest = DTRequestTestDataBuilder.aDTRequest()
                .withIdClub(null)
                .build();

        DT dt = DTTestDataBuilder.aDT().fromRequest(dtRequest)
                .withStatus(Estados.INACTIVO.getCodigo())
                .build();

        when(clubRepo.findById(idClub)).thenReturn(Optional.of(club));

        when(dtService.findById(dtRequest.getNUI_DT())).thenReturn(dt);

        //Act
        IllegalStateException ex = assertThrows(IllegalStateException.class,() -> clubService.assignDT(dtRequest, idClub));

        //Assert
        assertEquals("El status del DT no se encuentra en activo",ex.getMessage());

        verify(clubRepo,never()).save(any(Club.class));
        verify(dtService,never()).save(any(DTRequest.class));

    }

    @Test
    void assignDT_mustThrowIllegalArgumentException_whenDTAlreadyHasClub() {
        //Arrange

        short idClub = (short) faker.number().numberBetween(1,18);

        Club club = Club.builder().id(idClub).build();

        DTRequest dtRequest = DTRequestTestDataBuilder.aDTRequest().build();

        DT dt = DTTestDataBuilder.aDT().fromRequest(dtRequest)
                .build();

        when(clubRepo.findById(idClub)).thenReturn(Optional.of(club));

        when(dtService.findById(dtRequest.getNUI_DT())).thenReturn(dt);

        //Act
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,() -> clubService.assignDT(dtRequest, idClub));

        //Assert
        assertEquals("El DT ya tiene un club asignado",ex.getMessage());

        verify(clubRepo,never()).save(any(Club.class));

        verify(dtService,never()).save(any(DTRequest.class));
    }

    @Test
    void assignDT_mustCreateNewDTAndAssignToClub_whenDTNUI_IsNotProvided() {
        //Arrange

        short idClub = (short) faker.number().numberBetween(1,18);

        Club club = Club.builder().id(idClub).build();

        DTRequest dtRequest = DTRequestTestDataBuilder.aDTRequest()
                .withIdClub(null)
                .withNUIDT(null)
                .build();

        DT dt = DTTestDataBuilder.aDT().fromRequest(dtRequest)
                .build();

        long dtnewId = faker.number().randomNumber();

        when(clubRepo.findById(idClub)).thenReturn(Optional.of(club));

        when(dtService.save(any(DTRequest.class))).thenAnswer(i ->{

            dt.setId(dtnewId);
            dt.setClub(club);


            return DTResponseDtoTestDataBuilder.aDTResponseDto().fromEntity(dt).build();
        });


        when(dtService.findById(dtnewId)).thenReturn(dt);


        when(clubRepo.save(any(Club.class))).thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<Club> captor = ArgumentCaptor.forClass(Club.class);

        //Act
         clubService.assignDT(dtRequest, idClub);

        //Assert
        verify(clubRepo).save(captor.capture());

        Club clubUpdated = captor.getValue();

        assertNotNull(clubUpdated);
        assertNotNull(clubUpdated.getIdDt());
        assertSame(dt,clubUpdated.getIdDt());

        assertEquals(dtnewId,clubUpdated.getIdDt().getId());

        assertEquals(dtRequest.getPersona().getNombre(),clubUpdated.getIdDt().getPersona().getNombre());
        assertEquals(dtRequest.getPersona().getFechaNacimiento(),clubUpdated.getIdDt().getPersona().getFechaNacimiento());
        assertEquals(dtRequest.getPersona().getLugarNacimiento(),clubUpdated.getIdDt().getPersona().getLugarNacimiento());
        assertEquals(dtRequest.getPersona().getEstatura(),clubUpdated.getIdDt().getPersona().getEstatura());
        assertEquals(dtRequest.getPersona().getPeso(),clubUpdated.getIdDt().getPersona().getPeso());

        verify(clubRepo).findById(idClub);
        verify(dtService).save(any(DTRequest.class));
        verify(dtService).findById(dtnewId);
        verify(clubRepo).save(any(Club.class));
    }

    @Test
    void findByEstadoId_mustReturnListOfClubResponseDto() {
        // Arrange
        Short estadoId = (short) faker.number().numberBetween(1, 32);
        
        Estado estado = EstadoTestDataBuilder.anEstado().withId(estadoId).build();
        Ciudad ciudad = CiudadTestDataBuilder.aCiudad().withIdEstado(estado).build();

        Club club1 = ClubTestDataBuilder.aClub()
                .withId((short) 1)
                .withIdEstado(estado)
                .withIdCiudad(ciudad)
                .build();
                
        Club club2 = ClubTestDataBuilder.aClub()
                .withId((short) 2)
                .withIdEstado(estado)
                .withIdCiudad(ciudad)
                .build();
                
        List<Club> clubs = List.of(club1, club2);

        when(clubRepo.findByIdEstadoId(estadoId)).thenReturn(clubs);

        // Act
        List<ClubResponseDto> result = clubService.findByEstadoId(estadoId);

        // Assert
        assertNotNull(result);


        assertEquals(2, result.size());
        assertEquals(club1.getId(), result.get(0).getId());
        assertEquals(estado.getNombreEstado(), result.get(0).getIdCiudad().getIdEstado().getNombreEstado());
        assertEquals(club2.getId(), result.get(1).getId());
        assertEquals(estado.getNombreEstado(), result.get(1).getIdCiudad().getIdEstado().getNombreEstado());
        verify(clubRepo).findByIdEstadoId(estadoId);
    }

    @Test
    void findByEstadoId_mustReturnEmptyList_whenNoClubsInState() {
        // Arrange
        Short estadoId = (short) faker.number().numberBetween(1,32);

        when(clubRepo.findByIdEstadoId(estadoId))
                .thenReturn(List.of());

        // Act
        List<ClubResponseDto> result = clubService.findByEstadoId(estadoId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(clubRepo).findByIdEstadoId(estadoId);
    }

    @Test
    void findByCiudadId_mustReturnListOfClubResponseDto() {
        // Arrange
        Short ciudadId = (short) faker.number().randomDigit();
        
        Ciudad ciudad = Ciudad.builder().id(ciudadId).nombreCiudad(faker.address().cityName()).build();

        Club club1 = ClubTestDataBuilder.aClub()
                .withId((short) 1)
                .withIdCiudad(ciudad)
                .build();
                
        Club club2 = ClubTestDataBuilder.aClub()
                .withId((short) 2)
                .withIdCiudad(ciudad)
                .build();
                
        List<Club> clubs = List.of(club1, club2);

        when(clubRepo.findByIdCiudadId(ciudadId)).thenReturn(clubs);

        // Act
        List<ClubResponseDto> result = clubService.findByCiudadId(ciudadId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(club1.getId(), result.get(0).getId());
        assertEquals(ciudad.getNombreCiudad(), result.get(0).getIdCiudad().getNombreCiudad());
        assertEquals(club2.getId(), result.get(1).getId());
        assertEquals(ciudad.getNombreCiudad(), result.get(1).getIdCiudad().getNombreCiudad());
        verify(clubRepo).findByIdCiudadId(ciudadId);
    }

    @Test
    void findByCiudadId_mustReturnEmptyList_whenNoClubsInCity() {
        // Arrange
        Short ciudadId = (short) faker.number().randomDigit();

        when(clubRepo.findByIdCiudadId(ciudadId))
                .thenReturn(List.of());

        // Act
        List<ClubResponseDto> result = clubService.findByCiudadId(ciudadId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(clubRepo).findByIdCiudadId(ciudadId);
    }

    @Test
    void save_mustThrowNoSuchElementException_whenEstadoNotFound() {
        // Arrange
        ClubRequest request = ClubRequest.builder().idEstado((short) 1).build();

        when(catalogosService.findEstadoById(anyShort())).thenThrow(new NoSuchElementException("Estado no encontrado"));

        // Act
        assertThrows(NoSuchElementException.class, () -> clubService.save(request));

        // Assert
        verify(catalogosService).findEstadoById(anyShort());
        verify(catalogosService,never()).findCiudadById(anyShort());
        verifyNoInteractions(clubRepo);
    }

    @Test
    void save_mustThrowNoSuchElementException_whenCiudadNotFound() {
        // Arrange
        ClubRequest request = ClubRequest.builder().idEstado((short) 1).idCiudad((short) 1).build();

        when(catalogosService.findEstadoById(anyShort())).thenReturn(new Estado());
        when(catalogosService.findCiudadById(anyShort())).thenThrow(new NoSuchElementException("Ciudad no encontrada"));

        // Act
        assertThrows(NoSuchElementException.class, () -> clubService.save(request));

        //Assert
        verify(catalogosService).findEstadoById(anyShort());
        verify(catalogosService).findCiudadById(anyShort());
        verifyNoInteractions(clubRepo);
    }

    @Test
    void save_mustThrowIllegalArgumentException_whenDTAlreadyHasClub() {
        //Arrange
        ClubRequest request = ClubRequestTestDataBuilder.aClubRequest().build();

        Estado estado = EstadoTestDataBuilder.anEstado().withId(request.getIdEstado()).build();
        Ciudad ciudad = CiudadTestDataBuilder.aCiudad().withIdEstado(estado).build();

        DT dt = DTTestDataBuilder.aDT().build();

        when(catalogosService.findEstadoById(request.getIdEstado())).thenReturn(estado);
        when(catalogosService.findCiudadById(request.getIdCiudad())).thenReturn(ciudad);
        when(dtService.findById(request.getIdDt())).thenReturn(dt);

        //Act
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> clubService.save(request));

        //Assert
        assertEquals("El DT ya tiene un club asignado",ex.getMessage());


        verifyNoInteractions(estadioService);
        verifyNoInteractions(clubRepo);
    }

    @Test
    void save_mustSaveClubSuccessfully_whenValidDataIsProvidedWithoutDTAndEstadio() {
        //Arrange
        ClubRequest request = ClubRequestTestDataBuilder.aClubRequest()
                .withIdDt(null)
                .withIdEstadio(null)
                .build();

        Estado estado = EstadoTestDataBuilder.anEstado().withId(request.getIdEstado()).build();
        Ciudad ciudad = CiudadTestDataBuilder.aCiudad().withIdEstado(estado).build();

        short newIdClub = (short)faker.number().randomDigit();

        when(catalogosService.findEstadoById(request.getIdEstado())).thenReturn(estado);
        when(catalogosService.findCiudadById(request.getIdCiudad())).thenReturn(ciudad);

        when(clubRepo.saveAndFlush(any(Club.class))).thenAnswer(i -> {

            Club club = i.getArgument(0);

            club.setId(newIdClub);

            return club;

        });

        ArgumentCaptor<Club> clubCaptor = ArgumentCaptor.forClass(Club.class);


        //Act
        ClubResponseDto clubResponseDto = clubService.save(request);


        //Assert
        assertNotNull(clubResponseDto);
        assertEquals(newIdClub,clubResponseDto.getId());
        assertEquals(request.getNombreClub(),clubResponseDto.getNombreClub());
        assertEquals(request.getFechaFundacion(),clubResponseDto.getFechaFundacion());
        assertEquals(request.getPropietario(),clubResponseDto.getPropietario());


        verify(clubRepo).saveAndFlush(clubCaptor.capture());

        Club clubSaved = clubCaptor.getValue();

        assertEquals(newIdClub,clubSaved.getId());
        assertEquals(request.getNombreClub(),clubSaved.getNombreClub());
        assertEquals(request.getFechaFundacion(),clubSaved.getFechaFundacion());
        assertEquals(request.getPropietario(),clubSaved.getPropietario());

        verifyNoInteractions(dtService);
        verifyNoInteractions(estadioService);

    }

    @Test
    void save_mustSaveClubSuccessfully_whenValidDataIsProvidedWithDTAndEstadio() {
        //Arrange
        ClubRequest request = ClubRequestTestDataBuilder.aClubRequest()
                .build();

        DT dt = DTTestDataBuilder.aDT().withId(request.getIdDt())
                .withClub(null)
                .build();

        Estadio estadio = EstadioTestDataBuilder.anEstadio().withId(request.getIdEstadio()).build();

        Estado estado = EstadoTestDataBuilder.anEstado().withId(request.getIdEstado()).build();
        Ciudad ciudad = CiudadTestDataBuilder.aCiudad().withIdEstado(estado).build();

        short newIdClub = (short)faker.number().randomDigit();

        when(catalogosService.findEstadoById(request.getIdEstado())).thenReturn(estado);
        when(catalogosService.findCiudadById(request.getIdCiudad())).thenReturn(ciudad);
        when(dtService.findById(request.getIdDt())).thenReturn(dt);
        when(estadioService.findById(request.getIdEstadio())).thenReturn(estadio);


        when(clubRepo.saveAndFlush(any(Club.class))).thenAnswer(i -> {

            Club club = i.getArgument(0);

            club.setId(newIdClub);

            return club;

        });

        ArgumentCaptor<Club> clubCaptor = ArgumentCaptor.forClass(Club.class);


        //Act
        ClubResponseDto clubResponseDto = clubService.save(request);


        //Assert
        assertNotNull(clubResponseDto);
        assertNotNull(clubResponseDto.getIdDt());
        assertNotNull(clubResponseDto.getIdEstadio());

        assertEquals(newIdClub,clubResponseDto.getId());
        assertEquals(request.getNombreClub(),clubResponseDto.getNombreClub());
        assertEquals(request.getFechaFundacion(),clubResponseDto.getFechaFundacion());
        assertEquals(request.getPropietario(),clubResponseDto.getPropietario());

        assertEquals(request.getIdDt(),clubResponseDto.getIdDt().getId());
        assertEquals(request.getIdEstadio(),clubResponseDto.getIdEstadio().getId());




        verify(clubRepo).saveAndFlush(clubCaptor.capture());

        Club clubSaved = clubCaptor.getValue();

        assertNotNull(clubSaved);
        assertSame(dt,clubSaved.getIdDt());
        assertSame(estadio,clubSaved.getIdEstadio());

        assertEquals(newIdClub,clubSaved.getId());
        assertEquals(request.getNombreClub(),clubSaved.getNombreClub());
        assertEquals(request.getFechaFundacion(),clubSaved.getFechaFundacion());
        assertEquals(request.getPropietario(),clubSaved.getPropietario());
        assertEquals(request.getIdDt(),clubSaved.getIdDt().getId());
        assertEquals(request.getIdEstadio(),clubSaved.getIdEstadio().getId());

        verify(catalogosService).findEstadoById(request.getIdEstado());
        verify(catalogosService).findCiudadById(request.getIdCiudad());
        verify(dtService).findById(request.getIdDt());
        verify(estadioService).findById(request.getIdEstadio());


    }

    @Test
    void findAll_mustReturnListOfClubs() {
        // Arrange
        Club club1 = ClubTestDataBuilder.aClub().withId((short) 1).build();
        Club club2 = ClubTestDataBuilder.aClub().withId((short) 2).build();
        List<Club> clubs = List.of(club1, club2);

        when(clubRepo.findAll()).thenReturn(clubs);

        // Act
        List<Club> result = clubService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(clubs, result);
        verify(clubRepo).findAll();
    }

    @Test
    void findAll_mustReturnEmptyList_whenNoClubsExist() {
        // Arrange
        when(clubRepo.findAll()).thenReturn(List.of());

        // Act
        List<Club> result = clubService.findAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(clubRepo).findAll();
    }

    @Test
    void findAllDto_mustReturnListOfClubResponseDto() {
        // Arrange
        Club club1 = ClubTestDataBuilder.aClub().withId((short) 1).build();
        Club club2 = ClubTestDataBuilder.aClub().withId((short) 2).build();
        List<Club> clubs = List.of(club1, club2);

        when(clubRepo.findAll()).thenReturn(clubs);

        // Act
        List<ClubResponseDto> result = clubService.findAllDto();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(club1.getId(), result.get(0).getId());
        assertEquals(club2.getId(), result.get(1).getId());
        verify(clubRepo).findAll();
    }

    @Test
    void findAllDto_mustReturnEmptyList_whenNoClubsExist() {
        // Arrange
        when(clubRepo.findAll()).thenReturn(List.of());

        // Act
        List<ClubResponseDto> result = clubService.findAllDto();

        // Assert
        assertTrue(result.isEmpty());
        verify(clubRepo).findAll();
    }

    @Test
    void findById_mustThrowNoSuchElementException_whenClubNotFound() {
        // Arrange
        Short id = (short) faker.number().randomDigit();
        when(clubRepo.findById(id)).thenReturn(Optional.empty());

        // Act
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> clubService.findById(id));

        //Assert
        assertEquals("Club no encontrado",ex.getMessage());

    }

    @Test
    void findById_mustReturnClub_whenFound() {
        // Arrange
        Short id = (short) faker.number().numberBetween(1, 18);
        Club club = ClubTestDataBuilder.aClub().withId(id).build();

        when(clubRepo.findById(id)).thenReturn(Optional.of(club));

        // Act
        Club result = clubService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(club.getNombreClub(), result.getNombreClub());
        verify(clubRepo).findById(id);
    }

    @Test
    void findDtoById_mustThrowNoSuchElementException_whenClubNotFound() {
        // Arrange
        Short id = (short) faker.number().randomDigit();
        when(clubRepo.findById(id)).thenReturn(Optional.empty());

        // Act
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> clubService.findDtoById(id));

        //Assert
        assertEquals("Club no encontrado",ex.getMessage());
    }

    @Test
    void findDtoById_mustReturnClubResponseDto_whenFound() {
        // Arrange
        Short id = (short) faker.number().numberBetween(1, 18);
        Club club = ClubTestDataBuilder.aClub().withId(id).build();

        when(clubRepo.findById(id)).thenReturn(Optional.of(club));

        // Act
        ClubResponseDto result = clubService.findDtoById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(club.getNombreClub(), result.getNombreClub());
        verify(clubRepo).findById(id);
    }

    @Test
    void update_mustThrowNoSuchElementException_whenClubNotFound() {
        // Arrange
        Short id = (short) faker.number().randomNumber();
        ClubRequest request = new ClubRequest();
        when(clubRepo.findById(id)).thenReturn(Optional.empty());

        // Act
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> clubService.update(request, id));

        //Assert
        assertEquals("Club no encontrado",ex.getMessage());
    }

    @Test
    void update_mustUpdateClubSuccessfully_whenValidDataIsProvided() {
        //Arrange

        short clubid = (short) faker.number().numberBetween(1,18);

        ClubRequest clubRequest = ClubRequestTestDataBuilder.aClubRequest()
                .build();

        Club club = ClubTestDataBuilder.aClub()
                .withId(clubid)
                .build();

        when(clubRepo.findById(clubid)).thenReturn(Optional.of(club));
        when(clubRepo.saveAndFlush(any(Club.class))).thenAnswer(i -> i.getArgument(0));


        ArgumentCaptor<Club> captor = ArgumentCaptor.forClass(Club.class);

        //Act
        ClubResponseDto clubResponseDto = clubService.update(clubRequest, clubid);

        //Assert

        assertNotNull(clubResponseDto);
        assertEquals(clubid,clubResponseDto.getId());
        assertEquals(clubRequest.getNombreClub(),clubResponseDto.getNombreClub());
        assertEquals(clubRequest.getFechaFundacion(),clubResponseDto.getFechaFundacion());
        assertEquals(clubRequest.getPropietario(),clubResponseDto.getPropietario());

        verify(clubRepo).findById(clubid);
        verify(clubRepo).saveAndFlush(captor.capture());

        Club updatedClub = captor.getValue();
        assertEquals(club.getNombreClub(),updatedClub.getNombreClub());
        assertEquals(clubid,updatedClub.getId());




    }

    @Test
    void update_mustNotUpdateFields_whenNullValuesAreProvided() {
        //Arrange

        short clubid = (short) faker.number().numberBetween(1,18);

        ClubRequest clubRequest = ClubRequest.builder().build();

        Club club = ClubTestDataBuilder.aClub()
                .withId(clubid)
                .build();

        when(clubRepo.findById(clubid)).thenReturn(Optional.of(club));
        when(clubRepo.saveAndFlush(any(Club.class))).thenAnswer(i -> i.getArgument(0));


        ArgumentCaptor<Club> captor = ArgumentCaptor.forClass(Club.class);

        //Act
        ClubResponseDto clubResponseDto = clubService.update(clubRequest, clubid);

        //Assert

        assertNotNull(clubResponseDto);
        assertEquals(clubid,clubResponseDto.getId());

        assertNotNull(clubResponseDto.getNombreClub());
        assertNotNull(clubResponseDto.getFechaFundacion());
        assertNotNull(clubResponseDto.getPropietario());
        assertNotNull(clubResponseDto.getIdCiudad());
        assertNotNull(clubResponseDto.getIdCiudad().getIdEstado());




        verify(clubRepo).findById(clubid);
        verify(clubRepo).saveAndFlush(captor.capture());

        Club updatedClub = captor.getValue();
        assertEquals(clubid,updatedClub.getId());
        assertEquals(club.getNombreClub(),updatedClub.getNombreClub());
        assertEquals(club.getFechaFundacion(),updatedClub.getFechaFundacion());
        assertEquals(club.getPropietario(),updatedClub.getPropietario());
        assertEquals(club.getIdCiudad(),updatedClub.getIdCiudad());
        assertEquals(club.getIdEstado(),updatedClub.getIdEstado());



    }
    @Test
    void delete_mustThrowNoSuchElementException_whenClubNotFound(){
        //Arrange
        short idClub = 1;

        when(clubRepo.findByIdAndStatusIs(idClub, Estados.ACTIVO.getCodigo())).thenReturn(Optional.empty());

        // Act
        assertThrows(NoSuchElementException.class, () -> clubService.delete(idClub));

        // Assert
        verify(clubRepo).findByIdAndStatusIs(idClub, Estados.ACTIVO.getCodigo());
        verifyNoInteractions(jugadorRepository);
        verifyNoInteractions(dtService);
        verify(clubRepo,never()).save(any(Club.class));
    }

    @Test
    void delete_mustDeleteClubSuccessfully(){
        //Arrange
        short idClub = (short) faker.number().numberBetween(1,18);

        Club club = ClubTestDataBuilder.aClub().withId(idClub).build();

        Jugador jugador = JugadorTestDataBuilder.aJugador()
                .withIdClub(club)
                .build();

        DT dt = DTTestDataBuilder.aDT().withClub(club).build();

        club.setJugadores(new LinkedHashSet<>(Collections.singletonList(jugador)));
        club.setIdDt(dt);


        when(clubRepo.findByIdAndStatusIs(idClub, Estados.ACTIVO.getCodigo())).thenReturn(Optional.of(club));
        when(jugadorRepository.saveAll(any())).thenAnswer(invocation -> {

            Collection<Jugador> jugadoresEnviados = invocation.getArgument(0);

            return new ArrayList<>(jugadoresEnviados);
        });
        when(dtService.save(any(DT.class))).thenAnswer(i -> i.getArgument(0));
        when(clubRepo.save(any(Club.class))).thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<Club> captor = ArgumentCaptor.forClass(Club.class);
        ArgumentCaptor<DT> captorDT = ArgumentCaptor.forClass(DT.class);
        ArgumentCaptor<Set<Jugador>> captorJugador = ArgumentCaptor.forClass(Set.class);


        //Act
        clubService.delete(idClub);

        //Assert
        verify(clubRepo).findByIdAndStatusIs(idClub, Estados.ACTIVO.getCodigo());

        verify(jugadorRepository).saveAll(captorJugador.capture());
        verify(dtService).save(captorDT.capture());
        verify(clubRepo).save(captor.capture());

        Club clubSaved = captor.getValue();



        assertEquals(Estados.INACTIVO.getCodigo(),clubSaved.getStatus());
        assertNull(clubSaved.getIdDt());
        assertNull(clubSaved.getJugadores());

        DT dtSaved = captorDT.getValue();
        assertEquals(Estados.AGENTE_LIBRE.getCodigo(),dtSaved.getStatus());
        assertNull(dtSaved.getClub());


        Set<Jugador> jugadoresSaved = captorJugador.getValue();
        assertNotNull(jugadoresSaved);
        assertEquals(1,jugadoresSaved.size());

        Jugador jugadorSaved = jugadoresSaved.iterator().next();

        assertEquals(Estados.AGENTE_LIBRE.getCodigo(),jugadorSaved.getStatus());
        assertNull(jugadorSaved.getIdClub());








    }

}
