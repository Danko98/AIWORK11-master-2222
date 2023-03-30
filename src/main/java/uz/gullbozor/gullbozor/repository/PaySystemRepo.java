package uz.gullbozor.gullbozor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.gullbozor.gullbozor.entity.PaySystem;

public interface PaySystemRepo extends JpaRepository<PaySystem, Long> {
}
