import io.appium.java_client.MobileElement
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class HomeScreenTests: AppiumSetup() {

    companion object {
        const val getRecipeButtonId = "com.example.whattoeat:id/btnRoll"
        const val cookButtonId = "com.example.whattoeat:id/btnToDetail"
        const val addRecipeButtonId = "com.example.whattoeat:id/btnUtil"
        const val recipeTextViewId = "com.example.whattoeat:id/vMeal"
        const val recipeImageId = "com.example.whattoeat:id/vThumbnail"
        const val titleId = "com.example.whattoeat:id/vTitle"
        //
        const val savedRecipesRecyclerViewId = "com.example.whattoeat:id/rvMeals"
    }

    @BeforeTest
    fun setupEach() {
        driver.activateApp(appPackage)
        driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS) // timeout for splash screen
        println("---------- setup each")
    }

    @AfterTest
    fun tearDownEach() {
        driver.terminateApp(appPackage)
        println("---------- tear down each")
    }

    @Test
    fun check_all_views_display() {
        val titleTextView: MobileElement = driver.findElement(By.id(titleId))
        val getRecipeButton: MobileElement = driver.findElement(By.id(getRecipeButtonId))
        val recipeImageView: MobileElement = driver.findElement(By.id(recipeImageId))
        val addRecipeButton: MobileElement = driver.findElement(By.id(addRecipeButtonId))
        val cookButton: MobileElement = driver.findElement(By.id(cookButtonId))
        assert(titleTextView.isDisplayed)
        assert(titleTextView.text == "Home")
        assert(getRecipeButton.isDisplayed)
        assert(recipeImageView.isDisplayed)
        assert(addRecipeButton.isDisplayed)
        assert(cookButton.isDisplayed)
    }

    @Test
    fun check_all_enabled_buttons() {
        val getRecipeButton: MobileElement = driver.findElement(By.id(getRecipeButtonId))
        val addRecipeButton: MobileElement = driver.findElement(By.id(addRecipeButtonId))
        assert(getRecipeButton.getAttribute("enabled").equals("true"))
        assert(addRecipeButton.getAttribute("enabled").equals("true"))
    }

    @Test
    fun check_all_disabled_buttons() {
        val cookButton: MobileElement = driver.findElement(By.id(cookButtonId))
        assert(cookButton.getAttribute("enabled").equals("false"))
    }

    @Test
    fun click_get_recipe() {
        val getRecipeButton: MobileElement = driver.findElement(By.id(getRecipeButtonId))
        val cookButton: MobileElement = driver.findElement(By.id(cookButtonId))
        getRecipeButton.click()
        driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS)
        assert(cookButton.getAttribute("enabled").equals("true"))
        val recipeTextView = driver.findElement(By.id(recipeTextViewId))
        recipeTextView?.let {
            assert(it.text.isNotBlank())
        } ?: assert(false)
        cookButton.click()
        // TODO check detail navigation
    }

    @Test
    fun click_add_recipe_button() {
        val addRecipeButton: MobileElement = driver.findElement(By.id(addRecipeButtonId))
        addRecipeButton.click()
        driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS)
        val recyclerView: MobileElement = driver.findElement(By.id(savedRecipesRecyclerViewId))
        assert(recyclerView.isDisplayed)
    }
}