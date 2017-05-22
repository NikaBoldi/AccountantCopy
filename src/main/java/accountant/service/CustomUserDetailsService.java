package accountant.service;

import accountant.constants.State;
import accountant.models.db.UserDb;
import accountant.models.db.ProfileDb;
import accountant.models.ui.UserUi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private accountant.dao.UserDao dao;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
		UserDb userDb = dao.findBySso(ssoId);
		if (userDb == null) {
			throw new UsernameNotFoundException("Username not found");
		}

		boolean isActiveUser = State.valueOf(userDb.getState().toUpperCase()) == State.ACTIVE;
		return new org.springframework.security.core.userdetails.User(userDb.getSsoId(), userDb.getPasswd(), true,
				true, true, isActiveUser, getGrantedAuthorities(userDb));
	}

	private List<GrantedAuthority> getGrantedAuthorities(UserDb userDb) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (ProfileDb userProfile : userDb.getProfiles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getProfile()));
		}
		return authorities;
	}

}
