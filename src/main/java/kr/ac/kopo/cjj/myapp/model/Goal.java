// src/main/java/kr/ac/kopo/cjj/myapp/model/Goal.java
package kr.ac.kopo.cjj.myapp.model;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Goal {

    private String id;

    /** 목표 제목 */
    private String title;

    /** 이미지 URL (없으면 placeholder) */
    private String imageUrl;

    /** 시작일 */
    private String startDate;

    /** 진행도 (0~100) */
    private Integer progress;

    /** 카테고리 (꿈, 취미, 등) */
    private String category;

    /** 랜덤 단색 placeholder 용 컬러 코드 */
    private String randomColor;

    public Goal() {}

    // 필요하다면 생성자 추가
    public Goal(String title, String startDate, Integer progress, String category) {
        this.title = title;
        this.startDate = startDate;
        this.progress = progress;
        this.category = category;
        // imageUrl, randomColor는 Service 레이어에서 세팅
    }

}
