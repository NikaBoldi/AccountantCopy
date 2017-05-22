package accountant.service;

import accountant.models.ui.UserUi;

import java.util.Set;

public interface UserService {
	void persist(UserUi user);
	Set<UserUi> getAll();
	UserUi findById(int id);
	UserUi findBySso(String sso);
	void update(UserUi user);
	void delete(int userId);
	boolean isDuplicatedSsoId(UserUi user);
}