import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class CreditCardTest {
    private WebDriver driver;
    private  WebDriverManager;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    public void beforeEach() {
        СhromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");

    }

@AfterEach
    public void AfterEach() {
        driver.quit();
        driver = null;

    }
    @Test
    void shouldTest() {
        driver.get("http://localhost:9999");
        Thread.sleep(5000);
    }


}
