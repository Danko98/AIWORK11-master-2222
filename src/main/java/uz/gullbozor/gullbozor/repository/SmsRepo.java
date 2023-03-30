package uz.gullbozor.gullbozor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.gullbozor.gullbozor.entity.SmsToken;

public interface SmsRepo extends JpaRepository<SmsToken, Long> {
    boolean existsBySmsToken(String smsToken);
}
