package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserListWrapper;
import ru.kata.spring.boot_security.demo.repository.RoleCrudRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AppController {


    private UserService userService;
    private RoleCrudRepository roleCrudRepository;

    @Autowired
    public AppController(UserService userService, RoleCrudRepository roleCrudRepository) {
        this.userService = userService;
        this.roleCrudRepository = roleCrudRepository;
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView mainPage(@AuthenticationPrincipal User loggedUser) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        UserListWrapper userListWrapper = new UserListWrapper();
        userListWrapper.setUserList(userService.getUsersList());
        modelAndView.addObject("usersinwrapper", userListWrapper);
        modelAndView.addObject("newuser", new User());
        modelAndView.addObject("roles", roleCrudRepository.findAllByRoleNameNotNull());
        modelAndView.addObject("loggeduser", loggedUser);
        return modelAndView;
    }

    @GetMapping("/user")
    public ModelAndView userPage(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        modelAndView.addObject("loggeduser", user);
        return modelAndView;
    }

    @PutMapping("/edit")
    public ModelAndView userEdit(@ModelAttribute("usersinwrapper") UserListWrapper userListWrapper,
                                 @RequestParam(value = "action") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        if (action.equals("save")) {
            userService.saveUser(userListWrapper.getUserList()
                    .stream()
                    .filter(u -> u.getId() != null)
                    .findFirst().orElseThrow());
        }
        return modelAndView;
    }

    @DeleteMapping("/delete")
    public ModelAndView userDelete(@ModelAttribute("usersinwrapper") UserListWrapper userListWrapper,
                                   @RequestParam(value = "action") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        if (action.equals("delete")) {
            userService.deleteUser(userListWrapper.getUserList()
                    .stream()
                    .filter(u -> u.getId() != null)
                    .findFirst().orElseThrow());
        }
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView userAdd(@ModelAttribute("user") User user,
                                @RequestParam(value = "action") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        if (action.equals("add")) {
            userService.saveUser(user);
        }
        return modelAndView;
    }

    //    private boolean isAuthenticated() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || AnonymousAuthenticationToken.class.
//                isAssignableFrom(authentication.getClass())) {
//            return false;
//        }
//        return authentication.isAuthenticated();
//    }

}
