package life.majiang.community.controller;

import life.majiang.community.dto.AccessTockenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTockenDTO accessTockenDTO = new AccessTockenDTO();
        accessTockenDTO.setClient_id(clientID);
        accessTockenDTO.setClient_secret(clientSecret);
        accessTockenDTO.setCode(code);
        accessTockenDTO.setRedirect_uri(redirecturi);
        accessTockenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTockenDTO);
        GithubUser user = githubProvider.getuser(accessToken);
        //System.out.println(user.getName());
        return "index";
    }
}
