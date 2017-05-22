package accountant.models.ui;

import accountant.constants.Profile;

/**
 * @author lehr0416 on 08-Aug-16.
 */
public class ProfileUi {
    private String value;
    private String name;

    public ProfileUi(Profile profile) {
        this.value = profile.toString().toUpperCase();
        this.name = "profile.name." + profile.toString().toLowerCase();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
