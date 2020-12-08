@registration @regression
Feature: Validacion de codigo sms en registro

  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)
    Given Resetear app "<resetearApp>"
    Given Pulsar registrar "<pulsar_registrar>"
    And Completar hasta pantalla <completar_pantalla>
    Given Usuario esta en pantalla "<pantalla>"
    When Ingresar codigo SMS "<codigosms>"
    Then Mensaje de error en pantalla "<pantalla>" se encuentra "<error>"
    Examples:
    |codigosms|   pantalla   |error|       descripcion      |      testID   |resetearApp|pulsar_registrar|completar_pantalla|
    |  0000   | reg_sms_code |  t  |  Codigo sms incorrecto |  C253-C21553  |   si      |     true       |        6         |
#
#
  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)
  Reenvio de codigo sms(C258)
    Given Usuario esta en pantalla "<pantalla>"
    When tap reenviar codigo sms
    Then Validar que un nuevo codigo sms fue generado
  Examples:
  | pantalla|       descripcion    | testID  |
  |reg_sms_code| Reenvio de codigo sms|  C258   |
#
#
  @walletint @stage
  Scenario Outline: Ingresar codigo sms viejo(C254)
    Given Usuario esta en pantalla "<pantalla>"
    And tap reenviar codigo sms
    And Validar que un nuevo codigo sms fue generado
    When Usuario ingresa un codigo sms viejo
    Then Mensaje de error en pantalla "<pantalla>" se encuentra "<error>"
    Then Usuario esta en pantalla "<pantalla>"
  Examples:
  | pantalla | error |
  | reg_sms_code|   t   |
#
#
  @walletint @stage
  Scenario Outline: <descripcion> pantalla codigo sms - (<testID>)

   Retomar proceso de registro en pantalla de codigo sms

    Given Usuario esta en pantalla "<pantalla>"
    And Usuario cierra la app
    When Inicia la app
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
    |    pantalla  |           descripcion           | testID  |
    |  reg_sms_code   |  Retomar proceso de registro en |  C255   |
#
#
  @walletint @stage
  Scenario Outline: <descripcion> de codigo sms - (<testID>)

   Hacer background en la app desde la pantalla de codigo sms

    Given Usuario esta en pantalla "<pantalla>"
    When Presiona boton de aplicaciones recientes
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
      |    pantalla   |       descripcion          | testID  |
      |   reg_sms_code   | Background desde pantalla  |  C257   |
#
#
  @walletint @stage
  Scenario Outline: : <descripcion> en pantalla Codigo sms - (<testID>)

    Given Usuario esta en pantalla "<pantalla>"
    When Pulsar back en "<tipoBtn>" en pantalla "<pantalla>"
    Then Usuario esta en pantalla "reg_phone_number"
    And Click boton circulo siguiente en pantalla "reg_phone_number"
    Examples:
      |    tipoBtn  |      pantalla     | codigosms |            descripcion                | testID  |
      |     app     |     reg_sms_code     | correcto  |    Back desde el boton de la app      |  C233   |
      | dispositivo |     reg_sms_code     | correcto  | Back desde el boton del dispositivo   |  C234   |