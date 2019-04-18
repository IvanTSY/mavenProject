package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) // к этому конструктару обращаются все тесты
    {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new  WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n"); // с новой строки
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public List<WebElement> waitForAllElementsPresented(String locator, String errorMessage, long timeoutInSeconds)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(this.driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public WebElement waitForElementPresent(String locator, String error_message)
    {
        return waitForElementPresent(locator, error_message, 5);
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeUp(int timeOfSwipe)   // скрол страницы по y
    {
        if(driver instanceof AppiumDriver){
            TouchAction action = new TouchAction((AppiumDriver)driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int  end_y = (int) (size.height * 0.2);
            action
                    .press(PointOption.point(x,start_y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                    .moveTo(PointOption.point(x,end_y))
                    .release()
                    .perform();
        }else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }

    }

    public void swipeUpQuick() // метод для быстрого скрола
    {
        if(driver instanceof AppiumDriver) {
            swipeUp(200);
        }else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }
    }

    public void  swipeUpToFindElement(String locator, String error_message, int i)  // метод поиска всех элементов на странице
    {
        if(driver instanceof AppiumDriver) {
            By by = this.getLocatorByString(locator);
            driver.findElements(by);  //поиск всех элементов на странице
            driver.findElements(by).size(); // вывод количества найденых элементов

            while (driver.findElements(by).size() == 0) {  // цикл который ищит элементы
                swipeUpQuick();
            }
        }else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }
    }

    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes)
    {
        if(driver instanceof AppiumDriver) {
            int already_swiped = 0;

            while (!this.isElementLocatedOnTheScreen(locator)) {
                if (already_swiped > max_swipes) {
                    Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
                }

                swipeUpQuick();
                ++already_swiped;
            }
        }else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator)
    {
        int element_location_by_y = this.waitForElementPresent(locator, "Cannot find element by locator",10).getLocation().getY();
        if (Platform.getInstance().isMW()) // этот if вычисление позиции для скрола в WEB
        {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            element_location_by_y-= Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;
    }

    public void clickElementToTheRightUpperCorner(String locator, String error_message)  //клик по кнопке удалить статью для ios
    {
        if (driver instanceof AppiumDriver) {
            WebElement element = this.waitForElementPresent(locator + "/..", error_message); // "/.." переходим на элемент выше
            int right_x = element.getLocation().getX();  // вычисляем правую точку
            int upper_y = element.getLocation().getY(); // вычисляем вверх
            int lower_y = upper_y + element.getSize().getHeight(); // вычисляем низ
            int middle_y = (upper_y + lower_y) / 2; // вычисляем середину
            int width = element.getSize().getWidth(); // узнаем ширину элемента

            int point_to_click_x = (right_x + width) - 3; // находим правый верзний угол
            int point_to_click_y = middle_y;

            TouchAction action = new TouchAction((AppiumDriver)driver);
            action.tap(PointOption.point(point_to_click_x, point_to_click_y)).perform();
        }else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }
    }

    public void clickElementToTheRightUpperCornerForWEBFORM(String locator, String error_message)  //клик по кнопке удалить статью для ios
    {
        if (driver instanceof AppiumDriver) {
            //TODO Ищем правый верхний угол для веб формы
            WebElement element = this.waitForElementPresent(locator, error_message); // "/.." переходим на элемент выше
            int right_x = element.getLocation().getX();  // вычисляем правую точку
            int upper_y = element.getLocation().getY(); // вычисляем вверх
            int lower_y = upper_y + element.getSize().getHeight(); // вычисляем низ
            int middle_y = (upper_y + lower_y) / 2; // вычисляем середину
            int width = element.getSize().getWidth(); // узнаем ширину элемента

            int point_to_click_x = (right_x + width); // находим правый верзний угол -3 убрал
            int point_to_click_y = middle_y;

            TouchAction action = new TouchAction((AppiumDriver)driver);
            action.tap(PointOption.point(point_to_click_x, point_to_click_y)).perform();
        }else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }
    }

    public void swipeElementToLeft(String locator, String error_message) // скрол элемента влево по оси Х
    {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent( // находим элемент на странице
                    locator,
                    error_message,
                    10);
            int left_x = element.getLocation().getX(); //записует самую левую точку элемента по оси Х
            int right_x = left_x + element.getSize().getWidth(); // получаем точку а правой границы экрана
            int upper_y = element.getLocation().getY(); //
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2; // находим середину элемента

            TouchAction action = new TouchAction((AppiumDriver)driver);
            action.press(PointOption.point(right_x, middle_y));
            action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)));

            if (Platform.getInstance().isAndroid()) {
                action.moveTo(PointOption.point(left_x, middle_y));
            } else {
                int offset_x = (-1 * element.getSize().getWidth());
                action.moveTo(PointOption.point(offset_x, 0));
            }
            action.release();
            action.perform();
        }else {
            System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }
    }

    public int getAmountOfElements(String locator)
    {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();//  возврашаем кол-во которые были найдены при помощи функции driver.findElements
    }

    public void assertElementNotPresent(String locator, String error_message) // проверяем что в поиске нет ни одной статьи
    {
        int amount_of_elements = getAmountOfElements(locator); // вычисляем кол-во элементов по xpath
        if (amount_of_elements > 0) { //проверка если их больше 0
            String default_message = "' An element '" + locator + " supposed to be present "; // формируем строку с присутствующим элементом (если он есть)
            throw new AssertionError(default_message + "" +error_message); // выдаем сообщение
        }
    }

    public String waitForElementAndGetAtribute(String locator, String attribute, String error_message, long timeoutInSeconds) // получаем заголов статьи
    {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private By getLocatorByString(String locator_with_type) // метод для определения типов локатора
    {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath"))
        {
            return By.xpath(locator);
        } else if (by_type.equals("id"))
        {
            return By.id(locator);
        } else if(by_type.equals("css"))
        {
            return By.cssSelector(locator);
        }else {
        throw new IllegalArgumentException("Cannot get type of locator. Locator:" + locator_with_type);

        }
    }
    public void scrollWebPageUp()  // скрол для web
    {
        if (Platform.getInstance().isMW()){
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;  // исполнение джава скрипт на данной странице
            JSExecutor.executeScript("window.scrollBy(0, 250)");  //  по пиксельный скролл от пикселя 0 до пикселя 250
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform" + Platform.getInstance().getPlatformVar());
        }
    }

    public void scrollWebPageTitleElementNotVisible(String locator, String error_message, int max_swipes) // делает скрол пока нужный элемент не появится на экране
    {
        int already_swiped = 0;

        WebElement element = this.waitForElementPresent(locator, error_message);

        while (!this.isElementLocatedOnTheScreen(locator)) //пока элемент не на экране крутит цикл
        {
            scrollWebPageUp();
            ++already_swiped;
            if (already_swiped > max_swipes){
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
    }
    public boolean isElementPresent(String locator)  //метод определяет есть элемент на странице или нет
    {
        return getAmountOfElements(locator) > 0;
    }

    public  void tryClickElementWithFewAttempts(String locator, String error_message, int amount_of_attempts)  // метод который перехватывает ошибку что не может кликнуть на нужный элеменнт и кликает на него для Mob web
    {
        int current_attempts = 0;  // сколько уже раз кликнули
        boolean need_more_attempts = true;

        while (need_more_attempts) {
            try {
                this.waitForElementAndClick(locator, error_message, 5);
                need_more_attempts = false;
            } catch (Exception e) {
                if (current_attempts > amount_of_attempts) {
                    this.waitForElementAndClick(locator, error_message, 5);
                }
            }
            ++ current_attempts;
        }
    }

}
