package io.yorosoft.ebanking.controller;

import io.yorosoft.ebanking.model.User;
import io.yorosoft.ebanking.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private UserService userService;

    @Autowired
    public HomeController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() { return "index"; }
    
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("user",new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@ModelAttribute("user") User user, Model model){
        if(userService.checkUserExists(user.getUsername(), user.getEmail())){
            if(userService.CheckUsernameExists(user.getUsername())) model.addAttribute("usernameExists", true);
            if(userService.checkEmailExists(user.getEmail())) model.addAttribute("emailExists", true);
            return "signup";
        }else{
            userService.save(user);
            return "redirect:/";
        }
    }

}
