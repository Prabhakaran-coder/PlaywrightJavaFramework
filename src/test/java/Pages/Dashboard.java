package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.options.AriaRole;

public class Dashboard {
    Page page;

    public Dashboard(Page page){
     this.page=page;
    }

    public void DashboardSection(){

        assertThat(page.getByRole(AriaRole.LINK, new GetByRoleOptions().setName("Browse Events →"))).isVisible();
      page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Admin")).
           click();
               page.getByRole(AriaRole.NAVIGATION).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Manage Events")).isVisible();
      page.getByRole(AriaRole.NAVIGATION).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Manage Events")).
        click();
    }
}
