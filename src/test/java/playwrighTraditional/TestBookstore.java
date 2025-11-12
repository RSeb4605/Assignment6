package playwrighTraditional;
import java.nio.file.Paths;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.*;

import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;





@UsePlaywright

public class TestBookstore {

    private void navigateHome(Page page) {
        page.navigate("https://depaul.bncollege.com/");
    }

    private void partOne(Page page) {
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).fill("earbuds");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).press("Enter");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        page.getByText("brand JBL").first().click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Color Black (9)")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Price Over $50 (8)")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();
    }


    private void partTwo(Page page) {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
    }

    private void partFour(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed To Checkout")).first().click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).fill("Name");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).fill("Lastname");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).fill("goodemail@gmail.com");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).fill("3123123122");
    }

    private void partFive(Page page) {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
    }

    private void partSeven(Page page) {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back to cart")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove product JBL Quantum")).click();
    }

    @Test
    void testCaseBookstore(Page page){
        navigateHome(page);
        partOne(page);

        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless"))).isVisible();
        assertThat(page.getByLabel("main")).containsText("sku 668972707");
        assertThat(page.getByLabel("main")).containsText("$164.98");
        assertThat(page.getByLabel("main")).containsText("Adaptive noise cancelling allows awareness of environment when gaming on the go. Light weight, durable, water resist. USB-C dongle for low latency connection < than 30ms.");
    }

    @Test
    void testCaseShoppingCart(Page page){
        navigateHome(page);
        partOne(page);
        partTwo(page);

        page.locator(".sub-check").first().click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).fill("TEST");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply Promo Code")).click();

        assertThat(page.getByLabel("main")).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
        assertThat(page.getByLabel("main")).containsText("$164.98");
        assertThat(page.getByLabel("main")).containsText("(1 Item)");
        assertThat(page.getByLabel("main")).containsText("Your Shopping Cart");
        assertThat(page.getByText("The coupon code entered is")).isVisible();
    }

    @Test
    void testCaseCreateAccountPage(Page page){
        navigateHome(page);
        partOne(page);
        partTwo(page);

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed To Checkout")).first().click();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create Account"))).isVisible();
    }

    @Test
    void testCaseContactInformationPage(Page page){
        navigateHome(page);
        partOne(page);
        partTwo(page);
        partFour(page);

        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Contact Information"))).isVisible();
        assertThat(page.getByLabel("main")).containsText("Order Subtotal $164.98");
    }

    @Test
    void testCasePickupPage(Page page) {
        navigateHome(page);
        partOne(page);
        partTwo(page);
        partFour(page);
        partFive(page);

        assertThat(page.getByLabel("main")).containsText("Full Name Name Lastname Email Address goodemail@gmail.com Phone Number +13123123122");
        assertThat(page.locator("#bnedPickupPersonForm")).containsText("Pickup Location DePaul University Loop Campus & SAIC");
        assertThat(page.locator("#bnedPickupPersonForm")).containsText("I'll pick them up");
        assertThat(page.getByLabel("main")).containsText("PICKUP DePaul University Loop Campus & SAIC JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
    }

    @Test
    void testCasePaymentInfo(Page page){
        navigateHome(page);
        partOne(page);
        partTwo(page);
        partFour(page);
        partFive(page);

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

        assertThat(page.getByLabel("main")).containsText("Order Subtotal $164.98");
        assertThat(page.getByLabel("main")).containsText("PICKUP DePaul University Loop Campus & SAIC JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
    }

    @Test
    void testCaseEmptyCart(Page page){
        navigateHome(page);
        partOne(page);
        partTwo(page);
        partFour(page);
        partFive(page);

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

        partSeven(page);
        assertThat(page.getByLabel("main").getByRole(AriaRole.HEADING)).containsText("Your cart is empty");
    }
}
