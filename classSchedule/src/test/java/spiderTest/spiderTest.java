package spiderTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.utils.spider;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class spiderTest {
    @Test
    public void climbClass() {
        WebDriverManager.edgedriver().setup();

        WebDriver driver = new EdgeDriver();

        String url = "https://ehallapp.nju.edu.cn/jwapp/sys/wdkb/*default/index.do?t_s=1732623207550&amp_sec_version_=1&gid_=djlkWjBnWm92WFdZT2xmd0tLM1JZQVBHMzJ0N0FDQTMzTmFMVnVObEhQNENoanFSaEREYm5KdFFvTEZDN201L3RmUVpVMU90ekhpejRJVEhpMUhNQ3c9PQ&EMAP_LANG=zh&THEME=#/xskcb";
        List<String> classId = new ArrayList<>();
        try {
            driver.get(url);

            // 等待页面加载完成
            Thread.sleep(30000); // 或者使用 WebDriverWait

            // 获取表格内容
            List<WebElement> rows = driver.findElements(By.xpath("/html/body/main/article/section/div[5]/div[2]/div/div[4]/div[2]/div/table[1]/tbody/tr"));

            for (WebElement row : rows) {
                String classIdNum = row.findElement(By.xpath("./td[2]/span")).getText();
                classId.add(classIdNum);
            }
            driver.quit();
            for (int i = 0; i < classId.size(); i++) {
                System.out.println(classId.get(i));
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void delete() {
        String projectPath = System.getProperty("user.dir");
        String savePath = projectPath + "/classSchedule/src/main/java/org/example/images";
        Path folderPath = Paths.get(savePath);
        spider.deleteImage(folderPath + "/" + "qr_code_20241222_013443.png") ;
//        spider.deleteImage("D:\\classSchedule\\classSchedule\\src\\main\\java\\org\\example\\images\\qr_code_20241222_002723.png") ;
    }
}
