package anotherTests;

import helpers.FacebookTestUserAccount;
import helpers.TestBase;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by abarabash on 6/30/16.
 */
public class Class8 extends TestBase{

    @Test(priority = 3)
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

    @Test(priority = 1)
    public void test003_getPassword() {

        FacebookTestUserAccount testUser1 = facebookStore.createTestUser(true, "");

        Assert.assertNotNull(testUser1.getPassword());

    }

    @Test(dataProvider = "namesAndPasswords")
    public void testWithDataProvider(String n1, Integer n2){

        System.out.println(n1 + " " + n2);
    }

    @DataProvider(name = "namesAndPasswords")
    public Object[][] createData(){

        return new Object[][]{
            {"Alex", new Integer(1231231) },
            {"Igor", new Integer(1231231) },
        };
    }


}
