package life.yogaguo.community.dto;

import life.yogaguo.community.model.User;
import lombok.Data;

@Data
public class CommentToPage {
    private Integer id;


    private Integer parentId;


    private Integer parentType;


    private Integer commentor;


    private Long gmtCreate;


    private Long gmtModified;


    private Integer likeCount;


    private String content;

    private User user;

}
