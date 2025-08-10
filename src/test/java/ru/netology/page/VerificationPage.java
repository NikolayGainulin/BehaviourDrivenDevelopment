package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.date.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private static final String CODE_FIELD_SELECTOR = "[data-test-id=code] input";
    private static final String VERIFY_BUTTON_SELECTOR = "[data-test-id=action-verify]";
    private static final String ERROR_NOTIFICATION_SELECTOR = "[data-test-id=error-notification]";

    private final SelenideElement codeField = $(CODE_FIELD_SELECTOR);
    private final SelenideElement verifyButton = $(VERIFY_BUTTON_SELECTOR);
    private final SelenideElement errorNotification = $(ERROR_NOTIFICATION_SELECTOR);

    public VerificationPage() {
        codeField.shouldBe(Condition.visible, Duration.ofSeconds(15));
        verifyButton.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        enterVerificationCode(verificationCode.getCode());
        submitVerification();
        return new DashboardPage();
    }

    public void enterVerificationCode(String code) {
        codeField.shouldBe(Condition.visible)
                .setValue(code);
    }

    public void submitVerification() {
        verifyButton.shouldBe(Condition.enabled)
                .click();
    }

    public void verifyErrorNotification(String expectedError) {
        errorNotification.shouldBe(Condition.visible)
                .shouldHave(Condition.text(expectedError));
    }

    public void verifyCodeFieldIsVisible() {
        codeField.shouldBe(Condition.visible);
    }
}