import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditCardTest {
    private WebDriver driver;
    private  WebDriverManager driverManager;

    @BeforeAll
    public static void setupAll() {
      WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void AfterEach() {
        driver.quit();
        driver = null;

    }
    @Test //1
    void shouldPositiveTest() throws InterruptedException {
        // загрузить страницу
        driver.get("http://localhost:9999");
        // поиск элементов
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петрова Мария");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79782226677");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);

    }

    @Test
        //2 поле имя осталось пустым
    void shouldNegativeNameInputEmpty() throws InterruptedException {
        // загрузить страницу
        driver.get("http://localhost:9999");
        // поиск элементов
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79782226677");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
//        var actualText = driver.findElement(By.xpath(<span class="input input_type_text input_view_default input_size_m input_width_available input_has-label input_invalid input_theme_alfa-on-white" data-test-id='name'>")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);

    }

    @Test
        //3 пустое поле "телефон"
    void shouldInvalidPhoneInputEmpty() throws InterruptedException {
        // загрузить страницу
        driver.get("http://localhost:9999");
        // поиск элементов
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петрова Мария");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);

    }

    @Test
        //4 не отмечена галочка на чекбоксе
    void shouldNegativeUnCheckedCheckbox() throws InterruptedException {
        // загрузить страницу
        driver.get("http://localhost:9999");
        // поиск элементов
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петрова Мария");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79782226677");
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText().trim();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", actualText);

        assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());
    }

    @Test
        // 5 введён некорректрный номер телефона
    void shouldUncorrectPhoneInput() throws InterruptedException {
        // загрузить страницу
        driver.get("http://localhost:9999");
        // поиск элементов
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петрова Мария");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("Мария");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
//        var actualText = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
    }

}