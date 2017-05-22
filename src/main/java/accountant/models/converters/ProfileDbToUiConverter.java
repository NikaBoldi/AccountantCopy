package accountant.models.converters;

import accountant.constants.Profile;
import accountant.models.db.ProfileDb;
import accountant.models.ui.ProfileUi;
import org.springframework.core.convert.converter.Converter;

/**
 * @author lehr0416 on 08-Aug-16.
 */
public class ProfileDbToUiConverter implements Converter<ProfileDb, ProfileUi> {

    @Override
    public ProfileUi convert(ProfileDb profileDb) {
        Profile profile = Profile.valueOf(profileDb.getProfile().toUpperCase());
        return new ProfileUi(profile);
    }
}
