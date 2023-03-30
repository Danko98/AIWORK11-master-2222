package uz.gullbozor.gullbozor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.entity.CityEntity;
import uz.gullbozor.gullbozor.repository.CityRepo;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepo cityRepo;

    public ApiResponse addCity(List<CityEntity> cityDtoList) {


        for (CityEntity cityDto : cityDtoList) {

            if (!cityRepo.existsByName(cityDto.getName())) {
                cityRepo.save(cityDto);
            }

        }
        return new ApiResponse("Successfully saved",true);





    }

    public ApiResponse editCity(CityEntity cityDto, Integer id) {

        if (!cityRepo.existsById(id)) {
            return new ApiResponse("Not found city",false);
        }

        if (!cityRepo.existsByName(cityDto.getName())) {
            return new ApiResponse("This city name already exists",false);
        }
        Optional<CityEntity> optionalCityEntity = cityRepo.findById(id);
        CityEntity cityEntity = optionalCityEntity.get();
        cityEntity.setName(cityDto.getName());
        cityRepo.save(cityDto);
        return new ApiResponse("Successfully edited",true);

    }

    public ApiResponse getCityById(Integer id) {
        if (!cityRepo.existsById(id)) {
            return new ApiResponse("Not found city",true);
        }
        Optional<CityEntity> optionalCityEntity = cityRepo.findById(id);
        return new ApiResponse(optionalCityEntity.get());
    }

    public List<CityEntity> getCityList() {
        return cityRepo.findAll();
    }

    public List<CityEntity> getCityListByREgionId(Integer regionId) {
        return cityRepo.findAllByRegionId(regionId);
    }

    public ApiResponse deleteCity(Integer id) {
        if (!cityRepo.existsById(id)) {
            return new ApiResponse("Not found city",false);
        }
        cityRepo.deleteById(id);
        return new ApiResponse("Successfully deleted",true);
    }



}
