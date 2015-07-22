import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Access token for 1 hour
 * Get an access token from:
 * https://developers.facebook.com/tools/explorer
 * Copy and paste to 'accessTokenString' value
 *
 * OR (STRONGLY RECOMMENDED)
 * https://smashballoon.com/custom-facebook-feed/access-token/
 */

public class Main {
    final public static String accessTokenString = "PASTE_YOUR_ACCESS_TOKEN_HERE";

    private static List<FacebookCompany> facebookCompanies;

    private static String[] notFoundPages = {"2", "63", "145", "213", "219", "220", "317", "337", "351", "458",
            "474", "497", "547", "553", "656", "666", "677", "815", "899", "913", "1027", "1037",
            "1042", "1061", "1069", "1127", "1140", "1157", "1182", "1217", "1225", "1289", "1317",
            "1330", "1491", "1500", "1545", "1560", "1569", "1570"};
    private static String[] emptyPostsPages = {"124", "181", "249", "399", "1529", "744", "1306", "1562", "908",
            "1010", "1022", "1097", "1105", "1112", "1190", "1228", "1278", "1369", "1381", "1450",
            "1451", "1496", "1498", "1501", "1535", "1538", "1555", "1559", "1572", "676", "717",
            "748", "785", "920", "967", "976", "977", "1021", "1041", "1055", "1056", "1062", "1073",
            "1074", "1084", "1093", "1133", "1169", "1173", "1247", "1286", "1300", "1308", "1322",
            "1323", "1339", "1346", "1352", "1372", "1376", "1443", "1553", "1556", "1585",
            "1590", "1591", "479", "992", "1405", "1407", "1411", "1419", "1422", "1440", "1452"};

    private static void emptyPostsOrNotFoundPagesScraper(String[] pages, List<FacebookCompany> facebookCompanies) {
        Set<String> pagesSet = new HashSet<String>();
        Collections.addAll(pagesSet, pages);

        for (FacebookCompany facebookCompany : facebookCompanies) {
            if (pagesSet.contains(String.valueOf(facebookCompany.getId()))) {
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

    public static void main(String[] args) {
        facebookCompanies = Excel.getInformation("/home/jacob/Documents/FacebookCompanyScraper/FB Page Links All.xlsx");

//        new Thread(new ScraperThread(0, facebookCompanies.size(), facebookCompanies)).start();

        new Thread(new ScraperThread(0, 300, facebookCompanies)).start();
        new Thread(new ScraperThread(300, 600, facebookCompanies)).start();
        new Thread(new ScraperThread(600, 900, facebookCompanies)).start();
        new Thread(new ScraperThread(900, 1200, facebookCompanies)).start();
        new Thread(new ScraperThread(1200, facebookCompanies.size(), facebookCompanies)).start();


        //emptyPostsOrNotFoundPagesScraper(notFoundPages, facebookCompanies);
        //emptyPostsOrNotFoundPagesScraper(emptyPostsPages, facebookCompanies);
    }
}

class ScraperThread implements Runnable {
    private int start;
    private int end;
    private List<FacebookCompany> facebookCompanies;

    public ScraperThread(int start, int end, List<FacebookCompany> facebookCompanies) {
        this.start = start;
        this.end = end;
        this.facebookCompanies = facebookCompanies;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            FacebookCompany facebookCompany = facebookCompanies.get(i);

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