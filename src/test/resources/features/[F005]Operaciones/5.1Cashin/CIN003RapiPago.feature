@movements @regression
Feature: Validaciones en la página de RapiPago


  @walletint @stage
  Scenario: Validar elementos de la pantalla Rapipago - C1277
    Given Resetear app "si"
    And Find user type "any" and set preReq ""
    And Iniciar sesion
    And Pulsa el boton "cash-in" en el menu
    When Usuario presiona boton "rapipago"
    Then Validar pantalla flujo cash-in "RAPIPAGO"

  @walletint @stage
  Scenario: Comportamiento boton de copiar al portapapeles - C25030
    Given Usuario esta en pantalla "rapipago"
    When Usuario presiona boton "copy_rapipago_code"
    Then Validar snackbar "rapipago_copy_code"