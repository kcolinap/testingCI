@movements @regression
Feature: Landing de cashin
  Background:
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    When Pulsa el boton "cash-in" en el menu

  @walletint @stage
  Scenario: Landing pantalla cash in, validacion de elementos en pantalla landing (C489-C1119)
    Then Validar pantalla flujo cash-in "landing"

  @walletint
  Scenario Outline: Se valida que estén mostrandose las paginas de Cuenta Propia y Rapipagoal hacer click desde la Landing.
    And Usuario presiona boton "<escenario>"
    Then Usuario esta en pantalla "<escenario>"
    Then Resetear app "si"
    Examples:
      | escenario     |
      | rapipago      |
      | cuenta_propia |
#     | otras_cuentas | Otras_cuentas depende de Prisma y no puede validarse en w2 ni stage.