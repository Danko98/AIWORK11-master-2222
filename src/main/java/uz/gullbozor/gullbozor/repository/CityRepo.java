package uz.gullbozor.gullbozor.repository;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.gullbozor.gullbozor.entity.CityEntity;
import uz.gullbozor.gullbozor.entity.RegionEntity;

import java.util.List;

public interface CityRepo extends JpaRepository<CityEntity, Integer> {

    boolean existsByName(String name);
    List<CityEntity> findAllByRegionId(Integer regionId);

}
