package accountant.controller;

import java.util.*;

import accountant.constants.Profile;
import accountant.models.ui.UserUi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import accountant.data.Notification;
import accountant.service.ProfileService;
import accountant.service.UserService;

@Controller
public class UserController extends BaseController {

    static final String JSP_KEY_USER = "user";
    static final String JSP_KEY_USERS = "users";
    static final String JSP_KEY_USER_HEADER = "userHeader";
    static final String JSP_KEY_PROFILE_UI_LIST = "profileUiList";
    static final String JSP_KEY_IS_EDIT_MODE = "isEditMode";

    static final String JSP_PAGE_USERS = "users";
    static final String JSP_PAGE_USER_FORM = "modal/userForm";

    @Autowired
    UserService userService;

    @Autowired
    ProfileService userProfileService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getAll(@ModelAttribute ArrayList<Notification> notifications) {
        ModelAndView model = new ModelAndView(JSP_PAGE_USERS);
        defaultModelInitialize(model, notifications, "users.header");

        Set<UserUi> userUiSet = userService.getAll();
        model.addObject(JSP_KEY_USERS, userUiSet);
        model.addObject(JSP_KEY_USER, new UserUi());
        model.addObject(JSP_KEY_PROFILE_UI_LIST, userProfileService.findAll());


        return model;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ModelAndView getUserAjax(@PathVariable int userId) {
        ModelAndView model = new ModelAndView(JSP_PAGE_USER_FORM);

        UserUi userUi = null;
        if (userId == 0) {
            userUi = new UserUi();
            userUi.setProfiles(new HashSet<>(Arrays.asList(Profile.USER)));
            model.addObject(JSP_KEY_USER_HEADER, "users.popup_user.header.create");
        } else {
            userUi = userService.findById(userId);
            model.addObject(JSP_KEY_USER_HEADER, "users.popup_user.header.edit");
            model.addObject(JSP_KEY_IS_EDIT_MODE, true);
        }

        model.addObject(JSP_KEY_USER, userUi);
        model.addObject(JSP_KEY_PROFILE_UI_LIST, userProfileService.findAll());

        return model;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String createOrUpdateUserAjax(@ModelAttribute("user") UserUi userUi) {
        if (userService.isDuplicatedSsoId(userUi)) {

        }

        if (userUi.getId() == 0) {
            userService.persist(userUi);
        } else {
            userService.update(userUi);
        }

        return "redirect:/users";
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public @ResponseBody HttpStatus deleteUserAjax(@RequestBody Integer userId) {
		userService.delete(userId);
        return HttpStatus.OK;
    }

}
