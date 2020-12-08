@movements @regression
Feature: Detalle envio
  Contempla los Tcs que se encuentra en la seccion Transferencia- detalle de envio:

      C352 https://tunubi.atlassian.net/browse/NWA-538
      C353 https://tunubi.atlassian.net/browse/NWA-538
      C354 https://tunubi.atlassian.net/browse/NWA-538
      C355 https://tunubi.atlassian.net/browse/NWA-538
      C356 bug https://tunubi.atlassian.net/browse/NWA-1231
      C357 Pendiente
      C358 https://tunubi.atlassian.net/browse/NWA-538

######################################################################################################################3

  Background:
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    Given Permiso para ver contactos "on"
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"

########################################################################################################################
########################################################################################################################
  @walletint
  Scenario: Consultar Detalle transferencia (C352)
      Validar la pantalla de confirmacion de la transferencia

    Given Usuario esta en pantalla "dashboard"
    When Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Validar detalle contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    And "user1" ingresa un monto "menor" que el disponible en su cuenta
    When Click siguiente en pantalla "enviar_o_solicitar"
    Then Valida el detalle de la pantalla "enviar"
    Then Resetear app "si"

#############################################################
  @walletint
  Scenario: Back device desde detalle transferencia (C355)
      Validar que se regrese a la pantalla de contactos cuando
    se presiona el boton back del dispositivo

  Given Usuario esta en pantalla "dashboard"
  When Pulsa el boton "principal" en el menu
  And Pulsa el boton "enviar" en el menu
  And Permitir el acceso a los contactos "si"
  And Usuario esta en pantalla "contactos"
  And Validar que hay contactos "nubi"
  And Seleccionar contacto "nubi"
  And Validar detalle contacto "nubi"
  And Presiona sobre la opcion de cuenta "nubi"
  And "user1" ingresa un monto "menor" que el disponible en su cuenta
  When Click siguiente en pantalla "enviar_o_solicitar"
  Then Pulsar back en "dispositivo" en pantalla "transferdetail"
  Then Usuario esta en pantalla "enviar_o_solicitar"
    Then Resetear app "si"

############################################################
  @walletint
  Scenario: Cancelar el envio de dinero(C353)
  Validar que se regrese a la pantalla de contactos cuando
  se presiona el boton back dee la aplicacion en la pantalla
  detalle de transferencia

    Given Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Validar detalle contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    And "user1" ingresa un monto "menor" que el disponible en su cuenta
    When Click siguiente en pantalla "enviar_o_solicitar"
    Then Pulsar back en "app" en pantalla "transferdetail"
    And Usuario esta en pantalla "enviar_o_solicitar"
    Then Resetear app "si"

################################################################
  @walletint
  Scenario: Confirmar detalle de transferencia(C354)
    Validar que se accede a la pantalla de validacion de pin
    cuando se confirma la transaccion (C354).

    Given Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Validar detalle contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    And "user1" ingresa un monto "menor" que el disponible en su cuenta
    And Click siguiente en pantalla "enviar_o_solicitar"
    And Click siguiente en pantalla "detalle_de_operacion"
    Then Usuario esta en pantalla "pinvalidation"
    Then Resetear app "si"

#####################################################################
  @walletint
  Scenario: Matar app desde pantalla detalle de transferencia(C358)
    Valida que al matar o cerra la app desde la pantalla de detalle
    de transferencia, al ingresar, se visualiza el dashboard, y el
    saldo de la cuenta debe persistir (C358).

    Given Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Validar detalle contacto "nubi"
    And Presiona sobre la opcion de cuenta "nubi"
    And "user1" ingresa un monto "menor" que el disponible en su cuenta
    And Click siguiente en pantalla "enviar_o_solicitar"
    When Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "user1" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And "user1" valida el balance "before" antes de "send"
    Then Resetear app "si"
