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
import java.util.List;

public class spider {
    public static List<String> getInfo () throws Exception {

        List<String> list = new ArrayList<String>();
        String url = "https://ehallapp.nju.edu.cn/jwapp/sys/wdkb/*default/index.do?t_s=1732623207550&amp_sec_version_=1&gid_=djlkWjBnWm92WFdZT2xmd0tLM1JZQVBHMzJ0N0FDQTMzTmFMVnVObEhQNENoanFSaEREYm5KdFFvTEZDN201L3RmUVpVMU90ekhpejRJVEhpMUhNQ3c9PQ&EMAP_LANG=zh&THEME=#/xskcb";

        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();
        driver.get(url);

        // 页面跳转成功
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));  // 设置最大等待时间为 30 秒
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
            WebElement tdTarget = row.findElements(By.tagName("td")).get(1);
            WebElement span = tdTarget.findElement(By.tagName("span"));
            String title = span.getAttribute("title");
            list.add(title);
        }

        driver.quit();
        return list;
    }
}