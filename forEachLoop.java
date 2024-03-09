package assignment;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

public class forEachLoop {

    public static void main(String[] args)
    {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Hp\\Downloads\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver= new ChromeDriver();
        driver.get("http://www.flipkart.com/");

        System.out.println(driver.getCurrentUrl());
        driver.manage().window().maximize();

        //Get list of web-elements with tagName  - a
        List<WebElement> allLinks  = driver.findElements(By.tagName("a"));
        System.out.println(allLinks .size());

        //Traversing through the list and printing its text along with link address
        for(WebElement link: allLinks ){
            if(link.getAttribute("href")!= null)
             System.out.println(link.getText() + " - " + link.getAttribute("href"));
        }

        driver.quit();
    }
}