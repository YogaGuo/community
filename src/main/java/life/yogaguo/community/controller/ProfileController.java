package life.yogaguo.community.controller;

import life.yogaguo.community.dto.PageDTO;
import life.yogaguo.community.model.User;
import life.yogaguo.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
@SuppressWarnings("all")
@Controller
public class ProfileController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue ="2" ) Integer size){
        User user =(User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PageDTO pages = questionService.selectByUserId(user.getId(), page, size);
        model.addAttribute("pages",pages);
        return "profile";
    }
}
