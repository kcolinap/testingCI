@registration @regression
Feature: Editar Email

  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)

    Given Resetear app "si"
    Given Pulsar registrar "true"
    When Usuario ingresa un email valido "<tipoEmail>"
    And Click siguiente en pantalla "<pantalla>"
    Then Un "<tokenAntes>" fue generado
    And Pulsar "<boton>" para editar el email
    When Usuario ingresa un email valido "<tipoEmail>"
    And Boton siguiente en pantalla "<pantalla>" esta "<status>"
    And Click siguiente en pantalla "<pantalla>"
    Then Validar que nuevo email se muestra en pantalla
    Then Un "<tokenDespues>" fue generado
  Examples:
    |  boton  |  tipoEmail   |             descripcion           |  testID  |  tokenAntes |  tokenDespues | pantalla |   status   |
    |  back   |    nuevo     |    Editar email desde btn back    |   C131   |    token    |   nuevotoken  |  email   | habilitado |
    |modificar|    nuevo     |  Editar email desde btn modificar |   C132   |    token    |   nuevotoken  |  email   | habilitado |
    |   back  |  mismomail   |        No editar el mail          |   C134   |    token    |   nuevotoken  |  email   | habilitado |
##
##

  @walletint @stage
  Scenario: Re Editar email - (C137)

    Given Resetear app "si"
    Given Pulsar registrar "true"
    When Usuario ingresa un email valido "nuevo"
    And Click siguiente en pantalla "email"
    Then Un "token" fue generado
    And Pulsar back en "back" en pantalla "confirmaremail"
    When Usuario ingresa un email valido "nuevo"
    Then Boton siguiente en pantalla "email" esta "habilitado"
    And Click siguiente en pantalla "email"
    Then Validar que nuevo email se muestra en pantalla
    Then Un "nuevotoken" fue generado
    And Pulsar "back" para editar el email
    When Usuario ingresa un email valido "nuevo"
    Then Boton siguiente en pantalla "email" esta "habilitado"
    And Click siguiente en pantalla "email"
    Then Validar que nuevo email se muestra en pantalla
    Then Un "nuevotoken" fue generado