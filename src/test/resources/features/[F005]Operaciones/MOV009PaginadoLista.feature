@movements @regression
Feature: Paginado lista de movimientos

  @walletint
  Scenario Outline: <descripcion> <tipoCuenta> - (<id>)

    Flujo completo de envio de dinero accediendo desde el menu.

    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    Given Usuario "any" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    And Generar 15 movimientos de "<tipoMovmiento>"
    Examples:
    | tipoContacto | tipoMovmiento | tipoOperacion | tipoTransaccion | tipoValidacion |        descripcion       |     id   | tipoMonto|
    |    NUBI      |   recarga_celular     |      enviar   |     enviar      |       send     | Envio desde menu, cuenta | NWA-3200 |   menor  |
  #  |     nubi     |  bancaria  |     enviar    |  enviar bancaria |    sendbank   | Envio desde menu, cuenta | NWA-3028 |   mayor  |
