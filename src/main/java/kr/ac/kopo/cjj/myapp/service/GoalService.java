package kr.ac.kopo.cjj.myapp.service;

import kr.ac.kopo.cjj.myapp.model.Goal;
import kr.ac.kopo.cjj.myapp.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * 목표 관련 비즈니스 로직 처리 서비스
 */
@Service
public class GoalService {
    private final GoalRepository goalRepository;
    private final Random random = new Random();

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    /** 모든 목표 조회 */
    public List<Goal> getAllGoals() {
        return goalRepository.getAllGoals();
    }

    /** ID로 목표 조회 */
    public Goal getGoalById(String id) {
        return goalRepository.getGoalById(id);
    }

    public void updateGoal(Goal goal) {
        // 랜덤컬러는 그대로 두거나 새로 부여하지 않습니다
        goalRepository.updateGoal(goal);
    }

    /** 카테고리로 목표 조회 */
    public List<Goal> getGoalsByCategory(String category) {
        return goalRepository.getGoalsByCategory(category);
    }

    /** 새 목표 생성 */
    public void createGoal(Goal goal) {
        // 1) ID가 없으면 자동 생성
        if (goal.getId() == null || goal.getId().isEmpty()) {
            // 예: Goal1, Goal2, ... 형태로 생성
            int nextNum = goalRepository.getAllGoals().size() + 1;
            goal.setId("Goal" + nextNum);
        }

        System.out.printf("imageUrl: %s, randomColor: %s\n", goal.getImageUrl(), goal.getRandomColor());
        // 2) 랜덤 컬러가 없으면 생성
        if (goal.getRandomColor() == null || goal.getRandomColor().isEmpty() && goal.getImageUrl() == null || goal.getImageUrl().isEmpty()) {
            goal.setRandomColor(generateRandomColor());
        }

        // 3) 저장
        goalRepository.setNewGoal(goal);
    }

    /** ID로 목표 삭제 */
    public void deleteGoalById(String id) {
        goalRepository.deleteGoalById(id);
    }

    /** 24비트 랜덤 HEX 컬러 생성 */
    private String generateRandomColor() {
        System.out.printf("Generating random color...!\n");
        return String.format("#%06x", random.nextInt(0x1000000));
    }
}
