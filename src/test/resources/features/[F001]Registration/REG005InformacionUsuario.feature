@registration @regression
Feature: Informacion de usuario en registro

#
  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)

    Given Resetear app "<resetearApp>"
    Given Pulsar registrar "<pulsar_registrar>"
    Given Completar hasta pantalla <completar_pantalla>
    When Ingresar nombre de usuario "<username>"
    And Ingresar password "<password>"
    Then Boton circulo siguiente en pantalla "<pantalla>" esta "<status>"
    Then Mensaje de error en input de pantalla "<pantalla>" se encuentra "<error>"
    Examples:
      |  username |  password  | status |       error       |     pantalla     |        descripcion            | testID  |resetearApp|pulsar_registrar|completar_pantalla|
      |           |  111Cc-11  |   f    |         f         | USER_INFORMATION |            username vacio     |  C21512       |    si     |     true       |         4        |
      |   usuario |            |   f    |         f         | USER_INFORMATION |          pasword vacio        |         |           |                |      0            |
      |    use    |  111Cc-11  |   f    |    USER_LENGTH    | USER_INFORMATION | Username menos de 4 caracteres|   C163  |           |                |       0           |
      |  user#$   |  111Cc-11  |   f    | USER_SPECIAL_CHAR | USER_INFORMATION | Username caracteres especiales|   C163  |           |                |        0          |
      |  usuario  |  111(--11  |   f    |         f         | USER_INFORMATION |     Password sin letras       |   C168  |           |                |        0          |
      |  usuario  |  Cc-(kLha  |   f    |         f         | USER_INFORMATION |     Password sin numero       |   C169  |           |                |        0          |
      |  usuario  |   Cc11-(   |   f    |         f         | USER_INFORMATION |     Password menos de 8 char  |   C165  |           |                |        0          |
      |  usuario  |  c1-(klha  |   f    |         f         | USER_INFORMATION |     Password sin mayusculas   |   C167  |           |                |        0          |
      |  usuario  |  c1Nfklha  |   f    |         f         | USER_INFORMATION |   Password sin char especial  |   C170  |           |                |        0          |
      | user test |  111Cc-11  |   f    |    USER_SPACE     | USER_INFORMATION |     Espacio en campo user     |   C171  |           |                |        0          |
      |  usuario  |  c1Nf klha |   f    |  PASSWORD_SPACE   | USER_INFORMATION |   Espacio en campo password   |   C174  |           |                |        0          |
      |  usuario  |  111Cc-11  |   t    |         f         | USER_INFORMATION |         Usuario valido        |   C164 - C21513  |           |                |        0          |
      |  usuario  |  111Cc-11  |   t    |         f         | USER_INFORMATION |        Password valido        |   C166  |           |                |        0          |
#
#

  @walletint @stage
  Scenario Outline: <descripcion> pantalla <pantalla> - (<testID>)

  Retormar proceso de registro en pantalla de informacion de usuario

    Given Usuario esta en pantalla "<pantalla>"
    And Usuario cierra la app
    When Inicia la app
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
      |     pantalla      |           descripcion           | testID  |
      |  USER_INFORMATION |  Retomar proceso de registro en |  C249   |
#
#
  @walletint @stage
  Scenario Outline: <descripcion> creacion de usuario - (<testID>)

  Hacer background en la app desde la pantalla de datos personles(C148)
  Jira ticket: https://tunubi.atlassian.net/browse/NWA-26

    When Ingresar nombre de usuario "<username>"
    And Ingresar password "<password>"
    And Presiona boton de aplicaciones recientes
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
      |     pantalla      | username |  password  |          descripcion        | testID  |
      | USER_INFORMATION  | usuario  |  Test-0000  |  Background desde pantalla  |  C182   |
#
#
  @walletint @stage
  Scenario Outline: <descripcion>-(<testID>)

    Given Ingresar nombre de usuario "<user>"
    And Ingresar password "<pass>"
    And Click boton circulo siguiente en pantalla "<pantalla>"
    And Pulsar back en "dispositivo" en pantalla "REG_PHONE_NUMBER"
    When Ingresar nombre de usuario "<usernew>"
    And Ingresar password "<passnew>"
    And Click boton circulo siguiente en pantalla "<pantalla>"
    Then Pulsar back en "app" en pantalla "REG_PHONE_NUMBER"
    Then Validar persistencia de usuario "<usernew>" y password vacio
    Examples:
      |   user    |    pass    |   usernew  |   passnew  |     pantalla     | tipoBotonback|  descripcion  | testID  |
      | random    |  111Cc-11  | random2    |  cC111-11  | USER_INFORMATION |              | Editar usuario|  C181   |
#
#
  @walletint @stage
  Scenario Outline: <descripcion>-(<testID>)
    When Pulsar back en "<tipoBtn>" en pantalla "<pantalla>"
    Then Usuario esta en pantalla "cuil"
    Examples:
      |    tipoBtn  |      pantalla     |        descripcion           | testID  |
      |     app     |USER_INFORMATION |Volver a la pantalla de CUIL  |  C162   |


  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)

    Contempla el TC C176

    Given Click boton circulo siguiente en pantalla "cuil"
    When Usuario ingresa username ya registrado
    And Ingresar password "111Cc-11"
    And Click boton circulo siguiente en pantalla "<pantalla>"
    Then Mensaje de error en input de pantalla "<pantalla>" se encuentra "<error>"
    Examples:
    |     pantalla     | mensajeError|      error    |      descripcion      | testID |
    | USER_INFORMATION |             |  REGISTERED   | Usuario ya registrado |  C176  |


  @walletint @stage
  Scenario Outline: : <descripcion> en pantalla creacion de usuario - (<testID>)

    When Ingresar nombre de usuario "<user>"
    And Ingresar password "<pass>"
    And Boton circulo siguiente en pantalla "<pantalla>" esta "<status>"
    And Click boton circulo siguiente en pantalla "<pantalla>"
    When Pulsar back en "<tipoBtn>" en pantalla "reg_phone_number"
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
      |    tipoBtn  |      pantalla     |  user  |   pass  | status |            descripcion           | testID  |
      |     app     | USER_INFORMATION  | random | Test-0000|   t    |  Back desde el boton de la app   |  C175   |
      | dispositivo | USER_INFORMATION  | random | Test-0000|   t    |Back desde el boton dl dispositivo|  C183   |