package com.greenfox.reddit_project.controllers;

import com.greenfox.reddit_project.models.Post;
import com.greenfox.reddit_project.models.User;
import com.greenfox.reddit_project.services.PostService;
import com.greenfox.reddit_project.services.UserService;
import com.greenfox.reddit_project.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class RedditController {

    private final PostService postService;
    private final UserService userService;
    private final VoteService voteService;

    @Autowired
    public RedditController(PostService postService, UserService userService, VoteService voteService) {
        this.postService = postService;
        this.userService = userService;
        this.voteService = voteService;
    }

    @GetMapping({"/login","/login/"})
    public String showLogin() {
        return "login";
    }

    @PostMapping({"/login","/login/"})
    public String loginIn(RedirectAttributes attributes,
                          @RequestParam(name = "username") String name,
                          @RequestParam(name = "password") String password) {
        if (userService.loginVerification(name,password)) {
            return "redirect:/reddit/?name="+name;
        } else {
            attributes.addFlashAttribute("wrongLogin","Incorrect username or password");
            return "redirect:/login";
        }
    }

    @GetMapping({"/create","/create/"})
    public String showCreateForm(@ModelAttribute(name="user") User user ) {
        return "create";
    }

    @PostMapping ({"/create","/create/"})
    public String createAccount(RedirectAttributes attributes,
                                @RequestParam("name") String name,
                                @RequestParam("password") String passwordOne,
                                @RequestParam("passwordTwo") String passwordTwo,
                                @ModelAttribute(name = "user") User user) {
        if (userService.userExists(name)) {
            attributes.addFlashAttribute("userExists","User already exists");
            return "redirect:/create";
        }
        if (postService.verifyPassword(passwordOne,passwordTwo)) {
            userService.addNewUser(user);
            return "redirect:/reddit/?name="+name;
        } else {
            attributes.addFlashAttribute("noMatch","Passwords do not match");
            return "redirect:/create";
        }
    }

    @GetMapping({"/reddit","/reddit/"})
    public String showIndexWithPaging(Model model, @RequestParam(name = "name") String name) {

        return showIndexByPage(model,name,1);
    }

    @GetMapping({"/reddit/page/{i}","/reddit/page/{i}/"})
    public String showIndexByPage(Model model,
                                   @RequestParam(name = "name") String name,
                                  @PathVariable(name = "i") Integer pageNumber) {

        int currentPage = pageNumber;
        Long totalPosts = postService.listPages(currentPage).getTotalElements();
        int totalPages = postService.listPages(currentPage).getTotalPages();
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalPosts",totalPosts);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("posts",postService.listPages(pageNumber));
        model.addAttribute("userName",name);
        model.addAttribute("filteredByOwner",false);
        return "index";
    }

    @GetMapping({"/reddit/search/{id}","/reddit/search/{id}/"})
    public String sortByOwner(Model model, @RequestParam(name = "name") String name, @PathVariable(name = "id") Long id) {
        model.addAttribute("posts",userService.getUserById(id).getPosts());
        model.addAttribute("filteredByOwner",true);
        model.addAttribute("owner",userService.getUserById(id).getName());
        model.addAttribute("userName",name);

        return "index";
    }


//    @GetMapping({"/reddit","/reddit/"})
//    public String showIndex(Model model, @RequestParam("name") String name) {
//        model.addAttribute("posts",postService.getAllPostsSortedByScore());
//        model.addAttribute("userName",name);
//        return "index";
//    }

    @GetMapping({"/reddit/submit","/reddit/submit/"})
    public String showSubmitForm(Model model, @ModelAttribute(name = "post") Post post, @RequestParam("name") String name) {
        model.addAttribute("post",post);
        model.addAttribute("userName",name);
        return "submit_form";
    }

    @PostMapping({"/reddit/submit","/reddit/submit/"})
    public String submitPost( @ModelAttribute(name = "post") Post post,
                              @RequestParam("name") String name) {
        postService.assignPostToUser(post,userService.getUserByName(name));
        postService.updatePost(post);

        return "redirect:/reddit/?name="+name;
    }

    @PostMapping({"/reddit/like","/reddit/like/"})
    public String likePost(@RequestParam(name = "postId") Long postId,
                           @RequestParam("name") String name,
                           @RequestParam(value = "currentPage", required = false) Integer currentPage) {
        postService.increaseScore(postId);
        postService.updatePost(postService.findPostById(postId));
        if (currentPage == null) {
            Long userId = postService.findPostById(postId).getUser().getId();
            return "redirect:/reddit/search/"+userId+"?name="+name;
        }
//        userService.getUserByName(name).setHasVoted(true);
        return "redirect:/reddit/page/"+currentPage+"?name="+name;
    }

    @PostMapping({"/reddit/dislike","/reddit/dislike/"})
    public String dislikePost(@RequestParam(name = "postId") Long postId,
                              @RequestParam("name") String name,
                              @RequestParam(value = "currentPage", required = false) Integer currentPage) {
        postService.decreaseScore(postId);
        postService.updatePost(postService.findPostById(postId));
        if (currentPage == null) {
            Long userId = postService.findPostById(postId).getUser().getId();
            return "redirect:/reddit/search/"+userId+"?name="+name;
        }
        return "redirect:/reddit/page/"+currentPage+"?name="+name;
    }

}
