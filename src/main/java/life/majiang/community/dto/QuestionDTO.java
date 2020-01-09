package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String tag;
    private String description;
    private String title;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer     likeCount;
    private Integer     creator;
    private Integer     commentCount;
    private Integer     viewCount;
    private User user;

}
