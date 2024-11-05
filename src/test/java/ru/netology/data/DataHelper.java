package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import static java.lang.String.format;

public class DataHelper {
    public static final Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static String getApprovedCardNumber() {
        return ("4444 4444 4444 4441");
    }

    public static String getDeclinedCardNumber() {
        return ("4444 4444 4444 4442");
    }

    public static String getValidMonth() {
        LocalDate localDate = LocalDate.now();
        return format("%02d", localDate.getMonthValue());
    }

    public static String getValidYear() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getValidHolder() {
        return faker.name().firstName();
    }

    public static String getValidCVCCVV() {
        int result = (int) (Math.random() * 999) + 1;
        return String.format("%03d", result);
    }

    //Поле "Номер карты":

    //Заполнение поля номером, состоящим из 1 цифры
    public static CardInfo getCardNumberOfOneDigit() {
        return new CardInfo("2", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля номером, состоящим из 2 цифр
    public static CardInfo getCardNumberOfTwoDigits() {
        return new CardInfo("21", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля номером, состоящим из 5 цифр
    public static CardInfo getCardNumberOfFiveDigits() {
        return new CardInfo("21111", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля номером, состоящим из 15 цифр
    public static CardInfo getCardNumberOfFifteenDigits() {
        return new CardInfo("444444444444444", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля номером, состоящим из 17 цифр
    public static CardInfo getCardNumberOfSeventeenDigits() {
        return new CardInfo("44444444444444414", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля номером, состоящим из 18 цифр
    public static CardInfo getCardNumberOfEighteenDigits() {
        return new CardInfo("444444444444444144", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля номером, не зарегистрированным в базе данных
    public static CardInfo getCardNumberNotRegistered() {
        return new CardInfo("1234567891011121", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля с использованием специальных символов
    public static CardInfo getCardNumberWithSpecialSymbols() {
        return new CardInfo("@#&", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля кириллицей
    public static CardInfo getCardNumberWithCyrillic() {
        return new CardInfo("номер", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля латиницей
    public static CardInfo getCardNumberWithLatin() {
        return new CardInfo("number", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля арабской вязью
    public static CardInfo getCardNumberWithArabicLigature() {
        return new CardInfo("رقم", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля иероглифами
    public static CardInfo getCardNumberWithHieroglyphs() {
        return new CardInfo("号码", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Не заполнение поля
    public static CardInfo getCardNumberIfEmpty() {
        return new CardInfo("", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Поле "Месяц":

    //Заполнение поля несуществующим месяцем
    public static CardInfo getMonthIfNotExist() {
        return new CardInfo(getApprovedCardNumber(), "25", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля несуществующим месяцем в пределах граничных значений
    public static CardInfo getMonthIfNotExistBoundary() {
        return new CardInfo(getApprovedCardNumber(), "13", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением, равным двум нулям
    public static CardInfo getMonthDoubleZero() {
        return new CardInfo(getApprovedCardNumber(), "00", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением из 3 цифр
    public static CardInfo getMonthOfThreeDigits() {
        return new CardInfo(getApprovedCardNumber(), "123", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением из 1 цифры
    public static CardInfo getMonthOfOneDigit() {
        return new CardInfo(getApprovedCardNumber(), "4", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля с использованием специальных символов
    public static CardInfo getMonthWithSpecialSymbols() {
        return new CardInfo(getApprovedCardNumber(), "@#", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля кириллицей
    public static CardInfo getMonthWithCyrillic() {
        return new CardInfo(getApprovedCardNumber(), "месяц", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля латиницей
    public static CardInfo getMonthWithLatin() {
        return new CardInfo(getApprovedCardNumber(), "month", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля арабской вязью
    public static CardInfo getMonthWithArabicLigature() {
        return new CardInfo(getApprovedCardNumber(), "شهر", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля иероглифами
    public static CardInfo getMonthWithHieroglyphs() {
        return new CardInfo(getApprovedCardNumber(), "月", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Не заполнение поля
    public static CardInfo getMonthIfEmpty() {
        return new CardInfo(getApprovedCardNumber(), "", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Поле "Год":

    //Заполнение поля значением прошедшего года
    public static CardInfo getLastYear() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "22", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением, на 25 лет превышающего текущий год
    public static CardInfo getYear25YearsMore() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "49", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением из 1 цифры
    public static CardInfo getYearOfOneDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "2", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением из 3 цифр
    public static CardInfo getYearOfThreeDigits() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "202", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением, равным нулю
    public static CardInfo getYearIfZero() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "0", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля с использованием специальных символов
    public static CardInfo getYearWithSpecialSymbols() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "@#", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля кириллицей
    public static CardInfo getYearWithCyrillic() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "год", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля латиницей
    public static CardInfo getYearWithLatin() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "year", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля арабской вязью
    public static CardInfo getYearWithArabicLigature() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "سنة", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля иероглифами
    public static CardInfo getYearWithHieroglyphs() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "年", getValidHolder(), getValidCVCCVV());
    }

    //Не заполнение поля
    public static CardInfo getYearIfEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "", getValidHolder(), getValidCVCCVV());
    }

    //Поле "Владелец":

    //Заполнение поля значением из 1 буквы
    public static CardInfo getHolderOfOneLetter() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "N", getValidCVCCVV());
    }

    //Заполнение поля значением из 60 букв
    public static CardInfo getHolderOfSixtyLetters() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Mariiiiiiiiiiiiiiiiiiiiiiiiiiirrrrrrrrrrrrrrrrrrrriiiiiaaaaa Bond", getValidCVCCVV());
    }

    //Заполнение поля кирилицей
    public static CardInfo getHolderWithCyrillic() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Мария Бонд", getValidCVCCVV());
    }

    //Заполнение поля цифрами
    public static CardInfo getHolderWithDigits() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "123", getValidCVCCVV());
    }

    //Заполнение поля с использованием специальных символов
    public static CardInfo getHolderWithSpecialSymbols() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "@#", getValidCVCCVV());
    }

    //Не заполнение поля
    public static CardInfo getHolderIfEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "", getValidCVCCVV());
    }

    //Поле "CVC/CVV":

    //Заполнение поля значением из 1 цифры
    public static CardInfo getCVCCVVOnOneDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "1");
    }

    //Заполнение поля значением из 2 цифр
    public static CardInfo getCVCCVVOnTwoDigits() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "12");
    }

    //Заполнение поля значением из 4 цифр
    public static CardInfo getCVCCVVOnFourDigits() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "1234");
    }

    //Заполнение поля значением из 5 цифр
    public static CardInfo getCVCCVVOnFiveDigits() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "12345");
    }

    //Заполнение поля с использованием специальных символов
    public static CardInfo getCVCCVVWithSpecialSymbols() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "@#&");
    }

    //Заполнение поля кириллицей
    public static CardInfo getCVCCVVWithCyrillic() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "номер");
    }

    //Заполнение поля латиницей
    public static CardInfo getCVCCVVWithLatin() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "number");
    }

    //Заполнение поля арабской вязью
    public static CardInfo getCVCCVVWithArabicLigature() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "رقم");
    }

    //Заполнение поля иероглифами
    public static CardInfo getCVCCVVWithHieroglyphs() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "号码");
    }

    //Не заполнение поля
    public static CardInfo getCVCCVVIfEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "");
    }

    //Реквизиты карты:
    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String holder;
        String CVCCVV;
    }
}