package org.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class spider {
    public static List<String[]> getInfo () throws Exception {

        List<String[]> list = new ArrayList<>();
        String url = "https://ehallapp.nju.edu.cn/jwapp/sys/wdkb/*default/index.do?t_s=1732623207550&amp_sec_version_=1&gid_=djlkWjBnWm92WFdZT2xmd0tLM1JZQVBHMzJ0N0FDQTMzTmFMVnVObEhQNENoanFSaEREYm5KdFFvTEZDN201L3RmUVpVMU90ekhpejRJVEhpMUhNQ3c9PQ&EMAP_LANG=zh&THEME=#/xskcb";

        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bh-headerBar-title")));

        Thread.sleep(3000);

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl == null) {
            throw new Exception();
        }
        driver.get(currentUrl);
        WebElement tbody = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        for (WebElement row : rows) {

            WebElement tdTargetNO = row.findElements(By.tagName("td")).get(1);
            WebElement spanNO = tdTargetNO.findElement(By.tagName("span"));
            WebElement tdTargetTime = row.findElements(By.tagName("td")).get(6).findElement(By.tagName("span"));
            WebElement tdTargetTeacher = row.findElements(By.tagName("td")).get(4).findElement(By.tagName("span")) ;
            String NO = spanNO.getAttribute("title");
            String time = tdTargetTime.getAttribute("title");
            String teachername = tdTargetTeacher.getAttribute("title") ;
            String[] element = {NO, time, teachername};
            list.add(element);
        }
        driver.quit();
        for (int i = 0; i < list.size(); i++) {
            String timeAndLocation = list.get(i)[1].replaceAll("\\s+", " ").trim();
            list.get(i)[1] = timeAndLocation ;
            if(timeAndLocation.charAt(timeAndLocation.indexOf(",") + 1) == '周' && timeAndLocation.contains(",")) {
                String[] strings = timeAndLocation.split(",") ;
                if(strings[0].charAt(1) > strings[1].charAt(1)) {
                    list.get(i)[1] = strings[1] + "," + strings[0] ;
                }
            }
        }
        return list;
    }
}