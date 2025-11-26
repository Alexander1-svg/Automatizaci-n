package org.example.steps;

import io.cucumber.java.en.*;
import org.example.Utilidades.ExcelUtils;
import org.openqa.selenium.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LoginSteps {

    // --- MÉTODOS ORIGINALES (Se mantienen igual) ---

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

    // --- NUEVO MÉTODO PARA EXCEL (Data Driven Testing) ---

    @When("el usuario ejecuta la prueba de login masivo desde el excel {string} hoja {string}")
    public void el_usuario_ejecuta_login_desde_excel(String nombreArchivo, String nombreHoja) throws IOException, InterruptedException {

        // 1. Construir la ruta al archivo en resources/data
        String filePath = "src/test/resources/data/" + nombreArchivo;

        // 2. Leer los datos usando nuestra clase utilitaria
        List<Map<String, String>> data = ExcelUtils.getData(filePath, nombreHoja);

        System.out.println("Iniciando prueba masiva con " + data.size() + " casos de prueba...");

        // 3. Iterar sobre cada fila del Excel
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> row = data.get(i);

            String usuario = row.get("usuario");
            String contrasenia = row.get("contrasenia");
            String mensajeEsperado = row.get("mensaje_esperado");

            System.out.println("\n--- Caso " + (i + 1) + ": Usuario='" + usuario + "' ---");

            try {
                // A. Intentar Login (Reutilizamos el método existente)
                el_usuario_inicia_sesion_con(usuario, contrasenia);

                // B. Verificar el resultado según lo esperado en el Excel
                if (mensajeEsperado.contains("Hello")) {
                    // CASO: Se espera LOGIN EXITOSO
                    try {
                        WebElement saludoElement = CommonSteps.driver.findElement(By.xpath("//h1"));
                        String textoActual = saludoElement.getText();

                        if (textoActual.contains(mensajeEsperado)) {
                            System.out.println("✔ ÉXITO: Login correcto detectado.");
                            // Hacemos Logout para limpiar el estado para la siguiente iteración
                            CommonSteps.driver.findElement(By.id("LoginLink")).click();
                        } else {
                            throw new RuntimeException("Se esperaba mensaje de éxito '" + mensajeEsperado + "' pero se encontró '" + textoActual + "'");
                        }
                    } catch (NoSuchElementException e) {
                        throw new RuntimeException("Falló el login: No se encontró el elemento de bienvenida (h1). Posible credencial inválida.");
                    }

                } else {
                    // CASO: Se espera FALLO DE LOGIN
                    try {
                        WebElement errorElement = CommonSteps.driver.findElement(By.xpath("//*[contains(text(),'Login Failed')]"));
                        String textoError = errorElement.getText();

                        if (textoError.contains(mensajeEsperado)) {
                            System.out.println("✔ ÉXITO: Error esperado detectado correctamente.");
                        } else {
                            throw new RuntimeException("Se esperaba error '" + mensajeEsperado + "' pero se encontró '" + textoError + "'");
                        }
                    } catch (NoSuchElementException e) {
                        throw new RuntimeException("Falló la validación: Se esperaba un error de login, pero no apareció el mensaje 'Login Failed'. ¿Quizás entró exitosamente?");
                    }
                }

            } catch (Exception e) {
                // Capturamos cualquier error para imprimirlo y NO detener todo el bucle
                System.err.println("✘ FALLO EN CASO " + (i + 1) + ": " + e.getMessage());
            }

            // C. Resetear estado: Volver a la página de login para el siguiente dato
            CommonSteps.driver.get("https://demo.testfire.net/login.jsp");
            Thread.sleep(1000); // Pequeña pausa para estabilidad
        }
    }
}