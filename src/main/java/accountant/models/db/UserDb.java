package accountant.models.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import accountant.constants.State;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "users")
public class UserDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = "sso_id", unique = true, nullable = false)
    private String ssoId;

    @NotBlank
    @Column(name = "password")
    private String passwd;

    @NotBlank
    @Column(name = "first_name", nullable = true)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = true)
    private String lastName;

    @NotBlank
    @Column(name = "email", nullable = true)
    private String email;

    @Temporal(value = TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Generated(value = GenerationTime.INSERT)
    @Column(name = "created", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date created;

    @NotBlank
    @Column(name = "STATE", nullable = false)
    private String state = State.ACTIVE.toString();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_profiles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "profile_id")})
    private Set<ProfileDb> profiles = new HashSet<ProfileDb>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<ProfileDb> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<ProfileDb> profiles) {
        this.profiles = profiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDb user = (UserDb) o;

        if (id != user.id) return false;
        return ssoId.equals(user.ssoId);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + ssoId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", ssoId='" + ssoId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", created=" + created +
                ", profiles=" + profiles +
                '}';
    }

}
