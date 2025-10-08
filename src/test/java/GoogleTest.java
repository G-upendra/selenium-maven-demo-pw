import io.github.bonigarcia.wdm.WebDriverManager; // ⬅️ Import this
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
// ... other necessary imports ...

public class GoogleTest {

    private WebDriver driver;

    // Example setup method (e.g., @BeforeEach in JUnit 5)
    public void setupTest() {
        // 🔑 THE CRITICAL FIX: WebDriverManager downloads and configures the 
        // correct ChromeDriver binary path. This replaces the need for the GitHub Action.
        WebDriverManager.chromedriver().setup(); 
        
        // Initialize the driver using the configured binary
        driver = new ChromeDriver();
    }
    
    // Example test method
    // @Test
    public void testGoogleSearch() {
        driver.get("https://www.google.com");
        // ... rest of your test logic ...
    }

    // Example teardown method (e.g., @AfterEach in JUnit 5)
    public void teardownTest() {
        if (driver != null) {
            driver.quit();
        }
    }
}
