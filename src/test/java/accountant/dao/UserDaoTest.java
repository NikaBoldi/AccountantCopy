package accountant.dao;

import accountant.constants.State;
import accountant.models.db.ProfileDb;
import accountant.models.db.UserDb;
import accountant.util.StrGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class UserDaoTest extends AbstractDaoTest {
    @Autowired
    private UserDao userDao;

    private int userId, startUserListSize;
    private UserDb startUser;

    @Before
    public void setup() {
        startUserListSize = userDao.getAll().size();

        startUser = new UserDb();
        startUser.setFirstName(new StrGenerator().getRandomStr(10));
        startUser.setLastName(new StrGenerator().getRandomStr(10));
        startUser.setSsoId(new StrGenerator().getRandomStr(6));
        startUser.setPasswd(new StrGenerator().getRandomPassword(7));
        startUser.setPasswd("a@b.com");
        startUser.setState(State.ACTIVE.toString());

        ProfileDb startUserProfile = new ProfileDb();
        Set<ProfileDb> set = Stream.of(startUserProfile).collect(Collectors.toSet());
        startUser.setProfiles(set);
        userDao.persist(startUser);

        userId = startUser.getId();
    }

    @After
    public void cleanup() {
        userDao.delete(startUser);
    }

    @Test
    @Transactional
    @Rollback
    public void testUserListIncreasingAfterBeforeMethod() {
        int currentSize = userDao.getAll().size();
        assertTrue("User list size must grew by one and became " + startUserListSize + " + 1 = " + (startUserListSize
                + 1) + " but is " + currentSize, currentSize == (startUserListSize + 1));
    }
}