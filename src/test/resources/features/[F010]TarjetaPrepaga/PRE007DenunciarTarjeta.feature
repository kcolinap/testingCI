@prepaid_card @regression
Feature: Denunciar tarjeta

  @walletint
  Scenario Outline: <description> - <testId>

    Given Resetear app "si"
    And Usuario "<tipousuario>" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Actualizar estado de tarjeta 0
    And Pulsa el boton "tarjeta_prepaga" en el menu
    And Usuario esta en pantalla "<tipoTarjeta>"
    When Pulsar boton para "<action>" la tarjeta "<tipoTarjeta>"
    Then Validar pantalla de "<screenTypeResult>"
    Examples:
      |      tipousuario      | tipoTarjeta  |    action    |  screenTypeResult  |              description                 |         testId        |
      | PHYSICAL_PREPAID_CARD |  pc_physical |  REPORT_CARD |     REPORT_CARD    | Validar pantalla motivos de denuncia     | C22040-C22041-C22042  |


  @walletint
  Scenario Outline: <description> - <testId>

    Given Usuario esta en pantalla "REPORT_CARD"
    When Pulsar back en "<tipoBack>" en pantalla "REPORT_CARD"
    Then Usuario esta en pantalla "pc_physical"
    And Pulsar boton para "REPORT_CARD" la tarjeta "pc_physical"
    Examples:
      |   tipoBack  |                         description                           |      testId     |
      |      app    | Validar back desde la aplicacion pantalla de motivo denuncia  |      C22043     |
      | dispositivo | Validar back desde el dispositivo pantalla de motivo denuncia |      C22043     |


  @ignore @walletint
  Scenario Outline: <description> - <testId>

    Given Usuario esta en pantalla "REPORT_CARD"
    And se "desactiva" el modo avion del telefono
    When Pulsar confirmar denuncia
    Then Validar pantalla de "CONFIRM_REPORT_ERROR"
    And se "activa" el modo avion del telefono
    And Pulsar back en "app" en pantalla "ERROR_CONFIRM_REPORT_CARD"
    And Usuario esta en pantalla "pc_physical"
    Examples:
     |                             description                    |      testId     |
     |  Validar error al confirmar y back desde pantalla errorr   |  C22210-C22211  |
