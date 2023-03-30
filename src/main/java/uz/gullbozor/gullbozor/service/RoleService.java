package uz.gullbozor.gullbozor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.entity.Role;
import uz.gullbozor.gullbozor.entity.enums.RoleName;
import uz.gullbozor.gullbozor.repository.RoleRepo;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    public ApiResponse addRole() {
        Role role = new Role();
        role.setName(RoleName.valueOf("ROLE_ADMIN"));
        roleRepo.save(role);

        Role role2 = new Role();
        role2.setName(RoleName.valueOf("ROLE_MODER"));
        roleRepo.save(role2);

        Role role3 = new Role();
        role3.setName(RoleName.valueOf("ROLE_USER"));
        roleRepo.save(role3);

        return new ApiResponse("Successfuly create three Role ROLE_ADMIN ROLE_MODER ROLE_USER",true);
    }



}
