package uz.gullbozor.gullbozor.cotroller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import uz.gullbozor.gullbozor.dto.AdminBodyDto;
import uz.gullbozor.gullbozor.dto.AdminBodyDto2;
import uz.gullbozor.gullbozor.entity.AdminBody;
import uz.gullbozor.gullbozor.entity.AdminEntity;
import uz.gullbozor.gullbozor.entity.AdminHeadOne;
import uz.gullbozor.gullbozor.entity.AdminHeadTwo;
import uz.gullbozor.gullbozor.repository.AdminBodyRepo;
import uz.gullbozor.gullbozor.repository.AdminHeadOneRepo;
import uz.gullbozor.gullbozor.repository.AdminRepo;
import uz.gullbozor.gullbozor.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {


    @Autowired
    private AdminService adminService;



    @Autowired
    private AdminRepo adminRepo;

/// TEST
    @PostMapping("/addDataTest/{num}/{day}/{testPv}/{testMonth}/{testYear}")
    public void addDataTest(@PathVariable Byte num, @PathVariable Byte day, @PathVariable Integer testPv, @PathVariable Byte testMonth, @PathVariable Short testYear) {

        adminService.addBodyTest(num, day, testPv, testMonth, testYear);
        adminService.addHeadTwoTest(num, day, testPv, testMonth, testYear);
        adminService.addHeadOneTest(num, day, testPv, testMonth, testYear);

    }






    @PostMapping("/addData/{num}")
    public void addData(@PathVariable Byte num) {

        adminService.addBody(num);

        switch (num) {
            case 1:
            case 2:
            case 3: adminService.addHeadOne(num);
            break;

            case 4:
            case 5:
            case 6: adminService.addHeadTwo(num);
            break;

            default: break;
        }
    }

    @GetMapping("/getHeadOne")
    public ResponseEntity<AdminHeadOne> getHeadOne() {
        AdminHeadOne headOne = adminService.getHeadOneDefault();
        return ResponseEntity.ok(headOne);
    }

    @GetMapping("/getHeadTwo")
    public ResponseEntity<AdminHeadTwo> getHeadTwo() {
        AdminHeadTwo headTwo = adminService.getHeadTwoDefault();
        return ResponseEntity.ok(headTwo);
    }

    @GetMapping("/getHeadOne/{year}/{month}")
    public ResponseEntity<AdminHeadOne> getHeadOne(@PathVariable Short year, @PathVariable Byte month) {
        AdminHeadOne headOne = adminService.getHeadOne(year,month);
        return ResponseEntity.ok(headOne);
    }

    @GetMapping("/getHeadTwo/{year}/{month}")
    public ResponseEntity<AdminHeadTwo> getHeadTwo(@PathVariable Short year, @PathVariable Byte month) {
        AdminHeadTwo headTwo = adminService.getHeadTwo(year,month);
        return ResponseEntity.ok(headTwo);
    }

//    @GetMapping("/addBody/{num}")
//    public void addBody(@PathVariable Byte num) {
//        adminService.addBody(num);
//    }

    @GetMapping("/getBody/{num}/{year}/{month}")
    public ResponseEntity<AdminBodyDto2> getBody(@PathVariable Byte num, @PathVariable Short year, @PathVariable Byte month) {
        AdminBodyDto2 adminBodyDto2 = adminService.getBody(num, year, month);
        return ResponseEntity.ok(adminBodyDto2);
    }

    @GetMapping("/getBody/{num}")
    public ResponseEntity<AdminBodyDto2> getBody(@PathVariable Byte num) {
        AdminBodyDto2 adminBodyDto2 = adminService.getBodyDefault(num);
        return ResponseEntity.ok(adminBodyDto2);
    }




//    @PostMapping("/headOne")
//    public ResponseEntity<Long> getDate() {
//
//
//
//
//    }

    @GetMapping("/create")
    public ResponseEntity<AdminEntity> createTable() {
        AdminEntity adminEntity = new AdminEntity();
        adminRepo.save(adminEntity);
        return ResponseEntity.ok(adminEntity);
    }





}


