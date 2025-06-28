// GlobalControllerAdvice.java
package kr.ac.kopo.cjj.myapp;

import kr.ac.kopo.cjj.myapp.custom.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("profilePictureUrl")
    public String profilePictureUrl(Authentication authentication) {
        if (authentication != null
                && authentication.getPrincipal() instanceof CustomUserDetails cud) {
            return cud.getProfilePictureUrl();
        }
        // 인증 안 됐거나 기본 User인 경우 디폴트 이미지
        return "/images/default-profile.png";
    }
}
