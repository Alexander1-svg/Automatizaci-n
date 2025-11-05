
Feature: Login de usuario en la aplicacion

  Background:
    Given el navegador esta abierto en la pagina "https://demo.testfire.net/"
    And el usuario realiza clic en "//a[@id='AccountLink']" para dirigirse a la pagina de login

  Scenario: Inicio de sesión exitoso
    When el usuario ingresa en "//input[@id='uid']" el "jsmith" y en "//input[@id='passw']" la "Demo1234"
    And hace click en el boton de login "//input[@value='Login']"
    Then se deberia mostrar el campo "//h1" con el mensaje "Hello John Smith"

  Scenario: Tranferencia de fondos exitosa
    Given el usuario inicia sesión con "jsmith" y "Demo1234"
    When el usuario navega a la página de "Transfer Funds"
    And el usuario selecciona la cuenta de origen "800002"
    And el usuario selecciona la cuenta de destino "800003"
    And el usuario ingresa el monto de "10000"
    And el usuario confirma la transferencia
    Then se deberia mostrar un mensaje de transferencia exitosa "was successfully transferred"

  Scenario Outline: Inicio de sesion fallido
    When el usuario ingresa en "//input[@id='uid']" el "<usuario>" y en "//input[@id='passw']" la "<contrasenia>"
    And hace click en el boton de login "//input[@value='Login']"
    Then se deberia mostrar el campo "//*[contains(text(),'Login Failed')]" con el mensaje "<mensaje>"

    Examples:
      | usuario | contrasenia | mensaje      |
      | ccont   | 123456      | Login Failed |
      | bguti   | 123456      | Login Failed |

