package org.example.steps;

// Importaciones necesarias (añadimos Duration, Wait, Assert, Select)
import java.time.Duration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

// Importación para la aserción estándar
import org.junit.Assert;

public class LoginSteps {

    // 1. El driver ya NO es static
    private WebDriver driver;

    // 2. Añadimos el objeto WebDriverWait como variable de clase
    private WebDriverWait wait;

    // 3. Definimos un tiempo de espera máximo
    private static final int TIMEOUT_SECONDS = 10;

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

        // 4. Inicializamos el WebDriverWait
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // --- MÉTODOS ANTIGUOS (Corregidos con WebDriverWait) ---
    // (Estos métodos coinciden con tu Gherkin "feo" original)

    @Given("el navegador esta abierto en la pagina {string}")
    public void el_navegador_esta_abierto_en_la_pagina(String url) {
        // 5. No se necesita Thread.sleep aquí. driver.get() espera a que la página cargue.
        driver.get(url);

    }

    @Given("el usuario realiza clic en {string} para dirigirse a la pagina de login")
    public void el_usuario_realiza_clic_en_para_dirigirse_a_la_pagina_de_login(String xpath) {
        // 6. Reemplazamos findElement y Thread.sleep con wait.until
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @When("el usuario ingresa en {string} el {string} y en {string} la {string}")
    public void el_usuario_ingresa_en_el_y_en_la(String xpath_username, String username, String xpath_password, String password) {
        // 7. Usamos wait.until para encontrar cada elemento
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_username)));
        usernameField.click();
        usernameField.clear();
        usernameField.sendKeys(username);

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath_password)));
        passwordField.click();
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    @When("hace click en el boton de login {string}")
    public void hace_click_en_el_boton_de_login(String xpath) {
        // 8. Reemplazamos findElement y el Thread.sleep de 5 segundos
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @Then("se deberia mostrar el campo {string} con el mensaje {string}")
    public void se_deberia_mostrar_el_campo_con_el_mensaje(String xpath, String expectedMessage) {
        // 9. Usamos wait.until y una aserción Assert.assertTrue
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        String actualText = element.getText();

        String assertionMessage = String.format(
                "El mensaje esperado no se encontró. Esperado: '%s', Actual: '%s'",
                expectedMessage, actualText
        );
        Assert.assertTrue(assertionMessage, actualText.contains(expectedMessage));
    }

    // --- NUEVOS MÉTODOS PARA EL ESCENARIO 'Tranferencia de fondos exitosa' ---
    // (Estos métodos coinciden con tu Gherkin "bonito" refactorizado)

    @Given("el usuario inicia sesión con {string} y {string}")
    public void el_usuario_inicia_sesion_con(String username, String password) {
        // Este paso combina los 3 pasos de login en uno solo
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='uid']")));
        usernameField.clear();
        usernameField.sendKeys(username);

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='passw']")));
        passwordField.clear();
        passwordField.sendKeys(password);

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Login']")));
        loginButton.click();
    }

    @When("el usuario navega a la página de {string}")
    public void el_usuario_navega_a_la_pagina_de(String linkText) {
        WebElement transferLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
        transferLink.click();
    }

    @And("el usuario selecciona la cuenta de origen {string}")
    public void el_usuario_selecciona_la_cuenta_de_origen(String accountValue) {
        WebElement fromAccountDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='fromAccount']")));
        Select select = new Select(fromAccountDropdown);
        select.selectByValue(accountValue);
    }

    @And("el usuario selecciona la cuenta de destino {string}")
    public void el_usuario_selecciona_la_cuenta_de_destino(String accountValue) {
        WebElement toAccountDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='toAccount']")));
        Select select = new Select(toAccountDropdown);
        select.selectByValue(accountValue);
    }

    @And("el usuario ingresa el monto de {string}")
    public void el_usuario_ingresa_el_monto_de(String amount) {
        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='transferAmount']")));
        amountField.clear();
        amountField.sendKeys(amount);
    }

    @And("el usuario confirma la transferencia")
    public void el_usuario_confirma_la_transferencia() {
        WebElement transferButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='transfer']")));
        transferButton.click();
    }

    @Then("se deberia mostrar un mensaje de transferencia exitosa {string}")
    public void se_deberia_mostrar_un_mensaje_de_transferencia_exitosa(String expectedMessage) {
        String xpathDelMensaje = "//*[@id='_ctl0__ctl0_Content_Main_postResp']";

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathDelMensaje)));
        String actualText = element.getText();

        String assertionMessage = String.format(
                "El mensaje esperado no se encontró. Esperado: '%s', Actual: '%s'",
                expectedMessage, actualText
        );
        Assert.assertTrue(assertionMessage, actualText.contains(expectedMessage));
    }
}