package Pages;


import com.microsoft.playwright.Locator.ClickOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.MouseButton;

public class LoginPage {
    Page page;    
    private static final String Email = "Email";
    private static final String password = "••••••";
    String Base_url;
    
    public LoginPage(Page page,String Base_url){
        this.page = page;
        this.Base_url=Base_url;
    }

    public Dashboard LoginToApplication(){
        
        page.navigate(Base_url);
        System.out.println(page.title());
        assertThat(page).hasTitle("EventHub — Discover \u0026 Book Events");
        page.getByLabel(Email).fill("cryptobullgear@gmail.com");
        page.getByPlaceholder(password).fill("iV!RBFaV5K5K@Sb");
        page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Sign In")).
           click(new ClickOptions().setDelay(200.00).setButton(MouseButton.LEFT));
        
           Dashboard dashboard = new Dashboard(page);
           return dashboard;
    }

}
