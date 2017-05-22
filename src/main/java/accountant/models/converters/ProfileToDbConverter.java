package accountant.models.converters;

import accountant.constants.Profile;
import accountant.dao.ProfileDao;
import accountant.models.db.ProfileDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * @author lehr0416 on 08-Aug-16.
 */
public class ProfileToDbConverter implements Converter<Profile, ProfileDb> {

    @Autowired
    private ProfileDao profileDao;

    @Override
    public ProfileDb convert(Profile profile) {
        return profileDao.findByType(profile.toString());
    }
}
