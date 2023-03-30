package uz.gullbozor.gullbozor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.gullbozor.gullbozor.entity.Category;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    boolean existsByNameAndParentCategoryId(String name, Long parentCategoryId);


    //Parent bo'lgan categoryalarni chaqirish uchun
    List<Category> findAllByParentCategoryId(Long parentCategoryId);


}
