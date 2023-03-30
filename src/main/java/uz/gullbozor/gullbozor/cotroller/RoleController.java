package uz.gullbozor.gullbozor.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private RoleService roleService;



    @GetMapping
    public ResponseEntity<ApiResponse> addRole() {
        ApiResponse apiResponse = roleService.addRole();
        return ResponseEntity.ok(apiResponse);
    }

}
