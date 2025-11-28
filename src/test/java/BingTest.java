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
    @BeforeMethod
    public void setUp() {
        Bing.setUp(Bing.URL);
    }
    @Test
    public void searchByKeywordTest(){
        Bing.searchByKeyword("Baranauskas");
    }
    @AfterMethod
    public  void close(ITestResult result) throws IOException{
        if(result.isSuccess()) {
            Bing.closeBrowser();
        }else{
            SimpleDateFormat time = new SimpleDateFormat("MM_dd_HH_mm_ss");
            time.setTimeZone(TimeZone.getTimeZone("Europe/Vilnius"));
            long failTime = System.currentTimeMillis();
            String failedAt = time.format(failTime);
            System.out.println(failedAt);

            String tcName = result.getName();
            File srcshotFile =((TakesScreenshot) Bing.getBrowser()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcshotFile, new File("screenshots/failedc/" + tcName + " _ " + failedAt + ".png"));

        }
    }

}
