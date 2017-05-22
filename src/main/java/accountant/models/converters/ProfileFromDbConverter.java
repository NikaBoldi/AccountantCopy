package accountant.models.converters;

import accountant.constants.Profile;
import accountant.models.db.ProfileDb;
import org.springframework.core.convert.converter.Converter;

/**
 * @author lehr0416 on 08-Aug-16.
 */
public class ProfileFromDbConverter implements Converter<ProfileDb, Profile> {

    @Override
    public Profile convert(ProfileDb profileDb) {
        return Profile.valueOf(profileDb.getProfile().toUpperCase());
    }
}
