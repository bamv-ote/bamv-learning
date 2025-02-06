package bamv.training.microposts.controller;

import bamv.training.microposts.dao.MUserDao;
import bamv.training.microposts.dao.impl.MUserDaoImpl;
import bamv.training.microposts.dto.MicropostDto;
import bamv.training.microposts.dto.UserDto;
import bamv.training.microposts.entity.MUser;
import bamv.training.microposts.entity.TFollow;
import bamv.training.microposts.form.MicropostForm;
import bamv.training.microposts.form.UserForm;
import bamv.training.microposts.service.FollowService;
import bamv.training.microposts.service.MicropostService;
import bamv.training.microposts.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MicropostsController {
    @Autowired
    private UserService userService;

    @Autowired
    private MicropostService micropostService;

    @Autowired
    private FollowService followService;

    @GetMapping("/micropostshome")
    String micropostshome(Model model, @ModelAttribute MicropostForm micropostForm, BindingResult bindingResult, HttpServletRequest httpServletRequest, @RequestParam(name = "page", defaultValue = "1") int page) {
        /* ユーザー認証情報からユーザIDを取得 */
        String userId = httpServletRequest.getRemoteUser();

        /* Model ⇔ Controller */
        UserDto user = userService.findUser(userId); // 自ユーザー情報
        int myMicropostNumber = micropostService.countMicropostNumber(userId); // 自ユーザーマイクロポスト数
        List<MicropostDto> followsMicropostList = micropostService.searchFollowMicropost(userId, page); // 自ユーザーおよびフォローのマイクロポスト
        int myFollowNumber = followService.findFollowNumber(userId); // 自ユーザーのフォロー数
        int myFollowerNumber = followService.findFollowerNumber(userId); // 自ユーザーのフォロワー数

        /* View ⇔ Controller */
        model.addAttribute("myUserName", user.getName());
        model.addAttribute("myMicropostsNumber", myMicropostNumber);
        model.addAttribute("myFollowNumber", myFollowNumber);
        model.addAttribute("myFollowerNumber", myFollowerNumber);
        model.addAttribute("followsMicropostList", followsMicropostList);
        model.addAttribute("page", page);

        return "micropostshome";
    }

    @PostMapping("/postnewmicropost")
    String postnewmicropost(Model model, HttpServletRequest httpServletRequest, @ModelAttribute @Valid MicropostForm micropostForm, BindingResult bindingResult) {
        /* ユーザー認証情報からユーザIDを取得 */
        String userId = httpServletRequest.getRemoteUser();

        if (bindingResult.hasErrors())
            return micropostshome(model, micropostForm, bindingResult, httpServletRequest, 1);

        micropostService.createNewMicropost(userId, micropostForm.getContent());

        return "redirect:/micropostshome";
    }

    @GetMapping("/login")
    String login(Model model) {
        return "login";
    }

    @GetMapping("/myprofile")
    String myprofile(Model model, HttpServletRequest httpServletRequest, @RequestParam(name = "page", defaultValue = "1") int page) {
        /* ユーザー認証情報からユーザIDを取得 */
        String userId = httpServletRequest.getRemoteUser();

        /* Model ⇔ Controller */
        UserDto user = userService.findUser(userId); // 自ユーザー情報
        List<MicropostDto> followsMicropostList = micropostService.searchUserMicropost(userId, page); // 自ユーザーのマイクロポスト
        int myFollowNumber = followService.findFollowNumber(userId); // 自ユーザーのフォロー数
        int myFollowerNumber = followService.findFollowerNumber(userId); // 自ユーザーのフォロワー数

        /* View ⇔ Controller */
        model.addAttribute("myUserName", user.getName());
        model.addAttribute("myFollowNumber", myFollowNumber);
        model.addAttribute("myFollowerNumber", myFollowerNumber);
        model.addAttribute("followsMicropostList", followsMicropostList);
        model.addAttribute("page", page);

        return "myprofile";
    }
    @GetMapping("/user/{id}")
    String userProfile(@PathVariable("id") String userId, Model model, @RequestParam(name = "page", defaultValue = "1") int page){
        UserDto user = userService.findUser(userId);//表示されるユーザー
        List<MicropostDto> followsMicropostList = micropostService.searchUserMicropost(userId, page); //マイクロポスト
        int followNumber = followService.findFollowNumber(userId); // フォロー数
        int followerNumber = followService.findFollowerNumber(userId); // フォロワー数

        /* View ⇔ Controller */
        model.addAttribute("userId", userId);
        model.addAttribute("userName", user.getName());
        model.addAttribute("followNumber", followNumber);
        model.addAttribute("followerNumber", followerNumber);
        model.addAttribute("followsMicropostList", followsMicropostList);
        model.addAttribute("page", page);

        return "userprofile";
    }

    @GetMapping("/signup")
    String signup(Model model, @ModelAttribute UserForm userForm, BindingResult bindingResult) {
        return "signup";
    }

    @PostMapping("/signup")
    String postsignup(Model model, @ModelAttribute @Valid UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return signup(model, userForm, bindingResult);

        userService.createNewUser(userForm.getUserId(), userForm.getUserName(), userForm.getPassword());

        return "redirect:/login";
    }

    @GetMapping("/users")
    String getUsers(Model model, HttpServletRequest httpServletRequest){
        /* ユーザー認証情報からユーザIDを取得 */
        String thisUserId = httpServletRequest.getRemoteUser();

        //自分をのぞいたユーザーリスト
        List<MUser> users = userService.getOtherUsersList(thisUserId);
        List<TFollow>follow = followService.getFollowList(thisUserId);

        //フォローエンティティのリストからフォローしているIDだけを抽出してリストにする
        List<String> followedIds = follow.stream().map(f -> f.getFollowedUserId()).toList();
        model.addAttribute("usersList", users);
        model.addAttribute("followedList", followedIds);

        return "users";
    }

    @PostMapping("/follow")
    String follow(Model model, String followedId, HttpServletRequest httpServletRequest){
        //自分自身のID
        String thisUserId = httpServletRequest.getRemoteUser();
        followService.followUser(thisUserId, followedId);

        return "redirect:/users";
    }

}