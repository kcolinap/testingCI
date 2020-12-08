@ignore
@e2e
Feature: Flujo Cash Out por rapipago

  Background:
    Given Usuario esta en pantalla "login"
    Given Permiso para ver contactos "si"

  Scenario Outline: Flujo cash-out efectivo
    Given Usuario "bancario" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    When Pulsa el boton "principal" en el menu
    And Pulsa el boton "extraer" en el menu
    And Usuario esta en pantalla "cash_out"
    And Usuario presiona boton "<button>"
    Then Usuario esta en pantalla "efectivo-cout"
    Then Resetear app "si"
    Examples:
    | button |
    |efectivo-cout|
#