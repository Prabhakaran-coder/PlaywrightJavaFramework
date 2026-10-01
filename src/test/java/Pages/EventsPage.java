package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;

public class EventsPage {

    Page page;

    private static final String EVENT="#nav-events";
    private static final String EVENTCARD="#event-card";
    private static final String NOOFSEATS="seats";


    public EventsPage(Page page){
        this.page=page;
    }

    public void NavigateToEvents(){
      page.locator(EVENT, new Page.LocatorOptions().setHasText("Events")).click();
      
      //Locator listOfCards = page.locator("#event-card"); 
    }
    public Locator waitForEventsToLoad(){
        Locator listOfCards = page.locator(EVENTCARD);
        assertThat(listOfCards.first()).isVisible();
        return listOfCards;
    }

    public Locator FindBookedEvent(String titleCard){
     Locator listOfCards = waitForEventsToLoad();
     Locator targetCard= listOfCards.filter(new Locator.FilterOptions().setHasText(titleCard));
     return targetCard;
    }
    public int getSeatCount(Locator targetCard){
       // Locator targetCard = FindBookedEvent(titleCard);
        String NumberOfSeats = targetCard.getByText(NOOFSEATS).innerText();
        int seatsBeforeBooking = Integer.parseInt(NumberOfSeats.split(" ")[0]);
        System.out.println("Total Seats for Music Concert: " + NumberOfSeats);
        targetCard.getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Book Now")).click();
        return seatsBeforeBooking;
    }
}
