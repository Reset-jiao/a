package com.common.a.controller;

import com.common.a.dto.AccessToken;
import com.common.a.dto.GitHubUser;
import com.common.a.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GitHubController {

    @Autowired
    GitHubProvider gitHubProvider;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, Model model) {
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id("Iv1.7048a4811ab297d6");
        accessToken.setClient_secret("4b1dcce7c90e1fca9c7d212223c836975c152970");
        accessToken.setCode(code);
        accessToken.setRedirect_uri("http://localhost:8887/callback");
        accessToken.setState(state);
        String token = gitHubProvider.getAccessToken(accessToken);
        GitHubUser user = gitHubProvider.getUser(token);
        model.addAttribute("user", user);
        return "index";
    }
}
