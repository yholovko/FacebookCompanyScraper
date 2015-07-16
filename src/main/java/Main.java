import facebook4j.FacebookException;

import java.util.List;

/**
 * Get an access token from:
 * https://developers.facebook.com/tools/explorer
 * Copy and paste 'accessTokenString' value
 */

public class Main {
    final public static String accessTokenString = "CAACEdEose0cBAKPzzkJMcdOe4j2qgZB4mLOl1FXlZArMvCtGTAoz5w9aG1D57Ex4T4HOtLlvu8KoUFWNWpKk3ZCSKShGD9ZBcBvEFMsCkOQv9NvwHcNubHQfJGr9C2hdEJradPIK8STssUSZAHUB0rgcByKYf7vPcYlZCYYJ7EhZCY4M0epmxYeJMVhigABCM5mm3rari2JEOpKjxB2oqWdDi0prdPVIEAZD";

    public static void main(String[] args) throws FacebookException {
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