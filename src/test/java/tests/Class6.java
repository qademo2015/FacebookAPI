package tests;

import helpers.TestBase;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;


/**
 * Created by abarabash on 6/22/16.
 */
public class Class6 extends TestBase {

    @Test
    public void testMakeFriends() {
        account1 = facebookStore.createTestUser(true, "");
        account2 = facebookStore.createTestUser(true, "");

        account1.makeFriends(account2);

        String friends1 = account1.getFriends();
        String friends2 = account2.getFriends();

        assertTrue("The friends list for account1 does not contain account2", friends1.contains(account2.id()));
        assertTrue("The friends list for account2 does not contain account1", friends2.contains(account1.id()));
    }

    @Test
    public void deleteTestUser() {
        account1 = facebookStore.createTestUser(true, "");

        account1.delete();
    }


}
