package tests;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class TestBase {
    Playwright playwright;
    Browser browser;
    Page page;
    String Base_url;
    
    @BeforeMethod(alwaysRun = true) 
    public void Base() throws IOException{
         Properties prop = new Properties();
         FileInputStream fis = new FileInputStream("src/test/java/resources/objects.properties");
         prop.load(fis);
         
        // Base_url=prop.getProperty("qa.test");
        playwright = Playwright.create();
        PlaywrightAssertions.setDefaultAssertionTimeout(7000);
        String  envName = System.getProperty("env")!=null?System.getProperty("env"): prop.getProperty("env");
        Base_url =prop.getProperty(envName+".test");
         String BrowserName = System.getProperty("browser")!=null?System.getProperty("browser"): prop.getProperty("browser");
         browser = switch (BrowserName) {
            // case "firefox" -> playwright.firefox().launch(new LaunchOptions().setHeadless(false));
            // case "safari" -> playwright.webkit().launch(new LaunchOptions().setHeadless(false));
            // default -> playwright.chromium().launch(new LaunchOptions().setHeadless(false));
            case "firefox" -> playwright.firefox().launch();
            case "safari" -> playwright.webkit().launch();
            default -> playwright.chromium().launch();
        };
    
       page = browser.newPage();
       
    }

    
}
