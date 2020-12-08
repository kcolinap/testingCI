@ignore
@e2e
Feature: Aceptar solicitud

    Este fetaure contempla el flujo completo de aceptar una solicitud de transferencia.
  Se debe haber ejecutado el feature de registration, que crea dos usuarios que seran,
  utilizados en este feature

  Background:
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Permiso para ver contactos "si"

  Scenario Outline: Aceptar-Rechazar-Cancelar solicitud
    Given Usuario "<userToLog>" inicia sesion
    Then Usuario esta en pantalla "dashboard"
    Given Validate "<userToLog>" balance
    #When "<userTransfering>" tap action button to "<mode>"
    #And "<userTransfering>" set "<amount>" to be transfer
    And Select "<userTo>" to send - request money
    #And "<userTransfering>" tap button next to confirm transaction on "request"
    #Then Validate status transaction on "<mode>"
    Then "<userTransfering>" go to "<requestListType>" requests list
    Then "<userTransfering>" validate status request as "<requestStatus>"
    Then "<userToRequestList>" go to "<requestListType>" requests list
    Then "<userToRequestList>" validate status request as "<requestStatus>"
    And "<userToRequestList>" tap to "<type>" the request
    #And "<userToRequestList>" set its pin
    Then "<userToRequestList>" validate status request as "<newRequestStatus>"
    #Then Validate "<userToLog>" balance "<when>" type request "<type>"
    Then Resetear app "<resetApp>"
    #Then Usuario <userToLogAfter> inicia sesion
    #Then Validate "<userToLogAfter>" balance "<when>" type request "<type>"
    Then "<userToLogAfter>" go to "sended" requests list
    Then "<userToLogAfter>" validate status request as "<newRequestStatus>"
    Then Resetear app "si"
  Examples:
  | userToLog |  userTransfering | mode   | amount  | userTo  | userToTap | userToRequestList | requestListType | requestStatus  |  when  | type  | resetApp  | userToLogAfter  | newRequestStatus  |
  |  user1    |       user1      | request|  10.00  |  user2  |   user1   |       none        |      sended     |    Pendiente   | before |   a   |    no     |       none      |      Aceptado     |
  |   user2   |       none       | none   |  10.00  |  none   |   none    |       user2       |    received     |    Pendiente   |  after |   a   |    si     |     user1       |     Aceptado      |
  |  user1    |       user1      | request|  10.00  |  user2  |   user1   |       none        |      sended     |    Pendiente   | before |   r   |    no     |       none      |      Rechazado    |
  |   user2   |      none        | none   |  10.00  |  none   |    none   |       user2       |    received     |    Pendiente   |  after |   r   |    si     |     user1       |     Rechazado     |
#


#  Scenario Outline: Cancelar solicitud
#    Given Usuario <userToLog> inicia sesion
#    Given Validate "<userToLog>" balance
#    When "<userTransfering>" tap action button to "<mode>"
#    And "<userTransfering>" set "<amount>" to be transfer
#    And Select "<userTo>" to send - request money
#    And "<userTransfering>" tap button next to confirm transaction on "request"
#   # Then "none" set its pin
#    Then Validate status transaction on "<mode>"
#    Then "<userTransfering>" go to "<requestListType>" requests list
#    Then "<userTransfering>" validate status request as "<requestStatus>"
#    Then "<userToRequestList>" go to "<requestListType>" requests list
#    Then "<userToRequestList>" validate status request as "<requestStatus>"
#    And "<userToRequestList>" tap to "<type>" the request
#    Then "<userToRequestList>" validate status request as "<newRequestStatus>"
#    Then Validate "<userToLog>" balance "<when>" type request "<type>"
#    Then Resetear app "<resetApp>"
#    Then Usuario <userToLogAfter> inicia sesion
#    Then Validate "<userToLogAfter>" balance "<when>" type request "<type>"
#    Then "<userToLogAfter>" go to "sended" requests list
#    Then "<userToLogAfter>" validate status request as "<newRequestStatus>"
#  Examples:
#      | userToLog |  userTransfering | mode   | amount  | userTo  | userToTap | userToRequestList | requestListType | requestStatus  |  when  | type  | resetApp  | userToLogAfter  | newRequestStatus  |
#      |  user1    |       user1      | request|  10.00  |  user2  |   user1   |       none        |      sended     |    Pendiente   | before |   c   |    no    |       none      |      Cancelado     |
#      |   user2   |       none       | none   |  10.00  |  none   |   none    |       user2       |    received     |    Pendiente   |  after |   a   |    si     |     user1       |     Aceptado      |
#      |  user1    |       user1      | request|  10.00  |  user2  |   user1   |       none        |      sended     |    Pendiente   | before |   r   |    no    |       none      |      Rechazado    |
#      |   user2   |      none        | none   |  10.00  |  none   |    none   |       user2       |    received     |    Pendiente   |  after |   r   |    si     |     user1       |     Rechazado     |


#  Scenario: Acceptar solicitud
#
#    Este scenario prueba el flujo completo de aceptacion de solicitud desde el listado de
#    solicitudes. "user1" realiza la solicitud, "user2" acepta la solicitud
#
#
#    Given Usuario user1 inicia sesion
#    Given Validate "user1" balance
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
#
#    Then Usuario user2 inicia sesion
#    Given "user2" has more than cero in his balance
#    #Then "user2" knows his numeric balance
#    Then Validate "user2" balance "before" type request "a"
#    Then "user2" go to "received" requests list
#    And "user2" validate the request from user1
#    And "user2" tap to "accept" the request
#    Then Validate "user2" balance "after" type request "a"
#    Then Resetear app "si"
#    Then Usuario user1 inicia sesion
#    Then Validate "user1" balance "after" type request "a"
#    Then Resetear app "si"


