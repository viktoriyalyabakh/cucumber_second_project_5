package steps;

import com.github.javafaker.Faker;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.LoginPage;
import pages.ProductPage;
import utils.Driver;
import utils.DropdownHandler;

public class SmartBearSteps {

    WebDriver driver;
    ProductPage productPage;
    LoginPage loginPage;
    Faker faker;
    String customerName;
    String street;
    String city;
    String state;
    String zipCode;
    String cardNumber;
    String expirationDate;

    @Before
    public void setup(){
        driver = Driver.getDriver();
        productPage = new ProductPage();
        loginPage = new LoginPage();
        faker = new Faker();
        customerName = faker.name().fullName();
        street = faker.address().streetAddress();
        city = faker.address().city();
        state = faker.address().state();
        zipCode = faker.address().zipCode().substring(0, 5);
        cardNumber = faker.business().creditCardNumber();
        expirationDate = "12/26";

    }

    @And("validate below menu items are displayed")
    public void validateBelowMenuItemsAreDisplayed(DataTable menuItems) {
        for (int i = 0; i < menuItems.asList().size(); i++) {
            Assert.assertEquals(menuItems.asList().get(i), productPage.menuItems.get(i).getText());
        }
    }

    @When("user clicks on {string} button")
    public void userClicksOnButton(String button) {
        switch(button){
            case "Check All":
                productPage.checkAllButton.click();
                break;
            case "Uncheck All":
                productPage.uncheckAllButton.click();
                break;
            case "Process":
                productPage.processButton.click();
                break;
            case "Delete Selected":
                productPage.deleteAllButton.click();
                break;
            default:
                throw new NotFoundException("Text is not validated");
        }
    }

    @Then("all rows should be checked")
    public void allRowsShouldBeChecked() {
        for(WebElement checkbox : productPage.tableCheckboxes)
            Assert.assertTrue(checkbox.isSelected());
    }

    @Then("all rows should be unchecked")
    public void allRowsShouldBeUnchecked() {
        for(WebElement checkbox : productPage.tableCheckboxes)
            Assert.assertFalse(checkbox.isSelected());
    }

    @When("user clicks on {string} menu item")
    public void userClicksOnMenuItem(String option) {
        switch(option){
            case "View all orders":
                 productPage.menuItems.get(0).click();
                 break;
            case "Orders":
                productPage.menuItems.get(2).click();
                break;
            default:
                throw new NotFoundException("Text is not validated");
        }
    }

    @And("user selects {string} as product")
    public void userSelectsAsProduct(String productName) {
        DropdownHandler.selectByVisibleText(productPage.productDropdown, productName);
    }

    @And("user enters {int} as quantity")
    public void userEntersAsQuantity(int quantity) {
        productPage.quantityInputBox.sendKeys("" + quantity);
    }

    @And("user enters all address information")
    public void userEntersAllAddressInformation() {
        productPage.customerNameInputBox.sendKeys(customerName);
        productPage.streetInputBox.sendKeys(street);
        productPage.cityInputBox.sendKeys(city);
        productPage.stateInputBox.sendKeys(state);
        productPage.zipInputBox.sendKeys(zipCode);
    }

    @And("user enters all payment information")
    public void userEntersAllPaymentInformation() {
        productPage.paymentInputBoxes.get(1).click();
        productPage.cardNumberInputBox.sendKeys(cardNumber);
        productPage.expireDateInputBox.sendKeys(expirationDate);
    }

    @Then("user should see their order displayed in the List of All Orders table")
    public void userShouldSeeTheirOrderDisplayedInTheTable() {
        Assert.assertFalse(productPage.tableCheckboxes.size() <= 8);
    }

    @And("validate all information entered displayed correct with the order")
    public void validateAllInformationEnteredDisplayedCorrectWithTheOrder() {
    }

    @Then("user should see {string} message")
    public void userShouldSeeMessage(String message) {
        Assert.assertEquals(message, loginPage.message.getText());
    }

    @Then("validate all orders are deleted from the List of All Orders")
    public void validateAllOrdersAreDeletedFromThe() {
        Assert.assertTrue(productPage.tableCheckboxes.size() == 0);
    }

    @And("validate user sees {string} message")
    public void validateUserSeesMessage(String message) {
        Assert.assertEquals(message, productPage.emptyListMessage.getText());
    }
}
