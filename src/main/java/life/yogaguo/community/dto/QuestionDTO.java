package life.yogaguo.community.dto;

import life.yogaguo.community.model.Question;
import life.yogaguo.community.model.User;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QuestionDTO extends Question {
    private User user;
}
