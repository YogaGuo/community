package life.yogaguo.community.controller;

import life.yogaguo.community.dto.AccessTokenTO;
import life.yogaguo.community.dto.GithubUser;
import life.yogaguo.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Ctrl alt + v -> 快速new出对象
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenTO accessTokenTO = new AccessTokenTO();
        accessTokenTO.setCode(code);
        accessTokenTO.setRedirect_uri(redirectUri);
        accessTokenTO.setState(state);
        accessTokenTO.setClient_id(clientId);
        accessTokenTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenTO);
        GithubUser user = githubProvider.getUser(accessToken);
         System.out.println(user.getName());
        return "index";
    }
}
