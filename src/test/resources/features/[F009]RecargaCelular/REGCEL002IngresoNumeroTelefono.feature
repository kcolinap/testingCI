@cell_recharge @regression
Feature: Recarga de celular | Ingreso de numero telefonico
  Contempla los tc de las us NWA-3477 / NWA-3470

  @walletint @stage
 Scenario: Agregar el número de teléfono a recargar desde el link - (C1423)

   Given Resetear app "si"
   Given Usuario "any" inicia sesion
   Given Usuario esta en pantalla "dashboard"
   When Pulsa el boton "principal" en el menu
   And Pulsa el boton "recarga_celular" en el menu
   And Selecciona la compania "movistar"
   When Usuario pulsa link usar mi telefono
   Then Validar que se muestra el numero del usuario


   #********
 @walletint @stage
 Scenario Outline: Background del device desde la pantalla ¨A qué número querés recargar?¨ - (C1333)

   Given Pulsar back en "dispositivo" en pantalla "rc_ingresar_numero"
   And Usuario esta en pantalla "select_compania_recarga_celular"
   And Selecciona la compania "<compania>"
   When Ingresa codigo de area "<codigo>"
   And Ingresa numero "<numero>"
   And Presiona boton de aplicaciones recientes
   Then Usuario esta en pantalla "rc_ingresar_numero"
   And El codigo de area <codigo> persiste
   And El numero de telefono <numero> persiste
   Examples:
   |compania|codigo| numero|
   |claro   |  11  |99945454|
   |movistar|  11  |99945454|
   |nextel  |  11  |99945454|


 #*************
 @walletint @stage
 Scenario Outline: Validar botón y mensaje al ingresar numero telefonico - (C1320-C1319-C1318-C1317-C1315-C1314-C1313-C1312)

   Given Pulsar back en "dispositivo" en pantalla "rc_ingresar_numero"
   And Usuario esta en pantalla "select_compania_recarga_celular"
   And Selecciona la compania "<compania>"
   And Usuario esta en pantalla "rc_ingresar_numero"
   When Ingresa codigo de area "<codigo>"
   And Ingresa numero "<numero>"
   And Boton siguiente en pantalla "rc_ingresar_numero" esta "<status>"
   And Validar tipo error "<error>"
   Examples:
   |compania| codigo| numero  |   status    | error |
   | claro  | 2202  | 3456788 |deshabilitado|longitud|
   |movistar|  11   |  345678 |deshabilitado|longitud|
   | claro  |  11   | 15234567 |deshabilitado|erroneo|
   | claro  |  01   | 15234567 |deshabilitado|erroneo|
   |movistar|  311   | 1234567 |deshabilitado|cod_inexistente|
   |movistar|  11   | 57575757 |habilitado|                  |