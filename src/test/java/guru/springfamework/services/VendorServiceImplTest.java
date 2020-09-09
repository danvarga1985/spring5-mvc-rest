package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {
    private static final Long ID = 1L;
    private static final String NAME = "VendorA";
    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void getAllVendors() {
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());

        when(vendorRepository.findAll()).thenReturn(vendors);

        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        assertEquals(2, vendorDTOS.size());
    }

    @Test
    public void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void createNewVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(vendorDTO.getName());

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        assertEquals(vendorDTO.getName() ,savedVendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "/1", savedVendorDTO.getVendorUrl());
    }

    @Test
    public void saveVendorByDTO() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(vendorDTO.getName());

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO modifiedDTO = vendorService.saveVendorByDTO(ID, vendorDTO);

        assertEquals(vendorDTO.getName(), modifiedDTO.getName());
        assertEquals(VendorController.BASE_URL + "/1", modifiedDTO.getVendorUrl());
    }

    @Test
    public void deleteVendorById() {
        Long id = ID;

        vendorRepository.deleteById(id);

        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}