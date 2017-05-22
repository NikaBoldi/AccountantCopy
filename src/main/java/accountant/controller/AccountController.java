package accountant.controller;

import accountant.constants.Profile;
import accountant.data.Notification;
import accountant.models.ui.UserUi;
import accountant.service.ProfileService;
import accountant.service.UserService;
import accountant.util.SecurityHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

//@Controller
public class AccountController extends BaseController {

	private static final Logger logger = Logger.getLogger(AccountController.class);

	@Autowired
	UserService userService;

	@Autowired
	ProfileService profileService;

	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model, HttpServletRequest request) {
		Throwable exception = (Throwable) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

		if (exception instanceof BadCredentialsException) {
			model.addAttribute("errorMessage", "login.error.bad_cred");
			logger.warn("User typed bad credential");
		}

		if (exception instanceof AuthenticationServiceException) {
			model.addAttribute("errorMessage", "login.error.not_connection");
			logger.error("Could not get JDBC Connection!", exception);
		}

		if (exception != null) {
			model.addAttribute("error", "true");
		}

		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		logout(request, response);
		return "redirect:/login?logout";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView getSignUpPage(@ModelAttribute ArrayList<Notification> notifications) {
		ModelAndView model = new ModelAndView("user");
		defaultModelInitialize(model, notifications, "user.header.create");
		
		model.addObject("user", new UserUi());

		return model;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String createAccount(@ModelAttribute("user") UserUi userUi, RedirectAttributes redirectAttributes,
								HttpServletRequest request, HttpServletResponse response) {
		List<Notification> notifications = new ArrayList<Notification>();
		
		if (userService.isDuplicatedSsoId(userUi)) {
			notifications.add(new Notification("user.notification.warning.create.duplicate_sso_id"));
			redirectAttributes.addFlashAttribute(notifications);
			redirectAttributes.addFlashAttribute(userUi);
			
			return "redirect:/signup";
		}

        userUi.setProfiles(new HashSet<>(Arrays.asList(Profile.USER)));
		userService.persist(userUi);
		notifications.add(new Notification("user.notification.success.create"));
		redirectAttributes.addFlashAttribute(notifications);
		authenticateUserAndSetSession(userUi, request, response);

		return "redirect:/";
	}

	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ModelAndView getAccount(@ModelAttribute ArrayList<Notification> notifications) {
		ModelAndView model = new ModelAndView("user");
		defaultModelInitialize(model, notifications, "user.header.edit");

		UserUi userUi = userService.findBySso(SecurityHelper.getSso());

		model.addObject("user", userUi);
		model.addObject("roles", profileService.findAll());

		return model;
	}

	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public String editAccount(@ModelAttribute("user") UserUi userUi, RedirectAttributes redirectAttributes, HttpServletRequest request,
							  HttpServletResponse response, @RequestParam String action) {
		List<Notification> notifications = new ArrayList<>();

		try {
			switch (EditType.valueOf(action.toUpperCase())) {
			case ACCOUNT:
				userService.update(userUi);
				notifications.add(new Notification("user.notification.success.update.account"));
				break;
			case PASSWD:
				if (checkPasswd(userUi)) {
					userService.update(userUi);
					notifications.add(new Notification("user.notification.success.update.passwd"));
				} else {
					notifications.add(new Notification("user.notification.warning.update.passwd.wrong_passwd"));
				}
				break;
			case SSO_ID:
				editSsoId(userUi, notifications, request, response);
				break;
			default:
				logger.error("Unimplemented \"EditType\" value case!");
				break;
			}
		} catch (IllegalArgumentException iae) {
			logger.error("Wrong \"action\" variable value!");
		}

		redirectAttributes.addFlashAttribute(notifications);

		return "redirect:/account";
	}

	@RequestMapping(value = "/accessDenied", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView accessDeniedPage() {
		ModelAndView model = new ModelAndView("accessDenied");
		model.addObject(PAGE_HEADER, "global.http_error.403");
		
//		addMessage("global.notification.warning.access_denied");
		
		return model;
	}
	
	@RequestMapping(value = "/isDuplicatedSsoId", method = RequestMethod.GET)
	public @ResponseBody String isDuplicatedSsoIdAjax(@RequestParam("id") int id, @RequestParam("ssoId") String ssoId) {
		UserUi userUi = new UserUi();
		userUi.setId(id);
		userUi.setSsoId(ssoId);
		boolean isDuplicatedSsoId = userService.isDuplicatedSsoId(userUi);
		
		return "{ \"valid\": " + !isDuplicatedSsoId + " }";
	}
	
	private void editSsoId(UserUi userUi, List<Notification> notifications, HttpServletRequest request, HttpServletResponse response) {
		if (!checkPasswd(userUi)) {
			notifications.add(new Notification("user.notification.warning.update.sso_id.wrong_passwd"));
			return;
		}
		if (userService.isDuplicatedSsoId(userUi)) {
			notifications.add(new Notification("user.notification.warning.update.duplicate_sso_id"));
			return;
		}

		userService.update(userUi);
		notifications.add(new Notification("user.notification.success.update.sso_id"));
		authenticateUserAndSetSession(userUi, request, response);
	}

	private void authenticateUserAndSetSession(UserUi userUi, HttpServletRequest request, HttpServletResponse response) {
		logout(request, response);
		String rawPasswd = userUi.getPasswdNew().equals("") ? userUi.getPasswdOld() : userUi.getPasswdNew();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userUi.getSsoId(), rawPasswd);
		request.getSession();
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		String securityContext = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
		request.getSession().setAttribute(securityContext, SecurityContextHolder.getContext());
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}

	private boolean checkPasswd(UserUi userUi) {
		String encodedPassword = userService.findBySso(SecurityHelper.getSso()).getPasswdEncoded();
		String rawPassword = userUi.getPasswdOld();

		if (!new BCryptPasswordEncoder().matches(rawPassword, encodedPassword)) {
			return false;
		}

		return true;
	}

	public enum EditType {
		ACCOUNT("account"), SSO_ID("sso_id"), PASSWD("passwd");

		String type;

		private EditType(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return type;
		}

	}

	// Need test
//	@ModelAttribute("roles")
//	public List<ProfileDb> initializeProfiles() {
//		return profileService.findAll();
//	}

}

/*
 * Examples
 * 
 * @RequestMapping(value = "/admin", method = RequestMethod.GET) public String
 * adminPage(ModelMap model) { model.addAttribute("user", getPrincipal());
 * return "admin"; }
 * 
 * @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET) public
 * String accessDeniedPage(ModelMap model) { model.addAttribute("user",
 * getPrincipal()); return "accessDenied"; }
 * 
 * This method will be called on form submission, handling POST request It also
 * validates the user input
 * 
 * @RequestMapping(value = "/newUser", method = RequestMethod.POST) public
 * String saveRegistration(@Valid User user, BindingResult result, ModelMap
 * model) {
 * 
 * if (result.hasErrors()) { System.out.println("There are errors"); return
 * "newuser"; } // userService.save(user);
 * 
 * System.out.println("First Name : " + user.getFirstName());
 * System.out.println("Last Name : " + user.getLastName()); System.out.println(
 * "SSO ID : " + user.getSsoId()); System.out.println("Password : " +
 * user.getPasswd()); System.out.println("Email : " + user.getEmail());
 * System.out.println("Checking UsrProfiles...."); if (user.getUserProfiles() !=
 * null) { for (ProfileDb profile : user.getUserProfiles()) {
 * System.out.println("Profile : " + profile.getType()); } }
 * 
 * model.addAttribute("success", "User " + user.getFirstName() +
 * " has been registered successfully"); return "registrationsuccess"; }
 * 
 * private String getPrincipal() { String userName = null; Object principal =
 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 
 * if (principal instanceof UserDetails) { userName = ((UserDetails)
 * principal).getUsername(); } else { userName = principal.toString(); } return
 * userName; }
 * 
 */
