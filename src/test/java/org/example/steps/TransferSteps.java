package org.example.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.example.steps.CommonSteps.driver;

public class TransferSteps {

    @And("el usuario selecciona la cuenta de origen {string}")
    public void el_usuario_selecciona_la_cuenta_de_origen(String accountValue) throws InterruptedException {
        WebElement fromAccountDropdown = driver.findElement(By.xpath("//*[@id='fromAccount']"));
        Select select = new Select(fromAccountDropdown);
        select.selectByValue(accountValue);
        Thread.sleep(500);
    }

    @And("el usuario selecciona la cuenta de destino {string}")
    public void el_usuario_selecciona_la_cuenta_de_destino(String accountValue) throws InterruptedException {
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
