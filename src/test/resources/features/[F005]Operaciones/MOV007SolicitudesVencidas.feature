@movements @regression
Feature: Validacion del comportamiento de solicitudes expiradas.
  Se verifica tanto en el emisor como en el receptor el manejo de las solicitudes

  @test @regression
  Scenario: Validar la expiracion correcta de una solicitud en W2 (C11760, C11759, C11757, C11756)
    Given Usuario esta en pantalla "login"
    And Usuario "CON_NUBI" inicia sesion
    When Se ejecuta una solicitud p2p para expirar
    And Resetear app "si"
    And Usuario esta en pantalla "login"
    And "Receptor" de solicitud p2p inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "solicitudes" en el menu
    And Usuario esta en pantalla "solicitudes"
    Then Receptor "Acepta" la solicitud p2p
    And Usuario ingresa un numero de pin "valido"
    And Usuario esta en pantalla "solicitudes"
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And "Emisor" de solicitud p2p inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "solicitudes" en el menu
    And Usuario esta en pantalla "solicitudes"
    When Presiona sobre el tab "enviadas"
    Then Valida que la solicitud aparezca caducada

    









#    And Usuario esta en pantalla "dashboard"
#    And Pulsa el boton "principal" en el menu
#    And Pulsa el boton "enviar" en el menu
#    And Permitir el acceso a los contactos "si"
#    And Usuario esta en pantalla "contactos_desde_menu"
#    And Validar que hay contactos "nubi"
#    And Seleccionar contacto "nubi"
#    And Seleccionar la cuenta "nubi"
#    And "user" ingresa un monto "from_step" que el disponible en su cuenta
#    And Click siguiente en pantalla "enviar_o_solicitar"
#    And Click siguiente en pantalla "detalle_de_operacion"
#    And Usuario esta en pantalla "registropin"
#    And Usuario ingresa un numero de pin "valido"
#    And Usuario esta en pantalla "envio_exitoso"
#    And Pulsa sobre el boton finalizar
#    And Usuario esta en pantalla "dashboard"
#    When Se pulsa sobre el ultimo movimiento de tipo "envio" que acabamos de realizar con status ""
#    Then Se edita la categoria de movimiento




















































