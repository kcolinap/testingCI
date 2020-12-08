@ignore
@e2e
Feature: Flujo Cash In por rapipago

  Background:
    Given Usuario esta en pantalla "login"
    Given Permiso para ver contactos "si"

@walletint
  Scenario Outline: Flujo cash-in rapipago
    Given Usuario "any" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    When Pulsa el boton "principal" en el menu
    And Pulsa el boton "cargar" en el menu
    And Usuario esta en pantalla "cash_in"
    And Usuario presiona boton "<button>"
    Then Usuario esta en pantalla "rapipago"
    Then Resetear app "si"
    Examples:
      | button   |
      | rapipago |

  Scenario Outline: <descripcion>
    Given Usuario "con_cvu" inicia sesion
    And Usuario es tipo 2
    Given Usuario esta en pantalla "dashboard"
    When Pulsa el boton "principal" en el menu
    And Pulsa el boton "cargar" en el menu
    And Usuario esta en pantalla "cash_in"
    When Usuario presiona boton "Transferencia"
    And Usuario esta en pantalla "transferencia"
    And Usuario presiona boton "<button>"
    And Usuario esta en pantalla "<pantalla>"
    Then Resetear app "si"
    Examples:
      |    button   |                    descripcion                   |    pantalla   |
      |cuenta_propia| Flujo CashIn desde transferencia - cuenta propia | cuenta_propia |
      |otras_cuentas| Flujo CashIn desde transferencia - otras cuentas | otras_cuentas |