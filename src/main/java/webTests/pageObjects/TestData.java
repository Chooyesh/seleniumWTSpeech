package webTests.pageObjects;

import org.testng.annotations.DataProvider;

public class TestData {
    @DataProvider(name = "emailsAndExcepts")
    public final Object[][] emailsAndValids() {
        return new String[][]{
                {"у", "Адрес электронной почты должен содержать символ \"@\". В адресе \"у\" отсутствует символ \"@\"."},
                {"test@example.", "Недопустимое положение символа \".\" в адресе \"example.\"."},
                {"invalidemail", "Адрес электронной почты должен содержать символ \"@\". В адресе \"invalidemail\" отсутствует символ \"@\"."},
                {"@email.ru", "Введите часть адреса до символа \"@\". Адрес \"@email.ru\" неполный."},
                {"email@example", ""},
                {"рус@рус.ру", "Часть адреса до символа \"@\" не должна содержать символ \"р\"."},
                {"user@", "Введите часть адреса после символа \"@\". Адрес \"user@\" неполный."},
                {"user@@example.com", "Часть адреса после символа \"@\" не должна содержать символ \"@\"."},
        };
    }

    @DataProvider(name = "loginData")
    public final Object[][] loginData() {
        return new String[][]{
                {"email@email.ru", "user"}
        };
    }

    @DataProvider(name = "wrongLoginData")
    public final Object[][] wrongLoginData() {
        return new String[][]{
                {"ttooolong@ejao.ru", "ejao"}
        };
    }
    @DataProvider(name = "invalidUsernames")
    public final Object[][] getInvalidUsernames() {
        return new Object[][] {
                {"!@#$%&*"},
                {"user name"},
                {"username12345678901234567890"},
                {"username\\"},
                {""},
                {"username?"},
                {"username$123"},
                {"Русскиебуквы"}
        };
    }

    @DataProvider(name = "invalidPasswords")
    public Object[][] getInvalidPasswords() {
        return new Object[][] {
                {"123456"},
                {"password"},
                {"P@ssw"},
                {"password12345678901234567890"},
                {"пароль123"},
                {"!@#$%&*"},
                {"1234abcd"}
        };
    }

    @DataProvider(name = "goodDataToSignUp")
    public Object[][] getGoodDataToRSignup() {
        return new Object[][]{
                {"user@email.ru", "user","user"}
        };
    }
    @DataProvider(name = "alreadySignUp")
    public Object[][] getAalreadySignUp() {
        return new Object[][]{
                {"email@email.ru", "user","user"}
        };
    }
}
