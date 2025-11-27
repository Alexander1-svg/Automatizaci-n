package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.Utilidades.CaptureUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CommonSteps {

    public static WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); // Evita que cargue datos o contraseñas guardadas
        options.addArguments("--disable-save-password-bubble"); // Desactiva sugerencias de contraseñas
        options.addArguments("--disable-popup-blocking"); // Desactiva notificaciones
        options.addArguments("--disable-popup-blocking"); // Desactiva bloqueo de pop-ups
        options.addArguments("--no-default-browser-check"); // Evita aviso de navegador predeterminado
        options.addArguments("--disable-infobars"); // Quita la barra de "Chrome está siendo controlado..."
        options.addArguments("--user-data-dir=/tpm/chrome-test-profile"); //fuerza a usar un perfil limpio de Chrome (sin contraseñas ni datos guardados).

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown() throws Exception {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("el navegador esta abierto en la pagina {string}")
    public void el_navegador_esta_abierto_en_la_pagina(String url) throws IOException, InterruptedException {
        driver.get(url);
        Thread.sleep(1000);
        String obj="navegador_hasta_AltoroMutual";
        CaptureUtils.captureScreenShot(driver,"evidencia\\"+obj+" "+CaptureUtils.GetTimeStampValue()+".png");
    }

    @Given("el usuario realiza clic en {string} para dirigirse a la pagina de login")
    public void el_usuario_realiza_clic_en_para_dirigirse_a_la_pagina_de_login(String xpath) throws InterruptedException, IOException {
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(1000);
        String obj="navegada_hasta_login";
        CaptureUtils.captureScreenShot(driver,"evidencia\\"+obj+" "+CaptureUtils.GetTimeStampValue()+".png");
    }

    @When("el usuario navega a la página de {string}")
    public void el_usuario_navega_a_la_pagina_de(String linkText) throws InterruptedException, IOException {
        WebElement transferLink = driver.findElement(By.linkText(linkText));
        transferLink.click();
        Thread.sleep(1000);
        String obj="navegada_hasta_transferencia";
        CaptureUtils.captureScreenShot(driver,"evidencia\\"+obj+" "+CaptureUtils.GetTimeStampValue()+".png");
    }


}
