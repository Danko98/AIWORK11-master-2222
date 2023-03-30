package uz.gullbozor.gullbozor.dto;

import lombok.Getter;
import lombok.Setter;
import uz.gullbozor.gullbozor.entity.AddressEntity;


@Getter
@Setter
public class ShopDto {

    private String shopName;
    private Long sellerId;
    private String phoneNumber1;
    private String phoneNumber2;
    private String streetHouse;
    private Integer regionId;
    private Integer cityId;
}
