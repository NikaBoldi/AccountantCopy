package accountant.dao;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:spring-database-test.xml", "classpath:spring-security-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractDaoTest {
}
