package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.date.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private static final String TRANSFER_BUTTON_SELECTOR = "[data-test-id='action-transfer']";
    private static final String AMOUNT_INPUT_SELECTOR = "[data-test-id='amount'] input";
    private static final String FROM_INPUT_SELECTOR = "[data-test-id='from'] input";
    private static final String ERROR_MESSAGE_SELECTOR = "[data-test-id='error-notification'] .notification__content";

    private final SelenideElement transferButton = $(TRANSFER_BUTTON_SELECTOR);
    private final SelenideElement amountInput = $(AMOUNT_INPUT_SELECTOR);
    private final SelenideElement fromInput = $(FROM_INPUT_SELECTOR);
    private final SelenideElement transferHead = $(byText("Пополнение карты"));
    private final SelenideElement errorMessage = $(ERROR_MESSAGE_SELECTOR);

    public TransferPage() {
        transferHead.shouldBe(visible);
        amountInput.shouldBe(visible);
        fromInput.shouldBe(visible);
        transferButton.shouldBe(visible);
    }

    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInput.shouldBe(visible).setValue(amountToTransfer);
        fromInput.shouldBe(visible).setValue(cardInfo.getCardNumber());
        transferButton.shouldBe(visible).click();
    }

    public void verifyErrorMessage(String expectedText) {
        errorMessage.shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text(expectedText));
    }
}