package uz.gullbozor.gullbozor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.dto.*;
import uz.gullbozor.gullbozor.entity.UserEntity;
import uz.gullbozor.gullbozor.repository.UserRepo;
import uz.gullbozor.gullbozor.verifysms.PhoneVerificationService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PhoneVerificationService phoneVerificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ApiResponse editUser(ProfileEditDto registerDto, Long id) {

        if (!userRepo.existsById(id)) {
            return new ApiResponse("Bunday foydalanuvchi topilmadi",false);
        }

        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        UserEntity userEntity = optionalUserEntity.get();

        if (    userRepo.existsByUsername(registerDto.getPhoneNumber())    &&     (! registerDto.getPhoneNumber().equals(userEntity.getUsername()))    ) {
            return new ApiResponse("Bu telefon raqam ro'yxatdan o'tgan",false);
        }

        userEntity.setUsernameTest(registerDto.getUserName());
        userEntity.setUsername(registerDto.getPhoneNumber());
        userEntity.setSurname(registerDto.getSurname());
        userEntity.setPassword(passwordEncoder.encode(registerDto.getUserName()));
        userEntity.setCompanyId(registerDto.getCompanyId());
        userRepo.save(userEntity);

    return new ApiResponse("Profil muvaffaqiyatli tahrirlandi",true);

    }

    public ApiResponse getUserById(Long id) {
        if (!userRepo.existsById(id)) {
            return new ApiResponse("Not found User account",false);
        }
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        return new ApiResponse(optionalUserEntity.get());
    }

    public List<UserEntity> getUserList() {
        return userRepo.findAll();
    }

    public ApiResponse deleteUserById(Long id) {
        if (!userRepo.existsById(id)) {
            return new ApiResponse("Not found user account ",false);
        }
        userRepo.deleteById(id);
        return new ApiResponse("Successfully deleted",true);
    }



}
