Feature: Validar transferencia

  Background:
    Given el navegador esta abierto en la pagina "https://demo.testfire.net/"
    And el usuario realiza clic en "//a[@id='AccountLink']" para dirigirse a la pagina de login

  Scenario Outline: Transferencia
    Given que puedo acceder a la url "https://demo.testfire.net/"
    And puedo ingresar a mi aplicacion con mi usuario y mi password <fila_excel>
    When doy click en el enlace de transferencia de fondos "Transfer Funds"
    And selecciono la cuenta de origen y destino <fila_excel>
    And coloco el monto a transferir <fila_excel>
    And doy click en el boton de transferencia "//input[@id='transfer']"
    Then aparece el mensaje de transferencia correcta <fila_excel>

    Examples:
      | fila_excel|
      | 1        |
      | 2        |
      | 5        |