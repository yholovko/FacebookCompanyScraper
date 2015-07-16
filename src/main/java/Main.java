import java.util.List;
/**
 * Access token for 1 hour
 * Get an access token from:
 * https://developers.facebook.com/tools/explorer
 * Copy and paste 'accessTokenString' value
 *
 * OR (STRONGLY RECOMMENDED)
 * https://smashballoon.com/custom-facebook-feed/access-token/
 */

public class Main {
    final public static String accessTokenString = "763885633733453|UPL0nXC91GOdVIomVTYAlOB2JUs";

    public static void main(String[] args) {
        List<FacebookCompany> facebookCompanies = Excel.getInformation("/home/jacob/Documents/FacebookCompanyScraper/FB Page Links All.xlsx");

        for (FacebookCompany facebookCompany : facebookCompanies) {
            System.out.println(String.format("Start to processing %s", facebookCompany.getLink()));
            Scraper scraper = new Scraper(facebookCompany);
            Result result = scraper.getInformation();
            if (result != null) {
                Excel.writeToFile(facebookCompany, result);
                System.out.println(String.format("Finished %s", facebookCompany.getLink()));
            }
        }
    }
}