import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Excel {
    public static List<FacebookCompany> getInformation(String path) {
        List<FacebookCompany> facebookCompanies = new ArrayList<FacebookCompany>();

        try {
            FileInputStream fis = new FileInputStream(path);

            Workbook workbook = new XSSFWorkbook(fis); // Using XSSF for xlsx format, for xls use HSSF
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) { //iterating over each row
                FacebookCompany facebookCompany = new FacebookCompany();
                Iterator cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) { //Iterating over each cell (column wise)  in a particular row.
                    Cell cell = (Cell) cellIterator.next();

                    if (cell.getColumnIndex() == 0) {
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                            facebookCompany = null;
                            break;
                        }
                        facebookCompany.setId((int) cell.getNumericCellValue());
                    } else if (cell.getColumnIndex() == 1) {
                        facebookCompany.setName(String.valueOf(cell.getStringCellValue()));
                    } else if (cell.getColumnIndex() == 2) {
                        facebookCompany.setLink(String.valueOf(cell.getStringCellValue()));
                    }
                }
                if (facebookCompany != null){
                    facebookCompanies.add(facebookCompany);
                }
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return facebookCompanies;
    }

    public void writeToFile(File file, String text) {

    }
}