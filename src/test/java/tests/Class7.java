package tests;

import helpers.FacebookTestUserAccount;
import helpers.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by abarabash on 6/28/16.
 */
public class Class7 extends TestBase {


    @Test(priority = 2)
    public void test001_CreateUser() {

        FacebookTestUserAccount testUser1 = facebookStore.createTestUser(true, "");

        int listSize = facebookStore.getAllTestUsers().size();

        Assert.assertEquals(listSize, 2);

    }

    @Test(groups = "defaultGroup")
    public void test002_getProfileFeed() {

        FacebookTestUserAccount testUser1 = facebookStore.createTestUser(true, "");

        String profileFeed = testUser1.getProfileFeed();

        Assert.assertNotNull(profileFeed);

    }

    @Test(priority = 2)
    public void test003_getPassword() {

        FacebookTestUserAccount testUser1 = facebookStore.createTestUser(true, "");

        Assert.assertNotNull(testUser1.getPassword());

    }


}
