package accountant.service;

import accountant.models.db.MessageDb;
import accountant.models.db.UserDb;

import java.util.Set;

public interface MessageService {
	public void persist(MessageDb message);
	public Set<MessageDb> findAll(); // wd
	public MessageDb findById(int id);
	public Set<MessageDb> findIncoming(UserDb user);
	public Set<MessageDb> findOutcoming(UserDb user);
	public void delete(int messageId);
}
