import net.bytebuddy.asm.MemberSubstitution;
import org.apache.commons.io.FileUtils;
import org.example.Bing;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.internal.annotations.ITest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class BingTest {
    // Šis metodas paleidžiamas prieš kiekvieną testą @Test anotaciją.
    // Jis atidaro naršyklę ir nueina į Bing pagrindinį puslapį.
    @BeforeMethod
    public void setUp() {
        Bing.setUp(Bing.URL);
    }
    // Pagrindinis testas įveda žodį į Bing paiešką.
    @Test
    public void searchByKeywordTest(){
        Bing.searchByKeyword("Baranauskas");
    }
    // Šis metodas vykdomas po kiekvieno testo.
    // Čia tikrinamas testas pavyko ar nepavyko.
    @AfterMethod
    public  void close(ITestResult result) throws IOException{
        // Jei testas sėkmingas, tiesiog uždarome naršyklę.
        if(result.isSuccess()) {
            Bing.closeBrowser();
        }else{
            // Jei testas nepavyko, darome ekrano kopiją.
            // Sukuriame laiko formatą ekrano kopijos pavadinimui.
            SimpleDateFormat time = new SimpleDateFormat("MM_dd_HH_mm_ss");
            time.setTimeZone(TimeZone.getTimeZone("Europe/Vilnius"));
            // Fiksuojame laiką milisekundėmis.
            long failTime = System.currentTimeMillis();
            String failedAt = time.format(failTime);
            System.out.println(failedAt);
            // Gausime testo metodo test case pavadinimą.
            String tcName = result.getName();
            // Darome ekrano kopiją naudodami aktyvią naršyklę.
            File srcshotFile =((TakesScreenshot) Bing.getBrowser()).getScreenshotAs(OutputType.FILE);
             // Išsaugome ekrano kopiją į katalogą /screenshots/failedc/.
            FileUtils.copyFile(srcshotFile, new File("screenshots/failedc/" + tcName + " _ " + failedAt + ".png"));

        }
    }

}
