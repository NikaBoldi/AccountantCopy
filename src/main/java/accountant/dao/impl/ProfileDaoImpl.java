package accountant.dao.impl;

import java.util.HashSet;
import java.util.Set;

import accountant.models.db.ProfileDb;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import accountant.dao.AbstractDao;
import accountant.dao.ProfileDao;

@Repository("userProfileDao")
public class ProfileDaoImpl extends AbstractDao<Integer, ProfileDb>implements ProfileDao {

	@SuppressWarnings("unchecked")
	public Set<ProfileDb> getAll(){
		Criteria crit = createEntityCriteria();
		crit.addOrder(Order.asc("id"));
		return new HashSet<ProfileDb>(crit.list());
	}
	
	public ProfileDb findById(int id) {
		return getByKey(id);
	}
	
	public ProfileDb findByType(String profile) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("profile", profile));
		return (ProfileDb) crit.uniqueResult();
	}
	
}
