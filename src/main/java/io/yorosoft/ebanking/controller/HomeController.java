package io.yorosoft.ebanking.controller;

import io.yorosoft.ebanking.dao.RoleDao;
import io.yorosoft.ebanking.model.User;
import io.yorosoft.ebanking.model.security.UserRole;
import io.yorosoft.ebanking.service.UserService;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private UserService userService;

    private RoleDao roleDao;

    @Autowired
    public HomeController(UserService userService, RoleDao roleDao){
        this.userService = userService;
        this.roleDao = roleDao;
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
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));
            userService.createUser(user,userRoles);
            return "redirect:/";
        }
    }

}
