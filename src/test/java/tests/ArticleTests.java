package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;

import org.junit.Test;

public class ArticleTests extends CoreTestCase
{
    @Test
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();  //открытие поиска википедии
        SearchPageObject.typeSearchLine("Java"); //ввод текста в строку поиска
        SearchPageObject.clickByArticleWithSubString("bject-oriented programming language");  //клик по статье найденной по ресурс ид и тексту

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        String article_title = ArticlePageObject.getArticleTitle();   // ожидаем название статьи и получаем название статьи

        assertEquals(
                "We see unexpected title!",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();  //открытие поиска википедии
        SearchPageObject.typeSearchLine("Java"); //ввод текста в строку поиска
        SearchPageObject.clickByArticleWithSubString("bject-oriented programming language");  //клик по статье найденной по ресурс ид и тексту

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        ArticlePageObject.waitForTitleElement();  // выбираем элемент из списка поиска по заголовку
        ArticlePageObject.swipeToFooter();  // скрол до нужного элемента
    }
}
