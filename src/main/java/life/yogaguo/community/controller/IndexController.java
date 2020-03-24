package life.yogaguo.community.controller;

import life.yogaguo.community.dto.PageDTO;
import life.yogaguo.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by Yogaguo on 2019/11/28
 */
@Controller
public class IndexController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/")
    public String index(@RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue ="2" ) Integer size,
                        Model model){
        PageDTO pages = questionService.select(page,size);
        model.addAttribute("pages",pages);
        return "index";
    }
}
