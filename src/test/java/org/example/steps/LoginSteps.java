package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginSteps {
    static WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--incognito");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-infobars");
        options.addArguments("--user-data-dir=/tpm/chrome-test-profile");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();


    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("el navegador esta abierto en la pagina {string}")
    public void el_navegador_esta_abierto_en_la_pagina(String url) {
        driver.get(url);
    }

    @Given("el usuario realiza clic en {string} para dirigirse a la pagina de login")
    public void el_usuario_realiza_clic_en_para_dirigirse_a_la_pagina_de_login(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    @When("el usuario ingresa en {string} el {string} y en {string} la {string}")
    public void el_usuario_ingresa_en_el_y_en_la(String xpath_username, String username, String xpath_password, String password) throws InterruptedException {
        driver.findElement(By.xpath(xpath_username)).clear();
        driver.findElement(By.xpath(xpath_username)).sendKeys(username);

        Thread.sleep(1000);

        driver.findElement(By.xpath(xpath_password)).clear();
        driver.findElement(By.xpath(xpath_password)).sendKeys(password);

        Thread.sleep(1000);
    }

    @When("hace click en el boton de login {string}")
    public void hace_click_en_el_boton_de_login(String xpath) throws InterruptedException {

        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(2000);
    }

    @Then("se deberia mostrar el campo {string} con el mensaje {string}")
    public void se_deberia_mostrar_el_campo_con_el_mensaje(String xpath, String message) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        String text = element.getText();
        if(text.contains(message)) {
            System.out.println("El campo con el mensaje se encuentra" + text);
        }else {
            throw new RuntimeException("El campo con el mensaje no se encuentra" + text);
        }
    }
}
