package uz.gullbozor.gullbozor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.entity.DealerEntity;
import uz.gullbozor.gullbozor.entity.RegionEntity;
import uz.gullbozor.gullbozor.repository.DealerRepo;
import uz.gullbozor.gullbozor.repository.RegionRepo;

import java.util.List;
import java.util.Optional;

@Service
public class DealerService {

    @Autowired
    private DealerRepo dealerRepo;

    public ApiResponse addDealer(List<DealerEntity> dealerEntityList) {


        for (DealerEntity dealerEntity : dealerEntityList) {

            if (!dealerRepo.existsByDealerName(dealerEntity.getDealerName())) {
                dealerRepo.save(dealerEntity);
            }

        }
        return new ApiResponse("Successfully saved",true);
    }

    public ApiResponse editDealer(DealerEntity dealerEntityDto, Integer id) {

        if (!dealerRepo.existsById(id)) {
            return new ApiResponse("Not found dealer",false);
        }

        if (dealerRepo.existsByDealerName(dealerEntityDto.getDealerName())) {
            return new ApiResponse("This region name already exists",false);
        }
        Optional<DealerEntity> optionalDealerEntity = dealerRepo.findById(id);
        DealerEntity dealerEntity = optionalDealerEntity.get();
        dealerEntity.setDealerName(dealerEntityDto.getDealerName());
        dealerRepo.save(dealerEntity);
        return new ApiResponse("Successfully edited",true);
    }

    public ApiResponse getDealerById(Integer id) {
        if (!dealerRepo.existsById(id)) {
            return new ApiResponse("Not found dealer",false);
        }
        Optional<DealerEntity> optionalDealerEntity = dealerRepo.findById(id);
        return new ApiResponse(optionalDealerEntity.get());
    }

    public List<DealerEntity> getDealerList() {
        return dealerRepo.findAll();
    }

    public ApiResponse deleteById(Integer id) {
        if (!dealerRepo.existsById(id)) {
            return new ApiResponse("Not found dealer",false);
        }
        dealerRepo.deleteById(id);
        return new ApiResponse("Successfully deleted",true);
    }

}
