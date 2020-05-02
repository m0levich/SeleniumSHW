package com.github.m0levich;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumPracticePageTest {

    private WebDriver webDriver;

    @BeforeClass
    public void downloadDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void initDriver() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().window().maximize();
    }

    @Test
    public void buttonTest() {
        String buttonSelection = "button";
        sectionSelection(buttonSelection);
        webDriver.findElement(By.id("first")).click();
        WebElement result = webDriver.findElement(By.xpath("//label[2]"));
        Assert.assertEquals(result.getText(), "Excellent!","Result is not \"Excellent!\"");
        WebElement button = webDriver.findElement(By.xpath("//input[1]"));
        Assert.assertTrue(button.getAttribute("value").equalsIgnoreCase("CLICK ME TOO!"),"the button does not contain the text \"click me too!\"");
        button.click();
        returnToMenu();
        cookiesAdd(buttonSelection);
        checkCookieValue(buttonSelection);
    }

    @Test
    public void checkboxTest() {
        String checkbox = "checkbox";
        sectionSelection(checkbox);
        WebElement checkOne = webDriver.findElement(By.id("one"));
        checkOne.click();
        WebElement checkTwo = webDriver.findElement(By.id("two"));
        checkTwo.click();
        webDriver.findElement(By.id("go")).click();
        WebElement resultCheckboxes = webDriver.findElement(By.id("result"));
        Assert.assertEquals(resultCheckboxes.getText(), checkOne.getAttribute("value") + " " + checkTwo.getAttribute("value"),"Checkbox result failed");
        WebElement radioTwo = webDriver.findElement(By.id("radio_two"));
        radioTwo.click();
        webDriver.findElement(By.id("radio_go")).click();
        WebElement radioResult = webDriver.findElement(By.id("radio_result"));
        Assert.assertEquals(radioResult.getText(), radioTwo.getAttribute("value"),"Radio result failed");
        returnToMenu();
        cookiesAdd(checkbox);
        checkCookieValue(checkbox);
    }

    @Test
    public void selectTest() {
        String selector = "select";
        sectionSelection(selector);
        WebElement selectHero = webDriver.findElement(By.name("hero"));
        Select select = new Select(selectHero);
        select.selectByVisibleText("Niklaus Wirth");
        WebElement selectLanguages = webDriver.findElement(By.name("languages"));
        Select selectLang = new Select(selectLanguages);
        selectLang.selectByVisibleText("Java");
        selectLang.selectByVisibleText("Basic");
        webDriver.findElement(By.id("go")).click();
        WebElement selectResultHero = webDriver.findElement(By.xpath("//label[@name='result'][1]"));
        Assert.assertEquals("Niklaus Wirth", selectResultHero.getText(),"Result failed");
        WebElement selectResultLang = webDriver.findElement(By.xpath("//label[@name='result'][2]"));
        Assert.assertEquals("Java, Basic", selectResultLang.getText(),"Result failed");
        returnToMenu();
        cookiesAdd(selector);
        checkCookieValue(selector);
    }

    @Test
    public void formTest() {
        String form = "form";
        sectionSelection(form);
        fillFild("First Name:", "Ivan");
        fillFild("Last Name:", "Ivanov");
        fillFild("Email:", "ivantest@mail.ru");
        webDriver.findElement(By.xpath("//label[.='Sex:']/following-sibling::input[1]")).click();
        fillFild("Address:", "12, Lenina street,Moscow");
        webDriver.findElement(By.xpath("//textarea")).sendKeys("normal test information");
        webDriver.findElement(By.xpath("//label[.='Avatar:']/following-sibling::input[1]")).sendKeys(System.getProperty("user.dir") + "/picture.jpeg");
        webDriver.findElement(By.xpath("//input[@type=\"submit\"]")).click();
        returnToMenu();
        cookiesAdd(form);
        checkCookieValue(form);
    }

    @Test
    public void iFrameTest() {
        String iFrame = "iframe";
        sectionSelection(iFrame);
        webDriver.switchTo().frame(0);
        WebElement codeElement = webDriver.findElement(By.id("code"));
        String code = codeElement.getText();

        for (String a : code.split(" ")) {
            code = a;
        }

        webDriver.switchTo().defaultContent();
        webDriver.findElement(By.xpath("//input[@name='code']")).sendKeys(code);
        webDriver.findElement(By.xpath("//input[@name='ok']")).click();
        returnToMenu();
        cookiesAdd(iFrame);
        checkCookieValue(iFrame);
    }

    @Test
    public void alertTest() {
        String alerts = "alerts";
        sectionSelection(alerts);
        webDriver.findElement(By.xpath("//button[@class='get']")).click();
        Alert alertWithPassword = webDriver.switchTo().alert();
        String password = alertWithPassword.getText();

        for (String a : password.split(" ")) {
            password = a;
        }

        alertWithPassword.accept();
        webDriver.findElement(By.xpath("//button[@class='set']")).click();
        Alert entryPassword = webDriver.switchTo().alert();
        entryPassword.sendKeys(password);
        entryPassword.accept();
        WebElement actualText = webDriver.findElement(By.xpath("//button[@class='set']/following::label"));
        Assert.assertEquals(actualText.getText(), "Great!","Result not displayed");
        webDriver.findElement(By.xpath("//button[@class='return']")).click();
        webDriver.switchTo().alert().accept();
        cookiesAdd(alerts);
        checkCookieValue(alerts);
    }

    @Test
    public void tableTest() {
        String table = "table";
        sectionSelection(table);
        webDriver.findElement(By.xpath("//td[.='Ernst Handel']//ancestor::tr//input")).click();
        webDriver.findElement(By.xpath("//td[.='Canada']//ancestor::tr//input")).click();
        webDriver.findElement(By.xpath("//input[@value='Delete']")).click();
        Company company = new Company("Magazzini","Giovanni Bennett","UK");
        fillFildforTable(company);
        webDriver.findElement(By.xpath("//input[@value='Add']")).click();
        returnToMenu();
        cookiesAdd(table);
        checkCookieValue(table);
    }

    @Test
    public void negativeAlertsTest() {
        sectionSelection("alerts");
        webDriver.findElement(By.xpath("//button[@class='get']")).click();
        Alert alertWithPassword = webDriver.switchTo().alert();
        String password = alertWithPassword.getText();
        alertWithPassword.accept();
        webDriver.findElement(By.xpath("//button[@class='set']")).click();

        Alert entryPassword = webDriver.switchTo().alert();
        entryPassword.sendKeys(password);
        entryPassword.accept();

        String xpath = "//button[@class='set']/following::label";

        Assert.assertEquals(existsElement(xpath), false, "Element is displayed");
        cookiesAdd("negativeTest");
        checkCookieValue("negativeTest");
    }


    @AfterMethod
    public void closeDriver() {
        webDriver.quit();
    }

    private void returnToMenu() {
        WebElement returnMenu = webDriver.findElement(By.xpath("//label[@id='back']/a"));
        Assert.assertEquals(returnMenu.getText(), "Great! Return to menu", "Button with text fail");
        returnMenu.click();
    }

    private void fillFild(String fieldTitle, String value) {
        webDriver.findElement(By.xpath("//label[.='" + fieldTitle + "']/following-sibling::input[1]")).sendKeys(value);
    }

    private void fillFildforTable(Company company) {
        webDriver.findElement(By.xpath("//label[.='Company']/following-sibling::input[@type='text']")).sendKeys(company.getCompany());
        webDriver.findElement(By.xpath("//label[.='Contact']/following-sibling::input[@type='text']")).sendKeys(company.getContact());
        webDriver.findElement(By.xpath("//label[.='Country']/following-sibling::input[@type='text']")).sendKeys(company.getCountry());
    }

    private void sectionSelection(String id) {
        webDriver.findElement(By.id(id)).click();
    }

    private boolean existsElement(String xpath) {
        try {
            webDriver.findElement(By.xpath(xpath));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private void cookiesAdd(String name) {
        Cookie cookie = new Cookie(name, "done");
        webDriver.manage().addCookie(cookie);
    }

    private void checkCookieValue(String name){
        Assert.assertEquals(webDriver.manage().getCookieNamed(name).getValue(),"done","Check cookie value fail");
    }


}
