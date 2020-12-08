@movements @regression
Feature: Porcentaje de tamaño en iconos de categoria de movimientos
  Se valida que los iconos tengan un tamaño acorde a la resolucion de la pantalla.

  @walletint
  Scenario: Validar el que los iconos sean acordes a la pantalla
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
    And "user" ingresa un monto "from_step" que el disponible en su cuenta
    And Click siguiente en pantalla "enviar_o_solicitar"
    And Click siguiente en pantalla "detalle_de_operacion"
    And Usuario esta en pantalla "registropin"
    And Usuario ingresa un numero de pin "valido"
    And Usuario esta en pantalla "envio_exitoso"
    And Pulsa sobre el boton finalizar
    And Usuario esta en pantalla "dashboard"
    When Se pulsa sobre el ultimo movimiento de tipo "envio" que acabamos de realizar con status ""
    Then Se edita la categoria de movimiento

  @ignore
  Scenario: Validar el detalle de un movimiento al realizar una solicitud de dinero P2P (C1498)
    #Given para probar query




















































