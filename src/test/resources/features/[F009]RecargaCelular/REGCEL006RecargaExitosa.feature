@cell_recharge @regression
Feature: Recarga Celular | Loading y recarga exitosa
  Contempla los tc de la us NWA-3474

  @walletint @stage
  Scenario Outline:  Ir a la pantalla Dashboard - Back del device desde la patalla fin de la ¨Recarga de Celular¨  (C1347)

   Given Resetear app "si"
   Given Usuario "any" inicia sesion
   Given Usuario esta en pantalla "dashboard"
   And Pulsa el boton "principal" en el menu
   And Pulsa el boton "recarga_celular" en el menu
   When Selecciona la compania "<compania>"
    And Ingresar numero de telefono valido para recarga
    And Usuario ingresa monto "CORRECTO"
    And Validar pantalla recarga celular "rc_confirmacion"
    And Validar persistencia de datos
    And Pulsar boton Confirmar
    And Usuario ingresa clave nubi para flujo "MOBILE_RECHARGE"
    Then Validar pantalla recarga celular "rc_recharge_success"
    Then Pulsar back en "dispositivo" en pantalla "rc_recharge_success"
    Then Usuario esta en pantalla "dashboard"
   Examples:
   |compania| monto |  status  |
   | movistar  |  15   |habilitado|

  #********
  @walletint @stage
  Scenario Outline: Validar redireccion de boton Volver al inicio -  (C1345)

    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_celular" en el menu
    When Selecciona la compania "<compania>"
    And Ingresar numero de telefono valido para recarga
    And Usuario ingresa monto "CORRECTO"
    And Validar pantalla recarga celular "rc_confirmacion"
    And Validar persistencia de datos
    And Pulsar boton Confirmar
    And Usuario ingresa clave nubi para flujo "MOBILE_RECHARGE"
    Then Validar pantalla recarga celular "rc_recharge_success"
    Then Pulsar volver al inicio
    Then Usuario esta en pantalla "dashboard"
    Examples:
      |  compania | monto |  status  |
      | movistar  |  15   |habilitado|