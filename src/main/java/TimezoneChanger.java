import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.*;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

public class TimezoneChanger {
    public static void changeTimezoneToUS(String rootFolder) {
        File root = new File(rootFolder);

        for (File f : root.listFiles()) {
            try {
                FileInputStream fis = new FileInputStream(f);

                Workbook workbook = new XSSFWorkbook(fis); // Using XSSF for xlsx format, for xls use HSSF
                Sheet sheet = workbook.getSheetAt(0);

                for (Row row : sheet) { //iterating over each row
                    Iterator cellIterator = row.cellIterator();
                    String dateUa = row.getCell(4).getStringCellValue();
                    String timeUa = row.getCell(5).getStringCellValue();
                    String dateUs = null;
                    String timeUs = null;

                    try {
                        DateFormat dateUaFormat = new SimpleDateFormat("dd-MMMM-yyyy K:mm a");
                        dateUaFormat.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));

                        Date uaTime = dateUaFormat.parse(String.format("%s %s", dateUa, timeUa));
                        DateTime estTime = new DateTime(uaTime.getTime()).withZone(DateTimeZone.forID("EST")); //"America/New_York"

                        dateUs = String.format("%s-%s-%s", estTime.getDayOfMonth(), new DateFormatSymbols().getMonths()[estTime.getMonthOfYear() - 1], estTime.getYear());
                        timeUs = new SimpleDateFormat("h:mma").format(new SimpleDateFormat("HH:mm").parse(String.format("%s:%s", estTime.getHourOfDay(), estTime.getMinuteOfHour())));

                        while (cellIterator.hasNext()) { //Iterating over each cell (column wise)  in a particular row.
                            Cell cell = (Cell) cellIterator.next();

                            if (cell.getColumnIndex() == 4) {
                                cell.setCellValue(dateUs);
                            } else if (cell.getColumnIndex() == 5) {
                                cell.setCellValue(timeUs);
                                break;
                            }
                        }

                    } catch (ParseException e) {
                        System.err.println(String.format("File: %s; Row: %s", f.getName(), row.getRowNum()));
                    }
                }
                fis.close();

                FileOutputStream fos = new FileOutputStream(f);
                workbook.write(fos);
                fos.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        changeTimezoneToUS("/home/jacob/Desktop/result/");
    }
}