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
    private static boolean loggedIn = false;

    public static List<String[]> getInfo () throws Exception {
        System.out.println("getInfo: 开始获取课程信息");

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
            WebElement tdTargetName = row.findElements(By.tagName("td")).get(2);
            WebElement spanName = tdTargetName.findElement(By.tagName("span"));
            WebElement tdTargetTime = row.findElements(By.tagName("td")).get(6).findElement(By.tagName("span"));
            WebElement tdTargetTeacher = row.findElements(By.tagName("td")).get(4).findElement(By.tagName("span")) ;
            String NO = spanNO.getAttribute("title");
            String name = spanName.getAttribute("title");
            String time = tdTargetTime.getAttribute("title");
            String teachername = tdTargetTeacher.getAttribute("title") ;
            String[] element = {NO, time, teachername, name};
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
        setLoggedIn(true);
        return list;
    }


    public static String getQRCode() {
        try {
            System.out.println("getQRCode: 开始获取二维码");
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
            WebElement qrImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("qr_img")));
            System.out.println("getQRCode: 二维码元素已找到");
            
            String qrCodeUrl = qrImage.getAttribute("src");
            System.out.println("getQRCode: 原始二维码URL: " + qrCodeUrl);
            if (qrCodeUrl == null || qrCodeUrl.isEmpty()) {
                System.out.println("getQRCode: 二维码URL为空");
                return null;
            }

            String imagePath = downloadImage(qrCodeUrl);
            System.out.println("getQRCode: 保存后的图片路径: " + imagePath);
            return imagePath;
        } catch (Exception e) {
            System.out.println("getQRCode: 获取二维码失败 - " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    private static String downloadImage(String imageUrl) throws IOException {
        // 获取项目根目录
        String projectPath = System.getProperty("user.dir");
        String savePath = projectPath + "/classSchedule/classSchedule/src/main/java/org/example/images";
        Path folderPath = Paths.get(savePath);
        System.out.println("图片保存路径: " + folderPath);
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
            System.out.println("图片已保存到: " + savePath + pathSeparator + fileName);
        } finally {
            connection.disconnect();
        }
        // 返回实际文件路径
        return "/qrcode/" + fileName;  // 返回Web访问路径
    }
    public static void initialization(){
        System.out.println("initialization: 开始初始化");
        loggedIn = false;
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        System.out.println("initialization: EdgeDriver已创建");
        driver.get(url);
        System.out.println("initialization: 已导航到目标URL");
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean status) {
        loggedIn = status;
    }

    public static boolean checkLoginStatus() {
        try {
            if (driver == null) {
                System.out.println("checkLoginStatus: driver is null");
                return false;
            }
            System.out.println("checkLoginStatus: 开始检查登录状态");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            // 检查二维码元素是否消失
            try {
                WebElement qrImage = driver.findElement(By.id("qr_img"));
                System.out.println("checkLoginStatus: 二维码还在，未登录");
                return false;
            } catch (Exception e) {
                // 如果找不到二维码元素，说明已经登录成功
                System.out.println("checkLoginStatus: 二维码已消失，登录成功");
                setLoggedIn(true);
                return true;
            }
        } catch (Exception e) {
            System.out.println("checkLoginStatus: 登录状态检查失败 - " + e.getMessage());
            return false;
        }
    }

}