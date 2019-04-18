package lib.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject {

     protected static String
            SEARCH_INIT_ELEMENT,  //открытие поиска википедии
            SEARCH_INPUT,  //ввод текста в строку поиска
            SEARCH_CANCEL_BUTTON, //очистка текста с поля ввода поиска
            SEARCH_RESULT_BY_SUBSTRING_TPL, //ожидает элемент по ресурс ид и тексту из /* TEMPLATES METHODS */
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_EMPTY_SEARCH, // поиск лейба Search and read the free encyclopedia in your language на странице
            SEARCH_RESULT_TITLE_ELEMENT,
            SEARCH_BY_TITLE_AND_DESCRIPTION_TPL; //  TEMPLATES METHODS локатор тайтл + дескрипшен

    public SearchPageObject(RemoteWebDriver driver)
    {
        super(driver);  // берем драйвер из MainPageObject
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getElementByTitleAndDescription(String title, String description)
    {
        return SEARCH_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput()    //находит поиск, кликает по нему и проверяет что Input есть
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 10);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear()   // проверяем наличие кнопки Х ЗАКРЫТИЕ
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    public int checkSearchFoundSomeResults()
    {
        List<WebElement> arrayOfResultTitles = this.waitForAllElementsPresented(SEARCH_RESULT_TITLE_ELEMENT, "Cannot find search result title element is results", 5);
        return arrayOfResultTitles.size();
    }

    public void waitForElementByTitleAndDescription(String title, String description)   // проверяем наличие тайтла и дискрипшена
    {
        String search_result_xpath = getElementByTitleAndDescription(title, description);
        WebElement element = this.waitForElementPresent(search_result_xpath, "Cannot find search result with title" + title + " and " + description, 5);
    }

    public void waitForCancelButtonToDisappear()   // проверяем отсутствие кнопки Х ЗАКРЫТИЕ
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    public void clickCancelSearch()  // клик по кнопке Х ЗАКРЫТИЕ
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    public void typeSearchLine(String search_line)  //ввод текста в строку поиска
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line,"Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring)   //ожидает элемент по ресурс ид и тексту
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring" + substring);
    }

    public void clickByArticleWithSubString(String substring)   //клик по статье найденной по ресурс ид и тексту
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring" + substring, 10);
    }

    public int getAmountOfFoundArticles()  // возвращает  кол-во статей (элементов) по  определенному xpath
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                " Cannot find anything by request ",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel()  // поиск лейба No results found на странице
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch()  // проверяем что в поиске нет ни одной статьи
    {
       this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    public void waitForEmptyResultsLabe2()  // поиск лейба Search and read the free encyclopedia in your language на странице
    {
        this.waitForElementPresent(SEARCH_EMPTY_SEARCH, "Cannot find empty result element", 15);
    }

}
