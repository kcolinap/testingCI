@movements @regression
Feature: Validaciones en la página de Cuenta Propia

Background:
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    When Pulsa el boton "cash-in" en el menu

  @walletint
  Scenario: Se valida que se estén mostrando los componentes correctamente de la página de Cuenta Propia
    And Usuario presiona boton "cuenta_propia"
    Then Usuario esta en pantalla "cuenta_propia"
    Then Validar pantalla flujo cash-in "OWN_ACCOUNT"
    Then Resetear app "si"