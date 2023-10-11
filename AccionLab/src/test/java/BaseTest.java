import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

public class BaseTest {
    public static WebDriver driver ;

    public static void launchCheckers(){
        driver= WebDriverManager.chromedriver().create();
        driver.manage().window().maximize();
        driver.get("https://www.gamesforthebrain.com/game/checkers");
    }

}
