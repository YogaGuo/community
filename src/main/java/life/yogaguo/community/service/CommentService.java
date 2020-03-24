package life.yogaguo.community.service;

import life.yogaguo.community.dto.CommentToPage;
import life.yogaguo.community.ennums.CommentTypeEnum;
import life.yogaguo.community.exception.CustomizeErrorCodde;
import life.yogaguo.community.exception.CustomizeException;
import life.yogaguo.community.mapper.CommentMapper;
import life.yogaguo.community.mapper.QuestionExtMapper;
import life.yogaguo.community.mapper.QuestionMapper;
import life.yogaguo.community.mapper.UserMapper;
import life.yogaguo.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;
    @Transactional(rollbackFor = Exception.class)
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCodde.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getParentType() == null || !CommentTypeEnum.isExist(comment.getParentType())){
            throw new CustomizeException(CustomizeErrorCodde.TYPE_PARAM_ERROR);
        }
        if(comment.getParentType().equals( CommentTypeEnum.COMMENT.getType())){
            /**
             * 回复评论
             */
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbcomment == null){
                throw new CustomizeException(CustomizeErrorCodde.COMMENT_NOT_FOUND);
            }else {
                commentMapper.insert(comment);
            }
        }else {
            /**
             * 回复问题
             */
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCodde.QUESTION_NOT_FOUND);
            }else {
                commentMapper.insert(comment);
                question.setCommentCount(1);
                questionExtMapper.incrementCommentCount(question);
            }
        }
    }

    public List<CommentToPage> listQuestionId(Integer id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).
                andParentTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments .size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人id
        Set<Integer> collect = comments.stream().map(comment -> comment.getCommentor()).collect(Collectors.toSet());
        List<Integer> userList = new ArrayList<>();
        userList.addAll(collect);

        //获取去重评论人并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userList);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment为commentToPage
        List<CommentToPage> commentToPages = comments.stream().map(comment -> {
            CommentToPage commentToPage = new CommentToPage();
            BeanUtils.copyProperties(comment, commentToPage);
            commentToPage.setUser(userMap.get(comment.getCommentor()));
            return commentToPage;
        }).collect(Collectors.toList());
        return  commentToPages;
    }
}
