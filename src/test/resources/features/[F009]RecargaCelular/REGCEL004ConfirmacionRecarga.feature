@cell_recharge @regression
Feature: Recarga Celular | Confirmación de recarga
  Contempla los tc de la us NWA-3472


  @walletint @stage
  Scenario Outline: validacion de pantalla / validacion de persistencia de datos / redireccion boton recarga - (C1285-C1298-C1302-C1297-C1304)

   Given Resetear app "si"
   Given Usuario "any" inicia sesion
   Given Usuario esta en pantalla "dashboard"
   When Pulsa el boton "principal" en el menu
   And Pulsa el boton "recarga_celular" en el menu
   And Selecciona la compania "<compania>"
    And Usuario pulsa link usar mi telefono
   And Click siguiente en pantalla "set_phone_to_recharge"
   When Usuario ingresa monto "CORRECTO"
   Then Validar pantalla recarga celular "rc_confirmacion"
   And Validar persistencia de datos
   And Pulsar boton Confirmar
   And Usuario esta en pantalla "rc_nubi_password"
   Examples:
   |compania| monto |  status  | datoPersistido |
   | claro  |  15   |habilitado|    monto       |

  #********

  @walletint @stage
  Scenario Outline: Redireccion de boton back en <tipoBtn> - (<testID>)

    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    When Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_celular" en el menu
    And Selecciona la compania "<compania>"
    And Usuario pulsa link usar mi telefono
    And Click siguiente en pantalla "set_phone_to_recharge"
    And Usuario ingresa monto "CORRECTO"
    When Pulsar back en "<tipoBtn>" en pantalla "rc_confirmacion"
    Then Usuario esta en pantalla "rc_ingresar_monto"
    Examples:
      |compania| monto |  status  | datoPersistido |  tipoBtn  |testID|
      | claro  |  15   |habilitado|    monto       |dispositivo|C1307 |
      | claro  |  15   |habilitado|    telefono    |    app    |C1300 |