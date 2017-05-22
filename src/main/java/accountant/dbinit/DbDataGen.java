package accountant.dbinit;

import accountant.constants.Profile;
import accountant.constants.StateOfAppointment;
import accountant.dbinit.DbInitConfiguration.*;
import accountant.models.db.AppointmentDb;
import accountant.models.db.ProfileDb;
import accountant.models.db.UserDb;
import accountant.models.ui.UserUi;
import accountant.util.SecurityHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbDataGen {
    private static final String LOGIN = "admin";
    private static final String PASSWD = "pass";

    private ApplicationContext ctx;
    private ProfileServiceDbInit profileDao;
    private UserServiceDbInit userDao;
    private AppointmentServiceDbInit appointmentDao;

    private DbDataGen() {
        ctx = new AnnotationConfigApplicationContext(DbInitConfiguration.class);

        profileDao = ctx.getBean(ProfileServiceDbInit.class);
        userDao = ctx.getBean(UserServiceDbInit.class);
        appointmentDao = ctx.getBean(AppointmentServiceDbInit.class);

    }

    public static void main(String[] args) throws Throwable {
        new DbDataGen().dbInit();
    }

    public void dbInit() {
        // init admin
//        createUserProfiles();
//        createUser(LOGIN, PASSWD, "Andriy");

        // init doctor
        UserDb doctor = createUser("Doctor", PASSWD, "Doctor");

        // init patients
        UserDb ivan = createUser("Ivan", PASSWD, "Ivan");
        UserDb igor = createUser("Igor", PASSWD, "Igor");

        // init appointment
        createAppointment(doctor, ivan);
        createAppointment(doctor, ivan);
        createAppointment(doctor, ivan);

        createAppointment(doctor, igor);
        createAppointment(doctor, igor);

        ((ConfigurableApplicationContext) ctx).close();
    }

    private void createUserProfiles() {
        Profile[] profiles = Profile.values();

        for (Profile profile : profiles) {
            ProfileDb profileDB = new ProfileDb();
            profileDB.setProfile(profile.toString());
            profileDao.persist(profileDB);
        }
    }

    private UserDb createUser(String login, String passwd, String firstName) {
        UserDb user = new UserDb();

        user.setSsoId(login);
        user.setPasswd(SecurityHelper.getEncodedPassword(passwd));
        user.setFirstName(firstName);
        user.setLastName("Losoviy");
        user.setEmail(login + "@mail.ru");
        user.setProfiles(profileDao.getAll());

        userDao.persist(user);

        return user;
    }

    private void createAppointment(UserDb doctor, UserDb patient) {
        AppointmentDb appointment = new AppointmentDb();

//        appointment.setPlanned();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setPrice(15.26);
        appointment.setNote("Doctor is " + doctor.getFirstName() + ", patient is " + patient.getFirstName());
        appointment.setState(StateOfAppointment.PLANNED.toString());

        appointmentDao.persist(appointment);
    }

}

/*

    void test4() {
		Formatter formatter = new Formatter();
        int a = 5;
        String x = "Successfully deleted %d post(s)";
		formatter.format(x, a);
        String str = String.format(x, a);
        System.out.println(str);

    }

    void test3() throws Throwable {
		ShortUser u = shortUserService.findById(4);
        ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(u);
		System.out.println(json);
    }

 */