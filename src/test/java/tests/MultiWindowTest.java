package tests;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;

public class MultiWindowTest {
    Playwright playwright;
    Browser browser;
    Page page1;
    BrowserContext context;
    String Username;
   
    @BeforeMethod(alwaysRun=true)
    public void setup() {
        // Setup code for multi-window testing
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false));
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
        page1 = context.newPage();
        page1.navigate("https://rahulshettyacademy.com/loginpagePractise/");
    }

   @Test(groups = {"regression"})
    public void childWindowTest() {
        // Click on the link that opens a new window
    
       Page childPage = context.waitForPage(()->page1.locator(".blinkingText").first().click());
        childPage.waitForTimeout(3000);
       String EmailId = childPage.locator(".red").textContent().split("at ")[1].split(" ")[0];
       page1.locator("#username").fill(EmailId);
       Username = page1.locator("#username").inputValue();
       page1.waitForTimeout(3000);
       System.out.println("Email ID extracted from child window: " + Username);
       
    }

  @Test(groups={"Regression"})
    public void signIn(){
        page1.locator("#username").fill("rahulshettyacademy");
        page1.locator("#password").fill("Learning@830$3mK2");
        page1.getByRole(AriaRole.RADIO, new GetByRoleOptions().setName(" User")).click();
        page1.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Okay")).click();
        page1.getByRole(AriaRole.COMBOBOX,new GetByRoleOptions()).selectOption("Teacher");
        
        page1.locator("#terms").check();
        Assert.assertTrue(page1.getByRole(AriaRole.CHECKBOX, new GetByRoleOptions()).isChecked());
        //Assert.assertTrue(page1.locator("#terms").isChecked());
        page1.waitForTimeout(5000);
        page1.locator("#signInBtn").click();
        page1.waitForTimeout(5000);
    }

    @AfterMethod 
    public void tearDown() {
        // Stop tracing and save the trace file
        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("logs.zip")));
        browser.close();
        playwright.close();
    }

}
