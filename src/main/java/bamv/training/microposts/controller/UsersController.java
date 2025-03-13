
package bamv.training.microposts.controller;

import bamv.training.microposts.dto.MicropostDto;
import bamv.training.microposts.dto.UserDto;
import bamv.training.microposts.dto.UserListItemDto;
import bamv.training.microposts.entity.MUser;
import bamv.training.microposts.service.FollowService;
import bamv.training.microposts.service.MicropostService;
import bamv.training.microposts.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private MicropostService micropostService;


    @GetMapping("/users")
    public String showUsers(Model model, HttpServletRequest request,
                            @RequestParam(name = "page", defaultValue = "1") int page) {

        String currentUserId = request.getRemoteUser();
        int offset = (page - 1) * 10;

        List<MUser> users = userService.findAllUsersExcept(currentUserId, offset, 10);
        List<UserListItemDto> userList = users.stream()
                .map(u -> new UserListItemDto(
                        u.getUserId(),
                        u.getName(),
                        followService.isFollowing(currentUserId, u.getUserId())
                ))
                .collect(Collectors.toList());

        model.addAttribute("users", userList);
        model.addAttribute("page", page);
        return "users";
    }


    @PostMapping("/users/toggleFollow")
    public String toggleFollow(HttpServletRequest request,
                               @RequestParam("targetUserId") String targetUserId,
                               @RequestParam("page") int page) {
        String currentUserId = request.getRemoteUser();
        if (followService.isFollowing(currentUserId, targetUserId)) {
            followService.unfollow(currentUserId, targetUserId);
        } else {
            followService.follow(currentUserId, targetUserId);
        }
        return "redirect:/users?page=" + page;
    }


    @GetMapping("/userprofile")
    public String showProfile(Model model, @RequestParam("username") String username,
                              @RequestParam(name = "page", defaultValue = "1") int page) {
        UserDto userDto = userService.findUserByName(username);
        if (userDto == null) {
            return "redirect:/users";
        }
        List<MicropostDto> microposts = micropostService.searchUserMicropost(userDto.getUserId(), page);
        int followNumber = followService.findFollowNumber(userDto.getUserId());
        int followerNumber = followService.findFollowerNumber(userDto.getUserId());
        model.addAttribute("user", userDto);
        model.addAttribute("microposts", microposts);
        model.addAttribute("page", page);
        model.addAttribute("followNumber", followNumber);
        model.addAttribute("followerNumber", followerNumber);
        return "profile";
    }
}