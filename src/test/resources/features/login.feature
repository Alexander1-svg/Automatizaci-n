
Feature: Login de usuario en la aplicacion

  Background:
    Given el navegador esta abierto en la pagina "https://demo.testfire.net/"
    And el usuario realiza clic en "//a[@id='AccountLink']" para dirigirse a la pagina de login

  Scenario Outline: Inicio de sesión exitoso
    When el usuario ingresa credenciales "Login" fila <fila_excel>
    And hace click en el boton de login "//input[@value='Login']"
    Then se deberia mostrar el mensaje de exito del excel fila <fila_excel>

    Examples:
      | fila_excel |
      | 1          |

  Scenario Outline: Inicio de sesion fallido
    When el usuario ingresa credenciales desde excel fila <fila_excel>
    And hace click en el boton de login "//input[@value='Login']"
    Then se deberia mostrar el mensaje de error correspondiente a la fila <fila_excel>

    Examples:
      | fila_excel |
      | 1          |
      | 2          |

