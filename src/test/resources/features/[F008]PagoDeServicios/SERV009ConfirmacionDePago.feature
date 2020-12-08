@service_payment @payment_confirmation @regression
Feature: Validar la informacion de pago antes de confirmar el pago del servicio
  Casos de prueba para la validacion de la pantalla confirmacion de pago de servcios.

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

  @walletint
  Scenario: Validar elementos en pantalla CONFIRMACION - Monto Fijo (C1480)
    When Ingresa manualmente el codigo de la factura de tipo "monto_fijo" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    Then Usuario esta en pantalla "confirmacion_pago_servicio"
    And Valida los elementos de la pantalla confirmacion de pago de servicio desde "monto_fijo"

    #Actualmente no existe un codigo de factura que redirija a la pantalla monto abierto (16/06/2020)
  @walletint @ignore
  Scenario: Validar elementos en pantalla CONFIRMACION - Monto Abierto (C1480, C1486)
    When Ingresa manualmente el codigo de la factura de tipo "monto_abierto" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    And Usuario esta en pantalla "factura_monto_abierto"
    And Presiona el boton siguiente en la pantalla monto abierto
    Then Usuario esta en pantalla "confirmacion_pago_servicio"
    And Valida los elementos de la pantalla confirmacion de pago de servicio desde "monto_abierto"

  @walletint
  Scenario: Validar elementos en pantalla CONFIRMACION - Multiples Facturas (C1480)
    When Ingresa manualmente el codigo de la factura de tipo "multiples_facturas" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    And Usuario esta en pantalla "multifacturas"
    And Selecciona una factura de "monto_fijo" en la pantalla de multiples facturas
    Then Usuario esta en pantalla "confirmacion_pago_servicio"
    And Valida los elementos de la pantalla confirmacion de pago de servicio desde "multiples_facturas"

  @walletint
  Scenario: Validar redireccionamiento boton Pagar en pantalla CONFIRMACION (C1479)
    When Ingresa manualmente el codigo de la factura de tipo "monto_fijo" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    And Usuario esta en pantalla "confirmacion_pago_servicio"
    When Presiona el boton pagar en la pantalla confirmacion de pago de servicio
    Then Usuario esta en pantalla "pin"
