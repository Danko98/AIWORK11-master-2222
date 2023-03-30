package uz.gullbozor.gullbozor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.dto.AddressDto;
import uz.gullbozor.gullbozor.entity.AddressEntity;
import uz.gullbozor.gullbozor.entity.CityEntity;
import uz.gullbozor.gullbozor.entity.RegionEntity;
import uz.gullbozor.gullbozor.repository.AddressRepo;
import uz.gullbozor.gullbozor.repository.CityRepo;
import uz.gullbozor.gullbozor.repository.RegionRepo;

import java.util.List;
import java.util.Optional;
@Service
public class AddressService {

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private RegionRepo regionRepo;


    public ApiResponse addAddress(AddressDto addressDto) {
        if (!regionRepo.existsById(addressDto.getRegionId())) {
            return new ApiResponse("Not found region",false);
        }
        if (!cityRepo.existsById(addressDto.getCityId())) {
            return new ApiResponse("Not found city",false);
        }

        Optional<CityEntity> optionalCityEntity = cityRepo.findById(addressDto.getCityId());
        Optional<RegionEntity> optionalRegionEntity = regionRepo.findById(addressDto.getRegionId());

        AddressEntity address = new AddressEntity();
        address.setStreetAndHome(addressDto.getStreetAndHouse());
        address.setCity(optionalCityEntity.get());
        address.setRegionEntity(optionalRegionEntity.get());

        addressRepo.save(address);
        return new ApiResponse("Successfully saved",true);

    }

    public ApiResponse editAddress(AddressDto addressDto, Long id) {
        if (!addressRepo.existsById(id)) {
            return new ApiResponse("Not found address",false);
        }

        if (!regionRepo.existsById(addressDto.getRegionId())) {
            return new ApiResponse("Not found region",false);
        }
        if (!cityRepo.existsById(addressDto.getCityId())) {
            return new ApiResponse("Not found city",false);
        }

        Optional<AddressEntity> optionalAddressEntity = addressRepo.findById(id);
        Optional<CityEntity> optionalCityEntity = cityRepo.findById(addressDto.getCityId());
        Optional<RegionEntity> optionalRegionEntity = regionRepo.findById(addressDto.getRegionId());

        AddressEntity address = optionalAddressEntity.get();
        address.setStreetAndHome(addressDto.getStreetAndHouse());
        address.setCity(optionalCityEntity.get());
        address.setRegionEntity(optionalRegionEntity.get());

        addressRepo.save(address);
        return new ApiResponse("Successfully saved",true);

    }

    public ApiResponse getAddressById(Long id) {
        if (!addressRepo.existsById(id)) {
            return new ApiResponse("Not found address",false);
        }

        Optional<AddressEntity> optionalAddressEntity = addressRepo.findById(id);
        return new ApiResponse(optionalAddressEntity.get());

    }

    public List<AddressEntity> getAddressList() {
        return addressRepo.findAll();
    }

    public ApiResponse deleteAddressById(Long id) {
        if (!addressRepo.existsById(id)) {
            return new ApiResponse("Not found address",false);
        }
        addressRepo.deleteById(id);
        return new ApiResponse("Successfully deleted",true);
    }

}
