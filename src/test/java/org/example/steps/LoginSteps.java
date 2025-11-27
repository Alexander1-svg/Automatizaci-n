package org.example.steps;

import io.cucumber.java.en.*;
import org.example.Utilidades.CaptureUtils;
import org.example.Utilidades.ExcelUtils;
import org.openqa.selenium.*;

import static org.example.steps.CommonSteps.driver;

public class LoginSteps{

    String rutaExcel = "testData/Data.xlsx";

    @When("el usuario ingresa credenciales {string} fila {int}")
    public void el_usuario_ingresa_credenciales_hoja_fila(String nombreHoja, Integer fila) throws Exception {
        System.out.println("Ejecutando Login desde hoja: " + nombreHoja);
        Thread.sleep(2000);
        performLoginFromExcel(nombreHoja, fila);
        Thread.sleep(2000);
        String obj="ingresa_credenciales";
        CaptureUtils.captureScreenShot(driver,"evidencia\\"+obj+" "+CaptureUtils.GetTimeStampValue()+".png");
    }

    @Then("se deberia mostrar el mensaje de exito del excel fila {int}")
    public void se_deberia_mostrar_el_mensaje_de_exito_del_excel_fila(Integer fila) throws Exception {
        ExcelUtils.setExcelFileSheet(rutaExcel, "Login");
        String mensajeEsperado = ExcelUtils.getCellData(fila, 2);

        String mensajeActual = driver.findElement(By.xpath("//h1")).getText();

        if (mensajeActual.contains(mensajeEsperado)) {
            System.out.println("Login Exitoso verificado.");
            // Escribimos PASS en la columna 3
            ExcelUtils.setCellData("PASS", fila, 3);
        } else {
            // Escribimos FAIL en la columna 3
            ExcelUtils.setCellData("FAIL", fila, 3);
            throw new RuntimeException("Fallo Login. Esperaba: " + mensajeEsperado + " Vi: " + mensajeActual);
        }
        Thread.sleep(2000);
        String obj="mensaje_de_exito";
        CaptureUtils.captureScreenShot(driver,"evidencia\\"+obj+" "+CaptureUtils.GetTimeStampValue()+".png");
    }

    @When("el usuario ingresa credenciales desde excel fila {int}")
    public void el_usuario_ingresa_credenciales_desde_excel_fila(Integer fila) throws Exception {
        Thread.sleep(2000);
        System.out.println("Ejecutando Login Fallido");
        Thread.sleep(2000);
        performLoginFromExcel("LoginFallido", fila);
        Thread.sleep(2000);
        String obj="ingresa_credenciales_fallido";
        CaptureUtils.captureScreenShot(driver,"evidencia\\"+obj+" "+CaptureUtils.GetTimeStampValue()+".png");
    }

    @Then("se deberia mostrar el mensaje de error correspondiente a la fila {int}")
    public void se_deberia_mostrar_el_mensaje_de_error_correspondiente_a_la_fila(Integer fila) throws Exception {
        ExcelUtils.setExcelFileSheet(rutaExcel, "LoginFallido");
        String mensajeEsperado = ExcelUtils.getCellData(fila, 2);

        String mensajeActual = driver.findElement(By.xpath("//*[contains(text(),'Login Failed')]")).getText();

        if (mensajeActual.contains(mensajeEsperado)) {
            System.out.println("Error verificado correctamente.");
            // Escribimos PASS en la columna 3
            ExcelUtils.setCellData("PASS", fila, 3);
        } else {
            // Escribimos FAIL en la columna 3
            ExcelUtils.setCellData("FAIL", fila, 3);
            throw new RuntimeException("Fallo Validación Error. Esperaba: " + mensajeEsperado + " Vi: " + mensajeActual);
        }
        Thread.sleep(2000);
        String obj="mensaje_de_fallo";
        CaptureUtils.captureScreenShot(driver,"evidencia\\"+obj+" "+CaptureUtils.GetTimeStampValue()+".png");
    }

    private void performLoginFromExcel(String nombreHoja, Integer fila) throws Exception {
        ExcelUtils.setExcelFileSheet(rutaExcel, nombreHoja);
        String usuario = ExcelUtils.getCellData(fila, 0);
        String password = ExcelUtils.getCellData(fila, 1);

        driver.findElement(By.xpath("//input[@id='uid']")).clear();
        driver.findElement(By.xpath("//input[@id='uid']")).sendKeys(usuario);

        driver.findElement(By.xpath("//input[@id='passw']")).clear();
        driver.findElement(By.xpath("//input[@id='passw']")).sendKeys(password);
    }

    @When("hace click en el boton de login {string}")
    public void hace_click_en_el_boton_de_login(String xpath) throws InterruptedException {
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(2000);
    }
}