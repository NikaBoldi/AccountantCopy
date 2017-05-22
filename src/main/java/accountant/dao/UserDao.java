package accountant.dao;

import java.util.Set;

import accountant.constants.State;
import accountant.models.db.UserDb;

public interface UserDao {
	void persist(UserDb user);
	Set<UserDb> getAll();
	Set<UserDb> getAll(State state);
	UserDb findById(int id);
	UserDb findBySso(String sso);
	void update(UserDb user);
	void delete(UserDb user);
}

