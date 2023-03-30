package uz.gullbozor.gullbozor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.gullbozor.gullbozor.entity.AddressEntity;

public interface AddressRepo extends JpaRepository<AddressEntity, Long> {
}
