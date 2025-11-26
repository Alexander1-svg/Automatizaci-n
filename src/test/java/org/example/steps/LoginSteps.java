package org.example.steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;

import static org.example.steps.CommonSteps.driver;

public class LoginSteps{

    @When("el usuario ingresa en {string} el {string} y en {string} la {string}")
    public void el_usuario_ingresa_en_el_y_en_la(String xpath_username, String username, String xpath_password, String password) {
        CommonSteps.driver.findElement(By.xpath(xpath_username)).click();
        CommonSteps.driver.findElement(By.xpath(xpath_username)).clear();
        CommonSteps.driver.findElement(By.xpath(xpath_username)).sendKeys(username);

        CommonSteps.driver.findElement(By.xpath(xpath_password)).click();
        CommonSteps.driver.findElement(By.xpath(xpath_password)).clear();
        CommonSteps.driver.findElement(By.xpath(xpath_password)).sendKeys(password);
    }

    @When("hace click en el boton de login {string}")
    public void hace_click_en_el_boton_de_login(String xpath) throws InterruptedException {
        CommonSteps.driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(5000);
    }

    @Then("se deberia mostrar el campo {string} con el mensaje {string}")
    public void se_deberia_mostrar_el_campo_con_el_mensaje(String xpath, String message) {
        String text = CommonSteps.driver.findElement(By.xpath(xpath)).getText();
        if (text.contains(message)) {
            System.out.println("El campo con el mensaje se encuentra");
        } else {
            throw new RuntimeException("El campo con el mensaje no se encuentra");
        }
    }

    @Given("el usuario inicia sesión con {string} y {string}")
    public void el_usuario_inicia_sesion_con(String username, String password) throws InterruptedException {
        WebElement usernameField = CommonSteps.driver.findElement(By.xpath("//input[@id='uid']"));
        usernameField.clear();
        usernameField.sendKeys(username);

        WebElement passwordField = CommonSteps.driver.findElement(By.xpath("//input[@id='passw']"));
        passwordField.clear();
        passwordField.sendKeys(password);
        Thread.sleep(500);

        WebElement loginButton = CommonSteps.driver.findElement(By.xpath("//input[@value='Login']"));
        loginButton.click();
        Thread.sleep(3000);
    }
}