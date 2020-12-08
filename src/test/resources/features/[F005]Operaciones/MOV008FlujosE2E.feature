@movements @regression
Feature: P2P

  @walletint @stage @smoke
  Scenario Outline: <descripcion> - (<id>)

    Happy path flujos p2p. Envio y solicitud

    Given Resetear app "si"
    And Find user type "<kindUser>" and set preReq "<preReq>"
    And Validate origin user has contacts "<kindContact>"
    And Set current users balance
    And Iniciar sesion
    When Pulsa el boton "principal" en el menu
    When Pulsa el boton "<tipoOperacion>" en el menu
    And Aceptar permisos de "CONTACTOS"
    And Selecciona contacto "<tipoContacto>"
    And Confirmar contacto "<tipoContacto>"
    And Ingresar monto "<tipoMonto>" para "<tipoOperacion>"
    And Confirmar transaccion de "<tipoOperacion>"
    And Usuario ingresa clave nubi para flujo "<nubiPassword>"
    Then Validar transaccion exitosa "<tipoOperacion>"
    And Validar movimiento "<tipoMovimiento>", "" en "<tipoValidacion>" y "" con status ""
    And Ir al dashboard
    And Validar balance despues la transaccion de "<tipoOperacion>"
    Examples:
      | kindUser                     | kindContact  | preReq | tipoContacto | tipoOperacion   | tipoMovimiento       | nubiPassword | tipoValidacion     | descripcion                  | id       | tipoMonto |
      | ORIGIN_USER_P2P_NUBI_SENDING | NUBI_CONTACT | IV     | NUBI         | SEND            | P2P_NUBI_SEND        | P2P          | detalle_movimiento | Envio P2P, contacto NUBI     | NWA-3200 | <         |
      | ORIGIN_USER_P2P_NUBI_REQUEST | NUBI_CONTACT |        | NUBI         | REQUEST_PENDING | P2P_NUBI_REQ_PENDING |              |                    | Solicitud P2P, contacto NUBI | NWA-3200 | <         |



  @walletint @stage @smoke
  Scenario Outline: <descripcion> - (<id>)

   Aceptar - Rechazar solicitudes tthtyrgrr

    Given Resetear app "si"
    And Validate origin user has contacts "<kindContact>"
    And Set current users balance
    When Origin user "<typeRequest>" a p2p request
    And Validate pending "<typeRequest>" request
    And User "<typeAction>" the request
    Then Validar movimiento "<tipoMovimiento>", "" en "<tipoValidacion>" y "" con status ""
    And Ir al dashboard
    And Validar balance despues la transaccion de "<typeAction>"
    Examples:
      | kindContact  | typeRequest | typeAction | tipoMovimiento | tipoValidacion     | descripcion        |
      | NUBI_CONTACT | RECEIVE     | ACCEPT     | P2P_NUBI_SEND  | detalle_movimiento | Aceptar solicitud  |
      | NUBI_CONTACT | RECEIVE     | REJECT     |                |                    | Cancelar solicitud |