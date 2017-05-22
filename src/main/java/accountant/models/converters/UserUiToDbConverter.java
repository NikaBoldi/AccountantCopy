package accountant.models.converters;

import accountant.constants.Profile;
import accountant.constants.State;
import accountant.dao.ProfileDao;
import accountant.models.db.ProfileDb;
import accountant.models.db.UserDb;
import accountant.models.ui.UserUi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lehr0416 on 05-Aug-16.
 */

public class UserUiToDbConverter implements Converter<UserUi, UserDb> {

    @Autowired
    private ConversionServiceFactoryBean conversionServiceFactoryBean;

    @Override
    public UserDb convert(UserUi userUi) {
        UserDb userDb = new UserDb();

        userDb.setId(userUi.getId());
        userDb.setSsoId(userUi.getSsoId());
        userDb.setPasswd(userUi.getPasswdEncoded());
        userDb.setFirstName(userUi.getFirstName());
        userDb.setLastName(userUi.getLastName());
        userDb.setEmail(userUi.getEmail());
        userDb.setCreated(userUi.getCreated());
        userDb.setState(State.ACTIVE.toString());

        ConversionService conversionService = conversionServiceFactoryBean.getObject();
        Set<ProfileDb> profileDbSet = userUi.getProfiles().stream().map((p) -> conversionService.convert(p, ProfileDb.class)).collect(Collectors.toSet());
        userDb.setProfiles(profileDbSet);

        return userDb;
    }
}
