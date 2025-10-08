import io.github.bonigarcia.wdm.WebDriverManager; 
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions; // ⬅️ NEW IMPORT
import org.testng.Assert;
import org.testng.annotations.AfterMethod; 
import org.testng.annotations.BeforeMethod; 
import org.testng.annotations.Test;

public class GoogleTest {

    private WebDriver driver;

    @BeforeMethod
    public void setupTest() {
        // 1. Setup ChromeDriver using WebDriverManager (This is correct)
        WebDriverManager.chromedriver().setup(); 
        
        // 2. 🔑 CRITICAL FIX: Configure ChromeOptions for CI/headless environment
        ChromeOptions options = new ChromeOptions();
        
        // Use 'headless' mode since you are on a CI server (no visible screen)
        options.addArguments("--headless=new"); 
        
        // Essential arguments for CI environments to prevent the 'user data directory' error
        options.addArguments("--no-sandbox"); 
        options.addArguments("--disable-dev-shm-usage"); 
        options.addArguments("--window-size=1920,1080"); // Set a fixed, large resolution
        
        // 3. Initialize the driver with the configured options
        driver = new ChromeDriver(options);
        
        // Optional: Maximize is usually redundant when setting window size, but harmless.
        // driver.manage().window().maximize(); 
    }
    
    @Test
    public void testGoogleSearchForSelenium() {
        // 1. Navigate to Google
        driver.get("https://www.google.com");
        
        // Optional: Assert that the title is as expected
        String actualTitle = driver.getTitle();
        Assert.assertTrue(actualTitle.contains("Google"), "Page title is not correct.");
        
        // 2. Locate the Search Bar
        WebElement searchBox = driver.findElement(By.name("q"));
        
        // 3. Type "selenium" into the search bar
        String searchTerm = "selenium";
        searchBox.sendKeys(searchTerm);
        
        // 4. Submit the search by pressing the ENTER key
        searchBox.sendKeys(Keys.ENTER);
        
        // 5. Verification (Assert that the search was successful)
        String resultsPageTitle = driver.getTitle();
        System.out.println("Results Page Title: " + resultsPageTitle);
        
        Assert.assertTrue(resultsPageTitle.contains(searchTerm), 
                          "Verification Failed: Results page title does not contain the search term.");
    }

    @AfterMethod
    public void teardownTest() {
        // Close the browser after the test completes
        if (driver != null) {
            driver.quit();
        }
    }
}
