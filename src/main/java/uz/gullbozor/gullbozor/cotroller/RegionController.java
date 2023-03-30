package uz.gullbozor.gullbozor.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.entity.RegionEntity;
import uz.gullbozor.gullbozor.service.RegionService;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> addRegion(@RequestBody List<RegionEntity> regionEntityList) {
        ApiResponse apiResponse = regionService.addRegion(regionEntityList);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editRegion(@RequestBody RegionEntity regionDto, @PathVariable Integer id) {
        ApiResponse apiResponse = regionService.editRegion(regionDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getRegionById(@PathVariable Integer id) {
        ApiResponse apiResponse = regionService.getRegionById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<RegionEntity>> getRegionList() {
        List<RegionEntity> regionList = regionService.getRegionList();
        return ResponseEntity.ok(regionList);
    }

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteRegionById(@PathVariable Integer id) {
        ApiResponse apiResponse = regionService.deleteById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}
