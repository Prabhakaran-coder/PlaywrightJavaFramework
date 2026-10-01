package tests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

public class AutomationPractice {
     protected  Playwright playwright;
    protected Browser browser;
     private Page page;
     
    @BeforeMethod(alwaysRun = true)
    public void setup() {
       playwright = Playwright.create();
       PlaywrightAssertions.setDefaultAssertionTimeout(7000);
       browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false));
       page = browser.newPage();
    }

    @Test(groups={"regression"})
    public void testMethod() {
        
      page.navigate("https://rahulshettyacademy.com/AutomationPractice/");
      System.out.println(page.title());
      page.getByPlaceholder("Hide/Show Example").isVisible();
      page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Hide")).click();
      page.getByPlaceholder("Hide/Show Example").isHidden();
      page.waitForTimeout(3000);
      page.getByRole(AriaRole.BUTTON,new GetByRoleOptions().setName("Alert")).click();
      page.onDialog((dialog) -> dialog.accept());
      page.getByRole(AriaRole.BUTTON,new GetByRoleOptions().setName("Confirm")).click();
      page.onDialog((dialog) -> dialog.accept());

      page.locator("#mousehover").hover();

    }
    @AfterMethod 
     public void postTest(){
        page.isClosed();
    }
}
