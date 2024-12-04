package org.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URISyntaxException;
import java.io.File;

import java.io.*;
import java.nio.file.FileSystems;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class spider {

    public static List<String> getClassInf() {
        /*
//        WebDriverManager.edgedriver().setup();
//
//        WebDriver driver = new EdgeDriver();
//
//        String url = "https://ehallapp.nju.edu.cn/jwapp/sys/wdkb/*default/index.do?t_s=1732623207550&amp_sec_version_=1&gid_=djlkWjBnWm92WFdZT2xmd0tLM1JZQVBHMzJ0N0FDQTMzTmFMVnVObEhQNENoanFSaEREYm5KdFFvTEZDN201L3RmUVpVMU90ekhpejRJVEhpMUhNQ3c9PQ&EMAP_LANG=zh&THEME=#/xskcb";
//        List<String> classId = new ArrayList<>();
//        try {
//            driver.get(url);
//
//            Thread.sleep(30000);
//
//            List<WebElement> rows = driver.findElements(By.xpath("/html/body/main/article/section/div[5]/div[2]/div/div[4]/div[2]/div/table[1]/tbody/tr"));
//
//            for (WebElement row : rows) {
//                String classIdNum = row.findElement(By.xpath("./td[2]/span")).getText();
//                classId.add(classIdNum);
//            }
//            driver.quit();
//            return classId ;
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        try{
            return getStrings();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> getStrings() throws Exception {
        List<String> list = new ArrayList<>();

        String currentDir = new File(".").getCanonicalPath();

//        String currentDir = new File(spider.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();

        String FS = File.separator;
        String PySpiderPath = currentDir + FS + "src" + FS + "main" + FS + "java" + FS +
                "org.example" + FS + "utils" + FS + "spiderPY.py";

//        String PySpiderPath = currentDir + FS + "spiderPY.py";
//        String PyScriptPath = currentDir + "/src/test/resources/scripts/";

//        ProcessBuilder pb = new ProcessBuilder("python", PySpiderPath);
//        pb.directory(new File(System.getProperty("user.dir")));
//
//        Process p = pb.start();
        String[] strs = {"python" , PySpiderPath};
        Process p = Runtime.getRuntime().exec(strs);

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

        for(String line; (line = br.readLine()) != null; ){
            list.add(line);
        }
        return list;

         */

        try {
            return getInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
