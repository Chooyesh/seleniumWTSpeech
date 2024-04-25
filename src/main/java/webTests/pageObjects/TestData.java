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
                {"ejao@ejao.ru", "ejao"}
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
}
