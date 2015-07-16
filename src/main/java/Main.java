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
//    final public static String accessTokenString = "CAACEdEose0cBAM2ZAc9i6iEpVJsgZAE9ZAPR25w0QoeSHm0eZCM6UZBfhP0al3wThZCi64babwWOaU8KL8uODVhnBVrvC1bdZBJk5hfPvuYnt4PZAZCaS1UyPfAmBFuNavYrDnoysUIezcVNb54VCgVipVSWqmXDEsrWS64UZBddNaXgrMR9OIrpb4UooZCXQ7u1kHlkcP7OCZCcUCC4pRMxZBTIZBvLYZCorbAknwZD";

    public static void main(String[] args) {
        List<FacebookCompany> facebookCompanies = Excel.getInformation("/home/jacob/Documents/FacebookCompanyScraper/FB Page Links All.xlsx");

        for (FacebookCompany facebookCompany : facebookCompanies) {
            System.out.println(String.format("Start to processing %s with ID = %s", facebookCompany.getLink(), facebookCompany.getId()));
            Scraper scraper = new Scraper(facebookCompany);
            Result result = scraper.getInformation();
            if (result != null) {
                Excel.writeToFile(facebookCompany, result);
                System.out.println(String.format("Finished %s", facebookCompany.getLink()));
            }
        }
    }
}