package uz.gullbozor.gullbozor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSaveDto {

    private Long orderId;
    private String orderOwnerName;
    private String orderOwnerPhone;

}
