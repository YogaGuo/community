package life.yogaguo.community.controller;

import life.yogaguo.community.dto.CommentDTO;
import life.yogaguo.community.dto.ResultDTO;
import life.yogaguo.community.exception.CustomizeErrorCodde;
import life.yogaguo.community.model.Comment;
import life.yogaguo.community.model.User;
import life.yogaguo.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommenController {

    @Autowired
    private CommentService commentService;
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public@ResponseBody Object postComment(@RequestBody CommentDTO commentDTO,
                                           HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCodde.NO_LOGIN);
        }
        if(commentDTO == null || StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCodde.COMMENT_IS_EMPTY);
        }
        Comment comment  = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setParentType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentor(user.getId());
        comment.setLikeCount(0);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
