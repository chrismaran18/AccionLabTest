import io.restassured.response.Response;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;


public class playCheckersAndCard extends BaseTest {
public static String deckID;

    @Test(priority=0)
    public static void playMove() throws InterruptedException {
        launchCheckers();
        System.out.println("```````````Checkers Game Started`````````````");
            System.out.println("launched the Checkers");
        //myMove();
        blueTakenMove();
        System.out.println("```````````Checkers Game Completed`````````````");
        System.out.println("_________________________________________________");
    }
    public static void myMove() throws InterruptedException {

        confirmPageStability("confirmed that the site is up & running... its ready to play!");

        myMoves(6,2,5,1, " First ");
        waitForMyMove();    

        myMoves(6,4,5,3, " Second ");
        waitForMyMove();

        myMoves(7,3,6,2, " Third ");
        waitForMyMove();

        myMoves(6,8,5,7, " Fourth ");
        waitForMyMove();

        myMoves(8,4,7,3, " Fifth ");
        waitForMyMove();

        driver.findElement(By.xpath("//a[contains(text(),'Restart...')]")).click();
        Thread.sleep(5000);
        confirmPageStability("The game is Restarted Successfully!");
    }

    public static void blueTakenMove() throws InterruptedException {

        confirmPageStability("confirmed that the site is up & running... its ready to play!");

        myMoves(6,6,5,5, " First ");
        waitForMyMove();

        myMoves(6,8,5,7, " Second ");
        waitForMyMove();

        myMoves(7,5,5,7, " Third [blue piece taken] ");
        waitForMyMove();

        myMoves(6,4,5,3, " Fourth ");
        waitForMyMove();

        myMoves(7,7,5,5, " Fifth [blue piece taken] ");
        waitForMyMove();

        driver.findElement(By.xpath("//a[contains(text(),'Restart...')]")).click();
        Thread.sleep(5000);
        confirmPageStability("The game is Restarted Successfully!");
    }

    public static void waitForMyMove() throws InterruptedException {
        Thread.sleep(2500);
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),  'Make a move.')]")).isDisplayed());
    }

    public static void confirmPageStability(String myString){
        boolean ifDisplayed = driver.findElement(By.xpath("//*[contains(text(),  'Select an orange piece to move.')]")).isDisplayed();
        if(ifDisplayed){
            System.out.println(myString);
        }
    }

    public static void myMoves(int fromRow, int fromCol, int toRow, int toCol, String myMoveStr){
        driver.findElement(By.xpath("//div["+fromRow +"]/img["+ fromCol+"]")).click();
        driver.findElement(By.xpath("//div["+ toRow +"]/img["+ toCol +"]")).click();
        System.out.println("my"+ myMoveStr +"move is done");

        /*driver.findElement(By.xpath("//div[6]/img[2]")).click();
        driver.findElement(By.xpath("//div[5]/img[1]")).click();
        System.out.println("my First move is done");
        */
    }

    @Test(priority = 1)
    public static void deckCardsTest(){
        System.out.println("```````````Deck of Card Game Started`````````````");
        deckOfCards();
        shuffleCards();
        System.out.println("```````````Deck of Card Game Completed`````````````");
    }

    public static void deckOfCards(){
        Response response= given()
                .pathParams("myDeck", "deck/new")
                .contentType("application/json")
                .get("https://deckofcardsapi.com/api/{myDeck}");
                //.then().log().all();

        JSONObject jsonObject = new JSONObject(response.asString());
        deckID= jsonObject.getString("deck_id");
        System.out.println("the Deck ID is : "+deckID);
    }

    public static String getDeckID(){
        //System.out.println("the Deck ID is : "+deckID);
        return deckID;
    }

    public static void shuffleCards(){
        Response response= given()
                .pathParams("myDeck", getDeckID())
                .contentType("application/json")
                .get("https://deckofcardsapi.com/api/deck/{myDeck}/shuffle/");
        //.then().log().all();

        JSONObject jsonObject = new JSONObject(response.asString());
        boolean shuffled= jsonObject.getBoolean("shuffled");
        System.out.println("is the cards are Shuffled? : "+shuffled);
    }
}
