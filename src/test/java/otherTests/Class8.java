package otherTests;

import helpers.FacebookTestUserAccount;
import helpers.TestBase;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by abarabash on 6/30/16.
 */
public class Class8 extends TestBase{



    @Test(priority = 2)
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

    @DataProvider(name = "namesAndPasswords")
    public Object[][] createData1() {
        return new Object[][] {
                { "Alex", new Integer(122442436) },
                { "Igor", new Integer(323452347)},
        };
    }

    @Test(dataProvider = "namesAndPasswords")
    public void testWithDataProvider(String n1, Integer n2) {
        System.out.println(n1 + " " + n2);
    }



}
