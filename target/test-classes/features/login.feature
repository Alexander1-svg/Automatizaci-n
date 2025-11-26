Feature: Realizar transferencia entre cuentas

  Scenario Outline: Realizar transferencia correctamente con datos del Excel
    Given el navegador esta abierto en la pagina "https://demo.testfire.net/"
    And el usuario realiza clic en "//a[@id='AccountLink']" para dirigirse a la pagina de login
    # Pasamos el número de fila como argumento
    When el usuario se loguea usando los datos de la fila <NroFila> del excel "datos_transferencia.xlsx"
    And navega a la seccion de transferencias
    And completa el formulario de transferencia con los datos de la fila <NroFila> del excel "datos_transferencia.xlsx"
    Then se deberia mostrar un mensaje de exito segun la fila <NroFila> del excel "datos_transferencia.xlsx"

    Examples:
      | NroFila |
      | 1       |
      | 2       |
      | 3       |