package assignment;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class stockPrice {

    public static void main(String[] args) throws IOException {

        HashMap<String, String> dataFromExcel = excelSheetData();
        HashMap<String, String> dataFromWeb = webTableData();

        System.out.println("Comparing Values stored in the Two Hashmaps");

        Set<String> keyList= dataFromExcel.keySet();
        for(String key: keyList)
        {
            System.out.println(key);
            if(dataFromExcel.get(key).equals(dataFromWeb.get(key)))
            {
                System.out.println("Pass for the " + key);
            }
            else
            {
                System.out.println("Fail for the " + key);
            }
        }
    }

    public static HashMap<String, String> excelSheetData() throws IOException {
        System.out.println("Fetching Data from Excel sheet");

        HashMap<String, String> excelmap = new HashMap<>();

        FileInputStream file = new FileInputStream("C:\\Users\\Hp\\Desktop\\stock.xlsx");
        Sheet sh = WorkbookFactory.create(file).getSheet("Sheet1");
        int lastRow = sh.getLastRowNum();
        System.out.println(lastRow);
        for (int i = 0; i <= lastRow; i++) {
            Row curr_row = sh.getRow(i);

            Cell KeyCell = curr_row.getCell(0);
            String Key = KeyCell.getStringCellValue().trim();
            Cell ValueCell = curr_row.getCell(1);
            String Value = ValueCell.getStringCellValue().trim();

            excelmap.put(Key, Value);
        }
        return excelmap;
    }

    public static HashMap<String, String> webTableData() {

        System.out.println("Fetching Data from Website");

        System.setProperty("web driver.chrome.driver", "C:\\Users\\Hp\\Downloads\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https:/money.rediff.com/losers/bse/daily/groupall");
        driver.manage().window().maximize();
        //Thread.sleep(5000);
        List<WebElement> companyList = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[1]"));
        List<WebElement> currentPriceList = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[4]"));

        HashMap<String, String> webtablemap = new HashMap<String, String>();

        for (int i = 0; i < 10; i++) {
            String coloumn1data = companyList.get(i).getText();
            String coloumn2data = currentPriceList.get(i).getText();
            webtablemap.put(coloumn1data, coloumn2data);
        }
        driver.quit();
        return webtablemap;
    }


}
