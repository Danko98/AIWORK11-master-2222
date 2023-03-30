package uz.gullbozor.gullbozor.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;

@Getter
@Setter
public class AddressDto {

    private Integer regionId;
    private Integer cityId;
    private String StreetAndHouse;

}
