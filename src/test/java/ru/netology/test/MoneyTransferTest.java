package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.date.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.netology.date.DataHelper.generateInvalidAmount;
import static ru.netology.date.DataHelper.generateValidAmount;

class MoneyTransferTest {
    private static final String SERVICE_URL = "http://localhost:9999";
    private static final String INSUFFICIENT_FUNDS_MESSAGE = "Недостаточно средств для перевода";

    private DashboardPage dashboardPage;
    private DataHelper.CardInfo firstCardInfo;
    private DataHelper.CardInfo secondCardInfo;
    private int firstCardInitialBalance;
    private int secondCardInitialBalance;

    @BeforeEach
    void setup() {
        var loginPage = open(SERVICE_URL, LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);

        firstCardInfo = DataHelper.getFirstCardInfo();
        secondCardInfo = DataHelper.getSecondCardInfo();

        // Получаем актуальные балансы
        firstCardInitialBalance = dashboardPage.getCardBalance(firstCardInfo);
        secondCardInitialBalance = dashboardPage.getCardBalance(secondCardInfo);
    }


    @Test
    @DisplayName("Успешный перевод средств с первой карты на вторую")
    void shouldTransferFromFirstToSecond() {
        int amount = generateValidAmount(firstCardInitialBalance);
        var expectedBalanceFirstCard = firstCardInitialBalance - amount;
        var expectedBalanceSecondCard = secondCardInitialBalance + amount;

        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        dashboardPage.reloadDashboardPage();

        assertAll(
                () -> dashboardPage.checkCardBalance(firstCardInfo, expectedBalanceFirstCard),
                () -> dashboardPage.checkCardBalance(secondCardInfo, expectedBalanceSecondCard)
        );
    }

}