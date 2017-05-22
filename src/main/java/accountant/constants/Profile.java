package accountant.constants;

/**
 * @author lehr0416 on 05-Aug-16.
 */
public enum Profile {
    ADMIN("Admin"),
    USER("User");

    String profile;

    private Profile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return profile;
    }
}
