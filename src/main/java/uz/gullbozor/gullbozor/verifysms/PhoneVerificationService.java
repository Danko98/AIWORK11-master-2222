package uz.gullbozor.gullbozor.verifysms;

import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneVerificationService {

    private final TwilioProperties twilioProperties;

    @Autowired
    public PhoneVerificationService(TwilioProperties twilioProperties) {
        this.twilioProperties = twilioProperties;
    }

    //method to send to otp
    public VerificationResult startVerification(String phone) {
        try {
            Verification verification = Verification.creator(twilioProperties.getServiceSid(), phone, "sms").create();
            if ("approved".equals(verification.getStatus()) || "pending".equals(verification.getStatus())) {
                return new VerificationResult(verification.getSid());
            }
        }catch (ApiException e) {
            return new VerificationResult(new String[] {e.getMessage()});
        }
        return null;
    }


    public VerificationResult checkVerification(String phone, String code) {
        try {
            VerificationCheck verification = VerificationCheck.creator(twilioProperties.getServiceSid(), code).setTo(phone).create();
            if ("approved".equals(verification.getStatus()) ) {
                return new VerificationResult(verification.getSid());
            }
            return new VerificationResult(new String[]{"Invalid code"});
        }catch (ApiException e) {
            return new VerificationResult(new String[] {e.getMessage()});
        }

    }

}
