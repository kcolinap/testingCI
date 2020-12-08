@cell_recharge @regression
Feature: Recarga de celular | Ingreso por primera vez

  Contempla los tc de la us NWA-3469

  @walletint @stage
  Scenario: Validar ícono Celular desde menu Nube - (C1278)

    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    When Pulsa el boton "principal" en el menu
    Then Validar pantalla recarga celular "icono"

  @walletint @stage
  Scenario: Validar redirección al seleccionar la opción Recarga Celular - (C1279)
    Given Pulsa el boton "recarga_celular" en el menu
    Then Usuario esta en pantalla "select_compania_recarga_celular"


  @walletint @stage
  Scenario: Validar elementos en pantalla Selección de compañía - (C1280)
    Given Usuario esta en pantalla "select_compania_recarga_celular"
    Then Validar pantalla recarga celular "select_compania_recarga_celular"

  @walletint @stage
  Scenario Outline:  Validar redirección al seleccionar una compañía telefónica - (C1281)

    Given Usuario esta en pantalla "select_compania_recarga_celular"
    When Selecciona la compania "<compania>"
    Then Validar pantalla recarga celular "rc_ingresar_numero"
    Examples:
    |compania|
    | movistar  |
   # | movistar |
   # | nextel |

  @walletint @stage
  Scenario Outline: <descripcion> - <testId>
    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    When Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_celular" en el menu
    And Pulsar back en "<tipoback>" en pantalla "select_compania_recarga_celular"
    Then Usuario esta en pantalla "dashboard"
    Examples:
    |                           descripcion                              | testId | tipoback |
    |Back desde el boton de la app en la pantalla de listado de companias|  C1299 |    app   |
    |  Back desde el dispositivo en la pantalla de listado de companias  |  C1301 | dispositivo |

  @walletint @stage
  Scenario: Background desde la pantalla listado de Recarga de Celular - (C1303)
    Given Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_celular" en el menu
    And Usuario esta en pantalla "select_compania_recarga_celular"
    When Presiona boton de aplicaciones recientes
    Then Usuario esta en pantalla "select_compania_recarga_celular"