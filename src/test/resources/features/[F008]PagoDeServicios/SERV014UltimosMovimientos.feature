@service_payment @regression
Feature: Ver listado el movimiento de pago junto con su detalle
  Casos de prueba para listado de movimientos y detalle en pago de servicios

  @walletint
  Scenario: Validar impacto del Pago de Servicios en el Dashboard (C1796)
    Given Usuario esta en pantalla "login"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa sobre la tarjeta paga tus facturas
    And Usuario esta en pantalla "pago_de_servicios"
    And Presiona sobre el boton escanear codigo de barras
    And Permitir el acceso a la camara "si"
    #And Usuario presiona background desde el dispositivo
    And Usuario esta en pantalla "escanear_codigo_de_barras"
    And Presiona sobre el boton ingresar codigo manualmente en la pantalla "escaner_factura"
    And Usuario esta en pantalla "ingresar_codigo_manual"
    And Ingresa manualmente el codigo de la factura de tipo "multiples_facturas" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    And Selecciona una factura de "monto_fijo" en la pantalla de multiples facturas
    And Usuario esta en pantalla "confirmacion_pago_servicio"
    And Presiona el boton pagar en la pantalla confirmacion de pago de servicio
    And Usuario esta en pantalla "pin"
    And Usuario ingresa un numero de pin "valido"
    And Usuario esta en pantalla "pago_exitoso_servicio"
    And Presiona sobre el boton finalizar en la pantalla pago exitoso
    And Usuario esta en pantalla "dashboard"
    And Se valida que exista el ultimo movimiento de tipo "pago_de_servicio" y estado "" que acabamos de realizar

  @walletint
  Scenario: Validar el detalle de una transaccion de tipo pago de servicios.
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    When Se pulsa sobre el ultimo movimiento de tipo "pago_de_servicio" que acabamos de realizar con status ""
    Then Se valida los elementos de la pantalla detalle de movimiento "pago_de_servicio", con impuesto "", con monto "" y estado ""

  @walletint
  Scenario: Validar impacto del Pago de Servicios en listado de Ultimos Movimientos (C1797)
    Given Usuario esta en pantalla "dashboard"
    When Presiona sobre ultimos movimientos
    And Usuario esta en pantalla "ultimos_movimientos"
    Then Verifica si el movimiento "pago_de_servicio" con estado "" se encuentra en la lista





