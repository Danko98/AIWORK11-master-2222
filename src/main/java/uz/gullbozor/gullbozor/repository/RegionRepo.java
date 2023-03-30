package uz.gullbozor.gullbozor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.gullbozor.gullbozor.entity.RegionEntity;

public interface RegionRepo extends JpaRepository<RegionEntity, Integer> {
    boolean existsByName(String name);
}
