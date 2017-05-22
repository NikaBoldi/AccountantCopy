package accountant.service.impl;

import accountant.dao.UserDao;
import accountant.models.db.UserDb;
import accountant.models.ui.UserUi;
import accountant.service.BaseService;
import accountant.service.UserService;
import accountant.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseService implements UserService {

	@Autowired
	private UserDao dao;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void persist(UserUi userUi) {
	    UserDb userDb = convert(userUi, UserDb.class);
        userDb.setPasswd(passwordEncoder.encode(userUi.getPasswdNew()));

		dao.persist(userDb);
	}

	@Override
	public Set<UserUi> getAll() {
		Set<UserDb> userDbSet = dao.getAll();
		UserDb currentUser = dao.findBySso(SecurityHelper.getSso());
		int currentUserId = currentUser.getId();
		return userDbSet.stream().filter(u -> u.getId() != currentUserId).map((u) -> convert(u, UserUi.class)).collect(Collectors.toSet());
	}

	@Override
	public UserUi findById(int id) {
	    return convert(dao.findById(id), UserUi.class);
	}

	@Override
	public UserUi findBySso(String sso) {
		return convert(dao.findBySso(sso), UserUi.class);
	}

	@Override
	public void update(UserUi userUi) {
		UserDb userDb = convert(userUi, UserDb.class);
		if (!userUi.getPasswdNew().equals("")) {
			userDb.setPasswd(passwordEncoder.encode(userUi.getPasswdNew()));
		}

		dao.update(userDb);
	}

	@Override
	public void delete(int userId) {
		dao.delete(dao.findById(userId));
	}

	@Override
	public boolean isDuplicatedSsoId(UserUi userUi) {
        UserDb userDb = convert(userUi, UserDb.class);
	    UserDb userBySso = dao.findBySso(userDb.getSsoId());

		if (userBySso == null) {
			return false;
		} else if (userBySso.getId() == userDb.getId()) {
			return false;
		}

		return true;
	}

}
