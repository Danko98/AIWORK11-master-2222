package uz.gullbozor.gullbozor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.gullbozor.gullbozor.entity.AdminBody;

import java.util.List;
import java.util.Optional;

public interface AdminBodyRepo extends JpaRepository<AdminBody, Long> {

    Optional<AdminBody> findByYearAndMonthAndTypeNumberAndDay(Short year, Byte month, Byte typeNumber, Byte day);
    List<AdminBody> findAllByYearAndMonthAndTypeNumber(Short year, Byte month, Byte typeNumber);
    boolean existsByTypeNumber(Byte typeNumber);
    boolean existsByMonth(Byte month);
    boolean existsByYearAndMonth(Short year, Byte month);
    boolean existsByYearAndMonthAndTypeNumberAndDay(Short year, Byte month, Byte typeNumber, Byte day);
    boolean existsByYearAndMonthAndTypeNumber(Short year, Byte month, Byte typeNumber);




}
