@cell_recharge @regression
Feature: Recarga Celular | Ingresar monto
  Contempla los tc de la us NWA-3471

  @walletint @stage
  Scenario Outline: Validar elemento en la pantalla de Carga de monto - (C1286)
    Given Resetear app "si"
    Given Find user type "ANY" and set preReq ""
    And Iniciar sesion
    When Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_celular" en el menu
    And Selecciona la compania "<compania>"
    And Usuario pulsa link usar mi telefono
    When Click siguiente en pantalla "set_phone_to_recharge"
    Then Validar pantalla recarga celular "rc_ingresar_monto"
    Examples:
      | compania |
      | claro    |

  #********

  @walletint @stage ;
  Scenario Outline: Validar "pill" con saldo de la cuenta - Validar montos min y máx para compañía <compania> (C1287-C1288-C1289-1294)

    Given Pulsar back en "dispositivo" en pantalla "rc_ingresar_monto"
    Given Pulsar back en "dispositivo" en pantalla "rc_ingresar_numero"
    And Selecciona la compania "<compania>"
    And Usuario pulsa link usar mi telefono
    When Click siguiente en pantalla "set_phone_to_recharge"
    Then Validar monto maximo y minimo para la compania "<compania>"
    Examples:
      | compania |
      | claro    |
      | movistar |
      | nextel   |
      | Personal |
      | Tuenti   |

 #************************
  @ignore @walletint
  Scenario Outline: <descripcion> (C1290-C1291)

    Given Usuario esta en pantalla "rc_ingresar_monto"
    Given Pulsar back en "dispositivo" en pantalla "rc_ingresar_monto"
    Given Usuario esta en pantalla "rc_ingresar_numero"
    Given Pulsar back en "dispositivo" en pantalla "rc_ingresar_numero"
    Given Usuario esta en pantalla "select_compania_recarga_celular"
    And Selecciona la compania "<compania>"
    And Usuario esta en pantalla "rc_ingresar_numero"
    #And Validar pantalla recarga celular rc_ingresar_numero
    And Usuario pulsa link usar mi telefono
    And Validar que se muestra el numero del usuario
    When Click siguiente en pantalla ""
    And Usuario esta en pantalla "rc_ingresar_monto"
    When Usuario ingresa monto "<monto>"
    Then Boton siguiente en pantalla "rc_ingresar_monto" esta "<status>"
    Examples:
     |compania | monto |    status    |                descripcion                  |
     |  claro  |  50   |  habilitado  |boton habilitado. Monto entre minimo y maximo|
     |movistar |  550  |deshabilitado |boton deshabilitado, monto mayor al permitido|
     |movistar |   3   |deshabilitado |boton deshabilitado, monto menor al minimo   |
     |movistar | mayor |deshabilitado |boton deshabilitado, monto superior al disponible|


  @walletint @stage
  Scenario: Validar pantalla error saldo insuficiente (C1292)

    Given Usuario esta en pantalla "rc_ingresar_monto"
    When Usuario ingresa monto "MAYOR"
    Then Validar pantalla saldo insuficiente