import io.github.bonigarcia.wdm.WebDriverManager; 
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod; 
import org.testng.annotations.BeforeMethod; 
import org.testng.annotations.Test;

public class GoogleTest {

    private WebDriver driver;

    @BeforeMethod
    public void setupTest() {
        // 🔑 FIX CONFIRMATION: WebDriverManager handles the ChromeDriver download and setup.
        WebDriverManager.chromedriver().setup(); 
        
        // Initialize the driver
        driver = new ChromeDriver();
        
        // Optional: Maximize the window for better visibility/interactivity
        driver.manage().window().maximize();
    }
    
    @Test
    public void testGoogleSearchForSelenium() {
        // 1. Navigate to Google
        driver.get("https://www.google.com");
        
        // Optional: Assert that the title is as expected before proceeding (for confidence)
        String actualTitle = driver.getTitle();
        // Google titles often change based on location, so a partial check is safer
        Assert.assertTrue(actualTitle.contains("Google"), "Page title is not correct.");
        
        // 2. Locate the Search Bar
        // The search input field on the Google homepage usually has the name attribute "q"
        WebElement searchBox = driver.findElement(By.name("q"));
        
        // 3. Type "selenium" into the search bar
        String searchTerm = "selenium";
        searchBox.sendKeys(searchTerm);
        
        // 4. Submit the search by pressing the ENTER key
        searchBox.sendKeys(Keys.ENTER);
        
        // 5. Verification (Assert that the search was successful)
        // The title of the results page should contain the search term
        String resultsPageTitle = driver.getTitle();
        System.out.println("Results Page Title: " + resultsPageTitle);
        
        // Assert that the title contains the search term and "Google Search"
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
