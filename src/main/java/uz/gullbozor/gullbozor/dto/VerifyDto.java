package uz.gullbozor.gullbozor.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VerifyDto {


    @NotNull
    private String phoneNumber;

    @NotNull
    private String smsCode;
}
