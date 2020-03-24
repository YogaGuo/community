package life.yogaguo.community.service;

import life.yogaguo.community.dto.PageDTO;
import life.yogaguo.community.dto.QuestionDTO;
import life.yogaguo.community.exception.CustomizeErrorCodde;
import life.yogaguo.community.exception.CustomizeException;
import life.yogaguo.community.mapper.QuestionExtMapper;
import life.yogaguo.community.mapper.QuestionMapper;
import life.yogaguo.community.mapper.UserMapper;
import life.yogaguo.community.model.Question;
import life.yogaguo.community.model.QuestionExample;
import life.yogaguo.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("all")
@Service
public class QuestionService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    public PageDTO select(Integer page, Integer size) {
        Integer totalPages;
        PageDTO pageDTO = new PageDTO();
        Integer totalRecord =(int)questionMapper.countByExample(new QuestionExample());
        if(totalRecord %  size == 0){
            totalPages = totalRecord / size;
        }else{
            totalPages = (totalRecord / size) + 1;
        }
        if(page < 1){
            page=1;
        }
        if(page >totalPages){
            page = totalPages;
        }
        pageDTO.setPage(totalPages,page);
        int offSet = size*(page-1);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offSet,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionList(questionDTOList);
        return pageDTO;
    }

    public PageDTO selectByUserId(Integer userId, Integer page, Integer size) {
        Integer totalPages;
        PageDTO pageDTO = new PageDTO();
        //Integer totalRecord = questionMapper.countByUserId(userId);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCommentCountEqualTo(userId);
        Integer totalRecord  = (int)questionMapper.countByExample(questionExample);
        if(totalRecord %  size == 0){
            totalPages = totalRecord / size;
        }else{
            totalPages = (totalRecord / size) + 1;
        }
        if(page < 1){
            page=1;
        }
        if(page > totalPages){
            page = totalPages;
        }
        pageDTO.setPage(totalPages,page);
        int offSet = size*(page-1);
     //   List<Question> questions = questionMapper.selectByUserId(userId,offSet,size);
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria().andCommentCountEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample1, new RowBounds(offSet, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : questionList){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestionList(questionDTOList);
        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCodde.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        }else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTags(question.getTags());
            QuestionExample questionExample  = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
           int update=questionMapper.updateByExampleSelective(updateQuestion,questionExample);
           if(update != 1){
               throw new CustomizeException(CustomizeErrorCodde.QUESTION_NOT_FOUND);
           }
        }
    }

    public void incremeteReadCount(Integer id) {
        Question record = new Question();
        record.setId(id);
        record.setReadCount(1);
        questionExtMapper.incrementReadCount(record);
    }
}

