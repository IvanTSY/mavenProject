package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
    private static final String name_of_folder = "Learning programming"; // описываем переменную один раз,что бы везде сама подставлялась

    private static final String
            login = "IvanTSY",
            password = "02615948";
    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();  //открытие поиска википедии
        SearchPageObject.typeSearchLine("Java"); //ввод текста в строку поиска
        SearchPageObject.clickByArticleWithSubString("bject-oriented programming language");  //клик по статье найденной по ресурс ид и тексту

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();   // выбираем элемент из списка поиска по заголовку

        String article_title = ArticlePageObject.getArticleTitle();

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder); // передаем имя папки в которую будет добавляться статья
        } else {
            ArticlePageObject.addArticlesToMySaved();  // сохранение статьи для ios
        }
        if (Platform.getInstance().isMW()){
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver); // ввод данных для авторизации
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();  // ждем редиректа обратно на страницу

            assertEquals("WE are not on the same after login", // проверка что мы еще на этой странице
                    article_title,
                    ArticlePageObject.getArticleTitle()
            );

            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();  // находим элемент "Navigate up" и кликаем по нему ЗАКРЫВАЕМ

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);

        NavigationUI.openNavigation(); // открытие навигации в приложении вики для mob web
        NavigationUI.clickMyLists();  // находим элемент "My lists" и кликаем по нему

        MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);

        if (Platform.getInstance().isMW()){
            ArticlePageObject.deleteArticlesToMySaved(); // удаляем статью из списка
        } else {
            MyListPageObject.openFolderByName(name_of_folder);
            MyListPageObject.swipeByArticleToDelete(article_title);   // скролим элемент влево и сразуже идет проверка
        }
    }
}
