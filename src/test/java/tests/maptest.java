package tests;


import java.util.HashMap;
import java.util.Map;
//import java.util.Map;

public class maptest {

    public static void main(String[] args) {
        maptest d = new maptest();
        d.mapdata();
    }

     Map<String, String> eventData = new HashMap<>();
     public void mapdata(){
      eventData.put("Event title", "Book Fair 2026");
      eventData.put("Describe the event…", "Book Fair 2026");
      eventData.put("Category", "Concert");
      eventData.put("City", "Coimbatore");
      eventData.put("Venue", "Book Fair 2026");
      eventData.put("Event Date & Time", "Book Fair 2026");
      eventData.put("Price ($)", "Book Fair 2026");
      eventData.put("Total Seats", "Book Fair 2026");
        
    
            for (Map.Entry<String, String> entry : eventData.entrySet()) {
                    System.out.println(entry.getKey() + " = " + entry.getValue());
        }
     }

}
