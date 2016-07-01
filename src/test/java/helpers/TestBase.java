package helpers;

import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by abarabash on 6/28/16.
 */
public class TestBase {

    public FacebookTestUserStore facebookStore;
    private String applicationID;
    private String aplicationSecret;
    private Helper helper;
    private FacebookTestUserAccount testUser1;
    private FacebookTestUserAccount testUser2;
    private String accessToken;
    private String userId;

    public static Properties getFacebookConnectionProperties() throws IOException {

        Properties properties = new Properties();

        InputStream stream = FacebookTestUserStore.class.getClassLoader().getResourceAsStream("facebook-app.properties");

        properties.load(stream);

        stream.close();

        return properties;
    }

    @BeforeClass
    public void beforeAllTests() throws IOException {

        Properties properties = getFacebookConnectionProperties();

        applicationID = properties.getProperty("facebook.appId1");
        aplicationSecret = properties.getProperty("facebook.appSecret1");

        facebookStore = new FacebookTestUserStore(applicationID, aplicationSecret);

        facebookStore.deleteAllTestUsers();

        helper = new Helper();

    }

}