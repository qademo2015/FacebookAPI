package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by abarabash on 5/26/16.
 */
public class Class1 {

    @Test
    public void test001() {

        String expected = "right";
        String actual = "wrong";

        Assert.assertEquals(actual, expected, "string are not equal");

    }

    @Test
    public void test002() {

    }


}
