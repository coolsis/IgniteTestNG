package com.ignite.tests;

import com.ignite.utilities.BasePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.ignite.utilities.BrowserUtils.waitFor;

public class EIF_Tests extends BasePage {

    @Test
    public void happyPathTest(){
        ppObj.fillParentForm(myParent);
        spObj.fillStudentForm(myStudent);
        sclpObj.selectSchool("Ignite 101");
        fpObj.finish_Btn.click();
        waitFor(2);

        Assert.assertTrue(cpObj.confirmation_no.isDisplayed());
    }

}
