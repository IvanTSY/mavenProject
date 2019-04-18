package lib.ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_CHANGE_LANGUAGE_BUTTON,
            OPTIONS_SHARE_LINK_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            WAIT_ARTICLE_TITLE,
            ARTICLE_IN_MY_LIST,
            ARTICLE_TITLE,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
            DELETE_ARTICLE_FROM_MY_LIST_BUTTON,

            ARTICLE_DESCRIPTION;

    public ArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTitleElement()  // ожидаем название статьи
    {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page!", 15);
    }

    public String getArticleTitle() // получаем название статьи
    {
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return title_element.getAttribute("name");
        } else {
            return title_element.getText();
        }
    }


    public void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()){
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        } else {
            this.scrollWebPageTitleElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        }
    }

    public void addArticleToMyList(String name_of_folder) // добавление статьи в мой список
    {
        this.waitForElementAndClick( // находим элемент "More options" и кликаем по нему
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementPresent(  // выбираем элемент из списка поиска
                OPTIONS_CHANGE_LANGUAGE_BUTTON,
                "Cannot find options 'Change language'",
                5
        );

        this.waitForElementPresent(  // выбираем элемент из списка поиска
                OPTIONS_SHARE_LINK_BUTTON,
                "Cannot find options 'Share link'",
                5
        );

        this.waitForElementAndClick( // находим элемент "Add to reading list" и кликаем по нему
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find options 'Add to reading list'",
                5
        );

        this.waitForElementAndClick( // находим элемент "GOT IT" и кликаем по нему
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'GOT IT' tip overlay",
                5
        );

        this.waitForElementAndClear(    //  очистка текста с поля ввода
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder",
               5
        );

        this.waitForElementAndSendKeys( // вводим новый текст в поле ввода
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick( // находим элемент "OK" и кликаем по нему
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );

    }

    public void waitArticleTitlePresent() // ждем появление элемента
     {
         this.waitForElementPresent(
                 WAIT_ARTICLE_TITLE,
                 "Cannot find article title",
                 5
         );
     }

    public void addArticlesToMySaved()
    {
        if (Platform.getInstance().isMW()){
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5);
    }


    public void closeArticle()  // находим элемент "Navigate up" и кликаем по нему ЗАКРЫВАЕМ
    {
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X link",
                    5
            );
        } else {
            System.out.println("Method closeArticle() do nothing for platform" + Platform.getInstance().getPlatformVar());
        }
    }

    public void addTwoArticleToMyList(String name_of_folder) // сохранение второй статьи в готовый список
    {
        this.waitForElementAndClick( // находим элемент "More options" и кликаем по нему
                  OPTIONS_BUTTON,
                  "Cannot find button to open article options",
                  5
        );

        this.waitForElementPresent(  // выбираем элемент из списка поиска
                  OPTIONS_CHANGE_LANGUAGE_BUTTON,
                  "Cannot find options 'Change language'",
                  5
        );

        this.waitForElementPresent(  // выбираем элемент из списка поиска
                  OPTIONS_SHARE_LINK_BUTTON,
                  "Cannot find options 'Share link'",
                  5
        );

        this.waitForElementAndClick( // находим элемент "Add to reading list" и кликаем по нему
                  OPTIONS_ADD_TO_MY_LIST_BUTTON,
                  "Cannot find options 'Add to reading list'",
                  5
        );
    }

    public void clickOnArticleInMyList() // клик по статье в моем списке
    {
        this.waitForElementAndClick( // находим статью в мое листе и кликаем по ней
                ARTICLE_IN_MY_LIST,
                "Cannot find article PHP 'Scripting language'",
                5
        );
    }

    public void checkArticleTitle() // проверяем title в открытой статье
    {
        this.waitForElementPresent(
                ARTICLE_TITLE,
                "Cannot find article title 'PHP'",
                5
        );
    }

    public void checkArticleDescription() // проверяем дискрипшен в открытой статье
    {
        this.waitForElementPresent(
                ARTICLE_DESCRIPTION,
                "Cannot find article title Scripting language'",
                5
        );
    }
    public void deleteArticlesToMySaved() //
    {
        this.waitForElementAndClick(DELETE_ARTICLE_FROM_MY_LIST_BUTTON, "Cannot find option to delete article to reading list", 5);
    }

    public void removeArticleFromSavedIfItAdded()  // метод удаляет статью если она уже была добавлена в избранное
    {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)){
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    1
            );
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find button to add an article to saved list after removing it from this list before"
            );
        }
    }


}
