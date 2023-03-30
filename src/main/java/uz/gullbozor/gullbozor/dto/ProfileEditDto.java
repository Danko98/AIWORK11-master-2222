package uz.gullbozor.gullbozor.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
public class ProfileEditDto {

    private String phoneNumber; //username

    private String userName; //username

    private String surname;

    private Long companyId;

}
