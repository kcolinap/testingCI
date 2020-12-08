@ignore
@e2e
#Feature: Cancelar solicitud
#
#    Este fetaure contempla el flujo completo de cancelar una solicitud de transferencia.
#  Se debe haber ejecutado el feature de registration, que crea dos usuarios que seran,
#  utilizados en este feature
#
#  Background:
#    Given Usuario esta en pantalla "login"
#
#
#  Scenario: Acceptar solicitud
#
#    Este scenario prueba el flujo completo de aceptacion de solicitud desde el listado de
#    solicitudes. "user1" realiza la solicitud, "user2" acepta la solicitud
#
#
#    Given Usuario user1 inicia sesion
#    Given "user1" knows his numeric balance
#    Given Permiso para ver contactos "on"
#    When User tap action button to "request"
#    And Validate mode "request" is on
#    And Set an amount as "10.00" to be transfer
#    Then Tap continue button
#    Then Select "user2" to send - request money
#    Then "User1" tap button next to confirm transaction
#    Then Validate status transaction on "request"
#    Then Validate "user1" balance "before" type request "a"
#    Then Resetear app "si"
#    Then Usuario user2 inicia sesion
#    Then "user2" knows his numeric balance
#    Then Validate "user2" balance "before" type request "a"
#    Then "user2" go to "received" requests list
#    And "user2" validate the request from user1
#    And "user2" tap to "accept" the request
#    Then Validate "user2" balance "after" type request "a"
#    Then Resetear app "si"
#    Then Usuario user1 inicia sesion
#    Then Validate "user1" balance "after" type request "a"
#
#
