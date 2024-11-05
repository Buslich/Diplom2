package ru.netology.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.page.CreditPage;
import ru.netology.page.PaymentPage;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
        SQLHelper.clearPaymentTable();
        SQLHelper.clearCreditTable();
    }
    @Test
    void shouldPurchaseSuccessfullyWithValidCard() {
        var validCardInfo = DataHelper.getValidCardInfo();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(validCardInfo);
        paymentPage.submit();
        paymentPage.waitForSuccessMessage("Успешная покупка");
    }
    @Test
    void shouldFailPurchaseWithDeclinedCard() {
        var declinedCardInfo = DataHelper.getDeclinedCardInfo();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(declinedCardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Операция отклонена");
    }
    @Test
    void shouldFailWithExpiredCard() {
        var expiredCardInfo = DataHelper.getExpiredCardInfo();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(expiredCardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Истек срок действия карты");
    }
    @Test
    void shouldFailWhenAllFieldsAreEmpty() {
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(new CardInfo("", "", "", "", ""));
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Поле обязательно для заполнения");
    }
    @Test
    void shouldFailWithInvalidMonthFormat() {
        var cardInfo = DataHelper.getCardInfoWithInvalidMonth();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный формат месяца");
    }
    @Test
    void shouldFailWithInvalidYearFormat() {
        var cardInfo = DataHelper.getCardInfoWithInvalidYear();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный формат года");
    }
    @Test
    void shouldFailWithNumericCardHolderName() {
        var cardInfo = DataHelper.getNumericCardHolderInfo();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный формат имени владельца");
    }
    @Test
    void shouldFailWithInvalidCVC() {
        var cardInfo = DataHelper.getCardInfoWithInvalidCVC();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный формат CVC");
    }
    @Test
    void shouldFailWithInvalidCardNumber() {
        var cardInfo = DataHelper.getInvalidCardNumberInfo();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный формат номера карты");
    }
    @Test
    void shouldFailWithEmptyCardNumber() {
        var cardInfo = DataHelper.getCardInfoWithEmptyCardNumber();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Поле обязательно для заполнения");
    }
    @Test
    void shouldFailWithInvalidMonthAboveLimit() {
        var cardInfo = DataHelper.getCardInfoWithMonthAboveLimit();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный месяц");
    }
    @Test
    void shouldFailWithIncorrectCardNumberLength() {
        var cardInfo = DataHelper.getIncorrectCardNumberLength();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный формат номера карты");
    }
    @Test
    void shouldFailWithExpiredYear() {
        var cardInfo = DataHelper.getCardInfoWithExpiredYear();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Истёк срок действия карты");
    }
    @Test
    void shouldFailWithMonthZero() {
        var cardInfo = DataHelper.getCardInfoWithMonthZero();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный месяц");
    }
    @Test
    void shouldFailWithEmptyCVC() {
        var cardInfo = DataHelper.getCardInfoWithEmptyCVC();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Поле обязательно для заполнения");
    }
    @Test
    void shouldFailWithEmptyMonth() {
        var cardInfo = DataHelper.getCardInfoWithEmptyMonth();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Поле обязательно для заполнения");
    }
    @Test
    void shouldFailWithYearZero() {
        var cardInfo = DataHelper.getCardInfoWithYearZero();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный год");
    }
    @Test
    void shouldFailWithEmptyYear() {
        var cardInfo = DataHelper.getCardInfoWithEmptyYear();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Поле обязательно для заполнения");
    }
    @Test
    void shouldFailWithCVCZero() {
        var cardInfo = DataHelper.getCardInfoWithCVCZero();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный код CVC");
    }
    @Test
    void shouldFailWithIncorrectCardNumberFormat() {
        var cardInfo = DataHelper.getCardInfoWithIncorrectCardNumberFormat();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный формат номера карты");
    }
    @Test
    void shouldFailWithEmptyCardNumber() {
        var cardInfo = DataHelper.getCardInfoWithEmptyCardNumber();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Поле обязательно для заполнения");
    }
    @Test
    void shouldFailWithEmptyCardholder() {
        var cardInfo = DataHelper.getCardInfoWithEmptyCardholder();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Поле обязательно для заполнения");
    }
    @Test
    void shouldFailWithShortCardholderName() {
        var cardInfo = DataHelper.getCardInfoWithShortCardholderName();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Недостаточная длина имени");
    }
    @Test
    void shouldFailWithMonthZero() {
        var cardInfo = DataHelper.getCardInfoWithMonthZero();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный месяц");
    }
    @Test
    void shouldFailWithYearZero() {
        var cardInfo = DataHelper.getCardInfoWithYearZero();
        var paymentPage = new PaymentPage();
        paymentPage.enterCardData(cardInfo);
        paymentPage.submit();
        paymentPage.waitForErrorMessage("Неверный год");
    }

}
