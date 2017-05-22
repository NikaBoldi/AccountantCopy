package accountant.dao;

import java.util.Set;

import accountant.models.db.ProfileDb;

public interface ProfileDao {
	Set<ProfileDb> getAll();
	ProfileDb findById(int id);
	ProfileDb findByType(String type);
}
