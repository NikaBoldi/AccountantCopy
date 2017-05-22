package accountant.service.impl;

import java.util.Set;

import accountant.models.db.MessageDb;
import accountant.models.db.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import accountant.service.MessageService;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

	@Autowired
	private accountant.dao.MessageDao dao;

	@Override
	public void persist(MessageDb message) {
		dao.persist(message);
	}

	@Override
	public Set<MessageDb> findAll() {
		return dao.getAll();
	}

	@Override
	public MessageDb findById(int id) {
		MessageDb message = dao.findById(id);
		message.setFrom(new UserDb());
		message.setTo(new UserDb());
		
		return message;
	}
	
	@Override
	public Set<MessageDb> findIncoming(UserDb user) {
		return null;
	}
	
	@Override
	public Set<MessageDb> findOutcoming(UserDb user) {
		return null;
	}

	@Override
	public void delete(int messageId) {
		dao.markDelete(messageId);
	}

}
