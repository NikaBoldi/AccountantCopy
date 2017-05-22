package accountant.service;

import accountant.constants.Profile;
import accountant.models.ui.ProfileUi;

import java.util.Set;

public interface ProfileService {
	Set<ProfileUi> findAll();
	ProfileUi findById(int id);
//	ProfileUi findByType(String type);
}
