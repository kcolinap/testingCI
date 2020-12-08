@cell_recharge @regression
Feature: Recarga Celular | Recarga erronea
  Contempla los tc de la us NWA-3475

 @walletint @stage
  Scenario Outline: El numero de teléfono NO pertenece a la compañía telefónica seleccionada  (C1350)

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
  Then Validar pantalla recarga celular "rc_error_recharge"
   Examples:
   |compania| monto |  status  |
   | claro  |  81  |habilitado|
























