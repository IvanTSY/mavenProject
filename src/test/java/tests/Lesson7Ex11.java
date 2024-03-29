package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class Lesson7Ex11 extends CoreTestCase
{
    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveTwoArticlesToMyList()
    {
        String
                login = "IvanTSY",
                password = "02615948";
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else if(Platform.getInstance().isIOS()) {
            ArticlePageObject.addArticlesToMySaved();
        }else if (Platform.getInstance().isMW()) {
            ArticlePageObject.addArticlesToMySaved();
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login,password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            assertEquals("WE are not on the same after login",
                    "Log in",
                    ArticlePageObject.getArticleTitle()
            );
            ArticlePageObject.addArticlesToMySaved();
        }
//==============================================================================
        MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);

        if(Platform.getInstance().isIOS())
        {
            MyListPageObject.closeIOSUnknownForm(); // исправление для вебвью
        }else {System.out.println("Its not IOS device, next step");}
//==============================================================================

        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubString("oman Politician");

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addTwoArticleToMyList(name_of_folder);
        } else if(Platform.getInstance().isIOS()){
            ArticlePageObject.addArticlesToMySaved();
        }else if(Platform.getInstance().isAndroid()){
        MyListPageObject.openFolderByName(name_of_folder);
    }





        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        ArticlePageObject.waitArticleTitlePresent();

        if (Platform.getInstance().isAndroid()){
            MyListPageObject.openFolderByName(name_of_folder);
        }

        MyListPageObject.swipeByArticleToDelete(article_title);

        ArticlePageObject.clickOnArticleInMyList();
        ArticlePageObject.checkArticleDescription();

    }
}
