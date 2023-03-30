package uz.gullbozor.gullbozor.verifysms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberDto {
    private String phoneNumber;
    private String otp;

    public PhoneNumberDto(String phoneNum, String otp) {
        this.phoneNumber = phoneNum;
        this.otp = otp;
    }

    public PhoneNumberDto(String phoneNum) {
        this.phoneNumber = phoneNum;
    }
}
