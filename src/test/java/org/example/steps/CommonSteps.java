package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CommonSteps {

    public static WebDriver driver;

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
    public void el_navegador_esta_abierto_en_la_pagina(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(4000);
    }

    @Given("el usuario realiza clic en {string} para dirigirse a la pagina de login")
    public void el_usuario_realiza_clic_en_para_dirigirse_a_la_pagina_de_login(String xpath) throws InterruptedException {
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(2000);
    }

    @When("el usuario navega a la página de {string}")
    public void el_usuario_navega_a_la_pagina_de(String linkText) throws InterruptedException {
        WebElement transferLink = driver.findElement(By.linkText(linkText));
        transferLink.click();
        Thread.sleep(2000);
    }


}
