package org.uk.dvla.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static org.uk.dvla.core.WebElementsConstants.*;

public class VehicleEnquiryPageFactory {

    private WebDriver driver;
    private static final Logger LOG = Logger.getLogger(VehicleEnquiryPageFactory.class);

    public VehicleEnquiryPageFactory(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(how = How.XPATH, using = START_BUTTON_XPATH)
    private WebElement startNow;


    @FindBy(how = How.ID, using = VEHICLE_REG_NUMBER_INPUT_ID)
    private WebElement vehicleRegNumberInput;

    @FindBy(how = How.NAME, using = CONTINUE_BUTTON_NAME)
    private WebElement continueButton;

    @FindBy(how = How.XPATH, using = VEHICLE_MAKE_XPATH)
    private WebElement vehicleMake;

    @FindBy(how = How.XPATH, using = VEHICLE_COLOUR_XPATH)
    private WebElement vehicleColour;

    @FindBy(how = How.XPATH, using = VEHICLE_REG_NUMBER_XPATH)
    private WebElement vehicleRegNumber;

    @FindBy(how = How.LINK_TEXT, using = BACK_LINK)
    private WebElement back;

    public String checkHomePageLoaded() {
        return driver.getTitle();
    }

    public void clickStartButton() {
        startNow.click();
    }

    public void enterVehicleRegNumber(String regNo) {
        vehicleRegNumberInput.sendKeys(regNo);
        System.out.println("REgistration number " +regNo);
    }

    public void clickContinueButton() {
        continueButton.click();
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegNumber.getText();
    }

    public String getVehicleMake() {
        return vehicleMake.getText();
    }

    public String getVehicleColour() {
        return vehicleColour.getText();
    }

    public void clickBack() {
        back.click();
    }

}
