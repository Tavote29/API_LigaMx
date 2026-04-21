package com.fdf.liga_mx.services;

import com.fdf.liga_mx.mappers.*;
import com.fdf.liga_mx.models.dtos.request.DTRequest;
import com.fdf.liga_mx.models.dtos.response.DTResponseDto;
import com.fdf.liga_mx.models.entitys.*;
import com.fdf.liga_mx.models.enums.Estados;
import com.fdf.liga_mx.repository.DTRepository;
import com.fdf.liga_mx.repository.IClubRepository;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DTServiceImplTest {

    @Mock
    private IClubRepository clubRepository;
    @Mock
    private ICatalogosService catalogosService;
    @Mock
    private DTRepository dtRepository;
    @Mock
    private MediaStorageService mediaService;
    @Mock
    private IPersonaService personaService;

    private DTServiceImpl dtService;

    private static final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        StatusMapper statusMapper = new StatusMapper();
        NacionalidadMapper nacionalidadMapper = new NacionalidadMapper();
        PersonaMapper personaMapper = new PersonaMapper(statusMapper, nacionalidadMapper);
        DTMapper dtMapper = new DTMapper(personaMapper);

        dtService = new DTServiceImpl(
                clubRepository,
                catalogosService,
                dtRepository,
                dtMapper,
                personaMapper,
                mediaService,
                personaService
        );
    }

    

    @Test
    void save_mustThrowNoSuchElementException_whenClubNotFound() {
        // Arrange
        DTRequest request = DTRequestTestDataBuilder.aDTRequest().build();
        when(clubRepository.findByIdAndStatusIs(request.getIdClub(), Estados.ACTIVO.getCodigo())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> dtService.save(request));
        verify(clubRepository).findByIdAndStatusIs(request.getIdClub(), Estados.ACTIVO.getCodigo());
        verifyNoInteractions(dtRepository);
    }

    @Test
    void save_mustThrowNoSuchElementException_whenNacionalidadNotFound() {
        // Arrange
        DTRequest request = DTRequestTestDataBuilder.aDTRequest().build();
        when(clubRepository.findByIdAndStatusIs(request.getIdClub(), Estados.ACTIVO.getCodigo())).thenReturn(Optional.of(new Club()));
        when(catalogosService.findNacionalidadEntityById(request.getPersona().getIdNacionalidad()))
                .thenThrow(new NoSuchElementException("No se encontro la nacionalidad"));

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> dtService.save(request));

        verifyNoInteractions(dtRepository);
    }
    
    @Test
    void save_mustThrowNoSuchElementException_whenStatusNotFound() {
        // Arrange
        DTRequest request = DTRequestTestDataBuilder.aDTRequest().build();
        when(clubRepository.findByIdAndStatusIs(request.getIdClub(), Estados.ACTIVO.getCodigo())).thenReturn(Optional.of(new Club()));
        when(catalogosService.findNacionalidadEntityById(request.getPersona().getIdNacionalidad())).thenReturn(new Nacionalidad());
        when(catalogosService.findStatusEntityById(request.getPersona().getIdStatus()))
                .thenThrow(new NoSuchElementException("No se encontro el status"));

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> dtService.save(request));
    }

    @Test
    void save_mustSaveDTSuccessfully_whenDataIsValid(){
        //Arrange
        DTRequest dtRequest = DTRequestTestDataBuilder.aDTRequest()
                .withNUIDT(null)
                .build();



        Club club = ClubTestDataBuilder.aClub()
                .withId(dtRequest.getIdClub())
                .build();

        Nacionalidad nacionalidad = NacionalidadTestDataBuilder.aNacionalidad()
                .withId(dtRequest.getPersona().getIdNacionalidad())
                .build();

        Status status = StatusTestDataBuilder.aStatus().withId(dtRequest.getPersona().getIdStatus()).build();

        long newIdDT = faker.number().randomNumber();

        UUID newPersonaId = UUID.randomUUID();

        when(clubRepository.findByIdAndStatusIs(dtRequest.getIdClub(), Estados.ACTIVO.getCodigo())).thenReturn(Optional.of(club));
        when(catalogosService.findNacionalidadEntityById(dtRequest.getPersona().getIdNacionalidad())).thenReturn(nacionalidad);
        when(catalogosService.findStatusEntityById(dtRequest.getPersona().getIdStatus())).thenReturn(status);
        when(dtRepository.saveAndFlush(any(DT.class))).thenAnswer(i -> {

            DT dt = i.getArgument(0);

            dt.setId(newIdDT);
            dt.getPersona().setId(newPersonaId);



            return dt;

        });

        ArgumentCaptor<DT> captor = ArgumentCaptor.forClass(DT.class);


        //Act

        DTResponseDto result = dtService.save(dtRequest);



        //Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getIdPersona().getId());
        assertEquals(dtRequest.getIdClub(), result.getIdClub());
        assertEquals(dtRequest.getPersona().getNombre(), result.getIdPersona().getNombre());
        assertEquals(dtRequest.getPersona().getFechaNacimiento(), result.getIdPersona().getFechaNacimiento());
        assertEquals(dtRequest.getPersona().getLugarNacimiento(), result.getIdPersona().getLugarNacimiento());
        assertEquals(dtRequest.getPersona().getEstatura(), result.getIdPersona().getEstatura());

        verify(dtRepository).saveAndFlush(captor.capture());

        DT dtSaved = captor.getValue();

        assertNotNull(dtSaved);
        assertNotNull(dtSaved.getId());
        assertNotNull(dtSaved.getPersona().getId());
        assertEquals(newIdDT, dtSaved.getId());
        assertEquals(newPersonaId, dtSaved.getPersona().getId());
        assertEquals(dtRequest.getIdClub(), dtSaved.getClub().getId());
        assertEquals(dtRequest.getPersona().getNombre(), dtSaved.getPersona().getNombre());
        assertEquals(dtRequest.getPersona().getFechaNacimiento(), dtSaved.getPersona().getFechaNacimiento());
        assertEquals(dtRequest.getPersona().getLugarNacimiento(), dtSaved.getPersona().getLugarNacimiento());
        assertEquals(dtRequest.getPersona().getEstatura(), dtSaved.getPersona().getEstatura());


        verify(dtRepository).saveAndFlush(any(DT.class));
        verify(clubRepository).findByIdAndStatusIs(dtRequest.getIdClub(), Estados.ACTIVO.getCodigo());
        verify(catalogosService).findNacionalidadEntityById(dtRequest.getPersona().getIdNacionalidad());
        verify(catalogosService).findStatusEntityById(dtRequest.getPersona().getIdStatus());


    }



    @Test
    void findAll_mustReturnList() {
        // Arrange
        DT dt1 = DTTestDataBuilder.aDT().build();
        DT dt2 = DTTestDataBuilder.aDT().build();
        when(dtRepository.findAll()).thenReturn(List.of(dt1, dt2));

        // Act
        List<DT> result = dtService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(dtRepository).findAll();
    }
    
    @Test
    void findAll_mustReturnEmptyList_whenNoDTsExist() {
        // Arrange
        when(dtRepository.findAll()).thenReturn(List.of());

        // Act
        List<DT> result = dtService.findAll();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllDto_mustReturnList() {
        // Arrange
        DT dt1 = DTTestDataBuilder.aDT().build();
        when(dtRepository.findAll()).thenReturn(List.of(dt1));

        // Act
        List<DTResponseDto> result = dtService.findAllDto();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
    
    @Test
    void findAllDto_mustReturnEmptyList_whenNoDTsExist() {
        // Arrange
        when(dtRepository.findAll()).thenReturn(List.of());

        // Act
        List<DTResponseDto> result = dtService.findAllDto();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findById_mustThrowNoSuchElementException_whenDTNotFoundOrInactive() {
        // Arrange
        Long id = faker.number().randomNumber();
        when(dtRepository.findByIdAndStatusIs(eq(id), anyShort())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> dtService.findById(id));
    }

    @Test
    void findById_mustReturnDT_whenFoundAndActive() {
        // Arrange
        Long id = faker.number().randomNumber();
        DT dt = DTTestDataBuilder.aDT().withId(id).build();
        when(dtRepository.findByIdAndStatusIs(id, Estados.ACTIVO.getCodigo())).thenReturn(Optional.of(dt));

        // Act
        DT result = dtService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void findDtoById_mustThrowNoSuchElementException_whenDTNotFound() {
        // Arrange
        Long id = faker.number().randomNumber();
        when(dtRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> dtService.findDtoById(id));
    }
    
    @Test
    void findDtoById_mustReturnDTResponseDto_whenFound() {
        // Arrange
        Long id = faker.number().randomNumber();
        DT dt = DTTestDataBuilder.aDT().withId(id).build();
        when(dtRepository.findById(id)).thenReturn(Optional.of(dt));

        // Act
        DTResponseDto result = dtService.findDtoById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void update_mustThrowNoSuchElementException_whenDTNotFound() {
        // Arrange
        Long id = faker.number().randomNumber();
        DTRequest request = DTRequestTestDataBuilder.aDTRequest().build();
        when(dtRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> dtService.update(request, id));
    }
    
    @Test
    void update_mustThrowNoSuchElementException_whenNacionalidadNotFound() {
        // Arrange
        Long id = faker.number().randomNumber();
        DTRequest request = DTRequestTestDataBuilder.aDTRequest().build();
        when(dtRepository.findById(id)).thenReturn(Optional.of(new DT()));
        when(catalogosService.findNacionalidadEntityById(anyShort())).thenThrow(new NoSuchElementException());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> dtService.update(request, id));
    }
    
    @Test
    void update_mustThrowNoSuchElementException_whenStatusNotFound() {
        // Arrange
        Long id = faker.number().randomNumber();
        DTRequest request = DTRequestTestDataBuilder.aDTRequest().build();
        when(dtRepository.findById(id)).thenReturn(Optional.of(new DT()));
        when(catalogosService.findNacionalidadEntityById(anyShort())).thenReturn(new Nacionalidad());
        when(catalogosService.findStatusEntityById(anyShort())).thenThrow(new NoSuchElementException());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> dtService.update(request, id));
    }

    @Test
    void searchDT_mustReturnEmptyPage_whenNoMatchesFound() {
        // Arrange

        Page<DT> pageDT = new PageImpl<>(List.of());

        int page = 0;
        int size =10;
        String sort= "id,asc;";

        when(dtRepository.searchDT(any(Pageable.class), any(), any(), any())).thenReturn(pageDT);

        // Act
        Page<DTResponseDto> result = dtService.searchDT(page, size, sort, null, null, null);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void searchDT_mustReturnPage_whenDataFound() {
        // Arrange



        int page = 0;
        int size =10;
        String sort= "id,asc;";

        DT dt1 = DTTestDataBuilder.aDT().build();
        DT dt2 = DTTestDataBuilder.aDT().build();

        Page<DT> pageDT = new PageImpl<>(List.of(dt1, dt2));

        when(dtRepository.searchDT(any(Pageable.class), any(), any(), any())).thenReturn(pageDT);

        // Act
        Page<DTResponseDto> result = dtService.searchDT(page, size, sort, null, null, null);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(2, result.getTotalElements());

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(dtRepository).searchDT(captor.capture(),eq(null),eq(null),eq(null));

        assertEquals(page,captor.getValue().getPageNumber());
        assertEquals(size,captor.getValue().getPageSize());
        assertEquals(1,captor.getValue().getSort().toList().size());

    }

    @Test
    void saveWithFile_mustSaveWithoutImage_whenFileIsNull() throws IOException {
        // Arrange
        DTRequest request = DTRequestTestDataBuilder.aDTRequest()
                .withNUIDT(null)
                .build();

        Club club = ClubTestDataBuilder.aClub().withId(request.getIdClub()).build();
        Nacionalidad nacionalidad = NacionalidadTestDataBuilder.aNacionalidad().withId(request.getPersona().getIdNacionalidad()).build();
        Status status = StatusTestDataBuilder.aStatus().withId(request.getPersona().getIdStatus()).build();

        UUID newPersonaId = UUID.randomUUID();

        when(clubRepository.findByIdAndStatusIs(anyShort(), eq(Estados.ACTIVO.getCodigo()))).thenReturn(Optional.of(club));
        when(catalogosService.findNacionalidadEntityById(anyShort())).thenReturn(nacionalidad);
        when(catalogosService.findStatusEntityById(anyShort())).thenReturn(status);

        when(dtRepository.saveAndFlush(any(DT.class))).thenAnswer(i -> {

            DT newDt = i.getArgument(0);

            newDt.setId(faker.number().randomNumber());
            newDt.getPersona().setId(newPersonaId);



            return newDt;

        });

        // Act
        DTResponseDto result = dtService.save(request, (MultipartFile) null);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getIdPersona().getId());
        assertNull(result.getIdPersona().getImageUrl());
        assertEquals(request.getPersona().getNombre(), result.getIdPersona().getNombre());
        assertEquals(request.getPersona().getFechaNacimiento(), result.getIdPersona().getFechaNacimiento());
        assertEquals(request.getPersona().getLugarNacimiento(), result.getIdPersona().getLugarNacimiento());
        assertEquals(request.getPersona().getEstatura(), result.getIdPersona().getEstatura());
        assertEquals(request.getIdClub(), result.getIdClub());




        verifyNoInteractions(mediaService);
        verify(dtRepository).saveAndFlush(any(DT.class));
        verify(dtRepository,never()).save(any(DT.class));
    }

    @Test
    void saveWithFile_mustSaveWithoutImage_whenFileIsPresentButEmpty() throws IOException {
        // Arrange

        MockMultipartFile file = new MockMultipartFile("imagen", new byte[0]);

        DTRequest request = DTRequestTestDataBuilder.aDTRequest()
                .withNUIDT(null)
                .build();

        Club club = ClubTestDataBuilder.aClub().withId(request.getIdClub()).build();
        Nacionalidad nacionalidad = NacionalidadTestDataBuilder.aNacionalidad().withId(request.getPersona().getIdNacionalidad()).build();
        Status status = StatusTestDataBuilder.aStatus().withId(request.getPersona().getIdStatus()).build();

        UUID newPersonaId = UUID.randomUUID();

        when(clubRepository.findByIdAndStatusIs(anyShort(), eq(Estados.ACTIVO.getCodigo()))).thenReturn(Optional.of(club));
        when(catalogosService.findNacionalidadEntityById(anyShort())).thenReturn(nacionalidad);
        when(catalogosService.findStatusEntityById(anyShort())).thenReturn(status);

        when(dtRepository.saveAndFlush(any(DT.class))).thenAnswer(i -> {

            DT newDt = i.getArgument(0);

            newDt.setId(faker.number().randomNumber());
            newDt.getPersona().setId(newPersonaId);


            return newDt;

        });

        // Act
        DTResponseDto result = dtService.save(request, file);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getIdPersona().getId());
        assertNull(result.getIdPersona().getImageUrl());
        assertEquals(request.getPersona().getNombre(), result.getIdPersona().getNombre());
        assertEquals(request.getPersona().getFechaNacimiento(), result.getIdPersona().getFechaNacimiento());
        assertEquals(request.getPersona().getLugarNacimiento(), result.getIdPersona().getLugarNacimiento());
        assertEquals(request.getPersona().getEstatura(), result.getIdPersona().getEstatura());
        assertEquals(request.getIdClub(), result.getIdClub());




        verifyNoInteractions(mediaService);
        verify(dtRepository).saveAndFlush(any(DT.class));
        verify(dtRepository,never()).save(any(DT.class));


    }



    @Test
    void saveWithFile_mustSaveWithImage_whenFileIsPresent() throws IOException {
        // Arrange

        MockMultipartFile file = new MockMultipartFile(
                "imagen",               // Nombre del parámetro (el form-data key)
                "imagen.jpg",         // Nombre original del archivo
                "image/jpeg",         // Tipo de contenido (MIME type)
                "EXAMPLE DATA".getBytes()           // <--- ESTO HACE QUE isEmpty() DEVUELVA TRUE
        );

        DTRequest request = DTRequestTestDataBuilder.aDTRequest()
                .withNUIDT(null)
                .build();

        Club club = ClubTestDataBuilder.aClub().withId(request.getIdClub()).build();
        Nacionalidad nacionalidad = NacionalidadTestDataBuilder.aNacionalidad().withId(request.getPersona().getIdNacionalidad()).build();
        Status status = StatusTestDataBuilder.aStatus().withId(request.getPersona().getIdStatus()).build();

        long newDTId = faker.number().randomNumber();
        UUID newPersonaId = UUID.randomUUID();

        when(clubRepository.findByIdAndStatusIs(anyShort(), eq(Estados.ACTIVO.getCodigo()))).thenReturn(Optional.of(club));
        when(catalogosService.findNacionalidadEntityById(anyShort())).thenReturn(nacionalidad);
        when(catalogosService.findStatusEntityById(anyShort())).thenReturn(status);

        when(dtRepository.saveAndFlush(any(DT.class))).thenAnswer(i -> {

            DT newDt = i.getArgument(0);

            newDt.setId(newDTId);
            newDt.getPersona().setId(newPersonaId);


            return newDt;

        });

        when(mediaService.uploadFile(file, newPersonaId.toString())).thenReturn(newPersonaId.toString()+".jpg");
        when(dtRepository.save(any(DT.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        DTResponseDto result = dtService.save(request, file);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getIdPersona().getImageUrl());
        assertNotNull(result.getIdPersona().getId());
        assertEquals(request.getPersona().getNombre(), result.getIdPersona().getNombre());
        assertEquals(request.getPersona().getFechaNacimiento(), result.getIdPersona().getFechaNacimiento());
        assertEquals(request.getPersona().getLugarNacimiento(), result.getIdPersona().getLugarNacimiento());
        assertEquals(request.getPersona().getEstatura(), result.getIdPersona().getEstatura());
        assertEquals(request.getIdClub(), result.getIdClub());





        verify(dtRepository).saveAndFlush(any(DT.class));

        verify(dtRepository).save(any(DT.class));
    }


    @Test
    void liberarDt_mustThrowNoSuchElementException_whenDTNotFound() {
        // Arrange
        Long id = faker.number().randomNumber();
        when(dtRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> dtService.liberarDt(id));
    }

    @Test
    void liberarDt_mustThrowIllegalStateException_whenAlreadyLibreByStatusPersona() {
        //Arrange
        long idDT = faker.number().randomNumber();

        Status status = StatusTestDataBuilder.aStatus().withId(Estados.INACTIVO.getCodigo()).build();

        DT dt = DTTestDataBuilder.aDT().withId(idDT).withPersona(
                PersonaTestDataBuilder
                        .aPersona()
                        .withIdStatus(status)
                .build()).build();

        when(dtRepository.findById(idDT)).thenReturn(Optional.of(dt));

        //Act
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> dtService.liberarDt(idDT));

        //Assert
        assertEquals("La persona ya se encuentra inactiva", ex.getMessage());

        verify(dtRepository,never()).save(any(DT.class));



    }

    @Test
    void liberarDt_mustThrowIllegalStateException_whenAlreadyLibreByStatusDT_Retirado() {
        //Arrange
        long idDT = faker.number().randomNumber();

        Status status = StatusTestDataBuilder.aStatus().withId(Estados.ACTIVO.getCodigo()).build();

        DT dt = DTTestDataBuilder.aDT().withId(idDT)
                .withStatus(Estados.RETIRADO.getCodigo())
                .withPersona(
                        PersonaTestDataBuilder
                                .aPersona()
                                .withIdStatus(status)
                                .build())
                .build();

        when(dtRepository.findById(idDT)).thenReturn(Optional.of(dt));

        //Act
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> dtService.liberarDt(idDT));

        //Assert
        assertEquals("La persona ya se encuentra retirada", ex.getMessage());

        verify(dtRepository,never()).save(any(DT.class));

    }
    
    @Test
    void liberarDt_mustThrowIllegalStateException_whenAlreadyLibreByStatusDT_AgenteLibre() {

        //Arrange
        long idDT = faker.number().randomNumber();

        Status status = StatusTestDataBuilder.aStatus().withId(Estados.ACTIVO.getCodigo()).build();

        DT dt = DTTestDataBuilder.aDT().withId(idDT)
                .withStatus(Estados.AGENTE_LIBRE.getCodigo())
                .withPersona(
                        PersonaTestDataBuilder
                                .aPersona()
                                .withIdStatus(status)
                                .build())
                .build();

        when(dtRepository.findById(idDT)).thenReturn(Optional.of(dt));

        //Act
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> dtService.liberarDt(idDT));

        //Assert
        assertEquals("Ya se encuentra libre de competencia", ex.getMessage());

        verify(dtRepository,never()).save(any(DT.class));

    }

    @Test
    void liberarDt_mustSetAgenteLibreAndNullClubSuccessfully() {
        //Arrange
        long idDT = faker.number().randomNumber();

        Status status = StatusTestDataBuilder.aStatus().withId(Estados.ACTIVO.getCodigo()).build();

        DT dt = DTTestDataBuilder.aDT().withId(idDT)
                .withPersona(
                        PersonaTestDataBuilder
                                .aPersona()
                                .withIdStatus(status)
                                .build())
                .build();

        when(dtRepository.findById(idDT)).thenReturn(Optional.of(dt));
        when(dtRepository.save(any(DT.class))).thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<DT> captor = ArgumentCaptor.forClass(DT.class);

        //Act
        dtService.liberarDt(idDT);

        //Assert
        verify(dtRepository).save(captor.capture());

        DT dtUpdated = captor.getValue();

        assertEquals(Estados.AGENTE_LIBRE.getCodigo(), dtUpdated.getStatus());
        assertNull(dtUpdated.getClub());



    }
}
