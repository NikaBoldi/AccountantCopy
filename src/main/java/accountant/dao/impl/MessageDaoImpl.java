package accountant.dao.impl;

import java.util.HashSet;
import java.util.Set;

import accountant.models.db.MessageDb;
import accountant.models.db.UserDb;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import accountant.dao.AbstractDao;
import accountant.constants.State;

@Repository("messageDao")
public class MessageDaoImpl extends AbstractDao<Integer, MessageDb> implements accountant.dao.MessageDao {

	@Override
	public MessageDb findById(int id) {
		return getByKey(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<MessageDb> findIncoming(UserDb user) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("from", user));
		criteria.add(Restrictions.eq("state", State.ACTIVE.toString()));
		return new HashSet<MessageDb>(criteria.list());
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<MessageDb> findOutcoming(UserDb user) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("to", user));
		criteria.add(Restrictions.eq("state", State.ACTIVE.toString()));
		return new HashSet<MessageDb>(criteria.list());
	}

	@Override
	public void markDelete(int messageId) {
		MessageDb message = findById(messageId);
		message.setState(State.DELETED.toString());
		update(message);
	}

}
