package life.yogaguo.community.controller;

import life.yogaguo.community.dto.AccessTokenTO;
import life.yogaguo.community.dto.GithubUser;
import life.yogaguo.community.mapper.UserMapper;
import life.yogaguo.community.model.User;
import life.yogaguo.community.provider.GithubProvider;
import life.yogaguo.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Ctrl alt + v -> 快速new出对象
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){
        AccessTokenTO accessTokenTO = new AccessTokenTO();
        accessTokenTO.setCode(code);
        accessTokenTO.setRedirect_uri(redirectUri);
        accessTokenTO.setState(state);
        accessTokenTO.setClient_id(clientId);
        accessTokenTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenTO);

        GithubUser githubuser = githubProvider.getUser(accessToken);
        if(githubuser != null && githubuser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubuser.getName());
            user.setAccountId(String.valueOf(githubuser.getId()));
            user.setAvatarUrl(githubuser.getAvatarUrl());
            userService.createOrUpdateUser(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
