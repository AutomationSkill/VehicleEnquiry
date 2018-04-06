package org.uk.dvla.stepDefinition;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uk.dvla.Utils.VehicleFileType;
import org.uk.dvla.core.Browsers;
import org.uk.dvla.core.WebDriverFactory;
import org.uk.dvla.module.FileDetails;
import org.uk.dvla.module.VehicleDetails;
import org.uk.dvla.pageObjects.VehicleEnquiryPageFactory;
import org.uk.dvla.serviceLayer.FileServiceModuleImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.uk.dvla.core.WebElementsConstants.HOME_PAGE_TITLE;

public class VehicleEnquiryStepDefinition {

    private static final Logger LOG = LoggerFactory.getLogger(VehicleEnquiryStepDefinition.class);
    private static final String HOME_PAGE = "https://www.gov.uk/get-vehicle-information-from-dvla";
    private String directory;
    private List<FileDetails> fileDetails;
    private List<VehicleDetails> vehicleDetailsList;
    private List<VehicleDetails> actualVehicleDetails;
    private VehicleFileType inputFileType;
    private WebDriver driver;
    private VehicleEnquiryPageFactory vehicleEnquiryPageFactory;
    private FileServiceModuleImpl fileServiceModule;

    public VehicleEnquiryStepDefinition(){
        driver = WebDriverFactory.getInstance().getDriver(Browsers.CHROME);
        vehicleEnquiryPageFactory = PageFactory.initElements(driver,VehicleEnquiryPageFactory.class);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }



    @Given("^vehicle details are loaded from input directory \"([^\"]*)\"$")
    public void readInputDirectory(String inputDirectory) {

        fileServiceModule = new FileServiceModuleImpl(inputDirectory);
        System.out.println("inputdirectory"+inputDirectory);
    }

    @Given("^scanned and loaded the files for \"([^\"]*)\"$")
    public void scanAndLoadFiles(String fileType) throws Throwable {
        inputFileType = VehicleFileType.valueOf(fileType);
        System.out.println("StepDEfinition "+ fileType);
        fileDetails = fileServiceModule.getFileDetails(inputFileType);
        vehicleDetailsList = new ArrayList<>();
        for (FileDetails fileDetail : fileDetails) {
            LOG.info("\nFileDetails {}", fileDetail);
            vehicleDetailsList.addAll(fileServiceModule.getVehicleDetails(fileDetail.getFileAbsolutePath(), inputFileType));
        }
        LOG.debug("input List: {}", vehicleDetailsList);
    }

    @Given("^the user is in dvla vehicle info page$")
    public void homePage() {

        driver.get(HOME_PAGE);
        String assertTitle =vehicleEnquiryPageFactory.checkHomePageLoaded();
        Assert.assertEquals(assertTitle,HOME_PAGE_TITLE);
    }

    @When("^user selects start search for the vehicle information$")
    public void startSearch() {
        vehicleEnquiryPageFactory.clickStartButton();

    }

    @When("^user enters registration number$")
    public void user_enters_registration_number() {
        actualVehicleDetails = new ArrayList<>();
        for(VehicleDetails vehicle : vehicleDetailsList){
            System.out.println(vehicle.getRegistrationNumber());
            vehicleEnquiryPageFactory.enterVehicleRegNumber(vehicle.getRegistrationNumber());
        }

    }
    @And("^user selects continue$")
    public void userSelectsContinue() {
        vehicleEnquiryPageFactory.clickContinueButton();
    }

    @Then("^verify the vehicle details$")
    public void verify_the_vehicle_details() {
        for (VehicleDetails vehicle : actualVehicleDetails) {
            Assert.assertEquals(vehicle.getRegistrationNumber(), vehicleEnquiryPageFactory.getVehicleRegistrationNumber());
            Assert.assertEquals(vehicle.getColour(), vehicle.getColour());
            Assert.assertEquals(vehicle.getVehicleMake(), vehicleEnquiryPageFactory.getVehicleMake());
            vehicleEnquiryPageFactory.clickBack();
        }
        driver.quit();
    }

}
