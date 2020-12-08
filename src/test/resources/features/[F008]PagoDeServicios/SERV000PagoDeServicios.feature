@regression
Feature: Página de Pago de Servicios

  @walletint
  Scenario: Se verifican los elementos de la página de Pago de Servicios
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    When Pulsa el boton "pagar_factura" en el menu
    When Usuario esta en pantalla "pago_de_servicios"
    Then Valida los elementos de la pantalla principal de pago de servicios