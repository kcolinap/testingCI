@dashboard @regression
Feature: Regresion de la pantalla Dashboard


  @walletint @stage @smoke
  Scenario: Validar redirecion de Carga tu cuenta Nubi
    Given Resetear app "si"
    And Find user type "any" and set preReq ""
    And Iniciar sesion
    When Desde el dashboard, ir a la funcionalidad "ADD_ACCOUNT_MONEY"
    Then Validar pantalla flujo cash-in "landing"

  @walletint @stage @smoke
  Scenario: Validar redirecion de boton Tarjeta Prepaga Visa
    Given Resetear app "si"
    And Find user type "any" and set preReq ""
    And Iniciar sesion
    When Desde el dashboard, ir a la funcionalidad "PREPAID_CARD"
    Then Validar pantalla "PREPAID_CARD"

  @ignore @walletint @stage @smoke
  Scenario: Validar redirecion de boton ver Movimientos
    Given Resetear app "si"
    And Find user type "WITH_MOVEMENTS" and set preReq ""
    And Iniciar sesion
    When Desde el dashboard, ir a la funcionalidad "PREPAID_CARD"
    Then Validar pantalla "PREPAID_CARD"

  @ignore @walletint @stage
  Scenario: Matar la app para validar que la sesion ha sido cerrada
    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    When Usuario cierra la app
    And Inicia la app
    Then Usuario esta en pantalla "login"
