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

public class SeleniumHW {

    private WebDriver webDriver;

    @BeforeClass
    public void downloadDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void initDriver() {
        webDriver = new ChromeDriver();
    }

    @Test
    public void seleniumHW_2() {
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get("https://savkk.github.io/selenium-practice/");
        sectionSelection("button");
        webDriver.findElement(By.id("first")).click();
        WebElement result = webDriver.findElement(By.xpath("//label[2]"));
        Assert.assertEquals(result.getText(), "Excellent!");
        WebElement button = webDriver.findElement(By.xpath("//input[1]"));
        assertThat(button.getAttribute("value")).isEqualToIgnoringCase("CLICK ME TOO!");
        button.click();
        returnToMenu();
        sectionSelection("checkbox");
        WebElement checkOne = webDriver.findElement(By.id("one"));
        checkOne.click();
        WebElement checkTwo = webDriver.findElement(By.id("two"));
        checkTwo.click();
        webDriver.findElement(By.id("go")).click();
        WebElement resultCheckboxes = webDriver.findElement(By.id("result"));
        Assert.assertEquals(resultCheckboxes.getText(), checkOne.getAttribute("value") + " " + checkTwo.getAttribute("value"));
        WebElement radioTwo = webDriver.findElement(By.id("radio_two"));
        radioTwo.click();
        webDriver.findElement(By.id("radio_go")).click();
        WebElement radioResult = webDriver.findElement(By.id("radio_result"));
        Assert.assertEquals(radioResult.getText(), radioTwo.getAttribute("value"));
        returnToMenu();
        sectionSelection("select");
        WebElement selectHero = webDriver.findElement(By.name("hero"));
        Select select = new Select(selectHero);
        select.selectByVisibleText("Niklaus Wirth");
        WebElement selectLanguages = webDriver.findElement(By.name("languages"));
        Select selectLang = new Select(selectLanguages);
        selectLang.selectByVisibleText("Java");
        selectLang.selectByVisibleText("Basic");
        webDriver.findElement(By.id("go")).click();
        WebElement selectResultHero = webDriver.findElement(By.xpath("//label[@name='result'][1]"));
        Assert.assertEquals("Niklaus Wirth", selectResultHero.getText());
        WebElement selectResultLang = webDriver.findElement(By.xpath("//label[@name='result'][2]"));
        Assert.assertEquals("Java, Basic", selectResultLang.getText());
        returnToMenu();
        sectionSelection("form");
        fillFild("First Name:", "Ivan");
        fillFild("Last Name:", "Ivanov");
        fillFild("Email:", "ivantest@mail.ru");
        webDriver.findElement(By.xpath("//label[.='Sex:']/following-sibling::input[1]")).click();
        fillFild("Address:", "12, Lenina street,Moscow");
        webDriver.findElement(By.xpath("//textarea")).sendKeys("normal test information");
        webDriver.findElement(By.xpath("//label[.='Avatar:']/following-sibling::input[1]")).sendKeys(System.getProperty("user.dir") + "/picture.jpeg");
        webDriver.findElement(By.xpath("//input[@type=\"submit\"]")).click();
        returnToMenu();
        sectionSelection("iframe");
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
        sectionSelection("alerts");
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
        Assert.assertEquals(actualText.getText(),"Great!");
        webDriver.findElement(By.xpath("//button[@class='return']")).click();
        webDriver.switchTo().alert().accept();

        sectionSelection("table");

        webDriver.findElement(By.xpath("//td[.='Ernst Handel']//ancestor::tr//input")).click();
        webDriver.findElement(By.xpath("//td[.='Canada']//ancestor::tr//input")).click();
        webDriver.findElement(By.xpath("//input[@value='Delete']")).click();

        fillFildforTable("Company", "Magazzini");
        fillFildforTable("Contact","Giovanni Bennett");
        fillFildforTable("Country", "UK");
        webDriver.findElement(By.xpath("//input[@value='Add']")).click();
        returnToMenu();
    }

    @Test
    public void negativeAlertsTest(){
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get("https://savkk.github.io/selenium-practice/");
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

        Assert.assertEquals(existsElement(xpath),false);
    }



    @AfterMethod
    public void closeDriver() {
        webDriver.quit();
    }

    public void returnToMenu() {
        WebElement returnMenu = webDriver.findElement(By.xpath("//label[@id='back']/a"));
        Assert.assertEquals(returnMenu.getText(), "Great! Return to menu");
        returnMenu.click();
    }

    public void fillFild(String fieldTitle, String value) {
        webDriver.findElement(By.xpath("//label[.='" + fieldTitle + "']/following-sibling::input[1]")).sendKeys(value);
    }

    public void fillFildforTable(String fieldTitle, String value) {
        webDriver.findElement(By.xpath("//label[.='"+ fieldTitle +"']/following-sibling::input[@type='text']")).sendKeys(value);
    }

    public void sectionSelection(String id) {
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
}
