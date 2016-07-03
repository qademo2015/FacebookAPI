package tests;

import helpers.FacebookTestUserAccount;
import helpers.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by abarabash on 7/2/16.
 */
public class Class7 extends TestBase {

    @Test
    public void test001_createUser(){

        FacebookTestUserAccount createdAccount = facebookStore.createTestUser(true, "");

        Assert.assertEquals(2, facebookStore.getAllTestUsers().size());

        createdAccount.delete();

        Assert.assertEquals(1, facebookStore.getAllTestUsers().size());

    }
}
