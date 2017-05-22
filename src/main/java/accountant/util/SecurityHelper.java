package accountant.util;

import java.util.ArrayList;
import java.util.List;

import accountant.constants.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class SecurityHelper {

//    public static final String ROLE_PREFIX = "ROLE_";

    public static String getSso() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            return "null";
        } else if (principal instanceof User) {
            User user = (User) principal;
            return user.getUsername();
        } else {
            return principal.toString();
        }
    }

    public static List<String> getUserRoles() {
        List<String> roles = new ArrayList<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof User) {
                User user = (User) principal;
                for (GrantedAuthority authority : user.getAuthorities()) {
                    roles.add(authority.getAuthority());
                }
            }
        }
        return roles;
    }

    public static boolean isAdmin() {
        return hasUserTheProfile(Profile.ADMIN);
    }

    public static String getEncodedPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    private static boolean hasUserTheProfile(Profile profile) {
        for (String profileName : getUserRoles()) {
            profileName = profileName.substring(5).toUpperCase();
            if (profile == Profile.valueOf(profileName)) {
                return true;
            }
        }
        return false;
    }

//    public static Profile userRoleToType(String role) {
//        for (Profile profile : Profile.values()) {
//            if (role.equals(ROLE_PREFIX + profile.name()))
//                return profile;
//        }
//        return null;
//    }

}
