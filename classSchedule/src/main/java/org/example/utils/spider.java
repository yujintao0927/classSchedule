package org.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class spider {

    public static List<String> getClassInf() {
        WebDriverManager.edgedriver().setup();

        WebDriver driver = new EdgeDriver();

        String url = "https://ehallapp.nju.edu.cn/jwapp/sys/wdkb/*default/index.do?t_s=1732623207550&amp_sec_version_=1&gid_=djlkWjBnWm92WFdZT2xmd0tLM1JZQVBHMzJ0N0FDQTMzTmFMVnVObEhQNENoanFSaEREYm5KdFFvTEZDN201L3RmUVpVMU90ekhpejRJVEhpMUhNQ3c9PQ&EMAP_LANG=zh&THEME=#/xskcb";
        List<String> classId = new ArrayList<>();
        try {
            driver.get(url);

            Thread.sleep(30000);

            List<WebElement> rows = driver.findElements(By.xpath("/html/body/main/article/section/div[5]/div[2]/div/div[4]/div[2]/div/table[1]/tbody/tr"));

            for (WebElement row : rows) {
                String classIdNum = row.findElement(By.xpath("./td[2]/span")).getText();
                classId.add(classIdNum);
            }
            driver.quit();
            return classId ;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
