@cell_recharge @regression
Feature: Flujo Recarga Celular

  @walletint @stage @smoke
  Scenario Outline: Flujo Recarga celular

    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    When Pulsa el boton "recarga_celular" en el menu
    And Selecciona la compania "<compania>"
    And Ingresar numero de telefono valido para recarga
    And Usuario ingresa monto "<monto>"
    And Usuario confirma la recarga de celular
    And Usuario ingresa clave nubi para flujo "MOBILE_RECHARGE"
    Then Validar pantalla recarga celular "rc_recharge_success"
    Then Pulsar volver al inicio
    Then Usuario esta en pantalla "dashboard"
  Examples:
  | compania  |  monto | resultado |
  | movistar  | CORRECTO |  correcto |
##