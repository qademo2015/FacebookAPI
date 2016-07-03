package tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import helpers.FacebookTestUserAccount;
import helpers.TestBase;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by abarabash on 6/15/16.
 */
public class Class4 extends TestBase{

    @Test
    public void test001() {

        String message = "test message";

        String response = facebookStore.post("/" + userId + "/feed",
                helper.buildList("message", message, "access_token", accessToken), null, applicationId);

        String getResponse = facebookStore.get("/" + userId + "/feed",
                helper.buildList("access_token", accessToken), null, applicationId);

        JsonElement feed = parser.parse(getResponse);

        String actualMessage = ((JsonObject) feed).get("data").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsString();

        actualMessage = actualMessage + "241";

        Assert.assertEquals(actualMessage, message);

    }

    @Test
    public void test002_Change_user_Details() {

        String accountId = account1.id();

        List<NameValuePair> params = new LinkedList<NameValuePair>();

        String newName = "gavedName";
        String newPassword = "parssfdg2435";

        params.add(new BasicNameValuePair("name", newName));
        params.add(new BasicNameValuePair("password", newPassword));

        String result = facebookStore.post("/%s", params, null, accountId);

        assertNameChange(accountId, "actualName");

    }

    @AfterClass
    public void tearDown() {

        facebookStore.deleteAllTestUsers();
    }

    private void assertNameChange(String accountId, String expectedName) {

        List<FacebookTestUserAccount> users = facebookStore.getAllTestUsers();

        for (FacebookTestUserAccount user : users) {

            if (user.id().equals(accountId)) {
                String userdetails = user.getUserDetails();
                JsonElement details = parser.parse(userdetails);
                String actualName = details.getAsJsonObject().get("name").getAsString();
                Assert.assertEquals(actualName, expectedName);
            }
        }
    }




}
