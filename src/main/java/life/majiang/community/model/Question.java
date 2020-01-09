package life.majiang.community.model;

import lombok.Data;

@Data
public class Question {
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

}
