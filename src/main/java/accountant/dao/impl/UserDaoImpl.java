package accountant.dao.impl;

import accountant.dao.AbstractDao;
import accountant.dao.UserDao;
import accountant.models.db.UserDb;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, UserDb> implements UserDao {

	@Override
	public UserDb findById(int id) {
		return getByKey(id);
	}

	@Override
	public UserDb findBySso(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		return (UserDb) crit.uniqueResult();
	}

}
