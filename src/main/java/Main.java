import java.util.List;

/**
 * Get an access token from:
 * https://developers.facebook.com/tools/explorer
 * Copy and paste it in Scraper.java ==> 'accessTokenString' value
 */

public class Main {
    public static void main(String[] args) {
        List<FacebookCompany> facebookCompanies = Excel.getInformation("/home/jacob/Documents/FacebookCompanyScraper/FB Page Links All.xlsx");
        for (FacebookCompany facebookCompany : facebookCompanies) {
            Scraper scraper = new Scraper(facebookCompany);
            Excel.writeToFile(facebookCompany, scraper.getInformation());
        }
    }
}