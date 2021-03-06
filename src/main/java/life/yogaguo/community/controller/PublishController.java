package life.yogaguo.community.controller;

import life.yogaguo.community.dto.QuestionDTO;
import life.yogaguo.community.model.Question;
import life.yogaguo.community.model.User;
import life.yogaguo.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,Model model){
        QuestionDTO question = questionService.getById(id);
        //回显页面内容
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tags",question.getTags());
        model.addAttribute("id",question.getId());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
   public String doPost(
           @RequestParam( value = "title",required = false) String title,
           @RequestParam(value = "description",required = false) String description,
           @RequestParam(value = "tags",required = false) String tags,
           @RequestParam(value = "id",required = false) Integer id,
           HttpServletRequest request,
           Model model)

    {   //用于回显
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tags",tags);
        if(title == null || tags == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description == null || description == ""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tags == null || tags == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user =(User)request.getSession().getAttribute("user");
        if( user == null){
             model.addAttribute("error","用户未登陆");
             return "publish";
         }
        Question question = new Question();
         question.setTitle(title);
         question.setDescription(description);
         question.setTags(tags);
         question.setCreator(user.getId());
         question.setGmtCreate(System.currentTimeMillis());
         question.setGmtModified(question.getGmtCreate());
         question.setId(id);
         questionService.createOrUpdate(question);
         return "redirect:/";

    }
}
