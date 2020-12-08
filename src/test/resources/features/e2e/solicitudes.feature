@ignore
@e2e
Feature: Solicitud
  Contempla los flujos de solicitud p2p
  desde el menu enviar y desde la tarjeta de contactos
  frecuentes

  Background:
    Given Usuario esta en pantalla "login"
    Given Permiso para ver contactos "si"
    When Usuario "user1" inicia sesion
    Then Usuario esta en pantalla "dashboard"

#
#  Scenario: Enviar desde menu (NWA-3200)
#
#    Flujo completo de envio de dinero accediendo desde el menu.
#
#    Given Validate "user1" balance
#    #Given "user1" knows his numeric balance
#    #Given Permiso para ver contactos "si"
#    And Pulsa el boton "principal" en el menu
#    And Pulsa el boton "enviar" en el menu
#    And Usuario esta en pantalla "contactos_desde_menu"
#    And Validar que hay contactos nubi
#    When Seleccionar contacto nubi
#    Then Usuario esta en pantalla "detalle_contacto"
#    Then Validar que el contacto seleccionado posee cuenta nubi
#    Then Seleccionar la cuenta nubi
#    Then Validar que modo enviar este activo
#    Then usuario1 ingresa el monto 10.01
#    And usuario1 pulsa boton siguiente
#    And usuario1 pulsa boton para confirmar la transaccion al enviar
#    Then Usuario esta en pantalla "validacion_pin"
#    Then usuario1 ingresa su pin
#    Then Validar estado de transaccion enviar
#    Then Validate "user1" balance "after" type request "send"
#    Then Resetear app "si"
##
##
  Scenario: Solicitar desde tarjeta contacto frecuentes (NWA-3201)

  Flujo completo de solicitud de dinero accediendo desde la tarjeta de
  contactos frecuentes

    Given Validate "user1" balance
    #Given "user1" knows his numeric balance
    #Given Permiso para ver contactos "si"
    And Usuario presiona boton de contactos frecuentes
    And Usuario esta en pantalla "contactos_desde_dashboard"
    And Validar que hay contactos "nubi"
    When Seleccionar contacto "nubi"
    Then Usuario esta en pantalla "detalle_contacto"
    Then Validar que el contacto seleccionado posee cuenta "nubi"
    Then Seleccionar la cuenta "nubi"
    # Then Validar que modo solicitar este "activo"
    #Then "usuario1" ingresa el monto "10.11"
    #And usuario1 pulsa boton siguiente
    #And usuario1 pulsa boton para confirmar la transaccion al solicitar
    #Then Validar estado de transaccion solicitar
    #Then Validate "user1" balance "before" type request "a"
    Then Resetear app "si"