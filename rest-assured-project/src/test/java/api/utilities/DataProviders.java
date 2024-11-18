package api.utilities;

import org.testng.annotations.DataProvider;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class DataProviders {

	@DataProvider(name = "excelData")
    public static Iterator<Object[]> getData() throws IOException {
        String filePath = System.getProperty("user.dir")+"//Testdata//Userdata.xlsx";  // Replace with your Excel file path
        String sheetName = "Sheet1";                  // Replace with your sheet name

        // Use ExcelUtility to fetch the data
        List<Object[]> excelData = XLUtility.getExcelData(filePath, sheetName);

        // Return the data as an iterator
        return excelData.iterator();
    }
}
