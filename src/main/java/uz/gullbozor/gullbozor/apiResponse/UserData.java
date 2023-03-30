package uz.gullbozor.gullbozor.apiResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {

    private boolean success;
    private String massage;
    private String token;
    private Long user_id;

    public UserData(String massage, boolean success) {
        this.massage = massage;
        this.success = success;
    }

    public UserData(boolean success, String token, Long user_id) {
        this.success = success;
        this.token = token;
        this.user_id = user_id;
    }

    public UserData() {
    }
}
