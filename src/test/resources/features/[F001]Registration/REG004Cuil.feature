@registration @regression
Feature: Cuil

  @walletint @stage
  Scenario: Validar pantalla de CUIl - (C152)
    Given Resetear app "si"
    Given Pulsar registrar "true"
    When Completar hasta pantalla 3
    Then Validar pantalla "cuil"
#
#
  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)

    Given Pulsar back en "app" en pantalla "cuil"
    And Ingresar DNI "random"
    And Seleccionar genero "<genero>"
    And Boton circulo siguiente en pantalla "PERSONAL_INFORMATION" esta "<status>"
    And Click boton circulo siguiente en pantalla "PERSONAL_INFORMATION"
    And Validar que el prefijo es "<prefijoAntes>"
    When Cambiar prefijo a "<prefijoDespues>"
    Then Validar cambio en codigo verificador
    And Validar que el prefijo es "<prefijoDespues>"
    And Boton circulo siguiente en pantalla "cuil" esta "<status>"
    Examples:
    |genero|status|       prefijoAntes     |prefijoDespues|                    descripcion                  |    testID   |
    |  h   |   t  |            20          |      23      |     Editar prefijo 20 de sexo Hombre por 23     |  C153,C189  |
    |  h   |  t   |            20          |      24      |     Editar prefijo 20 de sexo Hombre por 24     |      C154   |
    |  h   |  t   |            20          |      27      |Ediar prefijo 20 de sexo Hombre por 27 sexo Mujer|      C156   |
    |  m   |  t   |            27          |      20      |Ediar prefijo 27 de sexo Mujer por 20 sexo Hombre|  C157,C190  |
    |  m   |  t   |            27          |      23      |Ediar prefijo 27 de sexo Mujer por 23 sexo Hombre|      C158   |
    |  m   |  t   |            27          |      24      |       Ediar prefijo 27 de sexo Mujer por 24     |      C159   |
    |  h   |  t   |      cambiar a 23      |      24      |               Editar prefijo 23 por 24          |      C172   |
    |  m   |  t   |      cambiar a 24      |      23      |               Editar prefijo 24 por 23          |      C180   |
    |  h   |  t   |      cambiar a 23      |      27      |               Editar prefijo 23 por 27          |      C192   |
#
#

  @walletint @stage
  Scenario Outline: Background desde pantalla CUIL - (<testID>)

    Hacer background en la app desde la pantalla de Cuil(C187)
    Jira ticket: https://tunubi.atlassian.net/browse/NWA-166

    Given Usuario esta en pantalla "<pantalla>"
    When Presiona boton de aplicaciones recientes
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
    |pantalla| testID  |
    |  cuil  |   C187  |
#
#

  @walletint @stage
  Scenario Outline: : <descripcion> - (<testID>)
    When Pulsar back en "<tipoBtn>" en pantalla "<pantalla>"
    And Boton circulo siguiente en pantalla "PERSONAL_INFORMATION" esta "t"
    And Click boton circulo siguiente en pantalla "PERSONAL_INFORMATION"
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
    |    tipoBtn  | pantalla |                               descripcion                                   | testID  |
    |     app     |   cuil   |Volver a la pantalla Datos Personales y luego al CUIL sin alterar ningún dato|  C188   |
#
#

  @walletint @stage
  Scenario Outline: Cuil duplicado - (C155-C186 - C21045 - C21510)
    Given Pulsar back en "app" en pantalla "cuil"
    And Usuario esta en pantalla "PERSONAL_INFORMATION"
    When Ingresar DNI "existente"
    And Boton circulo siguiente en pantalla "PERSONAL_INFORMATION" esta "t"
    And Click boton circulo siguiente en pantalla "PERSONAL_INFORMATION"
    And Usuario esta en pantalla "<pantalla>"
    And Click boton circulo siguiente en pantalla "cuil_existente"
    Then Validar CUIL duplicado
    #And Validar que no esta activa la aplicacion NUBI
    Examples:
    |pantalla|
    |  cuil  |