package life.yogaguo.community.controller;

import life.yogaguo.community.dto.CommentDTO;
import life.yogaguo.community.dto.CommentToPage;
import life.yogaguo.community.dto.QuestionDTO;
import life.yogaguo.community.mapper.QuestionMapper;
import life.yogaguo.community.service.CommentService;
import life.yogaguo.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 *
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
     @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id, Model model){

         QuestionDTO questionDTO = questionService.getById(id);
         List<CommentToPage> comments=commentService.listQuestionId(id);
         questionService.incremeteReadCount(id);
         model.addAttribute("question",questionDTO);
         model.addAttribute("comments",comments);
        return "question";
    }
}
