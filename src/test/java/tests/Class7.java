package tests;

import helpers.FacebookTestUserAccount;
import helpers.TestBase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by abarabash on 6/28/16.
 */
public class Class7 extends TestBase {

    @Test
    public void test001_createUser() {

        FacebookTestUserAccount createdAccount = facebookStore.createTestUser(true, "");

        assertEquals(2, facebookStore.getAllTestUsers().size());

        createdAccount.delete();

        assertEquals(1, facebookStore.getAllTestUsers().size());

    }


}
