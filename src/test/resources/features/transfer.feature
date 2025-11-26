Feature: Transferencia

  Background:
    Given el navegador esta abierto en la pagina "https://demo.testfire.net/"
    And el usuario realiza clic en "//a[@id='AccountLink']" para dirigirse a la pagina de login

  Scenario: Tranferencia de fondos exitosa
    Given el usuario inicia sesión con "jsmith" y "Demo1234"
    When el usuario navega a la página de "Transfer Funds"
    And el usuario selecciona la cuenta de origen "800002"
    And el usuario selecciona la cuenta de destino "800003"
    And el usuario ingresa el monto de "10000"
    And el usuario confirma la transferencia
    Then se deberia mostrar un mensaje de transferencia exitosa "was successfully transferred"