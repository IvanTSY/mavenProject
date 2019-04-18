package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject
{
    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "css:a#ca-watch.mw-ui-icon-mf-watch";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:a#ca-watch.mw-ui-icon-mf-watched";
        DELETE_ARTICLE_FROM_MY_LIST_BUTTON = "css:div.mw-ui-icon-mf-watched";
        ARTICLE_DESCRIPTION = "css:#mw-content-text > ul > li:nth-child(2) > a > div.info";

    }

    public MWArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}