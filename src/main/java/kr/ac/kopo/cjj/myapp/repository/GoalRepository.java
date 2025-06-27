// src/main/java/kr/ac/kopo/cjj/myapp/repository/GoalRepository.java
package kr.ac.kopo.cjj.myapp.repository;

import kr.ac.kopo.cjj.myapp.model.Goal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface GoalRepository {
    List<Goal> getAllGoals(); // 모든 목표 조회
    Goal getGoalById(String id); // ID로 목표 조회
    List<Goal> getGoalsByCategory(String category); // 카테고리로 목표 조회
    void setNewGoal(Goal goal); // 새로운 목표 추가

    void deleteGoalById(String id);
    // 필요하다면 커스텀 조회 메서드 추가
    // ▶ 수정 메서드 추가
    void updateGoal(Goal goal);
}
