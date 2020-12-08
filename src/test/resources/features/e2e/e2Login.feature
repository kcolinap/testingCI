@ignore
@e2e
Feature: Login process(c248)

  c248

  Background:
    Given Usuario esta en pantalla "login"
    #And Home screen has been showed


  Scenario: Loguearse en la App
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    Then Resetear app "si"
#      Then User sees the welcome screen with its name e2e. this is failing at tihis moment 31-05-2019


  Scenario Outline: <descripcion> - <testID>

    When Usuario pulsa olvide contrasenia
    #And Usuario esta en pantalla {string}
    And Ingresa email "<email>"
    And Tap boton enviar
    And Ir al link de recuperacion de clave
    And Ingresa clave "<clave>"
    And Validar clave "<tipoclave>"
    And Boton circulo siguiente en pantalla "<pantalla>" esta "<status>"
    And Click boton circulo siguiente en pantalla "<pantalla>"
    Then Usuario esta en pantalla "<pantallasucces>"
    Examples:
      |descripcion|        testID    |  email |  clave | pantalla | tipoclave | status |pantallasucces|
      |Nueva clave|  C585/C563/C580  |correcto|Test-0001|cambiopass| correcta  |    t   |    login |
