@ignore @cell_recharge @regression
Feature: Validacion de la transaccion recarga de celular

  @walletint
  Scenario Outline: Validar movimiento de recarga de celular

    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    Given Usuario "any" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_celular" en el menu
    And Selecciona la compania "<compania>"
    When Generar movimiento de tipo "RECARGA_CELULAR", estatus "", "" y monto "<monto>"
    Then Ir al dashboard
    Then Validar movimiento "RECARGA_CELULAR", "" en "<tipo_validacion>" y "<monto>" con status ""
  Examples:
  | compania  |  monto |  status  | resultado | tipo_validacion  |
  |   claro   |   15   |habilitado|  correcto | lista_movimiento |
  |   claro   |   18   |habilitado|  correcto | detalle_movimiento |
##