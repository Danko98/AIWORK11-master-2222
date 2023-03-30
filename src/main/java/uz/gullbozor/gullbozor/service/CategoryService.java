package uz.gullbozor.gullbozor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.dto.CategoryDto;
import uz.gullbozor.gullbozor.entity.Category;
import uz.gullbozor.gullbozor.repository.CategoryRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public ApiResponse addCategory(CategoryDto categoryDto) {

        if (categoryRepo.existsByNameAndParentCategoryId(categoryDto.getName(),categoryDto.getParentCategoryId())) {
            return new ApiResponse("This category already exists",false);
        }

        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setParentCategoryId(categoryDto.getParentCategoryId());
        categoryRepo.save(category);
        return new ApiResponse("Successfully saved",true);

    }

    public ApiResponse editCategory(CategoryDto categoryDto, Long id) {

        if (!categoryRepo.existsById(id)) {
            return new ApiResponse("Not found category",false);
        }

        if (categoryRepo.existsByNameAndParentCategoryId(categoryDto.getName(), categoryDto.getParentCategoryId())) {
            return new ApiResponse("This category already exists",false);
        }

        Optional<Category> optionalCategory = categoryRepo.findById(id);
        Category category = optionalCategory.get();
        category.setName(categoryDto.getName());
        category.setParentCategoryId(categoryDto.getParentCategoryId());
        categoryRepo.save(category);
        return new ApiResponse("Successfully edited",true);
    }

    public List<Category> getAllCategory() {
        return categoryRepo.findAll();
    }

    public ApiResponse getCategoryById(Long id) {

        if (!categoryRepo.existsById(id)) {
            return new ApiResponse("Not found category",false);
        }

        Optional<Category> optionalCategory = categoryRepo.findById(id);
        return new ApiResponse(optionalCategory.get());
    }

    public List<Category> getAllByParentId(Long parentId) {

        List<Category> allByParentCategory = categoryRepo.findAllByParentCategoryId(parentId);

        return allByParentCategory;


    }

    public ApiResponse deleteCategoryById(Long id) {
        if (!categoryRepo.existsById(id)) {
            return new ApiResponse("Not fount category", false);
        }
        categoryRepo.deleteById(id);
        return new ApiResponse("Successfully deleted",true);
    }


}
