package accountant.models.converters;

import accountant.constants.Profile;
import accountant.dao.ProfileDao;
import accountant.models.db.ProfileDb;
import accountant.models.ui.ProfileUi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * @author lehr0416 on 08-Aug-16.
 */
public class ProfileUiToDbConverter implements Converter<ProfileUi, ProfileDb> {

    @Autowired
    private ProfileDao profileDao;

    @Override
    public ProfileDb convert(ProfileUi profileUi) {
        Profile profile = Profile.valueOf(profileUi.getValue().toUpperCase());
        return profileDao.findByType(profile.toString());
    }
}
