@ignore @prepaid_card @regression
Feature: Tarjeta Prepaga, pin de extraccion, creacion y cambio


  @walletint
  Scenario Outline: <description> - <testId>

    Given Resetear app "si"
    And Usuario "<tipousuario>" inicia sesion
    And Actualizar estado de pin de extraccion "<pinStatus>"
    And Pulsa el boton "tarjeta_prepaga" en el menu
    And Usuario esta en pantalla "<tipoTarjeta>"
    When Pulsar boton para "<action>" la tarjeta "<tipoTarjeta>"
    And Usuario esta en pantalla "<screen>"
    #And Usuario ingresa un nuevo pin "correcto" de extraccion
    And Usuario esta en pantalla "EXTRACTION_PIN_CONFIRMATION"
    #And Usuario confirma un pin "<pinType>"
    Then Validar pantalla de "<screenTypeResult>"
    Examples:
      |      tipousuario      | tipoTarjeta  |  pinStatus  |    action    |   pinType   |      screenTypeResult  |                   description               |      testId   | screen                  |
      | PHYSICAL_PREPAID_CARD |  pc_physical |   DISABLED  |  CREATE_PIN  |   correcto  | CREATION_PIN_EXITOSA   |             Crear pin de extraccion         | C21541,C21531 | EXTRACTION_PIN_CREATION |
      | PHYSICAL_PREPAID_CARD |  pc_physical |   DISABLED  |  CREATE_PIN  |  incorrecto | ERROR_CONFIRMACION_PIN |     Error al confirmar pin de extraccion    |     C21535    | EXTRACTION_PIN_CREATION |
      | PHYSICAL_PREPAID_CARD |  pc_physical |   ENABLED   |  CHANGE_PIN  |   correcto  |   CAMBIO_PIN_EXITOSO   |           Cambio pin de extraccion          | C21544, C21543, C21545, C21548, C21687, C21550, C21547| EXTRACTION_PIN_CHANGE   |
      | PHYSICAL_PREPAID_CARD |  pc_physical |   ENABLED   |  CHANGE_PIN  |  incorrecto | ERROR_CONFIRMACION_PIN | Error al confirmar cambio pin de extraccion |     C21549    | EXTRACTION_PIN_CHANGE   |