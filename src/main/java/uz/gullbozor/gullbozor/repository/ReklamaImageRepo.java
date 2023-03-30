package uz.gullbozor.gullbozor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.gullbozor.gullbozor.entity.Reklama;

import java.util.Optional;

public interface ReklamaImageRepo extends JpaRepository<Reklama, Integer> {

    Optional<Reklama> findByPlaceNumber(Integer placeNumber);

}
