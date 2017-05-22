package accountant.models.ui;

import accountant.constants.Profile;
import accountant.models.db.ProfileDb;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lehr0416 on 05-Aug-16.
 */
public class UserUi {

    private int id;
    private String ssoId;
    private String passwdEncoded;
    private String passwdOld = "";
    private String passwdNew = "";
    private String firstName;
    private String lastName;
    private String email;
    private Date created;
    private Set<Profile> profiles = new HashSet<>();


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

    public String getPasswdEncoded() {
        return passwdEncoded;
    }

    public void setPasswdEncoded(String passwdEncoded) {
        this.passwdEncoded = passwdEncoded;
    }

    public String getPasswdOld() {
        return passwdOld;
    }

    public void setPasswdOld(String passwdOld) {
        this.passwdOld = passwdOld;
    }

    public String getPasswdNew() {
        return passwdNew;
    }

    public void setPasswdNew(String passwdNew) {
        this.passwdNew = passwdNew;
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

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }

}
