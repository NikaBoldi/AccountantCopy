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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class UserDaoTest extends AbstractDaoTest {
    @Autowired
    private UserDao userDao;

    private int userId, startUserListSize, deletedUserListSize;
    private UserDb startUser, deletedUser;
    private List<UserDb> afterMethodUsersForDeletion = new ArrayList<>();

    @Before
    public void setup() {
        startUserListSize = userDao.getAll().size();
        deletedUserListSize = userDao.getAll(State.DELETED).size();

        startUser = new UserDb();
        startUser.setFirstName(new StrGenerator().getRandomStr(10));
        startUser.setLastName(new StrGenerator().getRandomStr(10));
        startUser.setSsoId(new StrGenerator().getRandomStr(6));
        startUser.setPasswd(new StrGenerator().getRandomPassword(7));
        startUser.setPasswd(new StrGenerator().getRandomPassword(3) + "@" + new StrGenerator().getRandomPassword(3) +
                ".com");
        startUser.setState(State.ACTIVE.toString());

        ProfileDb startUserProfile = new ProfileDb();
        startUserProfile.setProfile(new StrGenerator().getRandomPassword(3));
        Set<ProfileDb> set = Stream.of(startUserProfile).collect(Collectors.toSet());
        startUser.setProfiles(set);
        userDao.persist(startUser);

        afterMethodUsersForDeletion.add(startUser);
        userId = startUser.getId();
    }

    @After
    public void cleanup() {
        for (UserDb userDb : afterMethodUsersForDeletion) {
            userDao.delete(userDb);
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testUserListIncreasingAfterBeforeMethod() {
        int currentSize = userDao.getAll().size();
        assertTrue("User list size must grew by one and became " + startUserListSize + " + 1 = " + (startUserListSize
                + 1) + " but is " + currentSize, currentSize == (startUserListSize + 1));
    }

    @Test
    @Transactional
    @Rollback
    public void testFindUserById() {
        UserDb localUser = userDao.findById(userId);
        assertEquals("Previously create user " + startUser + " must be equal to select by id=" + userId, startUser,
                localUser);
    }

    @Test
    @Transactional
    @Rollback
    public void testFindUserBySso() {
        UserDb localUser = userDao.findBySso(startUser.getSsoId());
        assertEquals("Previously create user " + startUser + " must be equal to select by ssoId=" + startUser
                        .getSsoId(), startUser,
                localUser);
    }

    @Test
    @Transactional
    @Rollback
    public void testFindAppropriateUserByDeletedState() {
        deletedUser = new UserDb();
        deletedUser.setFirstName(new StrGenerator().getRandomStr(10));
        deletedUser.setLastName(new StrGenerator().getRandomStr(10));
        deletedUser.setSsoId(new StrGenerator().getRandomStr(6));
        deletedUser.setPasswd(new StrGenerator().getRandomPassword(7));
        deletedUser.setPasswd(new StrGenerator().getRandomPassword(3) + "@" + new StrGenerator().getRandomPassword(3) +
                ".com");
        deletedUser.setState(State.DELETED.toString());
        ProfileDb localUserProfile = new ProfileDb();
        localUserProfile.setProfile(new StrGenerator().getRandomStr(3));
        Set<ProfileDb> set = Stream.of(localUserProfile).collect(Collectors.toSet());
        deletedUser.setProfiles(set);
        userDao.persist(deletedUser);
        afterMethodUsersForDeletion.add(deletedUser);

        int currentSize = userDao.getAll(State.DELETED).size();
        assertTrue("Delete user list size must grew by one and became " + deletedUserListSize + " + 1 = " +
                (deletedUserListSize + 1) + " but is " + currentSize, currentSize == (deletedUserListSize + 1));
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateNewUser() {
        UserDb userDb = new UserDb();
        userDb.setFirstName(new StrGenerator().getRandomStr(10));
        userDb.setLastName(new StrGenerator().getRandomStr(10));
        userDb.setSsoId(new StrGenerator().getRandomStr(6));
        userDb.setPasswd(new StrGenerator().getRandomPassword(7));
        userDb.setPasswd(new StrGenerator().getRandomPassword(3) + "@" + new StrGenerator().getRandomPassword(3) +
                ".com");
        userDb.setState(State.ACTIVE.toString());
        ProfileDb localUserProfile = new ProfileDb();
        localUserProfile.setProfile(new StrGenerator().getRandomStr(3));
        Set<ProfileDb> set = Stream.of(localUserProfile).collect(Collectors.toSet());
        userDb.setProfiles(set);
        userDao.persist(userDb);
        afterMethodUsersForDeletion.add(userDb);

        UserDb actualUserDb = userDao.findById(userDb.getId());
        assertEquals("Created userDb isn't equal to expected one. ", userDb, actualUserDb);
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateUser() {
        UserDb userDb = new UserDb();
        userDb.setFirstName(new StrGenerator().getRandomStr(10));
        userDb.setLastName(new StrGenerator().getRandomStr(10));
        userDb.setSsoId(new StrGenerator().getRandomStr(6));
        userDb.setPasswd(new StrGenerator().getRandomPassword(7));
        userDb.setPasswd(new StrGenerator().getRandomPassword(3) + "@" + new StrGenerator().getRandomPassword(3) +
                ".com");
        userDb.setState(State.ACTIVE.toString());
        ProfileDb localUserProfile = new ProfileDb();
        localUserProfile.setProfile(new StrGenerator().getRandomStr(3));
        Set<ProfileDb> set = Stream.of(localUserProfile).collect(Collectors.toSet());
        userDb.setProfiles(set);
        userDao.persist(userDb);
        afterMethodUsersForDeletion.add(userDb);

        UserDb actualUserDb = userDao.findById(userDb.getId());
        assertEquals("Create userDb isn't equal to expected one. ", userDb, actualUserDb);

        userDb.setState(State.DELETED.toString());
        userDao.update(userDb);

        actualUserDb = userDao.findById(userDb.getId());
        assertEquals("Updated userDb isn't equal to expected one. ", userDb, actualUserDb);
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteUser() {
        UserDb userDb = new UserDb();
        userDb.setFirstName(new StrGenerator().getRandomStr(10));
        userDb.setLastName(new StrGenerator().getRandomStr(10));
        userDb.setSsoId(new StrGenerator().getRandomStr(6));
        userDb.setPasswd(new StrGenerator().getRandomPassword(7));
        userDb.setPasswd(new StrGenerator().getRandomPassword(3) + "@" + new StrGenerator().getRandomPassword(3) +
                ".com");
        userDb.setState(State.ACTIVE.toString());
        ProfileDb localUserProfile = new ProfileDb();
        localUserProfile.setProfile(new StrGenerator().getRandomPassword(3));
        Set<ProfileDb> set = Stream.of(localUserProfile).collect(Collectors.toSet());
        userDb.setProfiles(set);
        userDao.persist(userDb);
        afterMethodUsersForDeletion.add(userDb);

        int beforeDeletion = userDao.getAll().size();
        int id = userDb.getId();

        UserDb actualUserDb = userDao.findById(userDb.getId());
        assertEquals("Create userDb isn't equal to expected one. ", userDb, actualUserDb);

        userDao.delete(userDb);

        UserDb mustBeEmptyUserDb = userDao.findById(id);
        assertNull("After deletion user " + userDb.getFirstName() + " isn't deleted", mustBeEmptyUserDb);
        int afterDeletion = userDao.getAll().size();
        assertTrue("After deletion 'before count' from value " + beforeDeletion + " hasn't been decreased to 'after " +
                "count' value " + (beforeDeletion - 1), afterDeletion == (beforeDeletion - 1));
    }
}