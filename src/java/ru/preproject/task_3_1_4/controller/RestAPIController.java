package ru.preproject.task_3_1_4.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import ru.preproject.task_3_1_4.model.User;
import ru.preproject.task_3_1_4.service.RoleService;
import ru.preproject.task_3_1_4.service.UserService;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping()
public class RestAPIController {
    private final UserService userService;
    private final RoleService roleService;
    private final SpringTemplateEngine templateEngine;

    public RestAPIController(UserService userService, RoleService roleService, SpringTemplateEngine templateEngine) {
        this.userService = userService;
        this.roleService = roleService;
        this.templateEngine = templateEngine;
    }

    @RequestMapping(value = "/api/v1/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.findUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.setPassword("");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/v1/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.addUser(user);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/v1/users/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();

        if (userService.findUserById(id) == null || user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.addUser(user);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/v1/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        if (userService.findUserById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/v1/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getDashboardUser(Principal principal) {
        UserDetails userDetails = userService.loadUserByUsername(principal.getName());
        Context ctx = new Context();
        ctx.setVariable("isAdmin", userDetails.getAuthorities() != null && userDetails.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN"));
        ctx.setVariable("authenticateduser", userDetails);
        return templateEngine.process("source", ctx);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getDashboardAdmin(Principal principal) {
        UserDetails userDetails = userService.loadUserByUsername(principal.getName());
        Context ctx = new Context();
        ctx.setVariable("users", userService.findAll());
        ctx.setVariable("roles", roleService.listRoles());
        ctx.setVariable("isAdmin", userDetails.getAuthorities() != null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        ctx.setVariable("authenticateduser", userDetails);
        return templateEngine.process("source", ctx);
    }
}
