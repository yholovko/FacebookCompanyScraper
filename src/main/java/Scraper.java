import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Scraper /*extends Thread*/ {
    private FacebookCompany facebookCompany;
    private WebDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
    private Result result = new Result();

    public Scraper(FacebookCompany facebookCompany) {
        this.facebookCompany = facebookCompany;
    }

    public WebDriver connectTo(String url) {
        while (true) {
            try {
                ((HtmlUnitDriver) driver).setJavascriptEnabled(true);

                driver.get(url);
                break;
            } catch (Exception e) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return driver;
    }

    //@Override
    public void run() {
//        synchronized (this) {
        WebDriver driver = connectTo(facebookCompany.getLink());

        Document doc = Jsoup.parse(driver.getPageSource());
        doc.select("#js_b > div > ul > li:nth-child > a");

        result.setNameOfTheCompany(facebookCompany.getName());

        //notify();
//        }
    }
}
