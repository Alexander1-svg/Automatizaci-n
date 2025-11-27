package org.example.steps;

import org.example.Utilidades.CaptureUtils;
import org.example.Utilidades.ExcelUtils; // Verifica que este import sea correcto
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.example.steps.CommonSteps.driver;

public class TransferSteps {

    String rutaExcel = "testData/Data.xlsx";
    String nombreHoja = "Transfer";

    @Given("que puedo acceder a la url {string}")
    public void que_puedo_acceder_a_la_url(String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Given("puedo ingresar a mi aplicacion con mi usuario y mi password {int}")
    public void puedo_ingresar_a_mi_aplicacion_con_mi_usuario_y_mi_password(Integer fila) throws Exception {

        ExcelUtils.setExcelFileSheet(rutaExcel, nombreHoja);

        driver.findElement(By.xpath("//*[@id='LoginLink']/font")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='uid']")));
        WebElement password = driver.findElement(By.xpath("//*[@id='passw']"));
        WebElement btnLogin = driver.findElement(By.xpath("//*[@id='login']/table/tbody/tr[3]/td[2]/input"));

        userName.clear();
        Thread.sleep(2000);
        userName.sendKeys(ExcelUtils.getCellData(fila, 0)); // Columna 0: Usuario
        Thread.sleep(2000);
        password.clear();
        password.sendKeys(ExcelUtils.getCellData(fila, 1)); // Columna 1: Password
        Thread.sleep(2000);
        btnLogin.click();
        Thread.sleep(2000);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        String obj="ingresa_credenciales";
        CaptureUtils.captureScreenShot(driver,"evidencia\\"+obj+" "+CaptureUtils.GetTimeStampValue()+".png");
        Thread.sleep(2000);
    }

    @When("doy click en el enlace de transferencia de fondos {string}")
    public void doy_click_en_el_enlace_de_transferencia_de_fondos(String linkTransfer) throws IOException, InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement mensaje = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/table[2]/tbody/tr/td[2]/div/p")));

        if (mensaje.isDisplayed())
            driver.findElement(By.linkText(linkTransfer)).click();
        else
            System.err.println("Error al ingresar en la cuenta...");
        Thread.sleep(2000);
    }

    @When("selecciono la cuenta de origen y destino {int}")
    public void selecciono_la_cuenta_de_origen_y_destino(Integer fila) throws Exception {
        // Seleccione la cuenta de origen (Columna 2)
        driver.findElement(By.xpath("//*[@id='fromAccount']")).sendKeys(ExcelUtils.getCellData(fila, 2));
        // Seleccione la cuenta destino (Columna 3)
        driver.findElement(By.xpath("//*[@id='toAccount']")).sendKeys(ExcelUtils.getCellData(fila, 3));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String obj="elecciono_la_cuenta_de_origen_y_destino";
        CaptureUtils.captureScreenShot(driver,"evidencia\\"+obj+" "+CaptureUtils.GetTimeStampValue()+".png");
        Thread.sleep(2000);
    }

    @When("coloco el monto a transferir {int}")
    public void coloco_el_monto_a_transferir(Integer fila) throws Exception {
        driver.findElement(By.xpath("//*[@id='transferAmount']")).clear();
        // Columna 4: Monto
        driver.findElement(By.xpath("//*[@id='transferAmount']")).sendKeys(ExcelUtils.getCellData(fila, 4));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        String obj="monto_a_transferir";
        CaptureUtils.captureScreenShot(driver,"evidencia\\"+obj+" "+CaptureUtils.GetTimeStampValue()+".png");
        Thread.sleep(2000);
    }

    @When("doy click en el boton de transferencia {string}")
    public void doy_click_en_el_boton_de_transferencia(String btnTransferir) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath(btnTransferir)).click();
        Thread.sleep(2000);
    }

    @Then("aparece el mensaje de transferencia correcta {int}")
    public void aparece_el_mensaje_de_transferencia_correcta(Integer fila) throws Exception {
        String mensajeObtenido = driver.findElement(By.xpath("//*[@id='_ctl0__ctl0_Content_Main_postResp']/span")).getText();
        // Columna 5: Mensaje Esperado
        String mensajeEsperado = ExcelUtils.getCellData(fila, 5);

        if (mensajeObtenido.contains(mensajeEsperado)) {
            System.out.println("Transferencia Exitosa");
            // Escribimos en el Excel (Columna 6)
            ExcelUtils.setCellData("Prueba OK", fila, 6);
        } else {
            // Escribimos en el Excel (Columna 6)
            ExcelUtils.setCellData("Prueba NO OK", fila, 6);
            throw new RuntimeException("Fallo Transferencia. Esperaba: " + mensajeEsperado + " Vi: " + mensajeObtenido);
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        String obj="mensaje_de_transferencia_correcta";
        CaptureUtils.captureScreenShot(driver,"evidencia\\"+obj+" "+CaptureUtils.GetTimeStampValue()+".png");
        Thread.sleep(2000);
    }
}