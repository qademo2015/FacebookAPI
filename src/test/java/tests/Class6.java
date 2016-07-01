package tests;

import helpers.FacebookTestUserAccount;
import helpers.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by abarabash on 6/22/16.
 */
public class Class6 extends TestBase {



    @Test
    public void test001_Make_Friends() {

        FacebookTestUserAccount testUser1 = facebookStore.createTestUser(true, "");
        FacebookTestUserAccount testUser2 = facebookStore.createTestUser(true, "");

        testUser1.makeFriends(testUser2);

        String friends1 = testUser1.getFriends();
        String friends2 = testUser2.getFriends();

        Assert.assertTrue(friends1.contains(testUser2.id()));
        Assert.assertTrue(friends2.contains(testUser1.id()));


    }

    @Test
    public void test002_delete_Test_User() {

        FacebookTestUserAccount testUser1 = facebookStore.createTestUser(true, "");

        testUser1.delete();

    }



}
