package Pages;

import com.microsoft.playwright.Locator.ClickOptions;
import com.microsoft.playwright.Locator.FilterOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;


public class BookAEvent {
    String BookingRef;
    Page page;

    private static final String FULL_NAME_LABEL = "Full Name";
    private static final String EMAIL_LABEL = "Email";
    private static final String PHONE_NUMBER_LABEL = "Phone Number";
     private static final String CONFIRM_BOOKING_LABEL = "Confirm Booking";
    public BookAEvent(Page page){
        this.page=page;
    }

    public void EventBooking(String Name,String Email,String PhoneNumber){
      page.waitForTimeout(10000);
    page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("+")).
      click(new ClickOptions().setClickCount(4));
      page.getByLabel(FULL_NAME_LABEL).fill(Name);
      page.getByLabel(EMAIL_LABEL).fill(Email);
      page.getByLabel(PHONE_NUMBER_LABEL).fill(PhoneNumber);
      page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName(CONFIRM_BOOKING_LABEL)).click();

      // Verify that the booking was successful by checking for a success message or the presence of the booking in the lis
    }

    public void EventBookingVerification(){
      assertThat(page.getByText("Your tickets are reserved.")).isVisible();
       BookingRef = page.locator(".booking-ref").innerText();
      System.out.println("Booking Reference: " + BookingRef);
    }

    public void viewBookings(){
        page.getByRole(AriaRole.LINK, new GetByRoleOptions().setName("View My Bookings")).click();
        assertThat(page.locator("#booking-card").filter(new FilterOptions().setHasText(BookingRef))).isVisible();
    }

}
