package com.github.m0levich;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.findElement(By.id("button")).click();
        webDriver.findElement(By.id("first")).click();
        WebElement result = webDriver.findElement(By.xpath("//label[2]"));
        Assert.assertEquals(result.getText(), "Excellent!");
        WebElement button = webDriver.findElement(By.xpath("//input[1]"));
        assertThat(button.getAttribute("value")).isEqualToIgnoringCase("CLICK ME TOO!");
        button.click();
        WebElement returnButton = webDriver.findElement(By.xpath("//label[@id='back']/a"));
        Assert.assertEquals(returnButton.getText(), "Great! Return to menu");
        returnButton.click();
        webDriver.findElement(By.id("checkbox")).click();
        WebElement checkOne = webDriver.findElement(By.id("one"));
        checkOne.click();
        WebElement checkTwo = webDriver.findElement(By.id("two"));
        checkTwo.click();
        webDriver.findElement(By.id("go")).click();
        WebElement resultCheckboxes = webDriver.findElement(By.id("result"));
        Assert.assertEquals(resultCheckboxes.getText(),checkOne.getAttribute("value") + " " + checkTwo.getAttribute("value"));
        WebElement radioTwo = webDriver.findElement(By.id("radio_two"));
        radioTwo.click();
        webDriver.findElement(By.id("radio_go")).click();
        WebElement radioResult = webDriver.findElement(By.id("radio_result"));
        Assert.assertEquals(radioResult.getText(),radioTwo.getAttribute("value"));
        WebElement returnMenu = webDriver.findElement(By.xpath("//label[@id='back']/a"));
        Assert.assertEquals(returnMenu.getText(),"Great! Return to menu");
    }

    @AfterMethod
    public void closeDriver() {
        webDriver.quit();
    }
}
