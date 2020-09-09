package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {
    public static final String NAME = "VendorA";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void testVendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void testVendorDTOToVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("DTO");

        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        assertEquals(vendorDTO.getName(), vendor.getName());
    }
}