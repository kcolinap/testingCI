@Registration @regression
Feature: validacion telefono en registro

  @walletint @stage
  Scenario Outline: <descripcion> -(<testID>)

    Given Resetear app "<resetearApp>"
    Given Pulsar registrar "<pulsar_registrar>"
    And Completar hasta pantalla <completar_pantalla>
    When Ingresar codigo de area "<codigo>"
    And Ingresar numero telefonico "<numero>"
    Then Boton circulo siguiente en pantalla "<pantalla>" esta "<status>"
    Then Mensaje de error en input de pantalla "<pantalla>" se encuentra "<error>"
    Examples:
      |    pantalla   | codigo  |    numero    |status|error|              descripcion               | testID  |resetearApp|pulsar_registrar|completar_pantalla|
      | REG_PHONE_NUMBER|         |   99976456   |  f   |  t  |         Codigo de area vacio           |         |   si      |     true       |         5        |
      | REG_PHONE_NUMBER|   11    |     99999    |  f   |  t  |            Numero de 5 digitos         |  C203   |           |                |        0         |
      | REG_PHONE_NUMBER|   11    |   15344345   |  f   |  t  |        Numero de telefono con 15       |  C204   |           |                |        0         |
      | REG_PHONE_NUMBER|   01    |   99994389   |  f   |  f  |            Codigo de area con 0        |  C205   |           |                |        0         |
      | REG_PHONE_NUMBER|   15    |   99994389   |  f   |  f  |            Codigo de area con 15       |  C206   |           |                |        0         |
      | REG_PHONE_NUMBER|   911   |   99994389   |  f   |  t  |            Codigo de area con 9        |  C236   |           |                |        0         |
      | REG_PHONE_NUMBER|  1234   |    1234567   |  f   |  t  |        Codigo de area inexistente      |  C199   |           |                |        0         |
      | REG_PHONE_NUMBER|    11   |   99994399   |  t   |  f  |         Codigo de area valido          |  C195   |           |                |        0         |
      | REG_PHONE_NUMBER|  1111   |    999943    |  t   |  f  |            Colocar numero              |  C196   |           |                |        0         |
      | REG_PHONE_NUMBER|  2966   |    999943    |  t   |  f  |       Codigo de provincia valido       |  C198   |           |                |        0         |
#
#
  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)

    When Ingresar codigo de area "<codigo>"
    And Ingresar numero telefonico "<numero>"
    Then El campo "<campo>" tiene solo la longitud valida
    Examples:
    |      pantalla   | codigo |  numero |   campo  |         descripcion           | testID  |
    | REG_PHONE_NUMBER|  11111 |  123456 |  codigo  |Limite de campo codigo de area |  C207   |
    | REG_PHONE_NUMBER|    11  |123456789|  numero  |Limite de campo numero de telef|  C208   |
#
#
  @walletint @stage
  Scenario Outline: <descripcion> pantalla Validacion de telefono - (<testID>)

    Given Usuario esta en pantalla "<pantalla>"
    And Usuario cierra la app
    When Inicia la app
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
    |     pantalla       |           descripcion           | testID  |
    |  REG_PHONE_NUMBER  |  Retomar proceso de registro en |  C250   |
#
#
  @walletint @stage
  Scenario Outline: <descripcion> de validacion de telefono - (<testID>)

    Hacer background en la app desde la pantalla de validacion de telefono(C148)

    Given Usuario esta en pantalla "<pantalla>"
    And Presiona boton de aplicaciones recientes
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
    |      pantalla   | codigo |  numero  |          descripcion        | testID  |
    | REG_PHONE_NUMBER|   11  | 99994399  |  Background desde pantalla  |  C818   |
#
#
  @walletint @stage
  Scenario Outline: : <descripcion> en pantalla Codigo sms - (<testID>)

    When Ingresar codigo de area "<codigo>"
    And Ingresar numero telefonico "<numero>"
    And Boton circulo siguiente en pantalla "<pantalla>" esta "<status>"
    And Click boton circulo siguiente en pantalla "<pantalla>"
    And Pulsar back en "<tipoBtn>" en pantalla "reg_sms_code"
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
      |    tipoBtn  |      pantalla       | codigo |  numero  | status |            descripcion           | testID  |
      |     app     |   REG_PHONE_NUMBER  |   11   |  random  |   t    |  Back desde el boton de la app   |  c819   |
      | dispositivo |   REG_PHONE_NUMBER  |   11   |  random  |   t    |Back desde el boton dl dispositivo|  C820   |

  @walletint @stage
  Scenario Outline: <descripcion>-(<testID>)
    Given Usuario esta en pantalla "<pantalla>"
    When Pulsar back en "<tipoBtn>" en pantalla "<pantalla>"
    Then Usuario esta en pantalla "user_information"
    Examples:
    |    tipoBtn  |        pantalla     |                descripcion                | testID  |
    |     app     |   REG_PHONE_NUMBER  |Volver a la pantalla de creacion de usuario|  C197   |