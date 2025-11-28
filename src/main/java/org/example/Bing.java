package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class Bing {
    // WebDriver objektas naudojamas naršyklės valdymui.
    private static WebDriver browser;
    // Nuolatinė reikšmė Bing paieškos URL.
    public static final String URL = "https://www.bing.com";
    public static void main(String[] args) {
        System.out.println("Maven + Selenium");
        // Paleidžiame naršyklę ir atidarome puslapį.
        setUp(URL);
        // Atliekame paiešką pagal raktinį žodį.
        searchByKeyword("Baranauskas");
        // Išgauname surastų rezultatų skaičių.
        int actualResults = getResults();
        // Palyginame gautą rezultatų kiekį su tikėtinu.
        compareResults(500000, actualResults);
        // Uždaryti naršyklę, kai baigiame darbą.
        closeBrowser();

    }
    // Paleidžia Chrome naršyklę ir atidaro nurodytą URL..
    public static void setUp(String url){
        // Nurodome tiesioginį kelią iki ChromeDriver naršyklės.
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Karolis\\IdeaProjects\\Baranauskas\\src\\main\\library\\chromedriver142.exe");
        // Sukuriame naują ChromeDriver naršyklę.
        browser = new ChromeDriver();
        // Atidarome pasirinktą puslapį.
        browser.get(url);

    }
    //  Įveda raktažodį į Bing paieškos laukelį ir aktyvuoja paiešką.
    public static void searchByKeyword(String keyword){
        // Surandame paieškos laukelį pagal ID.
        WebElement searchField = browser.findElement(By.id("sb_form_q"));
        // Įrašome raktinį žodį.
        searchField.sendKeys(keyword);
        // Paspaudžiame ENTER, kad pradėtų paiešką.
        searchField.sendKeys(Keys.ENTER);
    }
    //  Nuskaito paieškos rezultatų skaičių iš Bing puslapio.
    public static int getResults(){
        // Laukiame, kol atsiras paieškos rezultatų informacija.
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(20));
        // Susirandame HTML elementą, kuriame yra parašytas rezultatų kiekis. Reliatyvus kelias.
        WebElement searchResults = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"b_tween_searchResults\"]/span"))
        );
        // Išvedame žalią tekstą į konsolę.
        System.out.println("Paieškos rezultatai: " + searchResults.getText());
        // Randamas elementas pagal klasę.
        //WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(20));
        //WebElement searchResults = wait.until(
        // ExpectedConditions.visibilityOfElementLocated(By.className("sb_count"))
        //);
        //System.out.println("Paieškos rezultatai: " + searchResults.getText());

        // Absoliutus kelias.
        //WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(20));
        //WebElement searchResults = wait.until(
        //ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/main/div/div[1]/span"))
        //);
        //System.out.println("Paieškos rezultatai: " + searchResults.getText());

        // Pašaliname raides ir tarpus, kad liktų tik skaičiai.
        String resultStr = searchResults.getText()
                .replaceAll("[a-zA-Zų]", "")  // Pašaliname raides.
                .replaceAll("[, ]",""); // Pašaliname tarpus ir kablelius.
        // Paverčiame tekstą į skaičių.
        int resultInt = Integer.parseInt(resultStr);
        return resultInt;


    }
    // Prijungia tikėtiną rezultatų kiekį su faktiniu ir išveda atitinkamą tekstą.
    public static void compareResults(int expectedResults, int actualResults ){
        // Tikriname populiarumą pagal paieškos rezultatų skaičių.
        if(actualResults < expectedResults){
            System.out.println("Rašytojas nelabai populiarus internetinėse platybėse.");
        }else{
            System.out.println("Jaunimas dar neužmiršo Anykščių Šilelio.");
        }
    }
    // Uždaro naršyklę ir baigia Selenium darbą.
    public static void closeBrowser(){
        browser.close();

    }

    public static WebDriver getBrowser(){
        return browser;
    }

}

