@cell_recharge @regression
Feature: Recarga Celular | Ingreso de Clave Nubi


  @walletint @stage
  Scenario Outline: Validar elementos de la pantalla Ingreso de clave nubi - validar boton back - background (C1323-C1327-C1329)

   Given Resetear app "si"
   Given Usuario "any" inicia sesion
   Given Usuario esta en pantalla "dashboard"
   And Pulsa el boton "principal" en el menu
   And Pulsa el boton "recarga_celular" en el menu
   And Selecciona la compania "<compania>"
   And Ingresar numero de telefono valido para recarga
   And Usuario ingresa monto "CORRECTO"
   And Validar pantalla recarga celular "rc_confirmacion"
   And Validar persistencia de datos
   And Pulsar boton Confirmar
   Then Validar pantalla recarga celular "rc_nubi_password"
   And Presiona boton de aplicaciones recientes
   And Usuario esta en pantalla "rc_nubi_password"
   And Pulsar back en "app" en pantalla "rc_nubi_password"
   Then Usuario esta en pantalla "rc_confirmacion"
   Examples:
   |compania| monto |  status  |
   | movistar  |  15   |habilitado|

  #********

  @walletint @stage
  Scenario Outline: Clave Nubi incorrecta - (C1307-C1300)

    Given Pulsar boton Confirmar
    Given Usuario esta en pantalla "rc_nubi_password"
    When Usuario ingresa clave nubi "<clave_nubi>" en recarga de celular
    Then Validar pantalla al ingresar Clave Nubi "<clave_nubi>" en recarga de celular

    Examples:
      |     clave_nubi    |
      |     incorrecto    |