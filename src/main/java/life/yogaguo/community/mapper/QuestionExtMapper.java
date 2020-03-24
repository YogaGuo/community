package life.yogaguo.community.mapper;

import life.yogaguo.community.model.Question;
import life.yogaguo.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface QuestionExtMapper {
    /**
     *
     * @param record
     * @return
     */
    int incrementReadCount(Question record);

    /**
     *
     * @return
     */
    int incrementCommentCount(Question question);
}