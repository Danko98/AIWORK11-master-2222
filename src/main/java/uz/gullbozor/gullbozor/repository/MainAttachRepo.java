package uz.gullbozor.gullbozor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.gullbozor.gullbozor.entity.Attach;
import uz.gullbozor.gullbozor.entity.MainAttach;

import java.util.Optional;

public interface MainAttachRepo extends JpaRepository<MainAttach, Long> {


}
