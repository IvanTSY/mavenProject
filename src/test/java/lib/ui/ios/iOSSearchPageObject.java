package lib.ui.ios;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSSearchPageObject extends SearchPageObject
{

    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia')]";  //открытие поиска википедии
        SEARCH_INPUT = "xpath://XCUIElementTypeStaticText[@name='Wikipedia, scroll to top of Explore']";  //ввод текста в строку поиска
        SEARCH_CANCEL_BUTTON = "id:Close"; //очистка текста с поля ввода поиска
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";   //ожидает элемент по ресурс ид и тексту из /* TEMPLATES METHODS */
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        SEARCH_RESULT_TITLE_ELEMENT = "id:org.wikipedia:id/page_list_item_title";

    }

    public iOSSearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
