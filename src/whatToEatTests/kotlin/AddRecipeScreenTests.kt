import io.appium.java_client.MobileElement
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import java.util.concurrent.TimeUnit
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class AddRecipeScreenTests: AppiumSetup() {

    companion object {
        const val utilButtonId = "com.example.whattoeat:id/btnUtil"
        const val backButtonId = "com.example.whattoeat:id/btn_back"
        // TextFields
        const val mealNameTextFieldId = "com.example.whattoeat:id/etMealName"
        const val mealAreaTextFieldId = "com.example.whattoeat:id/etMealArea"
        const val mealCategoryTextFieldId = "com.example.whattoeat:id/etMealCategory"
        const val mealImageTextFieldId = "com.example.whattoeat:id/etThumbnailUrl"
        const val mealYtTextFieldId = "com.example.whattoeat:id/etYoutubeUrl"
        const val mealInstructionTextFieldId = "com.example.whattoeat:id/etInstruction"
        const val ingredientTextFieldId = "com.example.whattoeat:id/etIngredient"
        const val measurementTextFieldId = "com.example.whattoeat:id/etMeasurement"
        //
        const val ingredientMeasurementLlId = "com.example.whattoeat:id/llIngredientMeasurement"
        const val addIngredientButtonId = "com.example.whattoeat:id/btnAdd"
        const val ingredientDeleteButtonId = "com.example.whattoeat:id/btnDelete"
        //
        const val savedRecipesRecyclerViewId = "com.example.whattoeat:id/rvMeals"

    }

    @BeforeTest
    fun setupEach() {
        driver.activateApp(appPackage)
        driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS) // timeout for splash screen
        // nagvigation to add recipe screen
        val utilButton1 = driver.findElement(By.id(utilButtonId))
        utilButton1.click()
        driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS)
        val utilButton2 = driver.findElement(By.id(utilButtonId))
        utilButton2.click()
        println("---------- setup each")
    }

    @AfterTest
    fun tearDownEach() {
        driver.terminateApp(appPackage)
        println("---------- tear down each")
    }

    @Test
    fun back_button_click() {
        val backButton = driver.findElement(By.id(backButtonId))
        backButton.click()
        driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS)
        val recyclerView: MobileElement = driver.findElement(By.id(savedRecipesRecyclerViewId))
        assert(recyclerView.isDisplayed)
    }

    // TODO find toast message?
    @Test
    fun save_recipe_button_click_with_empty_form() {
        val saveButton = driver.findElement(By.id(utilButtonId))
        saveButton.click()
        driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS)
        val toastView = driver.findElement(By.xpath("//android.widget.Toast[1]"))
        assert(toastView.isDisplayed)
    }

    @Test
    fun save_recipe_button_click_with_filled_form() {
        val mealNameTextField = driver.findElement(By.id(mealNameTextFieldId))
        val mealAreaTextField = driver.findElement(By.id(mealAreaTextFieldId))
        val mealCategoryTextField = driver.findElement(By.id(mealCategoryTextFieldId))
        val mealImageTextField = driver.findElement(By.id(mealImageTextFieldId))
        val mealYtTextField = driver.findElement(By.id(mealYtTextFieldId))
        val mealInstructionTextField = driver.findElement(By.id(mealInstructionTextFieldId))

        val ingredientTextField = driver.findElement(By.id(ingredientTextFieldId))
        val measurementTextField = driver.findElement(By.id(measurementTextFieldId))
        val addIngredientButton = driver.findElement(By.id(addIngredientButtonId))

        mealNameTextField.sendKeys("test meal name")
        mealAreaTextField.sendKeys("test meal area")
        mealCategoryTextField.sendKeys("test meal category")
        // dummy image url
        mealImageTextField.sendKeys(
            "https://fastly.picsum.photos/id/406/200/300.jpg?hmac=hL72xK7v5nIaSK6F5XcGWjvxXslx72ZNRshXUAci5Bc"
        )
        // leave yt empty
        mealInstructionTextField.sendKeys("test meal instruction")

        for (i in (1..3)) {
            ingredientTextField.sendKeys("ingredient $i")
            measurementTextField.sendKeys("measurement $i")
            addIngredientButton.click()
        }
        driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS)
        // TODO
        val recyclerView: MobileElement = driver.findElement(By.id(savedRecipesRecyclerViewId))
        assert(recyclerView.isDisplayed)
    }

    @Test
    fun add_ingredient_click() {
        val ingredientTextField = driver.findElement(By.id(ingredientTextFieldId))
        val measurementTextField = driver.findElement(By.id(measurementTextFieldId))
        val addIngredientButton = driver.findElement(By.id(addIngredientButtonId))

        ingredientTextField.sendKeys("ingredient")
        measurementTextField.sendKeys("measurement")
        addIngredientButton.click()
        driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS)
        val ingredientMeasurementLl = driver.findElement(By.id(ingredientMeasurementLlId))
        assert(ingredientMeasurementLl.isDisplayed)
    }

    @Test
    fun remove_added_ingredient_click() {
        val ingredientTextField = driver.findElement(By.id(ingredientTextFieldId))
        val measurementTextField = driver.findElement(By.id(measurementTextFieldId))
        val addIngredientButton = driver.findElement(By.id(addIngredientButtonId))

        ingredientTextField.sendKeys("ingredient")
        measurementTextField.sendKeys("measurement")
        addIngredientButton.click()
        driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS)
        val ingredientMeasurementLl = driver.findElement(By.id(ingredientMeasurementLlId))
        if (ingredientMeasurementLl.isDisplayed) {
            val ingredientDeleteButton = driver.findElement(By.id(ingredientDeleteButtonId))
            ingredientDeleteButton.click()
            assert(!ingredientMeasurementLl.isDisplayed)
        } else {
            assert(false)
        }
    }

    @Test
    fun meal_name_empty_focus_error_message() {
        val mealNameTextField = driver.findElement(By.id(mealNameTextFieldId))
        val saveButton = driver.findElement(By.id(utilButtonId))
        saveButton.click()
        driver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS)
        assert(mealNameTextField.getAttribute("error").isNullOrBlank())
    }
}