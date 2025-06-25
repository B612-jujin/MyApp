package kr.ac.kopo.cjj.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // 4-1) 커스텀 로그인 페이지 매핑
    @GetMapping("/login")
    public String loginPage() {
        // src/main/resources/templates/login.html 뷰를 반환
        return "login";
    }

    // 4-2) 인증 성공 후 이동할 홈 페이지 매핑
    @GetMapping({"/", "/home"})
    public String homePage() {
        // src/main/resources/templates/homeasd.html 뷰를 반환
        return "home";
    }
}
