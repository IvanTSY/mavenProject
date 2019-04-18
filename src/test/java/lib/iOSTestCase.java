package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class iOSTestCase extends TestCase {

    protected AppiumDriver driver;
    private static String AppiumURL = "http://0.0.0.0:4723/wd/hub";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName","iPhone 8 Plus");
        capabilities.setCapability("platformVersion","11.0");
        capabilities.setCapability("app","/Users/testers/Desktop/JavaAppiumAutomation/apks/wikipedia.app");

        driver = new IOSDriver(new URL(AppiumURL), capabilities);
        this.rotateScreenPortrait(); // девайс всегда будет переходить в портретный режим перед запуском теста
    }

    @Override
    protected void tearDown() throws Exception

    {
        driver.quit();

        super.tearDown();
    }

    protected void rotateScreenPortrait()
    {
        driver.rotate(ScreenOrientation.PORTRAIT); // делаем переворот в портретный режим
    }

    protected void rotateScreenLandscape()
    {
        driver.rotate(ScreenOrientation.LANDSCAPE); // поворот телефона в ландшафтный режим
    }

    protected void backGroundApp(int seconds)
    {
        driver.runAppInBackground(Duration.ofMillis(seconds)); // отсылаем приложение в бекграунд, автоматически разворачивается
    }

}
