package Pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;

public class EventsCreate {

    Page page;
    Map<String, String> eventPlaceHolderData = new HashMap<>();
    Map<String,String>eventLabelData = new HashMap<>();
    Map<String,String> selectMap = new HashMap<>();
    
    public EventsCreate(Page page){
        this.page=page;
    }

    public void CreateEvent( String EventTitle,String Description,String EventType, String City, String Venue, String EventDateTime,String Price, String TotalSeats){
      eventPlaceHolderData.put("Event title", EventTitle);
      eventPlaceHolderData.put("Describe the event…", Description);
      selectMap.put("Category",EventType);
      eventLabelData.put("City", City);
      eventLabelData.put("Venue", Venue);
      eventLabelData.put("Event Date & Time", EventDateTime);
      eventLabelData.put("Price ($)", Price);
      eventLabelData.put("Total Seats", TotalSeats);
      
      
      
     for (Map.Entry<String, String> placeholderEntry : eventPlaceHolderData.entrySet()) {
        page.getByPlaceholder(placeholderEntry.getKey()).fill(placeholderEntry.getValue());
        // System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        for(Map.Entry<String, String> SelectOption:selectMap.entrySet()){
            page.getByLabel(SelectOption.getKey()).selectOption(SelectOption.getValue());
        }
        for (Map.Entry<String, String> Labelentry : eventLabelData.entrySet()) {
        page.getByLabel(Labelentry.getKey()).fill(Labelentry.getValue());
                   // System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        page.waitForTimeout(5000);
      page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("+ Add Event")).
      click();

      assertThat(page.getByText("Event created!")).
      isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout( 2000.00));
    }

}
