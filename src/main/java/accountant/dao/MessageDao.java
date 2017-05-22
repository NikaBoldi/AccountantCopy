package accountant.dao;

import java.util.Set;

import accountant.models.db.MessageDb;
import accountant.models.db.UserDb;

public interface MessageDao {
	public void persist(MessageDb message);
	public Set<MessageDb> getAll(); // wc wd
	public MessageDb findById(int id);
	public Set<MessageDb> findIncoming(UserDb user);
	public Set<MessageDb> findOutcoming(UserDb user);
	public void delete(MessageDb message);
	public void markDelete(int messageId);
}
