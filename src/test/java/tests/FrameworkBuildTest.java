package tests;
import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;

import Pages.BookAEvent;
import Pages.Dashboard;
import Pages.EventsCreate;
import Pages.EventsPage;
import Pages.LoginPage;
import utils.DataProviderUtils;

public class FrameworkBuildTest extends TestBase {
    
  @DataProvider(name="eventBookingData")
  public Object[][] eventBookingData() throws IOException{
    return DataProviderUtils.getJsonDataToMap("/src/test/java/resources/eventBookingData.json");
  }
   
  @Test(groups={"smoke"},dataProvider="eventBookingData",description = "Login to EventHub")
    public void testMethod(HashMap<String,String> data) {
      LoginPage login = new LoginPage(page,Base_url);
      Dashboard dashboardPage=login.LoginToApplication();
      dashboardPage.DashboardSection();

       
      //Event creation page
      EventsCreate createAnEvent = new EventsCreate(page);
      createAnEvent.CreateEvent(
        data.get("titlePrefix"),
        data.get("description"),
        data.get("category"),
        data.get("city"),
        data.get("venue"),
        data.get("dateTime"),
        data.get("price"),
        data.get("totalSeats")
      );
      
        //FilterEvent
      EventsPage eventsDataPage = new EventsPage(page);
      eventsDataPage.NavigateToEvents();
      Locator targetCard= eventsDataPage.FindBookedEvent(data.get("titlePrefix"));
      int seatsBeforeBooking = eventsDataPage.getSeatCount(targetCard);
     
      //Concert booking
      BookAEvent bookAEvent = new BookAEvent(page);
      bookAEvent.EventBooking(data.get("fullName"),data.get("email"),data.get("phone"));
      bookAEvent.EventBookingVerification();

      //view booking
      bookAEvent.viewBookings();
      
      // Verify that the number of seats has decreased after booking
      seatVerficationAfterBooking NumOfSeatsAfterBooking = new seatVerficationAfterBooking(page);
      int seatsAfterBooking = NumOfSeatsAfterBooking.verifySeatsPostBooking(data.get("titlePrefix"));

      Assert.assertTrue(seatsAfterBooking < seatsBeforeBooking);
      }
    @AfterMethod 
    public void postTest(){
        page.isClosed();
    }
}


