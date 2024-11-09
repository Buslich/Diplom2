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
    @DisplayName("Should approved card payment by credit")
    void shouldCreditPaymentApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(cardinfo);
        form.paymentApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    @DisplayName("Should approved card payment")
    void shouldCardPaymentApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(cardinfo);
        form.paymentApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Should declined payment by credit")
    void shouldCreditPaymentDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(cardinfo);
        form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    @DisplayName("Should declined payment")
    void shouldCardPaymentDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(cardinfo);
        form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    //Негативные сценарии формы "Оплата по карте"

    //Заполнение поля «Номер карты» номером, состоящим из 1 цифры
    @Test
    public void shouldCardNumberOfOneDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfOneDigit());
        form.incorrectCardNumberVisible();
    }

    //заполнение поля "номер карты" 2мя цифрами
    @Test
    public void shouldCardNumberOfTwoDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfTwoDigits());
        form.incorrectCardNumberVisible();
    }

    //заполнение поля "номер карты" 15ю цифрами
    @Test
    public void shouldCardNumberOfFifteenDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfFifteenDigits());
        form.incorrectCardNumberVisible();
    }

    //заполнение поля "номер карты" 17ю цифрами
    @Test
    public void shouldCardNumberOfSeventeenDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfSeventeenDigits());
        form.paymentDeclined();
    }

    //заполнение поля "номер карты" спец. символами
    @Test
    public void shouldCardWithSpecialSymbols() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithSpecialSymbols());
        form.incorrectCardNumberVisible();
    }

    //заполнение поля "номер карты" символами кириллицы
    @Test
    public void shouldCardWithCyrillic() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithCyrillic());
        form.incorrectCardNumberVisible();
    }

    //заполнение поля "номер карты" латиницей
    @Test
    public void shouldCardWithLatin() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithLatin());
        form.incorrectCardNumberVisible();
    }

    //не заполнение номера карты
    @Test
    public void shouldCardIfEmpty() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }

    @Test
    public void shouldMonthIfNotExist() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    //несуществующий месяц в пределах граничных значений
    @Test
    public void shouldMonthIfNotExistBoundary() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthIfNotExistBoundary());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    //месяц равный двум нулям
    @Test
    public void shouldMonthDoubleZero() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthDoubleZero());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц из 3 цифр
    @Test
    public void shouldMonthOfThreeDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthOfThreeDigits());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц из 1 цифры
    @Test
    public void shouldMonthOfOneDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthOfOneDigit());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц, с использованием специальных символов
    @Test
    public void shouldMonthWithSpecialSymbols() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithSpecialSymbols());
        form.incorrectMonthVisible("Неверный формат");
    }

    //указание прошедшего года
    @Test
    public void shouldLastYear() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    //указание года на 25 лет превышающий текущий
    @Test
    public void shouldYear25YearsMore() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYear25YearsMore());
        form.incorrectYearVisible("Неверно указан срок действия карты");
    }

    //год из 1 цифры
    @Test
    public void shouldYearOfOneDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearOfOneDigit());
        form.incorrectYearVisible("Неверный формат");
    }

    //год из 3 цифр
    @Test
    public void shouldYearOfThreeDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearOfThreeDigits());
        form.incorrectYearVisible("Неверный формат");
    }

    //год со значением равным нулю
    @Test
    public void shouldYearIfZero() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearIfZero());
        form.incorrectYearVisible("Неверный формат");
    }

    //год с использованием специальных символов
    @Test
    public void shouldYearWithSpecialSymbols() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithSpecialSymbols());
        form.incorrectYearVisible("Неверный формат");
    }

    //поле "Владелец", состоящее из 1 буквы
    @Test
    public void shouldHolderOfOneLetter() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderOfOneLetter());
        form.incorrectHolderVisible();
    }

    //поле "Владелец", состоящее из 60 букв
    @Test
    public void shouldHolderOfSixtyLetters() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderOfSixtyLetters());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" кириллицей
    @Test
    public void shouldHolderWithCyrillic() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithCyrillic());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" цифрами
    @Test
    public void shouldHolderWithDigits() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" специальными символами
    @Test
    public void shouldHolderSpecialSymbols() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithSpecialSymbols());
        form.incorrectHolderVisible();
    }

    //не заполнение поля "Владелец"
    @Test
    public void shouldHolderIfEmpty() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible();
    }

    //код из 1 цифры
    @Test
    public void shouldCVCCVVOnOneDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnOneDigit());
        form.incorrectCodeVisible();
    }

    //код из 2 цифр
    @Test
    public void shouldCVCCVVOnTwoDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnTwoDigits());
        form.incorrectCodeVisible();
    }

    //код из 4 цифр
    @Test
    public void shouldCVCCVVOnFourDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnFourDigits());
        form.incorrectCodeVisible();
    }

    //код из 5 цифр
    @Test
    public void shouldCVCCVVOnFiveDigit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnFiveDigits());
        form.incorrectCodeVisible();
    }

    //Незаполнение формы
    @Test
    void shouldFormIfEmpty() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCard();
        var form = new PaymentPage();
        form.emptyForm();
    }

//Негативные сценарии для формы «Кредит по данным карты»

    //Заполнение поля «Номер карты» номером, состоящим из 1 цифры
    @Test
    public void shouldCardNumberOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfOneDigit());
        form.incorrectCardNumberVisible();
    }

    //Заполнение поля «Номер карты» номером, состоящим из 2 цифр
    @Test
    public void shouldCardNumberOfTwoDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfTwoDigits());
        form.incorrectCardNumberVisible();
    }

    //Заполнение поля «Номер карты» номером, состоящим из 15 цифр
    @Test
    public void shouldCardNumberOfFifteenDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfFifteenDigits());
        form.incorrectCardNumberVisible();
    }

    //Заполнение поля «Номер карты» номером, состоящим из 17 цифр
    @Test
    public void shouldCardNumberOfSeventeenDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfSeventeenDigits());
        form.paymentDeclined();
    }

    //Заполнение поля «Номер карты» специальными символами
    @Test
    public void shouldCardWithSpecialSymbolsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithSpecialSymbols());
        form.incorrectCardNumberVisible();
    }

    //Заполнение поля «Номер карты» значением на кириллице
    @Test
    public void shouldCardWithCyrillicByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithCyrillic());
        form.incorrectCardNumberVisible();
    }

    //Заполнение поля «Номер карты» значением на латинице
    @Test
    public void shouldCardWithLatinByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithLatin());
        form.incorrectCardNumberVisible();
    }

    //Не заполнение поля «Номер карты»
    @Test
    public void shouldCardIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }

    //Заполнение поля «Месяц» несуществующим месяцем, в пределах граничных значений
    @Test
    public void shouldMonthIfNotExistByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    //Заполнение поля «Месяц» значением равным двум нулям
    @Test
    public void shouldMonthDoubleZeroByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthDoubleZero());
        form.incorrectMonthVisible("Неверный формат");
    }

    //Заполнение поля «Месяц» значением из 3 цифр
    @Test
    public void shouldMonthOfThreeDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthOfThreeDigits());
        form.incorrectMonthVisible("Неверный формат");
    }

    //Заполнение поля «Месяц» значением из 1 цифры
    @Test
    public void shouldMonthOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthOfOneDigit());
        form.incorrectMonthVisible("Неверный формат");
    }

    //Заполнение поля «Месяц» специальными символами
    @Test
    public void shouldMonthWithSpecialSymbolsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithSpecialSymbols());
        form.incorrectMonthVisible("Неверный формат");
    }

    //Заполнение поля «Год» значением прошедшего года
    @Test
    public void shouldLastYearByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    //Заполнение поля «Год» значением, на 25 лет превышающего текущий год
    @Test
    public void shouldYear25YearsMoreByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYear25YearsMore());
        form.incorrectYearVisible("Неверно указан срок действия карты");
    }

    //Заполнение поля «Год» значением из 1 цифры
    @Test
    public void shouldYearOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearOfOneDigit());
        form.incorrectYearVisible("Неверный формат");
    }

    //Заполнение поля «Год» значением из 3 цифр
    @Test
    public void shouldYearOfThreeDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearOfThreeDigits());
        form.incorrectYearVisible("Неверный формат");
    }

    // Заполнение поля «Год» значением, равным нулю
    @Test
    public void shouldYearIfZeroByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearIfZero());
        form.incorrectYearVisible("Неверный формат");
    }

    //Заполнение поля «Год» специальными символами
    @Test
    public void shouldYearWithSpecialSymbolsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearWithSpecialSymbols());
        form.incorrectYearVisible("Неверный формат");
    }

    //Заполнение поля «Владелец» значением из 1 буквы
    @Test
    public void shouldHolderOfOneLetterByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderOfOneLetter());
        form.incorrectHolderVisible();
    }

    //Заполнение поля «Владелец» значением из 60 букв
    @Test
    public void shouldHolderOfSixtyLettersByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderOfSixtyLetters());
        form.incorrectHolderVisible();
    }

    //Заполнение поля «Владелец» значением на кириллице
    @Test
    public void shouldHolderWithCyrillicByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithCyrillic());
        form.incorrectHolderVisible();
    }

    //Заполнение поля «Владелец» цифрами
    @Test
    public void shouldHolderWithDigitsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible();
    }

    //Заполнение поля «Владелец» специальными символами
    @Test
    public void shouldHolderSpecialSymbolsByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithSpecialSymbols());
        form.incorrectHolderVisible();
    }

    //Не заполнение поля «Владелец»
    @Test
    public void shouldHolderIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible();
    }

    //Заполнение поля «CVC/CVV» значением из 1 цифры
    @Test
    public void shouldCVCCVVOnOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnOneDigit());
        form.incorrectCodeVisible();
    }

    //Заполнение поля «CVC/CVV» значением из 2 цифр
    @Test
    public void shouldCVCCVVOnTwoDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnTwoDigits());
        form.incorrectCodeVisible();
    }

    //Заполнение поля «CVC/CVV» значением из 4 цифр
    @Test
    public void shouldCVCCVVOnFourDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnFourDigits());
        form.incorrectCodeVisible();
    }

    //Заполнение поля «CVC/CVV» значением из 5 цифр
    @Test
    public void shouldCVCCVVOnFiveDigitByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnFiveDigits());
        form.incorrectCodeVisible();
    }

    // Не заполнение формы «Оплата по карте»
    @Test
    void shouldFormIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        purchasepage.buyByCreditCard();
        var form = new CreditPage();
        form.emptyForm();
    }
}










