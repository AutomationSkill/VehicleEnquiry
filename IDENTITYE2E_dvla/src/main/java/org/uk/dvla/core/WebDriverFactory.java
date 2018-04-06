package org.uk.dvla.core;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {

    public final static String CHROME_DRIVER_MAC_PATH = "src/main/resources/driver/mac/chromedriver";
    public final static String CHROME_DRIVER_PATH = "src/main/resources/driver/win/chromedriver.exe";
    private static WebDriverFactory webDriverFactory = new WebDriverFactory();
    private static final Logger LOGGER = Logger.getLogger(WebDriverFactory.class);
    private static final String MESSAGE = "Request Driver :";
    private static final String UNKNOWN_ENVIRONMENT = "Unknown Environment :";

    private WebDriverFactory() {
    }

    public static WebDriverFactory getInstance() {
        return webDriverFactory;
    }

    public WebDriver getDriver(Browsers browser) {
        WebDriver driver = null;
        LOGGER.info(MESSAGE + browser);
        switch (browser) {
            case CHROME:
                System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability("marionette", true);
                driver = new FirefoxDriver(capabilities);
                break;
            case SAFARI:
                driver = new SafariDriver();
                break;
            default:
                System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
                driver = new ChromeDriver();
        }
        return driver;
    }

    private String getChromeDriverPath() {
        Platform platform = Platform.getCurrent();
        LOGGER.info("Platform :" + platform);
        if (Platform.MAC.is(platform)) {
            return CHROME_DRIVER_MAC_PATH;
        } else if (Platform.WIN10.is(platform)) {
            return CHROME_DRIVER_PATH;
        } else {
            throw new IllegalArgumentException(UNKNOWN_ENVIRONMENT);
        }
    }
}
