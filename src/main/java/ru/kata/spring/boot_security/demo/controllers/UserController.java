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

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleCrudRepository roleCrudRepository;


    @GetMapping("/admin")
    public ModelAndView usersPage(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        UserListWrapper userListWrapper = new UserListWrapper();
        userListWrapper.setUserList(userService.getUsersList());
        modelAndView.addObject("usersinwrapper", userListWrapper);
//        modelAndView.addObject("usersList", userService.getUsersList());
        modelAndView.addObject("roles", roleCrudRepository.findAllByRoleNameNotNull());
        modelAndView.addObject("loggeduser", user);
        return modelAndView;
    }

    @GetMapping("/user")
    public ModelAndView userPage(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

//    @GetMapping("/edit/{userId}")
//    public String getUserForEdit(@PathVariable("userId") Long userId, Model model) {
//        model.addAttribute("editinguser", userService.getUser(userId));
//        return "userr";
//    }

//    @GetMapping("/allusers")
//    public String getAllUsers(Model model) {
//        model.addAttribute("allusers", userService.getUsersList());
//        return "admin/allusers";
//    }

//    @GetMapping("/edit/{userId}")
//    public ModelAndView userEditPage(@PathVariable("userId") Long userId) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("redirect:/admin");
//        modelAndView.addObject("user", userService.getUser(userId));
//        modelAndView.addObject("roles", roleCrudRepository.findAllByRoleNameNotNull());
//        return modelAndView;
//    }

    @PutMapping("/edit")
    public ModelAndView userEdit(@ModelAttribute("usersinwrapper") UserListWrapper userListWrapper,
                                 @RequestParam(value = "action") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
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
        modelAndView.setViewName("redirect:/admin");
        if (action.equals("delete")) {
            userService.deleteUser(userListWrapper.getUserList()
                    .stream()
                    .filter(u -> u.getId() != null)
                    .findFirst().orElseThrow());
        }
        return modelAndView;
    }

//    @GetMapping(value = "/add")
//    public ModelAndView userAddPage() {
//        User newUser = new User();
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("adduser");
//        modelAndView.addObject("newuser", newUser);
//        modelAndView.addObject("roles", roleCrudRepository.findAllByRoleNameNotNull());
//        return modelAndView;
//    }

    @PostMapping("/add")
    public ModelAndView userAdd(@ModelAttribute("user") User user,
                                @RequestParam(value = "action") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        if (action.equals("save")) {
            userService.saveUser(user);
        }
        return modelAndView;
    }

//    @GetMapping("/delete/{userId}")
//    public ModelAndView userDeletePage(@PathVariable("userId") Long userId) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("deleteuser");
//        modelAndView.addObject("user", userService.getUser(userId));
//        return modelAndView;
//    }

//    @DeleteMapping("/delete/{userId}")
//    public ModelAndView userDelete(@ModelAttribute("user") User user,
//                                   @ModelAttribute("userId") Long id,
//                                   @RequestParam(value = "action") String action) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("redirect:/admin");
//        if (action.equals("delete")) {
//            user.setId(id);
//            userService.deleteUser(user);
//        }
//        return modelAndView;
//    }


//    private UserService userService;
//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/admin")
//    public ModelAndView usersPage() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("admin");
//        modelAndView.addObject("usersList", userService.getUsersList());
//        return modelAndView;
//    }
//
//    @GetMapping("/user")
//    public ModelAndView userPage(@AuthenticationPrincipal User user) {
// //       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("user");
//        modelAndView.addObject("user", user);
////        modelAndView.addObject("genders", Gender.values());
//        return modelAndView;
//    }
//
//    @GetMapping("/edit/{userId}")
//    public ModelAndView userEditPage(@PathVariable("userId") Long userId) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("edituser");
//        modelAndView.addObject("user", userService.getUser(userId));
//        return modelAndView;
//    }
//
//    @PutMapping("/edit")
//    public ModelAndView userEdit(@ModelAttribute("user") User user,
//                                 @RequestParam(value = "action") String action) {
//        ModelAndView modelAndView = new ModelAndView();
//        User userFromDB = userService.getUser(user.getId());
//        modelAndView.setViewName("redirect:/admin");
//        if (action.equals("save")) {
//            user.setRole(userFromDB.getRole());
//            userService.saveUser(user);
//        }
//        return modelAndView;
//    }
//
//    @GetMapping(value = "/add")
//    public ModelAndView userAddPage() {
//        User newUser = new User();
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("adduser");
//        modelAndView.addObject("newuser", newUser);
//        return modelAndView;
//    }
//
//    @PostMapping ("/add")
//    public ModelAndView userAdd(@ModelAttribute("user") User user,
//                                @RequestParam(value = "action") String action) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("redirect:/admin");
//        if (action.equals("save")) {
//            Set<Role> userRoleSet = new HashSet<>();
//            userRoleSet.add(new Role("ROLE_USER"));
//            user.setRole(userRoleSet);
//            userService.saveUser(user);
//        }
//        return modelAndView;
//    }
//
//    @GetMapping("/delete/{userId}")
//    public ModelAndView userDeletePage(@PathVariable("userId") Long userId) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("deleteuser");
//        modelAndView.addObject("user", userService.getUser(userId));
//        return modelAndView;
//    }
//
//    @DeleteMapping("/delete/{userId}")
//    public ModelAndView userDelete(@ModelAttribute("user") User user,
//                                   @ModelAttribute("userId") Long id,
//                                   @RequestParam(value = "action") String action) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("redirect:/admin");
//        if (action.equals("delete")) {
//            user.setId(id);
//            userService.deleteUser(user);
//        }
//        return modelAndView;
//    }

}
