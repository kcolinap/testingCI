@service_payment @regression
Feature: Ingreso de PIN para confirmar una operacion de tipo Pago de Servicio
  Casos de prueba aplicados al ingreso de PIN para confirmar el pago de un servicio

  @walletint
  Scenario: Validacion de elementos de la pantalla PIN (C1539)
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
    When Presiona el boton pagar en la pantalla confirmacion de pago de servicio
    Then Usuario esta en pantalla "pin"
#    And Se validan los elementos de la pantalla pin despues de "cargar la pantalla"

  @walletint
  Scenario: Validacion elementos de la pantalla PIN Error (C1540)
    Given Usuario esta en pantalla "pin"
    And Usuario ingresa un numero de pin "invalido"
#    Then Se validan los elementos de la pantalla pin despues de "ingresar un pin invalido"

  @walletint
  Scenario: Validacion al cerrar la pantalla PIN (C1541)
    Given Usuario esta en pantalla "pin"
    When Usuario pulsa boton cerrar en pantalla "ingresar_pin"
    Then Usuario esta en pantalla "confirmacion_pago_servicio"

  @walletint
  Scenario: Validar redireccionamiento a pantalla Confirmacion de pago exitoso (C1542)
    Given Usuario esta en pantalla "confirmacion_pago_servicio"
    And Presiona el boton pagar en la pantalla confirmacion de pago de servicio
    And Usuario esta en pantalla "pin"
    And Usuario ingresa un numero de pin "valido"
    Then Usuario esta en pantalla "pago_exitoso_servicio"





