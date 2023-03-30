package uz.gullbozor.gullbozor.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminBodyDto2 {


    private Integer max;
    private List<AdminBodyDto> adminBodyDtoList;


}
