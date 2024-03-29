package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSArticlePageObject extends ArticlePageObject
{
    static {
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "xpath://XCUIElementTypeButton[@name='Back']"; //или id:Back
        ARTICLE_DESCRIPTION = "id:Scripting language"; //  дескрипшен
    }

    public iOSArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

}
