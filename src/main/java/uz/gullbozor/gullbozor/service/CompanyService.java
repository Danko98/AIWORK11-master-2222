package uz.gullbozor.gullbozor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.dto.CompanyRegisterDto;
import uz.gullbozor.gullbozor.entity.CompanyEntity;
import uz.gullbozor.gullbozor.entity.UserEntity;
import uz.gullbozor.gullbozor.repository.CompanyRepo;
import uz.gullbozor.gullbozor.repository.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CompanyRepo companyRepo;

    public ApiResponse addCompany(CompanyRegisterDto companyRegisterDto) {
        if (!companyRepo.existsByCompanyOwnerId(companyRegisterDto.getCompanyOwnerId())) {

            CompanyEntity companyEntity = new CompanyEntity();
            companyEntity.setCompanyName(companyRegisterDto.getCompanyName());
            companyEntity.setCompanyOwnerId(companyRegisterDto.getCompanyOwnerId());
            companyEntity.setCityId(companyRegisterDto.getCityId());
            companyEntity.setLocation(companyRegisterDto.getLocation());
            companyEntity.setDealerId(companyRegisterDto.getDillerId());
            companyEntity.setInstallIsFree(companyRegisterDto.getInstallIsFree());
            companyEntity.setPartOfDoor(companyRegisterDto.getPartOfDoor());
            companyEntity.setPartOfWindow(companyRegisterDto.getPartOfWindow());
            companyEntity.setPhoneNumber(companyRegisterDto.getPhoneNumber());
            companyEntity.setRegionId(companyRegisterDto.getRegionId());
            companyEntity.setTgLink(companyRegisterDto.getTgLink());

            CompanyEntity save = companyRepo.save(companyEntity);
            Long id = save.getId();

            Optional<UserEntity> optionalUserEntity = userRepo.findById(companyRegisterDto.getCompanyOwnerId());
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setCompanyId(id);

            userRepo.save(userEntity);

            return new ApiResponse("Yangi korxona ma'lumotlari saqlandi",true);
        }else {
            return new ApiResponse("Bu Foydalanuvchining korxonasi mavjud yangi yaratish uchun eskisini o'chirib tashlang!!!",false);
        }

    }

    public ApiResponse editCompany(CompanyRegisterDto companyRegisterDto, Long companyId) {

        if (companyRepo.existsById(companyId)) {

            if (companyRepo.existsByCompanyOwnerId(companyRegisterDto.getCompanyOwnerId())) {

                Optional<CompanyEntity> optionalCompanyEntity = companyRepo.findById(companyId);
                CompanyEntity companyEntity = optionalCompanyEntity.get();

                companyEntity.setId(companyId);
                companyEntity.setCompanyName(companyRegisterDto.getCompanyName());
                companyEntity.setCompanyOwnerId(companyRegisterDto.getCompanyOwnerId());
                companyEntity.setCityId(companyRegisterDto.getCityId());
                companyEntity.setLocation(companyRegisterDto.getLocation());
                companyEntity.setDealerId(companyRegisterDto.getDillerId());
                companyEntity.setInstallIsFree(companyRegisterDto.getInstallIsFree());
                companyEntity.setPartOfDoor(companyRegisterDto.getPartOfDoor());
                companyEntity.setPartOfWindow(companyRegisterDto.getPartOfWindow());
                companyEntity.setPhoneNumber(companyRegisterDto.getPhoneNumber());
                companyEntity.setRegionId(companyRegisterDto.getRegionId());
                companyEntity.setTgLink(companyRegisterDto.getTgLink());

                companyRepo.save(companyEntity);

                return new ApiResponse("Korxona ma'lumotlari o'zgartirildi", true);
            } else {
                return new ApiResponse("Bu Foydalanuvchining korxonasi mavjud yangi yaratish uchun eskisini o'chirib tashlang!!!", false);
            }
        }else {
            return new ApiResponse("Bunday korxona topilmadi",false);
        }


    }

    public ApiResponse getCompanyById(Long companyId) {
        if (!companyRepo.existsById(companyId)) {
            return new ApiResponse("Bunda korxona topilmadi",true);
        }
        Optional<CompanyEntity> optionalCompanyEntity = companyRepo.findById(companyId);
        return new ApiResponse(optionalCompanyEntity.get());
    }

    public List<CompanyEntity> getCompanyList() {
        return companyRepo.findAll();
    }

    public List<CompanyEntity> getCompanyListByRegionId(Integer regionId) {
        return companyRepo.findAllByRegionId(regionId);
    }

    public List<CompanyEntity> getCompanyListByCityId(Integer cityId) {
        return companyRepo.findAllByCityId(cityId);
    }

    public ApiResponse deleteCompany(Long companyId) {
        if (!companyRepo.existsById(companyId)) {
            return new ApiResponse("Bunday korxona topilmadi",false);
        }
        companyRepo.deleteById(companyId);
        return new ApiResponse("Korxona o'chirildi",true);
    }


}
