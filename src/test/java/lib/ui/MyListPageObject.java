package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            REMOVE_FROM_SAVED_BUTTON,
            WEB_FORM;

    private static String getFolderXpathByName(String name_of_folder) // заменяет значение константы FOLDER_BY_NAME_TPL на значение из name_of_folder
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getRemoveButtonByTitle(String article_title)  // получает кнопку удаления по тайтлу статьи
    {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }

    private static String getSavedArticleXpathByTitle(String article_title) // выдает xpath для статьи TPL
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    //TODO тут передаем имя в икспас убрал ВЕБФОРМ СТРИНГ
    private static String getWebFormToClose(){
        return MyListPageObject.WEB_FORM;
    }

    public MyListPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void openFolderByName(String name_of_folder)  // находим папку с названием "Learning programming" и кликаем по нему
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);   // для того что бы передавать правильный xpath
        this.waitForElementAndClick(
                folder_name_xpath, //используем переменную которая через строку выше
                "Cannot find folder by name" + name_of_folder,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String article_title) // убеждаемся что статья присутствует
    {
        String article_xpath = getFolderXpathByName(article_title);   // для того что бы передавать правильный xpath
        this.waitForElementPresent(article_xpath, "Cannot find saved article by title" + article_title, 15);
    }

    public void waitForArticleToDisappearByTitle(String article_title) // убеждаемся что статья удалилась
    {
        String article_xpath = getFolderXpathByName(article_title);   // для того что бы передавать правильный xpath
        this.waitForElementNotPresent(article_xpath, "Saved article still present with title" + article_title, 15);
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getFolderXpathByName(article_title);

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()){
            this.swipeElementToLeft(
                    article_xpath,
                    "Cannot find saved article"
            );
        } else {
            String remove_locator = getRemoveButtonByTitle(article_title);
            this.waitForElementAndClick(
                    remove_locator,
                    "Cannot click button to remove article from saved",
                    10
            );
        }

        if (Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article");
        }

        if (Platform.getInstance().isMW()){
            driver.navigate().refresh();
        }

        this.waitForArticleToDisappearByTitle(article_title); //  убеждаемся что статья удалилась
    }

    public void closeIOSUnknownForm(){
        this.waitForElementPresent(WEB_FORM,"not see web v iew",5);
        this.clickElementToTheRightUpperCornerForWEBFORM(
                WEB_FORM,"Not find web element");
    }

}
