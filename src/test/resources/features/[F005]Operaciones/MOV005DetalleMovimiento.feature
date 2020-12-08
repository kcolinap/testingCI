@movements @regression
Feature: Detalle de Movimientos (Solicitud y Envio)
  Se valida que los distintos movimientos (P2P, CVU a CBU, CBU a CVU, etc) se reflejen de manera adecuada en el detalle
  de cada movimiento.

  Se contempla los siguientes test cases:
  º https://nubiwallet.testrail.io//index.php?/cases/view/1497
  º https://nubiwallet.testrail.io//index.php?/cases/view/1498
  º https://nubiwallet.testrail.io//index.php?/cases/view/1499
  ª https://nubiwallet.testrail.io//index.php?/cases/view/1501
  @walletint
  Scenario: Validar el detalle de un movimiento al realizar un envío P2P (C1497)
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    And Usuario "con_nubi" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permitir el acceso a los contactos "si"
    And Usuario esta en pantalla "contactos_desde_menu"
    And Validar que hay contactos "nubi"
    And Seleccionar contacto "nubi"
    And Seleccionar la cuenta "nubi"
    And "user1" ingresa un monto "menor" que el disponible en su cuenta
    And Click siguiente en pantalla "enviar_o_solicitar"
    And Click siguiente en pantalla "detalle_de_operacion"
    And Usuario esta en pantalla "registropin"
    And Usuario ingresa un numero de pin "valido"
    And Usuario esta en pantalla "envio_exitoso"
    And Pulsa sobre el boton finalizar
    And Usuario esta en pantalla "dashboard"
    When Se pulsa sobre el ultimo movimiento de tipo "envio" que acabamos de realizar con status ""
    Then Se valida los elementos de la pantalla detalle de movimiento "envio", con impuesto "", con monto "" y estado ""


  @ignore
  Scenario: Validar el detalle de un movimiento al realizar una solicitud de dinero P2P (C1498)
    #Given para probar query




















































