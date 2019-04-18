package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase
{
    @Test
    public void testSearch() // тест поискаа статьи
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();  //открытие поиска википедии
        SearchPageObject.typeSearchLine("Java"); //ввод текста в строку поиска
        SearchPageObject.waitForSearchResult("bject-oriented programming language");  //ожидает элемент по ресурс ид и тексту
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();  //открытие поиска википедии
        SearchPageObject.typeSearchLine("Java"); //ввод текста в строку поиска
        SearchPageObject.clickCancelSearch();   // клик по кнопке Х ЗАКРЫТИЕ
        SearchPageObject.clickCancelSearch();   // клик по кнопке Х ЗАКРЫТИЕ
        SearchPageObject.waitForCancelButtonToAppear();  // проверяем наличие кнопки Х ЗАКРЫТИЕ
        SearchPageObject.waitForCancelButtonToDisappear();   // проверяем отсутствие кнопки Х ЗАКРЫТИЕ
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();  //открытие поиска википедии
        String search_line = "Linkin Park Diskography"; // объявили переменную для ввода в поиск
        SearchPageObject.typeSearchLine(search_line); //ввод текста в строку поиска который объявлен в переменной
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue( // убеждаемся что найденых элементов больше 0
                "We found toofew results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();  //открытие поиска википедии
        String search_line = "djdjjdjf"; // объявили переменную для ввода в поиск
        SearchPageObject.typeSearchLine(search_line); //ввод текста в строку поиска который объявлен в переменной
        SearchPageObject.waitForEmptyResultsLabel();  // поиск лейба No results found на странице
        SearchPageObject.assertThereIsNoResultOfSearch();  // проверяем что в поиске нет ни одной статьи

    }
}
