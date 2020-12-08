@service_payment @generic_error @regression
Feature: Mostrar pantalla de error generico al escanear o ingresar un codigo invalido
  Casos de prueba para la pantalla de error generico de pagos de servicio

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
    When Ingresa manualmente el codigo de la factura de tipo "random" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente

  @walletint
  Scenario: Validar que al ingresar manualmente un codigo erroneo se muestre la pantalla de error generico y validar sus elementos (C1593, C1594)
    Then Usuario esta en pantalla "ps_error_generico"
    And Valida los elementos de la pantalla error generico de pago de servicios

  @walletint
  Scenario: Validar redireccionamieto del boton Reintentar (C1595)
    And Usuario esta en pantalla "ps_error_generico"
    When Presiona el boton Reintentar en pantalla error generico
    Then Usuario esta en pantalla "ingresar_codigo_manual"

  @walletint
  Scenario: Validar redireccionamieto del link Volver al inicio (C1596)
    And Usuario esta en pantalla "ps_error_generico"
    When Presiona sobre el link volver al inicio
    Then Usuario esta en pantalla "dashboard"

  @walletint
  Scenario: Validar que se pueda corregir el codigo y continuar el flujo Pago de servicios (C1597)
    And Usuario esta en pantalla "ps_error_generico"
    When Presiona el boton Reintentar en pantalla error generico
    And Usuario esta en pantalla "ingresar_codigo_manual"
    And Se borra el contenido del campo "codigo_factura_manual"
    And Ingresa manualmente el codigo de la factura de tipo "monto_fijo" a pagar
    And Presiona sobre el boton continuar en pantalla ingresar codigo manualmente
    Then Usuario esta en pantalla "confirmacion_pago_servicio"
