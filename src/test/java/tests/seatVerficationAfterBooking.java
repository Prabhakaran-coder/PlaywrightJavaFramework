package tests;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class seatVerficationAfterBooking {

    Page page;

    public seatVerficationAfterBooking(Page page){
        this.page=page;
    }

    public int verifySeatsPostBooking(String title){
        page.locator("#nav-events", new Page.LocatorOptions().setHasText("Events")).click();
       page.waitForTimeout(3000);
       String NumberOfSeatsAfterBooking = page.locator("#event-card").filter(new Locator.FilterOptions().setHasText(title)).getByText("seats").innerText();
       int seatsAfterBooking = Integer.parseInt(NumberOfSeatsAfterBooking.split(" ")[0]);

        return seatsAfterBooking;
    }

}
