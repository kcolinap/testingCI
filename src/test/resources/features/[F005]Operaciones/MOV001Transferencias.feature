@movements @regression
Feature: TCS seccion: TRANSFERENCIAS

  Contempla todos los casos embebidos en la seccion transferencia de movimientos

  C340 https://tunubi.atlassian.net/browse/NWA-554
  C341 https://tunubi.atlassian.net/browse/NWA-933

  @walletint
  Scenario: Completitud de pantalla (C340)
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    When Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Validar detalle contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    Then Valida la existencia de todos los elementos de la pantalla "enviar"
    Then Resetear app "si"

  @walletint
  Scenario Outline: <scenarioTitle>
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    And Validate "user1" balance
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    When "user1" ingresa un monto "<monto>" que el disponible en su cuenta
    Then Valida que del boton siguiente este "<statusButton>"
    Then Resetear app "si"
    Examples:
      | scenarioTitle                       | monto | statusButton  |
      | Monto mayor a de la cuenta (C341)   | mayor | deshabilitado |
      | Monto diponible de la cuenta (C342) | menor | habilitado    |

  @walletint
  Scenario Outline: <scenarioTitle>
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    When Ingresa el monto siguiente: "<monto>"
    Then Valida que del boton siguiente este "deshabilitado"
    Then Resetear app "si"
    Examples:
      | scenarioTitle                                              | monto |
      | No ingresar caracteres especiales en el campo monto (C343) | ++.++ |
      | No ingresar letras en el campo monto (C343)                | tt.tt |

  @walletint
  Scenario: Validate borrar monto ingresado
  Test rail C344

    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    And Usuario conoce su balance
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    When "user1" ingresa un monto "menor" que el disponible en su cuenta
    Then Campo monto vuelve a su estado por defecto despues de borrar el monto
    And Valida que del boton siguiente este "deshabilitado"
    Then Resetear app "si"

  @walletint
  Scenario: Validar back button on wallet screen (C345)
    Este case valida cuando el usuario hace click en el botton back de la app
    cuando se encuentra en la pantalla de ingresar el monto a enviar\solicitar(c345)

    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    When Pulsar back en "app" en pantalla "wallet"
    Then Usuario esta en pantalla "detalle_contacto"
    Then Resetear app "si"

#    ####################################################

  @walletint
  Scenario: Validar back device button (C346)

    Este case valida cuando el usuario presiona el botton back del dispositivo
    cuando se encuentra en la pantalla de ingresar el monto a enviar\solicitar(c346)

    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    When Pulsar back en "dispositivo" en pantalla "wallet"
    Then Usuario esta en pantalla "wallet"
    Then Resetear app "si"

#    ######################################

  @walletint
  Scenario: Validar retorno a la app al hacer backgroud (C347)

    Este case valida presiona el boton de apps reciente,
    y la wallet se ejecuta en background(c347)

    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    And Usuario conoce su balance
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    And "user1" ingresa un monto "menor" que el disponible en su cuenta
    When Usuario presiona background desde el dispositivo
    Then Usuario esta en pantalla "enviar_o_solicitar"
    And el monto ingresado persiste
    Then Resetear app "si"

#    ###########################################################
  @walletint
  Scenario: Bloqueo de dispositivo (C348)

    Este case valida cuando se bloquea el dispositivo. Cuanto se esta realizando un
    envio o solicitud, el monto debe persistir en la p antalla billetera(c348)

    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    And Usuario conoce su balance
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    And "user1" ingresa un monto "menor" que el disponible en su cuenta
    When Usuario "bloquea" el dispositivo movil
    And Usuario "desbloquea" el dispositivo movil
    Then Usuario esta en pantalla "enviar_o_solicitar"
    And el monto ingresado persiste
    Then Resetear app "si"

#  ########################################################################

  @walletint
  Scenario: Matar la aplicacion (C349)

  Este case valida cuando se mata o cierra la app cuando se esta haciendo un envio,
  la transaccion queda cancelada, y el usuario vuelve al Login(c349)

    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    And Usuario conoce su balance
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    And "user1" ingresa un monto "menor" que el disponible en su cuenta
    When Resetear app "si"
    Then Usuario esta en pantalla "login"
    Then Resetear app "si"


