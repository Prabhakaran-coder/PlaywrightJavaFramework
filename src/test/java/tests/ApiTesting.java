package tests;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

public class ApiTesting {
    Playwright playwright;
    @Test 
    public void setup() {

        HashMap<String, String> loginPayload = new HashMap<>();
        loginPayload.put("email", "cryptobullgear@gmail.com");
        loginPayload.put("password", "iV!RBFaV5K5K@Sb");

        HashMap<String, String> EventDataHashMap = new HashMap<>();
        EventDataHashMap.put("title", "Inter-College-Meet 2026");
        EventDataHashMap.put("description", "Inter-College-Meet 2026");
        EventDataHashMap.put("category", "Concert");
        EventDataHashMap.put("city", "Erode");
        EventDataHashMap.put("venue", "Nandha Engineering College - Auditorium");
        EventDataHashMap.put("eventDate", "2026-10-15T19:00");
        EventDataHashMap.put("price", "100");
        EventDataHashMap.put("totalSeats", "1000");

        //Api to login and get the token
        playwright = Playwright.create();
        APIRequestContext apiRequestContext = playwright.request().newContext();
        APIResponse apiResponse = apiRequestContext.post("https://api.eventhub.rahulshettyacademy.com/api/auth/login",
        RequestOptions.create().setData(loginPayload));

        Assert.assertTrue( apiResponse.ok());
        String token = JsonPath.read(apiResponse.text(),"$.token");
        //System.out.println("Token: " + token);

        //Api to create an event using the token
        APIResponse apiEventResponse= apiRequestContext.post("https://api.eventhub.rahulshettyacademy.com/api/events",
        RequestOptions.create().setData(EventDataHashMap).setHeader("Authorization", "Bearer " + token));
        
       // System.out.println("Event Creation Response: " + apiEventResponse.text());
        int eventId = JsonPath.read(apiEventResponse.text(),"$.data.id");
        System.out.println("Event ID: " + eventId);
        Assert.assertTrue(apiEventResponse.ok());

        //Api to retrieve the event details using the token
        APIResponse retrieveEvents = apiRequestContext.get("https://api.eventhub.rahulshettyacademy.com/api/events/",
        RequestOptions.create().setHeader("Authorization", "Bearer " + token).setQueryParam("page", "1").
        setQueryParam("limit", "10"));
        
         
         List<Integer> RetrieveEventId = JsonPath.read(retrieveEvents.text(),"$.data[*].id");
        
         Assert.assertTrue( RetrieveEventId.contains(eventId));
         //System.out.println(RetrieveEventId);
         Assert.assertTrue(retrieveEvents.ok(),"The events are retrieved successfully");

        APIResponse DeleteEvent = apiRequestContext.delete("https://api.eventhub.rahulshettyacademy.com/api/events/"+eventId, RequestOptions.create().setHeader("Authorization", "Bearer " + token));
        Assert.assertTrue(DeleteEvent.ok());

        //verify the deleted record 
        APIResponse postDeletAPIResponse= apiRequestContext.get("https://api.eventhub.rahulshettyacademy.com/api/events/", RequestOptions.create().setHeader("Authorization", "Bearer " + token));
        List<Integer> RetrieveEventIdAfterDelete = JsonPath.read(postDeletAPIResponse.text(),"$.data[*].id");
        Assert.assertFalse( RetrieveEventIdAfterDelete.contains(eventId));
    }
}
