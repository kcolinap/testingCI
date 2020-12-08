@service_payment @payment_successful @regression
Feature: Mostrar pantalla de confirmacion de pago para indicar al usuario que el abono de servicio fue exitoso
  Casos de prueba de la pantalla confirmacion de pago.

  Background:
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa sobre la tarjeta paga tus facturas
    And Usuario esta en pantalla "pago_de_servicios"
    And Presiona sobre el boton escanear codigo de barras
    And Permitir el acceso a la camara "si"
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

  @walletint
  Scenario: Validar elementos de la pantalla PS Confirmacion de pago y redireccionamiento del boton back del dispositivo  (C1579 y C1606)
    When Usuario esta en pantalla "pago_exitoso_servicio"
    Then Valida los elementos de pago exitoso
    When Pulsar back en "dispositivo" en pantalla "ps_pago_exitoso"
    Then Usuario esta en pantalla "dashboard"

  @walletint
  Scenario: Validar redireccionamiento del boton Finalizar de la pantalla PS Confirmacion de pago (C1605)
    Given Usuario esta en pantalla "pago_exitoso_servicio"
    When Presiona sobre el boton volver al inicio en la pantalla pago exitoso
    Then Usuario esta en pantalla "dashboard"


  #TODO Assert de descargar de comprobante (aun no implementado por desarrollo)
  #El expected result verdadero sera obtener el PDF (comprobante)
  @walletint
  Scenario: Validar redireccionamiento del boton Descargar comprobante de la pantalla PS Confirmacion de pago (C1608)
    When Usuario esta en pantalla "pago_exitoso_servicio"
    Then Se valida que el enlace descargar comprobante exista y este habilitado





