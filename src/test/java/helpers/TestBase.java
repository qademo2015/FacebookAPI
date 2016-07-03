package helpers;

import com.google.gson.JsonParser;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by abarabash on 7/2/16.
 */
public class TestBase {

    public JsonParser parser;
    public Helper helper;
    public Properties properties;
    public String applicationId;
    public String applicationSecret;
    public FacebookTestUserStore facebookStore;
    public FacebookTestUserAccount account1;
    public FacebookTestUserAccount account2;
    public List<FacebookTestUserAccount> createdUsers;
    public String accessToken;
    public String userId;
    private List<FacebookTestUserAccount> createdAccounts;

    public static Properties getFacebookConnectionProperties() throws IOException {

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


}
