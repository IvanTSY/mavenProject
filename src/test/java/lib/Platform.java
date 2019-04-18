package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_MOBILE_WEB = "mobile_web";

    private static final String APPIUM_URL = "http://0.0.0.0:4723/wd/hub";

    private static Platform instance;

    private Platform(){} // приватный конструктор

    public static Platform getInstance()
    {
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public RemoteWebDriver getDriver() throws Exception // метод который отвечает за выбраный драйвер
    {
        URL URL = new URL(APPIUM_URL);
        if(this.isAndroid()) {
            return new AndroidDriver(URL, this.getAndroidDesiredcCapabilities());
        } else if (this.isIOS()){
            return new IOSDriver(URL, this.getIOSDesiredcCapabilities());
        } else  if (this.isMW()){
            return new ChromeDriver(this.getMWChromeOptions());
        }else
            throw new Exception("Cannot detect type of the Driver. Platform value:" + this.getPlatformVar());
    }


    public boolean isAndroid() // определяет является ли платформа Android
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()  // определяет является ли платформа IOS
    {
        return isPlatform(PLATFORM_IOS);
    }

    public boolean isMW()  // определяет является ли платформа WEB
    {
        return isPlatform(PLATFORM_MOBILE_WEB);
    }


    private DesiredCapabilities getAndroidDesiredcCapabilities()  // capabilities для Android
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AVDNexus5");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","C:\\Users\\FBI\\Desktop\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredcCapabilities() // capabilities для IOS
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName","iPhone SE");
        capabilities.setCapability("platformVersion","11.3");
        capabilities.setCapability("app","/Users/testers/Desktop/JavaAppiumAutomation/apks/wikipedia.app");
        return capabilities;
    }

    private ChromeOptions getMWChromeOptions() // запускает браузер Chrome
    {
        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
        deviceMetrics.put("width", 360); //высота девайса
        deviceMetrics.put("height", 640); //ширина девайса
        deviceMetrics.put("pixelRation", 3.0); //плотность пикселей девайса

        Map<String, Object> mobileEmulation = new HashMap<String, Object>(); // параметры юзер агента
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=340,640");

        return chromeOptions;
    }

    private boolean isPlatform(String my_platform)// сравнивание с переменной которая приходит на вход
    {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    public String getPlatformVar() // получение переменной окружения
    {
        return System.getenv("PLATFORM");
    }


}
