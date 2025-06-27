package kr.ac.kopo.cjj.myapp.repository;

import kr.ac.kopo.cjj.myapp.model.Goal;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class GoalRepositoryImpl implements GoalRepository {
    private List<Goal> listOfGoals = new ArrayList<Goal>();

    public GoalRepositoryImpl(){
        Goal goal1 = new Goal();
        Goal goal2 = new Goal();
        Goal goal3 = new Goal();
        Goal goal4 = new Goal();

        goal1.setId("Goal1");
        goal1.setTitle("운동하기");
        goal1.setStartDate("2025/06/04");
        goal1.setProgress(0);
        goal1.setCategory("건강");

        goal2.setId("Goal2");
        goal2.setTitle("독서하기");
        goal2.setStartDate("2025/06/05");
        goal2.setProgress(0);
        goal2.setCategory("취미");


        goal3.setId("Goal3");
        goal3.setTitle("코딩하기");
        goal3.setStartDate("2025/06/05");
        goal3.setProgress(0);
        goal3.setCategory("IT");

        listOfGoals.add(goal1);
        listOfGoals.add(goal2);
        listOfGoals.add(goal3);


    }

    @Override
    public List<Goal> getAllGoals() {
        return new ArrayList<>(listOfGoals);
    }

    @Override
    public Goal getGoalById(String id) {
        return listOfGoals.stream()
                .filter(goal -> Objects.equals(goal.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Goal ID: " + id));
    }

    @Override
    public List<Goal> getGoalsByCategory(String category) {
        return listOfGoals.stream()
                .filter(goal -> category.equals(goal.getCategory()))
                .toList();
    }

    @Override
    public void setNewGoal(Goal goal) {
        listOfGoals.add(goal);
    }

    @Override
    public void deleteGoalById(String id) {
        boolean removed = listOfGoals.removeIf(goal -> Objects.equals(goal.getId(), id));
        if (!removed) {
            throw new IllegalArgumentException("삭제할 Goal ID가 없습니다: " + id);
        }
    }

    @Override
    public void updateGoal(Goal goal) {
        for (int i = 0; i < listOfGoals.size(); i++) {
            if (Objects.equals(listOfGoals.get(i).getId(), goal.getId())) {
                listOfGoals.set(i, goal);
                return;
            }
        }
        throw new IllegalArgumentException("수정할 Goal ID가 없습니다: " + goal.getId());
    }
}
