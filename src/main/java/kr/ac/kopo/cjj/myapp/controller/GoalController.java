package kr.ac.kopo.cjj.myapp.controller;

import kr.ac.kopo.cjj.myapp.model.Goal;
import kr.ac.kopo.cjj.myapp.service.GoalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    /** 목표 리스트 페이지 렌더링 */
    @GetMapping
    public String listGoals(Model model) {
        List<Goal> goals = goalService.getAllGoals();
        model.addAttribute("goals", goals);
        return "home";  // home.html에 goals를 렌더링
    }

    /** 새 목표 생성 폼 */
    @GetMapping("/new")
    public String newGoalForm(Model model) {
        model.addAttribute("goal", new Goal());
        return "goal-form";
    }

    /** 새 목표 등록 */
    @PostMapping("/new")
    public String createGoal(@ModelAttribute Goal goal) {
        goalService.createGoal(goal);
        return "redirect:/";
    }

    /** 목표 수정 폼 */
    @GetMapping("/{id}/edit")
    public String editGoalForm(@PathVariable String id, Model model) {
        Goal goal = goalService.getGoalById(id);
        model.addAttribute("goal", goal);
        return "goal-form";
    }

    /** 목표 업데이트 */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id, @ModelAttribute Goal goal) {
        goal.setId(id);
        goalService.updateGoal(goal);     // ← create가 아니라 update 호출
        return "redirect:/";
    }

    /** 목표 삭제 GET 매핑 */
    @GetMapping("/{id}/delete")
    public String deleteGoal(@PathVariable String id) {
        goalService.deleteGoalById(id);
        return "redirect:/goals";
    }
}
