
Feature: Login de usuario en la aplicacion

  Background:
    Given el navegador esta abierto en la pagina "https://demo.testfire.net/"
    And el usuario hace clic en el enlace de 'Login'

  Scenario: Inicio de sesión exitoso
    When el usuario ingresa "jsmith" en el campo 'Usuario' y "Demo1234" en el campo 'Contrasena'
    And hace clic en el boton 'Login'
    Then se deberia mostrar el saludo "Hello John Smith"

  Scenario Outline: Inicio de sesion fallido
    When el usuario ingresa "<usuario>" en el campo 'Usuario' y "<contrasenia>" en el campo 'Contrasena'
    And hace clic en el boton 'Login'
    Then se deberia mostrar el mensaje de error "<mensaje>"

    Examples:
      | usuario | contrasenia | mensaje      |
      | admin   | 1234        | Login Failed |
      | jsmith  | admin       | Login Failed |

