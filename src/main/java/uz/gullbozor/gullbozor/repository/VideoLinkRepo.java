package uz.gullbozor.gullbozor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.gullbozor.gullbozor.entity.VideoLink;

public interface VideoLinkRepo extends JpaRepository<VideoLink, Long> {


    Page<VideoLink> findAllByCategoryId(Long categoryId, Pageable pageable);


}
