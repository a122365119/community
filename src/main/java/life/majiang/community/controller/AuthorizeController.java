package life.majiang.community.controller;

import life.majiang.community.dto.AccessTockenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.net.httpserver.HttpServerImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirecturi;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request ){
        AccessTockenDTO accessTockenDTO = new AccessTockenDTO();
        accessTockenDTO.setClient_id(clientID);
        accessTockenDTO.setClient_secret(clientSecret);
        accessTockenDTO.setCode(code);
        accessTockenDTO.setRedirect_uri(redirecturi);
        accessTockenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTockenDTO);
        GithubUser githubUser = githubProvider.getuser(accessToken);
        if (githubUser != null) {
            //登陆成功，写cookie和session
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            //登陆失败，重新登陆
            return "redirect:/";
        }
    }
}
