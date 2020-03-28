package com.github.m0levich;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        webDriver.findElement(By.id("button")).click();
        webDriver.findElement(By.id("first")).click();
        WebElement result = webDriver.findElement(By.xpath("//label[2]"));
        Assert.assertEquals(result.getText(), "Excellent!");
        WebElement button = webDriver.findElement(By.xpath("//input[1]"));
        assertThat(button.getAttribute("value")).isEqualToIgnoringCase("CLICK ME TOO!");
        button.click();
        returnToMenu();
        webDriver.findElement(By.id("checkbox")).click();
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
        webDriver.findElement(By.xpath("//a[.='Select']")).click();
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
        webDriver.findElement(By.id("form")).click();
        fillFild("First Name:", "Ivan");
        fillFild("Last Name:", "Ivanov");
        fillFild("Email:", "ivantest@mail.ru");
        webDriver.findElement(By.xpath("//label[.='Sex:']/following-sibling::input[1]")).click();
        fillFild("Address:", "12, Lenina street,Moscow");
        webDriver.findElement(By.xpath("//textarea")).sendKeys("normal test information");
        webDriver.findElement(By.xpath("//label[.='Avatar:']/following-sibling::input[1]")).sendKeys(System.getProperty("user.dir") + "/picture.jpeg");
        webDriver.findElement(By.xpath("//input[@type=\"submit\"]")).click();
        returnToMenu();
        webDriver.findElement(By.id("iframe")).click();
        webDriver.switchTo().frame(0);
        WebElement codeElement = webDriver.findElement(By.id("code"));
        String code = codeElement.getText();

        for (String retval : code.split(" ")) {
            code = retval;
        }

        webDriver.switchTo().defaultContent();
        webDriver.findElement(By.xpath("//input[@name='code']")).sendKeys(code);
        webDriver.findElement(By.xpath("//input[@name='ok']")).click();
        returnToMenu();
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
}
