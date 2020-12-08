@loan @delivery_address @regression
Feature: Direccion de entrega
  Permite configurar la dirección de entrega del producto.

  Background:
    Given Resetear app "si"
    And Usuario esta en pantalla "login"
    And Usuario "con_prestamo" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Se visualiza la tarjeta de promocion de prestamos
    And Usuario presiona sobre el boton pedi el tuyo en la tarjeta de promociones
    And Usuario esta en pantalla "lista_de_articulos"
    And Se valida los elementos de la pantalla lista de productos en promocion
    And Usuario presiona el boton comprar en el producto 0
    And Usuario esta en pantalla "datos_del_prestamo"
    And Presiona el boton continuar en pantalla downpayment
    When Usuario esta en pantalla "direccion_de_envio"

  @walletint
  Scenario: Direccion de envio - Formato, Botones Volver, Boton Minimizar, Boton Alternar (C20782, C20785, C20786, C20787, C20788, C20805)
    Then Se validan los elementos de la pantalla direccion de envio
    When Pulsar back en "app" en pantalla "direccion_de_envio"
    Then Usuario esta en pantalla "datos_del_prestamo"
    When Presiona el boton continuar en pantalla downpayment
    And Usuario esta en pantalla "direccion_de_envio"
    And Pulsar back en "dispositivo" en pantalla "direccion_de_envio"
    Then Usuario esta en pantalla "datos_del_prestamo"
    When Presiona el boton continuar en pantalla downpayment
    And Usuario minimiza la app 2 segundos y vuelve abrirla
    Then Usuario esta en pantalla "direccion_de_envio"
    When Usuario presiona background desde el dispositivo
    Then Usuario esta en pantalla "direccion_de_envio"

  @walletint
  Scenario Outline: Direccion de envio - Link Crear otra direccion, Formato, Desambiguacion (C20783, C20805, C20806, C20808, C20810)
    And Presiona sobre el boton crear otra dirección
    Then Usuario esta en pantalla "agregar_direccion"
    And Usuario completa form de datos adicionales <modoCompletitud> y modo "<tipoDatos>" desde "prestamos"
    And Validar pantalla con <modoCompletitud> y datos "<tipoDatos>" desde "prestamos"
    Examples:
      | modoCompletitud | tipoDatos   |
      | 1               | CORRECTOS   |
      | 0               | CORRECTOS   |
      | 1               | DESAMBIGUOS |
      | 0               | DESAMBIGUOS |

  @walletint
  Scenario: Agregar / Confirmar Direccion - Boton volver de la App y del Telefono, Minimizar y Alternar (C20812, C20813, C20814, C20815, C20816, C20817, C20818, C20819)
    And Presiona sobre el boton crear otra dirección
    And Usuario esta en pantalla "agregar_direccion"
    When Usuario minimiza la app 2 segundos y vuelve abrirla
    Then Usuario esta en pantalla "agregar_direccion"
    When Usuario presiona background desde el dispositivo
    Then Usuario esta en pantalla "agregar_direccion"
    When Pulsar back en "app" en pantalla "agregar_direccion"
    Then Usuario esta en pantalla "direccion_de_envio"
    When Presiona sobre el boton crear otra dirección
    And Usuario esta en pantalla "agregar_direccion"
    And Pulsar back en "dispositivo" en pantalla "agregar_direccion"
    Then Usuario esta en pantalla "direccion_de_envio"
    When Presiona sobre el boton crear otra dirección
    And Usuario esta en pantalla "agregar_direccion"
    And Usuario completa form de datos adicionales 1 y modo "CORRECTOS" desde "prestamos"
    And Usuario esta en pantalla "confirmar_direccion"
    When Usuario minimiza la app 2 segundos y vuelve abrirla
    Then Usuario esta en pantalla "confirmar_direccion"
    When Usuario presiona background desde el dispositivo
    Then Usuario esta en pantalla "confirmar_direccion"
    When Pulsar back en "dispositivo" en pantalla "confirmar_direccion"
    Then Usuario esta en pantalla "agregar_direccion"

  @walletint
  Scenario: Direccion de envio - Boton Continuar (C20784)
    And Presiona sobre el boton continuar en la pantalla direccion de envio
    Then Usuario esta en pantalla "confirmacion_de_pago"




