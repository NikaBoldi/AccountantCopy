package accountant.dao;

import accountant.models.db.UserDb;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = "classpath:spring-database-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    @Ignore
    //@Transactional
    @Rollback(true)
    public void mainTest() {
        Set<UserDb> users = userDao.getAll();
        assertNotNull(users);
    }
}
