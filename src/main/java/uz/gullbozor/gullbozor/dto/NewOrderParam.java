package uz.gullbozor.gullbozor.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderParam {


    private Integer width;
    private Integer height;
    private Integer count;
    private Byte colorNumber;
    private Byte glassNumber;
    private Integer category;
    private Byte shelfSize;
    private Long companyId;
    private String orderOwnerName;
    private String orderOwnerPhone;
    private Byte ruchkaTypeNum;
    private String region;
    private String city;
    private boolean responseToCompany;

}
