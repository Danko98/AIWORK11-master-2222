package uz.gullbozor.gullbozor.dto;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class CategoryDto {

    private Long parentCategoryId;
    private String name;
}
