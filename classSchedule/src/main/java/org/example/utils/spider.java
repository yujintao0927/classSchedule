package org.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class spider {

    private static WebDriver driver;

    private static final String url = "https://ehallapp.nju.edu.cn/jwapp/sys/wdkb/*default/index.do?t_s=1732623207550&amp_sec_version_=1&gid_=djlkWjBnWm92WFdZT2xmd0tLM1JZQVBHMzJ0N0FDQTMzTmFMVnVObEhQNENoanFSaEREYm5KdFFvTEZDN201L3RmUVpVMU90ekhpejRJVEhpMUhNQ3c9PQ&EMAP_LANG=zh&THEME=#/xskcb";
    public static List<String[]> getInfo () throws Exception {

        List<String[]> list = new ArrayList<>();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
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


    public static String getQRCode() {



        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(30));
        WebElement qrImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("qr_img")));

        try {
            return downloadImage(qrImage.getAttribute("src"));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String downloadImage(String imageUrl) throws IOException {
        // 创建保存文件夹（如果不存在）
        String savePath = ".\\classSchedule\\src\\main\\java\\org\\example\\images";//todo 要把服务器上的绝对地址加过来
        Path folderPath = Paths.get(savePath);
        System.out.println(folderPath);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        // 连接并下载图片
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000); // 超时时间
        connection.setReadTimeout(10000);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "qr_code_" + timestamp + ".png";

        String pathSeparator = FileSystems.getDefault().getSeparator();
        try (InputStream inputStream = connection.getInputStream(); FileOutputStream outputStream = new FileOutputStream(savePath + pathSeparator + fileName)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
//            System.out.println("图片保存成功：" + savePath + "/" + fileName);
        } finally {
            connection.disconnect();
        }
        return savePath + fileName;
    }
    public static void initialization(){
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.get(url);
    }


}