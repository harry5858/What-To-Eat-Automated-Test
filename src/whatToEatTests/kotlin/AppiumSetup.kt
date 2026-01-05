import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.AndroidMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL


/*
* Setup base configuration to use ie
* 1. which device
* 2. which application
* */

open class AppiumSetup {
    companion object {
        lateinit var driver: AppiumDriver<MobileElement>
        private val caps = DesiredCapabilities()

        //
        const val appPackage = "com.example.whattoeat"
        private const val activityName = "com.example.whatToEat.MainActivity"
        private const val automationName = "UiAutomator2"
        private const val platformName = "Android"
        private const val serverUrl = "http://localhost:4723/wd/hub"


        @JvmStatic
        @BeforeAll
        fun setup() {
            println("------------setup once")
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName)
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName)
//            caps.setCapability(MobileCapabilityType.FULL_RESET, true)
            caps.setCapability(MobileCapabilityType.NO_RESET, true)
            caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage)
            caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, activityName)
            caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true)
            driver = AndroidDriver(URL(serverUrl), caps)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            println("------------tear down once")
            driver.quit()
        }
    }
}