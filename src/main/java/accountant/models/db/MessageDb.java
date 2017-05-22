package accountant.models.db;

import accountant.constants.State;
import accountant.models.db.UserDb;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="MESSAGES")
public class MessageDb {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Temporal( value = TemporalType.TIMESTAMP ) 
    @org.hibernate.annotations.Generated(value=GenerationTime.INSERT) 
    @Column(name = "CREATED", nullable = false, insertable=false, updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date created;

	@NotBlank
	@Column(name="SUBJECT", nullable=false)
	private String subject;
	
	@NotBlank
	@Column(name = "BODY", nullable = false, length = 16777215, columnDefinition = "MEDIUMTEXT")
	private String body;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FROM_USER_ID", nullable=false)
	private UserDb from;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="TO_USER_ID", nullable=false)
	private UserDb to;
	
	@NotBlank
	@Column(name="STATE", nullable=false)
	private String state = State.ACTIVE.toString();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public UserDb getFrom() {
		return from;
	}

	public void setFrom(UserDb from) {
		this.from = from;
	}

	public UserDb getTo() {
		return to;
	}

	public void setTo(UserDb to) {
		this.to = to;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "MessageDb [subject=" + subject + "]";
	}
	
}
