package tests;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.ClickOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Route;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.MouseButton;


public class ApiMockUsingRoute {
   protected  Playwright playwright;
    protected Browser browser;
     private Page page;
     
    @BeforeMethod 
    public void setup() {
       playwright = Playwright.create();
       PlaywrightAssertions.setDefaultAssertionTimeout(7000);
       browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false));
       page = browser.newPage();
    }

    @Test 
    public void Mock(){
      page.navigate("https://eventhub.rahulshettyacademy.com/login");
      System.out.println(page.title());
      assertThat(page).hasTitle("EventHub — Discover \u0026 Book Events");
      page.getByLabel("Email").fill("cryptobullgear@gmail.com");
      page.getByPlaceholder("••••••").fill("iV!RBFaV5K5K@Sb");
      page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Sign In")).
           click(new ClickOptions().setDelay(200.00).setButton(MouseButton.LEFT));
      assertThat(page.getByRole(AriaRole.LINK, new GetByRoleOptions().setName("Browse Events →"))).isVisible();
      
      //verification of cards and banner with mock data
      page.route("**/api/events**", route->route.fulfill( new Route.FulfillOptions().
      setPath(Paths.get("src/test/java/resources/events_4.json"))));

      page.navigate("https://eventhub.rahulshettyacademy.com/events");
      page.waitForTimeout(7000);

      
      Locator NoOfCards = page.locator("#event-card");
      assertThat(NoOfCards.first()).isVisible();
      Assert.assertEquals(NoOfCards.count(), 4);

      page.locator(".mx-1");
      assertThat(page.locator(".mx-1")).isHidden();

      //with original data
      page.route("**/api/events**", route->route.fulfill( new Route.FulfillOptions().
      setPath(Paths.get("src/test/java/resources/events_6.json"))));

      page.navigate("https://eventhub.rahulshettyacademy.com/events");
      page.waitForTimeout(7000);

      
      Locator NoOfCards1 = page.locator("#event-card");
      assertThat(NoOfCards1.first()).isVisible();
      Assert.assertEquals(NoOfCards1.count(), 7);

      assertThat(page.locator(".mx-1").first()).isVisible();
       }
}