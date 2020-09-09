package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository
                .findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));

                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO returnedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnedVendorDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));

        return returnedVendorDTO;
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);

        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }

            VendorDTO returnVendorDTO = vendorMapper.vendorToVendorDTO(vendor);
            returnVendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));

            return returnVendorDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO returnedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnedVendorDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));

        return returnedVendorDTO;

    }
}
