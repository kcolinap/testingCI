@prepaid_card @regression
Feature: Tarjeta Prepaga, aviso de viaje


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
      |      tipousuario      | tipoTarjeta  |    action    |  screenTypeResult  |           description               |      testId     |
      | PHYSICAL_PREPAID_CARD |  pc_physical |  AVISO_VIAJE |     AVISO_VIAJE    | Validar pantalla aviso de viaje     | C21826, C21696  |


  @walletint
  Scenario Outline: <description> - <testId>

    Given Resetear app "si"
    And Usuario "<tipousuario>" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Actualizar estado de tarjeta 0
    And Pulsa el boton "tarjeta_prepaga" en el menu
    And Usuario esta en pantalla "pc_physical"
    When Pulsar boton para "<action>" la tarjeta "<tipoTarjeta>"
    And Usuario esta en pantalla "AVISO_VIAJE"
    And Pulsar back en "<tipoBack>" en pantalla "AVISO_VIAJE"
    Then Usuario esta en pantalla "pc_physical"
    Examples:
      |      tipousuario      | tipoTarjeta  |    action    |  tipoBack  |                      description                     |      testId     |
      | PHYSICAL_PREPAID_CARD |  pc_physical |  AVISO_VIAJE |     app    | Validar back desde la aplicacion pantalla de aviso de viaje     |      C21825     |
      | PHYSICAL_PREPAID_CARD |  pc_physical |  AVISO_VIAJE |     dispositivo    | Validar back desde el dispositivo pantalla de aviso de viaje     |      C21825     |


  @walletint
  Scenario Outline: <description> - <testId>

    Given Resetear app "si"
    And Usuario "<tipousuario>" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Actualizar estado de tarjeta 0
    And Pulsa el boton "tarjeta_prepaga" en el menu
    And Usuario esta en pantalla "pc_physical"
    And Pulsar boton para "<action>" la tarjeta "<tipoTarjeta>"
    And Usuario esta en pantalla "AVISO_VIAJE"
    When Pulsar boton avisar
    Then Validar que no esta activa la aplicacion NUBI
    Examples:
      |      tipousuario      | tipoTarjeta  |    action    |        description             |      testId     |
      | PHYSICAL_PREPAID_CARD |  pc_physical |  AVISO_VIAJE |  Verificar botón de Avisar     |      C21697     |
