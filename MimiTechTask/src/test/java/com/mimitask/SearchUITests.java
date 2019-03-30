package com.mimitask;

import io.appium.java_client.MobileElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class SearchUITests {

    static AppiumDriver<MobileElement> driver;
    public static final String ID_SKIP ="btn_skip";
    public static final String CLASSNAME_ADDICON="android.widget.ImageButton";
    public static final String ID_EDITICON="fab_note";
    public static final String ID_EDITOR="editor";
    public static final String ID_SAVEBUTTON="save_btn";
    public static final String ID_CLOSEICON="close";
    public static final String XPATH_SEARCHICON="//android.widget.TextView[@content-desc=\"Search\"]";
    public static final String ID_SEARCHTEXTVIEW="searchTextView";
    public static final String ID_BODYCLICK ="body";
    String firstnote = "Hello world, testing app";
    String secondnote="Concert booking";
    String thirdnote="Kino at 6PM";


    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8.0.0");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("appActivity", "com.beatles.notebook.activity.SplashLiteActivity");
        capabilities.setCapability("appPackage", "notes.notebook.memo.pad.color.notepad.locker");

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);

    }

    @Test
    public void testAddNote() {
        driver.findElement(By.id(ID_SKIP)).click();
        waitSeconds(1);

        //First note
        driver.findElement(By.className(CLASSNAME_ADDICON)).click();
        driver.findElement(By.id(ID_EDITICON)).click();

        driver.findElement(By.id(ID_EDITOR)).sendKeys(firstnote);
        driver.findElement(By.id(ID_SAVEBUTTON)).click();

        String editornote = driver.findElement(By.id("body")).getText();
        Assert.assertEquals("Did not create any note", firstnote, editornote);
    }

    @Test
    public void testEditNotes(){
        driver.findElement(By.id(ID_SKIP)).click();
        waitSeconds(1);

        //First note
        driver.findElement(By.className(CLASSNAME_ADDICON)).click();
        driver.findElement(By.id(ID_EDITICON)).click();

        driver.findElement(By.id(ID_EDITOR)).sendKeys(firstnote);
        driver.findElement(By.id(ID_SAVEBUTTON)).click();

        driver.findElement(By.id("body")).click();
        driver.findElement(By.id(ID_EDITOR)).sendKeys(" edited");
        driver.findElement(By.id(ID_SAVEBUTTON)).click();
        String editednote = driver.findElement(By.id("body")).getText();
        String input = firstnote.concat(" edited");
        Assert.assertEquals("Edit note failed", input, editednote);
    }

    @Test
    public void testSearchNotes() {

        driver.findElement(By.id(ID_SKIP)).click();
        waitSeconds(1);

        //First note
        driver.findElement(By.className(CLASSNAME_ADDICON)).click();
        driver.findElement(By.id(ID_EDITICON)).click();

        driver.findElement(By.id(ID_EDITOR)).sendKeys(firstnote);
        driver.findElement(By.id(ID_SAVEBUTTON)).click();

        //Second note
        driver.findElement(By.className(CLASSNAME_ADDICON)).click();
        driver.findElement(By.id(ID_EDITICON)).click();
        driver.findElement(By.id(ID_EDITOR)).sendKeys(secondnote);
        driver.findElement(By.id(ID_SAVEBUTTON)).click();

        //Third note
        driver.findElement(By.className(CLASSNAME_ADDICON)).click();
        driver.findElement(By.id(ID_EDITICON)).click();
        driver.findElement(By.id(ID_EDITOR)).sendKeys(thirdnote);
        driver.findElement(By.id(ID_SAVEBUTTON)).click();

        //Close Rate pop up box
        driver.findElement(By.id(ID_CLOSEICON)).click();

        // Search
        driver.findElement(By.xpath(XPATH_SEARCHICON)).click();
        waitSeconds(1);
        driver.findElement(By.id(ID_SEARCHTEXTVIEW)).sendKeys("Hello world");
        driver.findElement(By.id(ID_BODYCLICK)).click();
        String editornote=driver.findElement(By.id("editor")).getText();
        Assert.assertEquals("Wrong Search results", firstnote, editornote);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    private void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {

        }

    }
}
