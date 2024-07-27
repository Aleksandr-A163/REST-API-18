package tests;

import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import tests.api.BookStoreSteps;
import helpers.FakerData;


@Tag("book")
@DisplayName("Tests for Book Store Application")
public class BooksStoreTests extends TestBase {

    private final ProfilePage profilePage = new ProfilePage();

    @Test
    @DisplayName("Delete a book from user profile")
    void deleteBookFromProfileBooksListTest() {
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