package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import tests.steps.BookStoreSteps;
import helpers.FakerData;

@DisplayName("Tests for Book Store Application")
public class BooksStoreTests extends TestBase {

    private final ProfilePage profilePage = new ProfilePage();

    @Test
    @DisplayName("Delete a book from user profile")
    void deleteBookFromProfileBooksListTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        BookStoreSteps step = new BookStoreSteps();
        String userName = FakerData.generateUsername();
        String password = FakerData.generatePassword(9, 14);
        String isbn = "9781593275846";
        step.userRegistrationApi(userName, password);
        step.getTokenApi(userName, password);
        LoginResponseModel loginResponse = step.loginUserApi(userName, password);
        step.addBookToProfileApi(loginResponse, isbn);
        step.setAuthCookie(loginResponse);
        profilePage.openProfilePage()
            .removeBanner()
            .checkBookIsInProfile(isbn)
            .deleteBook()
            .checkBooksListIsEmpty();
    }
}