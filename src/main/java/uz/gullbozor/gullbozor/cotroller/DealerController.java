package uz.gullbozor.gullbozor.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.entity.CityEntity;
import uz.gullbozor.gullbozor.entity.DealerEntity;
import uz.gullbozor.gullbozor.service.CityService;
import uz.gullbozor.gullbozor.service.DealerService;

import java.util.List;

@RestController
@RequestMapping("/dealer")
public class DealerController {

    @Autowired
    private DealerService dealerService;

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> addDealer(@RequestBody List<DealerEntity> dealerEntityList) {
        ApiResponse apiResponse = dealerService.addDealer(dealerEntityList);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editCity(@RequestBody DealerEntity dealerEntityDto, @PathVariable Integer id) {
        ApiResponse apiResponse = dealerService.editDealer(dealerEntityDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDealerById(@PathVariable Integer id) {
        ApiResponse apiResponse = dealerService.getDealerById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<DealerEntity>> getCityList() {
        List<DealerEntity> dealerEntityList = dealerService.getDealerList();
        return ResponseEntity.ok(dealerEntityList);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
//    @GetMapping("/byRegionId/{regionId}")
//    public ResponseEntity<List<DealerEntity>> getCityListByRegionId(@PathVariable Integer regionId) {
//        List<DealerEntity> dealerEntityList = dealerService.getCityListByREgionId(regionId);
//        return ResponseEntity.ok(dealerEntityList);
//    }

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDealerById(@PathVariable Integer id) {
        ApiResponse apiResponse = dealerService.deleteById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
