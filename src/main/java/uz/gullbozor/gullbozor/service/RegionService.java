package uz.gullbozor.gullbozor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.entity.RegionEntity;
import uz.gullbozor.gullbozor.repository.RegionRepo;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepo regionRepo;

    public ApiResponse addRegion(List<RegionEntity> regionDtoList) {


        for (RegionEntity regionDto : regionDtoList) {

            if (!regionRepo.existsByName(regionDto.getName())) {
                regionRepo.save(regionDto);
            }

        }
        return new ApiResponse("Successfully saved",true);
    }

    public ApiResponse editRegion(RegionEntity regionDto, Integer id) {

        if (!regionRepo.existsById(id)) {
            return new ApiResponse("Not found region",false);
        }

        if (regionRepo.existsByName(regionDto.getName())) {
            return new ApiResponse("This region name already exists",false);
        }
        Optional<RegionEntity> optionalRegionEntity = regionRepo.findById(id);
        RegionEntity regionEntity = optionalRegionEntity.get();
        regionEntity.setName(regionDto.getName());
        regionRepo.save(regionEntity);
        return new ApiResponse("Successfully edited",true);
    }

    public ApiResponse getRegionById(Integer id) {
        if (!regionRepo.existsById(id)) {
            return new ApiResponse("Not found region",false);
        }
        Optional<RegionEntity> optionalRegionEntity = regionRepo.findById(id);
        return new ApiResponse(optionalRegionEntity.get());
    }

    public List<RegionEntity> getRegionList() {
        return regionRepo.findAll();
    }

    public ApiResponse deleteById(Integer id) {
        if (!regionRepo.existsById(id)) {
            return new ApiResponse("Not found region",false);
        }
        regionRepo.deleteById(id);
        return new ApiResponse("Successfully deleted",true);
    }

}
