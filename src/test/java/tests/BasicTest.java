package tests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.ClickOptions;
import com.microsoft.playwright.Locator.FilterOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.LocatorAssertions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.MouseButton;

public class BasicTest {
   protected  Playwright playwright;
    protected Browser browser;
     private Page page;
     
   

    @Test(description = "Login to EventHub")
    public void testMethod() {
      
  
      page.navigate("https://eventhub.rahulshettyacademy.com/login");
        System.out.println(page.title());
        assertThat(page).hasTitle("EventHub — Discover \u0026 Book Events");
        page.getByLabel("Email").fill("cryptobullgear@gmail.com");
        page.getByPlaceholder("••••••").fill("iV!RBFaV5K5K@Sb");
        page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Sign In")).
           click(new ClickOptions().setDelay(200.00).setButton(MouseButton.LEFT));
     // post successfull login, verify the presence of the "Browse Events →" link
      assertThat(page.getByRole(AriaRole.LINK, new GetByRoleOptions().setName("Browse Events →"))).isVisible();
      page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Admin")).
           click(new ClickOptions().setTimeout(4000.00).setButton(MouseButton.LEFT));
               page.getByRole(AriaRole.NAVIGATION).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Manage Events")).isVisible();
      page.getByRole(AriaRole.NAVIGATION).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Manage Events")).
        click(new ClickOptions().setDelay(200.00).setButton(MouseButton.LEFT));

       //Event creation page
      page.getByPlaceholder("Event title").fill("Book Fair 2026");
      page.getByPlaceholder("Describe the event…").fill("Book Fair 2026");
      page.getByLabel("Category").selectOption("Concert");
      page.getByLabel("City").fill("Coimbatore");
      page.getByLabel("Venue").fill("Nandha Engineering College - Auditorium");
      page.getByLabel("Event Date & Time").fill("2026-10-15T19:00");
      page.getByLabel("Price ($)").fill("100");
      page.getByLabel("Total Seats").fill("500");
      page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("+ Add Event")).
      click();
      
      // Verify that the event was created successfully by checking for a success message or the presence of the new event in the list
      assertThat(page.getByText("Event created!")).
      isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout( 2000.00));

      //cards filter
      //Locator Nav = page.locator("#nav-events");
      //Nav.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Events")).click();
      page.locator("#nav-events", new Page.LocatorOptions().setHasText("Events")).click();
      //Locator listOfCards = page.locator("#event-card");
      Locator filteredCards = page.locator("#event-card").filter(new Locator.FilterOptions().setHasText("Book Fair 2026"));
      String NumberOfSeats = filteredCards.getByText("seats").innerText();
      System.out.println("Total Seats for Music Concert: " + NumberOfSeats);
      int seatsBeforeBooking = Integer.parseInt(NumberOfSeats.split(" ")[0]);

      //Concert booking
      filteredCards.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Book Now")).click();
      page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("+")).
      click(new ClickOptions().setClickCount(4));

      page.getByLabel("Full Name").fill("Prabhakaran");
      page.getByLabel("Email").fill("cryptobullgear@example.com");
      page.getByLabel("Phone Number").fill("9876543210");
      page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Confirm Booking")).click();

      // Verify that the booking was successful by checking for a success message or the presence of the booking in the list
      assertThat(page.getByText("Your tickets are reserved.")).isVisible();
      String BookingRef = page.locator(".booking-ref").innerText();
      System.out.println("Booking Reference: " + BookingRef);

      //View my bookings
      page.getByRole(AriaRole.LINK, new GetByRoleOptions().setName("View My Bookings")).click();
      assertThat(page.locator("#booking-card").filter(new FilterOptions().setHasText(BookingRef))).isVisible();

       page.locator("#nav-events", new Page.LocatorOptions().setHasText("Events")).click();
       page.waitForTimeout(3000);
       String NumberOfSeatsAfterBooking = page.locator("#event-card").filter(new Locator.FilterOptions().setHasText("Book Fair 2026")).getByText("seats").innerText();
       int seatsAfterBooking = Integer.parseInt(NumberOfSeatsAfterBooking.split(" ")[0]);
      
      // Verify that the number of seats has decreased after booking
      Assert.assertTrue(seatsAfterBooking < seatsBeforeBooking);
      }
    @AfterMethod 
    public void postTest(){
        page.isClosed();
    }
}


