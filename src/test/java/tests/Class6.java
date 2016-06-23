package tests;

import com.google.gson.JsonParser;
import helpers.FacebookTestUserAccount;
import helpers.FacebookTestUserStore;
import helpers.Helper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

/**
 * Created by abarabash on 6/22/16.
 */
public class Class6 {

    private JsonParser parser;
    private Helper helper;
    private Properties properties;
    private String applicationId;
    private String applicationSecret;
    private FacebookTestUserStore facebookStore;
    private FacebookTestUserAccount account1;
    private FacebookTestUserAccount account2;
    private List<FacebookTestUserAccount> createdUsers;
    private String accessToken;
    private String userId;
    private List<FacebookTestUserAccount> createdAccounts;

    private static Properties getFacebookConnectionProperties() throws IOException {

        Properties properties = new Properties();

        InputStream stream = FacebookTestUserStore.class.getClassLoader().getResourceAsStream("facebook-app.properties");

        properties.load(stream);

        stream.close();

        return properties;
    }

    @BeforeClass
    public void beforeAllTests() throws IOException {

        parser = new JsonParser();

        helper = new Helper();

        properties = getFacebookConnectionProperties();

        applicationId = properties.getProperty("facebook.appId1");
        applicationSecret = properties.getProperty("facebook.appSecret1");

        facebookStore = new FacebookTestUserStore(applicationId, applicationSecret);

        facebookStore.deleteAllTestUsers();

        createdAccounts = new LinkedList<FacebookTestUserAccount>();
    }

    @Test
    public void testMakeFriends() {
        account1 = createAccount();
        account2 = createAccount();

        account1.makeFriends(account2);

        String friends1 = account1.getFriends();
        String friends2 = account2.getFriends();

        assertTrue("The friends list for account1 does not contain account2", friends1.contains(account2.id()));
        assertTrue("The friends list for account2 does not contain account1", friends2.contains(account1.id()));
    }

    @Test
    public void deleteTestUser() {
        account1 = createAccount();

        account1.delete();


    }

    private FacebookTestUserAccount createAccount() {
        FacebookTestUserAccount account = facebookStore.createTestUser(true, "");
        createdAccounts.add(account);
        return account;
    }
}
