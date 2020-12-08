@prepaid_card @regression
Feature: Tarjeta Prepaga, movimientos

  @walletint
  Scenario Outline: <description> - <testId>

    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    When Usuario "<tipousuario>" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "tarjeta_prepaga" en el menu
    And Usuario esta en pantalla "<tipoTarjeta>"
    When Generar movimiento de tipo "<tipoMovimiento>", estatus "<status>", "<tipoImpuesto>" y monto "<monto>"
    And Ir al dashboard
    Then Validar movimiento "<tipoMovimiento>", "<tipoImpuesto>" en "<tipoValidacion>" y "<monto>" con status "<status>"
    Examples:
      | tipousuario           |  tipoTarjeta  | tipoMovimiento | tipoImpuesto | monto  | tipoValidacion     | status    |                             description                    |          testId       |
      | PHYSICAL_PREPAID_CARD |  pc_physical  | extraccion_tp  | sin_impuesto | 117.01 | lista_movimiento   |           | Extraccion sin impuesto, validacion en lista de movimiento |  C10719-C10722-C10725 |
      | PHYSICAL_PREPAID_CARD |  pc_physical  | extraccion_tp  | con_impuesto | 100.01 | detalle_movimiento |           |     Extraccion tp con impuesto, validacion del detalle     | C10723-C10732-C10733-C10735        |
      | PHYSICAL_PREPAID_CARD |  pc_physical  | extraccion_tp  | con_impuesto | 100.01 | detalle_movimiento | rechazado |  Extraccion rechazada con impuesto. Validacion del detalle | C11748-C11749                       |
      | PHYSICAL_PREPAID_CARD |  pc_physical  | extraccion_tp  | sin_impuesto | 100.01 | lista_movimiento   | rechazado |   Extraccion rechazada sin impuesto. Validacion en lista.  | C11752-C10723-C11754-C11755         |
      | PHYSICAL_PREPAID_CARD |  pc_physical  | extraccion_tp  | sin_impuesto | 127.01 | lista_movimiento   | cancelado | Extraccion cancelada sin impuesto. validacion en lista     | C11761-C11762-C11764-C11765-C11766-C11768         |
      | PHYSICAL_PREPAID_CARD |  pc_physical  | extraccion_tp  | con_impuesto | 130.01 | detalle_movimiento | cancelado | Extraccion cancelada con impuesto. Validacion en detalle   | C11767-C11771-C11772-C11773-C11774         |
#      | PHYSICAL_PREPAID_CARD | pc_physical      | consumo        | sin_impuesto | 100.01 | detalle_movimiento |           | Consumo sin impuesto, validacion en detalle                        |         |
#      | PHYSICAL_PREPAID_CARD | pc_physical      | consumo        | con_impuesto | 100.01 | lista_movimiento   |           |                         |         |
#      #| PHYSICAL_PREPAID_CARD | pc_physical      | consumo        | con_impuesto | 100.01 | detalle_movimiento |           |
#      | PHYSICAL_PREPAID_CARD | pc_physical      | consumo        | sin_impuesto | 127.01 | lista_movimiento   | rechazado |                         |         |
#      | PHYSICAL_PREPAID_CARD | pc_physical      | consumo        | sin_impuesto | 130.01 | detalle_movimiento | rechazado |                         |         |
#      | PHYSICAL_PREPAID_CARD | pc_physical      | consumo        | sin_impuesto | 135.01 | lista_movimiento   | cancelado |                         |         |
#      | PHYSICAL_PREPAID_CARD | pc_physical      | consumo        | sin_impuesto | 149.01 | detalle_movimiento | cancelado |                         |         |
#      | PHYSICAL_PREPAID_CARD | pc_physical      | cargo          | sin_impuesto | 155.01 | detalle_movimiento |           |                         |         |
#      | PHYSICAL_PREPAID_CARD | pc_physical      | cargo          | sin_impuesto | 155.01 | lista_movimiento   |           |                         |         |

  @walletint
  Scenario Outline: Validacion de consumos por suscripcion

    Given Usuario esta en pantalla "login"
    When Usuario "<tipousuario>" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "tarjeta_prepaga" en el menu
    #And Validar elementos de tarjeta prepaga "<tipoTarjeta>"
    And Usuario esta en pantalla "<tipoTarjeta>"
    When Generar movimiento de tipo "<tipoMovimiento>", estatus "<nombreComercio>", "<tipoImpuesto>" y monto "<monto>"
    And Ir al dashboard
    Then Validar movimiento de suscripcion "<tipoMovimiento>", "<tipoImpuesto>" en "<tipoValidacion>" y "<monto>" con nombreComercio "<nombreComercio>"
    Then Resetear app "si"
     # Si la posición 23 es blanco se muestra la posición 1 a la 23
     # Si la posición 24 que se trae del comercio es un blanco, se muestra la descripción entre la posición 1 y 23
     # Sino se muestra de la posición 1 a la 25
    Examples:
      |      tipousuario     | tipoTarjeta  | tipoMovimiento | tipoImpuesto | monto  |   tipoValidacion     |  nombreComercio    |
      | VIRTUAL_PREPAID_CARD | pc_virtual   |   suscripcion  | sin_impuesto | 100.11 | detalle_movimiento   |                    |
      | VIRTUAL_PREPAID_CARD | pc_virtual   |   suscripcion  | con_impuesto | 100.00 | detalle_movimiento   |                     |
      | PHYSICAL_PREPAID_CARD | pc_physical  |   suscripcion  | con_impuesto | 130.00 | detalle_movimiento   |Mostrar hasta esta pos. info extra|
      | PHYSICAL_PREPAID_CARD | pc_physical  |   suscripcion  | con_impuesto | 100.00 | detalle_movimiento   |Mostrar hasta esta posi. extra|
      | PHYSICAL_PREPAID_CARD | pc_physical  |   suscripcion  | con_impuesto | 100.00 | detalle_movimiento   |Mostrar hasta veinticinco|


  @walletint @ignore
  Scenario: Validacion de movimientos tarjeta prepaga (extraccion)
    Given Usuario esta en pantalla "login"
    When Usuario "PHYSICAL_PREPAID_CARD" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "tarjeta_prepaga" en el menu
    And Validar elementos de tarjeta prepaga "fisica"
    When Generar movimiento de tipo "extraccion_tp", estatus "", "sin_impuesto" y monto "100.01"
    And Ir al dashboard
    Then Validar movimiento "extraccion_tp", "sin_impuesto" en "detalle_movimiento" y "100.01" con status ""
    Then Resetear app "si"