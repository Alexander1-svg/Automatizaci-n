package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.Select;

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
    public void el_navegador_esta_abierto_en_la_pagina(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(3000);
    }

    @Given("el usuario realiza clic en {string} para dirigirse a la pagina de login")
    public void el_usuario_realiza_clic_en_para_dirigirse_a_la_pagina_de_login(String xpath) throws InterruptedException {
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(1000);
    }

    @When("el usuario ingresa en {string} el {string} y en {string} la {string}")
    public void el_usuario_ingresa_en_el_y_en_la(String xpath_username, String username, String xpath_password, String password) {
        driver.findElement(By.xpath(xpath_username)).click();
        driver.findElement(By.xpath(xpath_username)).clear();
        driver.findElement(By.xpath(xpath_username)).sendKeys(username);
        driver.findElement(By.xpath(xpath_password)).click();
        driver.findElement(By.xpath(xpath_password)).clear();
        driver.findElement(By.xpath(xpath_password)).sendKeys(password);
    }

    @When("hace click en el boton de login {string}")
    public void hace_click_en_el_boton_de_login(String xpath) throws InterruptedException {
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(5000);
    }

    @Then("se deberia mostrar el campo {string} con el mensaje {string}")
    public void se_deberia_mostrar_el_campo_con_el_mensaje(String xpath, String message) {
        String text = driver.findElement(By.xpath(xpath)).getText();
        if (text.contains(message)) {
            System.out.println("El campo con el mensaje se encuentra");
        }else  {
            throw new RuntimeException("El campo con el mensaje no se encuentra");
        }
    }

    // --- NUEVO MÉTODOS ---

    @Given("el usuario inicia sesión con {string} y {string}")
    public void el_usuario_inicia_sesion_con(String username, String password) throws InterruptedException {
        WebElement usernameField = driver.findElement(By.xpath("//input[@id='uid']"));
        usernameField.clear();
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.xpath("//input[@id='passw']"));
        passwordField.clear();
        passwordField.sendKeys(password);
        Thread.sleep(500);

        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Login']"));
        loginButton.click();
        Thread.sleep(3000);
    }

    @When("el usuario navega a la página de {string}")
    public void el_usuario_navega_a_la_pagina_de(String linkText) throws InterruptedException {
        WebElement transferLink = driver.findElement(By.linkText(linkText));
        transferLink.click();
        Thread.sleep(1000);
    }

    @And("el usuario selecciona la cuenta de origen {string}")
    public void el_usuario_selecciona_la_cuenta_de_origen(String accountValue) throws InterruptedException {
        WebElement fromAccountDropdown = driver.findElement(By.xpath("//*[@id='fromAccount']"));
        Select select = new Select(fromAccountDropdown);
        select.selectByValue(accountValue);
        Thread.sleep(500);
    }

    @And("el usuario selecciona la cuenta de destino {string}")
    public void el_usuario_selecciona_la_cuenta_de_destino(String accountValue) throws InterruptedException {
        // --- CAMBIO REQUERIDO: Eliminado 'wait' ---
        WebElement toAccountDropdown = driver.findElement(By.xpath("//*[@id='toAccount']"));
        Select select = new Select(toAccountDropdown);
        select.selectByValue(accountValue);
        Thread.sleep(500);
    }

    @And("el usuario ingresa el monto de {string}")
    public void el_usuario_ingresa_el_monto_de(String amount) throws InterruptedException {
        WebElement amountField = driver.findElement(By.xpath("//*[@id='transferAmount']"));
        amountField.clear();
        amountField.sendKeys(amount);
        Thread.sleep(500);
    }

    @And("el usuario confirma la transferencia")
    public void el_usuario_confirma_la_transferencia() throws InterruptedException {
        WebElement transferButton = driver.findElement(By.xpath("//*[@id='transfer']"));
        transferButton.click();
        Thread.sleep(2000);
    }

    @Then("se deberia mostrar un mensaje de transferencia exitosa {string}")
    public void se_deberia_mostrar_un_mensaje_de_transferencia_exitosa(String expectedMessage) throws InterruptedException {
        String xpathDelMensaje = "//*[@id='_ctl0__ctl0_Content_Main_postResp']";
        Thread.sleep(1000);
        WebElement element = driver.findElement(By.xpath(xpathDelMensaje));
        String actualText = element.getText();

        if (actualText.contains(expectedMessage)) {
            System.out.println("El mensaje de transferencia se encuentra: " + actualText);
        } else {
            throw new RuntimeException(String.format(
                    "El mensaje esperado no se encontró. Esperado: '%s', Actual: '%s'",
                    expectedMessage, actualText
            ));
        }
    }
}