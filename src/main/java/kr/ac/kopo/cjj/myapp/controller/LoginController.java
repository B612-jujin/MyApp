package kr.ac.kopo.cjj.myapp.controller;

import kr.ac.kopo.cjj.myapp.service.GoalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final GoalService goalService;

    public LoginController(GoalService goalService) {
        this.goalService = goalService;
    }

    // 4-1) 커스텀 로그인 페이지 매핑
    @GetMapping("/login")
    public String loginPage() {
        // src/main/resources/templates/login.html 뷰를 반환
        return "login";
    }

    // 4-2) 인증 성공 후 이동할 홈 페이지 매핑
    @GetMapping({"/", "/home"})
    public String homePage(Model model) {
        // 서비스에서 목표 리스트를 가져와 모델에 담아줍니다.
        model.addAttribute("goals", goalService.getAllGoals());
        return "home";  // templates/home.html
    }

    @GetMapping("/logout")
    public String logoutPage() {
        // 로그아웃 후 이동할 페이지 (로그아웃 성공 메시지 등)
        return "login"; // src/main/resources/templates/login.html 뷰를 반환
    }
}
