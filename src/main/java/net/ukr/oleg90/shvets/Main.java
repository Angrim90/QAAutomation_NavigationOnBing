package net.ukr.oleg90.shvets;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * @author Shvets Oleg
 * @version 1.0
 */
/*Разработать скрипт в виде обычного приложения (с использованием метода main():
1. Открыть главную панель происковой системы Bing
2. Перейти в раздел поиска изображений. Дождаться, что заголовок страницы имеет название "Лента изображений Bing"
3. Выполнить прокрутку несколько раз. Каждый раз проверять что при достижении низа страницы подгружаются новые блоки с изображениями
4. В поисковую строку ввести слово без последней буквы,
 Дождатсья появления слова целиком в выпадающем списке, Выбрать искомое слово и дождаться загрузки поискового запроса
5.Установить фильтр Дата - "В прошлом месяце" , дождаться обновления результатов
6. Нажать на первый из результатов поиска, дождаться перехода в режим слайд шоу
7. Выполнить переключение на следующее, предыдущее изображение,
после переключения изображений необходимо дождаться обновления очереди изображений
8.Нажать на отображаемое изображение в режиме слайд шоу и удостовериться что картинка загрузилась в отдельной вкладке\окне*/

public class Main extends BaseScript {

    static final String searchEnteringText = "autoca";
    static final String searchText = "autocar";


    public static void main(String[] args) {
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //1
        driver.get("https://www.bing.com/");
       driver.manage().window().maximize();
        WebElement pictureButton = driver.findElement(By.id("scpt1"));
        //2
        pictureButton.click();
        (new WebDriverWait(driver, 20)).until(ExpectedConditions
                .titleIs("Лента изображений Bing"));
        WebElement title = driver.findElement(By.xpath("//title[text()='Лента изображений Bing']"));

        String titleText = title.getText();

        if (!titleText.contains("Лента изображений Bing"))
            throw new NoSuchElementException("page \"Images\" do not loaded (2)");

        //3
        int picturesCount = driver.findElements(By.xpath("//div[@class = 'imgpt']")).size();

        System.out.println(picturesCount);
        scrollTop(driver);
        //TODO не скроллит на маленьком окне

        (new WebDriverWait(driver, 20)).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//img[@data-bm]"), picturesCount));
        int nextPicturesCount = driver.findElements(By.xpath("//div[@class = 'imgpt']")).size();

        System.out.println(nextPicturesCount);
            if (picturesCount >= nextPicturesCount) {
                (new WebDriverWait(driver, 20)).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//img[@data-bm]"), picturesCount));
                System.out.println("waiting...");
                scrollTop(driver);
                System.out.println(nextPicturesCount);

            } else System.out.println("New images have been loaded(3)");

        scrollBottom(driver);
        //4
        String searchingElement = "";
        searchingElement = "//li[@role = 'option' ]";
        setSearchEnteringText(driver, By.xpath(searchingElement));
        //5
        searchingElement = "fltIdtLnk";
        WebElement filter = driver.findElement(By.id(searchingElement));
       clickButton(driver,filter);
        searchingElement = "//ul[@class='ftrUl']/li/span[@title ='Фильтр: Дата' ]/span[@class='ftrTB ftrP11']";
        WebElement data = driver.findElement(By.xpath(searchingElement));
        clickButton(driver,data);
        searchingElement = "//div[@class='ftrD_MmVert']/a[@title = 'В прошлом месяце']";
        WebElement setDate = driver.findElement(By.xpath(searchingElement));
        clickButton(driver,setDate);
        //6
        searchingElement = "//div[@class='img_cont hoff']";
        (new WebDriverWait(driver, 15)).until(ExpectedConditions
                .elementToBeClickable(By.xpath(searchingElement)));

        WebElement element1 = driver.findElement(By.xpath(searchingElement));
        (new WebDriverWait(driver, 15)).until(ExpectedConditions
                .visibilityOf(element1));
        searchingElement = "//div[@class = 'iuscp varh']";
            List<WebElement > pictures = driver.findElements(By.xpath(searchingElement));
            WebElement firstPicture = pictures.get(0);

        clickButton(driver,firstPicture);
        (new WebDriverWait(driver, 15)).until(ExpectedConditions
                .visibilityOf(driver.findElement(By.id("OverlayIFrame"))));
        driver.switchTo().frame(driver.findElement(By.id("OverlayIFrame")));

        WebElement nextButton = driver.findElement
                (By.xpath("//a[@title = 'Следующий результат поиска изображений']"));
        (new WebDriverWait(driver, 15)).until(ExpectedConditions
                .visibilityOf(nextButton));
        if(nextButton.isDisplayed()){
            System.out.println("We are on slide show(6) ");
        }
        else System.out.println("Something wrong (6)");

        //7
    int pictureNumber =0;
        WebElement activePicture = driver.findElement(By.xpath("//div/a[@idx='0']"));

        WebElement nextPicture = driver.findElement(By.xpath("//div/a[@idx='1']"));
        clickButton(driver,nextButton);

        if(activePicture.getAttribute("class").equals("iol_fst iol_fsst")){
            System.out.println(" i feel bad :(");

        }
        else if (nextPicture.getAttribute("class").equals("iol_fst iol_fsst"))
            System.out.println("its OK im works right :)");
        else System.out.println("something wrong");

        WebElement previewButton = driver.findElement
                (By.xpath("//a[@id='iol_navl' and @title = 'Предыдущий результат поиска изображений']"));
        previewButton.click();
        //8 Нажать на отображаемое изображение в режиме слайд шоу и удостовериться что картинка загрузилась в отдельной вкладке\окне*/
        String windowHandler = driver.getWindowHandle();

        WebElement image = driver.findElement(By.xpath("//img[@class='mainImage accessible nofocus']"));
        image.click();

        Set<String> handlerSet = driver.getWindowHandles();
        if(handlerSet.size()>1&&handlerSet.contains(windowHandler)){
            System.out.println("Image loads in another handle");
        }
        else System.out.println("Something wrong(8)");
        driver.quit();


    }

    public static void scrollTop(WebDriver driver) {

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("window.scrollBy(0,500)", "");
        executor.executeScript("window.scrollBy(0,500)", "");
        executor.executeScript("window.scrollBy(0,500)", "");



    }

    public static void scrollBottom(WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollTo(0,0)", "");
    }
public static WebElement clickButton( WebDriver driver,WebElement element){
    WebElement webElement = element;
    (new WebDriverWait(driver, 15)).until(ExpectedConditions
            .elementToBeClickable(webElement));
    webElement.click();
    return webElement;
}

    public static void setSearchEnteringText(WebDriver driver, By by) {
        (new WebDriverWait(driver, 10)).until(presenceOfElementLocated(By.cssSelector("#sb_form_q")));
        WebElement inputText = driver.findElement(By.cssSelector("#sb_form_q"));
        inputText.clear();
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(inputText));
        inputText.sendKeys(searchEnteringText);
        List<WebElement> searchingElement = driver.findElements(by);
        Actions action = new Actions(driver);
        for (int i = 0; i < searchingElement.size(); i++) {
            String dropText = searchingElement.get(i).getText();
            action.sendKeys(Keys.ARROW_DOWN).build().perform();
            if (dropText.length() == (searchEnteringText.length() + 1) && dropText.equals(searchText)) {
                action.sendKeys(Keys.ENTER).build().perform();
                return;
            }
        }
    }


}