import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<FacebookCompany> facebookCompanies = Excel.getInformation("C:\\Users\\Jacob\\Documents\\Projects\\FacebookCompanyScraper\\FB Page Links All.xlsx");


        for (FacebookCompany facebookCompany : facebookCompanies) {
            Scraper scraperThread = new Scraper(facebookCompany);
            scraperThread.run();
//            scraperThread.setName(facebookCompany.getName());
//            scraperThread.start();
//
//            synchronized (scraperThread) {
//                try {
//                    System.out.println("Waiting for "+ scraperThread.getName() +" to complete...");
//                    scraperThread.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(scraperThread.getName() + "Finished");
//            }
        }
    }
}