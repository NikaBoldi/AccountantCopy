package accountant.service.impl;

import accountant.constants.Profile;
import accountant.dao.ProfileDao;
import accountant.models.ui.ProfileUi;
import accountant.service.BaseService;
import accountant.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service("userProfileService")
@Transactional
public class ProfileServiceImpl extends BaseService implements ProfileService {

    @Autowired
    ProfileDao dao;

    @Override
    public Set<ProfileUi> findAll() {
        return dao.getAll().stream()
                .map((pDb) -> convert(pDb, ProfileUi.class))
                .collect(Collectors.toSet());
    }

    @Override
    public ProfileUi findById(int id) {
        return convert(dao.findById(id), ProfileUi.class);
    }

//    public Profile findByType(String type) {
//        return convert(dao.findByType(type), Profile.class);
//    }

}
