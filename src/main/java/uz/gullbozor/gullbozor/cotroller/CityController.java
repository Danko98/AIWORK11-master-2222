package uz.gullbozor.gullbozor.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.entity.CityEntity;
import uz.gullbozor.gullbozor.repository.CityRepo;
import uz.gullbozor.gullbozor.service.CityService;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> addCity(@RequestBody List<CityEntity> cityEntityList) {
        ApiResponse apiResponse = cityService.addCity(cityEntityList);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editCity(@RequestBody CityEntity cityDto, @PathVariable Integer id) {
        ApiResponse apiResponse = cityService.editCity(cityDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCityById(@PathVariable Integer id) {
        ApiResponse apiResponse = cityService.getCityById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<CityEntity>> getCityList() {
        List<CityEntity> cityList = cityService.getCityList();
        return ResponseEntity.ok(cityList);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/byRegionId/{regionId}")
    public ResponseEntity<List<CityEntity>> getCityListByRegionId(@PathVariable Integer regionId) {
        List<CityEntity> cityList = cityService.getCityListByREgionId(regionId);
        return ResponseEntity.ok(cityList);
    }

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCityById(@PathVariable Integer id) {
        ApiResponse apiResponse = cityService.deleteCity(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
