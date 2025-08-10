package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.date.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private static final String BALANCE_START = "баланс: ";
    private static final String BALANCE_FINISH = " р.";
    private static final String DASHBOARD_SELECTOR = "[data-test-id=dashboard]";
    private static final String CARD_ITEM_SELECTOR = ".list__item div";
    private static final String RELOAD_BUTTON_SELECTOR = "[data-test-id='action-reload']";

    private final SelenideElement heading = $(DASHBOARD_SELECTOR);
    private final ElementsCollection cards = $$(CARD_ITEM_SELECTOR);
    private final SelenideElement reloadButton = $(RELOAD_BUTTON_SELECTOR);

    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = getCard(cardInfo).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        getCard(cardInfo).$("button").click();
        return new TransferPage();
    }

    private SelenideElement getCard(DataHelper.CardInfo cardInfo) {
        return cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId()));
    }

    public void reloadDashboardPage() {
        reloadButton.click();
        heading.shouldBe(Condition.visible);
    }

    private int extractBalance(String text) {
        try {
            var start = text.indexOf(BALANCE_START);
            var finish = text.indexOf(BALANCE_FINISH);
            var value = text.substring(start + BALANCE_START.length(), finish);
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            throw new RuntimeException("Could not extract balance from text: " + text, e);
        }
    }

    public void checkCardBalance(DataHelper.CardInfo cardInfo, int expectedBalance) {
        getCard(cardInfo).shouldBe(Condition.visible)
                .shouldHave(Condition.text(BALANCE_START + expectedBalance + BALANCE_FINISH));
    }
}