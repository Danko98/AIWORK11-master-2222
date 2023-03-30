package uz.gullbozor.gullbozor.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AnnounceDto {

    private String title;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String image8;
    private String description;
    private String phoneNumber;
    private Integer topNumber;
    private Integer department;
    private Integer callingCount;
    private Long categoryId;
    private Integer regionId;
    private Integer cityId;
    private Long shopId;
    private Long sellerId;
    private double height;
    private double diameter;
    private double weight;
    private double price;
    private boolean isUSD;
    private boolean myAnnounce;
    private boolean isActive;
    private boolean customer;
    private boolean isWithPot;
    private boolean isWithFertilizer;
    private boolean isAllowed;

}
