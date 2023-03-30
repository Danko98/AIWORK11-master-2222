package uz.gullbozor.gullbozor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.gullbozor.gullbozor.entity.DealerEntity;
import uz.gullbozor.gullbozor.entity.RegionEntity;

public interface DealerRepo extends JpaRepository<DealerEntity, Integer> {
    boolean existsByDealerName(String name);
}
