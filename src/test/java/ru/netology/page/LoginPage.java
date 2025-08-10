package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.date.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private static final String LOGIN_FIELD_SELECTOR = "[data-test-id=login] input";
    private static final String PASSWORD_FIELD_SELECTOR = "[data-test-id=password] input";
    private static final String LOGIN_BUTTON_SELECTOR = "[data-test-id=action-login]";

    private final SelenideElement loginField = $(LOGIN_FIELD_SELECTOR);
    private final SelenideElement passwordField = $(PASSWORD_FIELD_SELECTOR);
    private final SelenideElement loginButton = $(LOGIN_BUTTON_SELECTOR);

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.shouldBe(visible).setValue(info.getLogin());
        passwordField.shouldBe(visible).setValue(info.getPassword());
        loginButton.shouldBe(visible).click();
        return new VerificationPage();
    }
}