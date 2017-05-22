package accountant.util;

import accountant.dao.ProfileDao;
import accountant.models.db.ProfileDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleToUserProfileConverter implements Converter<Object, ProfileDb> {

    @Autowired
    ProfileDao profileDao;

    /*
     * Gets ProfileDb by Id
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    public ProfileDb convert(Object element) {
        Integer id = Integer.parseInt((String) element);

        ProfileDb profileDb = profileDao.findById(id);
        System.out.println("Profile : " + profileDb);
        return profileDb;
    }

	/*
     * Gets ProfileDb by type
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	/*
	public ProfileDb convert(Object element) {
		String type = (String)element;
		ProfileDb profile= userProfileService.findByType(type);
		System.out.println("Profile ... : "+profile);
		return profile;
	}
	*/

}