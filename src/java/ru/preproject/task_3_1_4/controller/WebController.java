package ru.preproject.task_3_1_4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.preproject.task_3_1_4.model.User;
import ru.preproject.task_3_1_4.service.RoleService;
import ru.preproject.task_3_1_4.service.UserService;

import java.security.Principal;

@Controller
public class WebController {

    private final UserService userService;
    private final RoleService roleService;

    public WebController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String admin(@ModelAttribute("createuser") User createuser, Principal principal, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("authenticateduser", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("roles", roleService.listRoles());
        return "source";
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        model.addAttribute("authenticateduser", userService.loadUserByUsername(principal.getName()));
        return "source";
    }

}
