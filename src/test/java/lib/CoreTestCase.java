package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.WelcomPageObject;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class CoreTestCase extends TestCase {

    protected RemoteWebDriver driver;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        driver = Platform.getInstance().getDriver();

        this.rotateScreenPortrait(); // девайс всегда будет переходить в портретный режим перед запуском теста
        this.skipeWelcomePageForIOSApp(); // клик по кнопке skip
        this.openWikiWebPageForMobileWeb();

    }

    @Override
    protected void tearDown() throws Exception

    {
        driver.quit();
        super.tearDown();
    }

    protected void rotateScreenPortrait()
    {
        if (driver instanceof AppiumDriver){
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT); // делаем переворот в портретный режим
        } else {
            System.out.println("Method rotateScreenPortrait() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }

    }

    protected void rotateScreenLandscape()
    {
        if (driver instanceof AppiumDriver){
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE); // поворот телефона в ландшафтный режим
        } else {
            System.out.println("Method rotateScreenLandscape() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }

    }

    protected void backGroundApp(int seconds)
    {
        if (driver instanceof AppiumDriver){
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(Duration.ofMillis(seconds)); // отсылаем приложение в бекграунд, автоматически разворачивается
        } else {
            System.out.println("Method backgroundApp() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }


    }

    protected  void openWikiWebPageForMobileWeb() // открытие страницы браузера для wiki
    {
        if (Platform.getInstance().isMW()) {
            driver.get("https://en.m.wikipedia.org");
        } else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }
    }

    private void skipeWelcomePageForIOSApp()
    {
        if(Platform.getInstance().isIOS()) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            WelcomPageObject WelcomPageObject = new WelcomPageObject(driver);
            WelcomPageObject.clickSkip();
        }
    }

}
