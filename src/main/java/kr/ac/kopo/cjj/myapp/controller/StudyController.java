package kr.ac.kopo.cjj.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudyController {

    @GetMapping("/study")
    public String studyPage() {
        return "study";  // templates/study.html
    }
}
