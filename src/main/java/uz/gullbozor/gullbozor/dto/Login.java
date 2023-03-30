package uz.gullbozor.gullbozor.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Login {

    @NotNull
    private String phoneNumber; //username

    @NotNull
    private String userName; //userning passwordi


}
