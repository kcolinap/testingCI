@registration @regression
Feature: Email

    Contienes los TC incluidos en la seccion:

    Registro -> ... -> Email

#
#

  @walletint @stage
  Scenario Outline: <Descripcion> - (<testrailID>)

    Given Resetear app "<resetApp>"
    Given Pulsar registrar "<pulsarRegistrar>"
    When Usuario ingresa el email "<email>"
    Then Boton siguiente en pantalla "<pantalla>" esta "<status>"
    Then Mensaje de error en input de pantalla "<pantalla>" se encuentra "<visibilidadError>"
    Examples:
    |       email       |   pantalla    |     status      |   visibilidadError  |      testrailID    |         Descripcion              |  pulsarRegistrar    | resetApp |
    | test@yopmail.com  |     email     |    habilitado   |        false        |        C25         |         Email válido             |  true              |    si      |
    |                   |     email     |  deshabilitado  |         false        |    C26-C21511      |           sin email              |  false              |          |
    | #&*%#@yopmail.com |     email     |  deshabilitado  |         true        |    C26-C21511      |  email con caracteres especiales |  false               |        |
    |  testyopmail.com  |     email     |  deshabilitado  |        true        |    C26-C21511      |         email sin @              |  false              |          |
    |   test@yopmail    |     email     |  deshabilitado  |        true        |    C26-C21511      |        email sin .com            |  false              |          |
    |  test@yopmailcom  |     email     |  deshabilitado  |        true        |    C26-C21511      |       email sin puntos           |  false              |          |
    #|    y@yopmail.com  |     email     | deshabilitado   |        true        |    C26-C21511      |        email con una letra       |  false              |          |
    #|    5@yopmail.com  |     email     | deshabilitado   |        true        |    C26-C21511      |        email con un numero       |  false              |          |
    |    255caracteres  |     email     | deshabilitado   |         true        |    C26-C21511      |        email con +254char        |  false              |          |
#
#
  @walletint @stage
  Scenario Outline: <Descripcion> en <pantalla> - (<testID>)

    Regresar a la pantalla de email.

    Given Resetear app "<resetApp>"
    Given Pulsar registrar "<pulsarRegistrar>"
    When Usuario ingresa un email valido "nuevo"
    And Click siguiente en pantalla "email"
    And Pulsar back en "<btnBack>" en pantalla "<pantalla>"
    And Email ingresado esta en el input
    Then Boton siguiente en pantalla "email" esta "<status>"
    Examples:
    |   btnBack   |       Descripcion      | testID |     pantalla     |    status   |  resetApp |  pulsarRegistrar |
    |     app     |  Back desde aplicacion |  C102  |  confirmaremail  | habilitado  |   si      | true             |
    | dispositivo | Back desde dispositivo |  C102  |   confirmaremail | habilitado  |   si      | true             |


  @walletint @stage
  Scenario: Volver a la pantalla de email desde el boton modificar - (C103)

    Given Resetear app "si"
    Given Pulsar registrar "true"
    And Usuario ingresa un email valido "nuevo"
    And Click siguiente en pantalla "email"
    When Pulsar "modificar" para editar el email
    Then Email ingresado esta en el input
    And Boton siguiente en pantalla "email" esta "habilitado"

##
  @walletint @stage
  Scenario: Validar un email ya registrado - (C480-C481)

    Given Resetear app "si"
    Given Pulsar registrar "true"
     When Usuario ingresa un email ya registrado
     And Click siguiente en pantalla "email"
     Then Validar pantalla mail ya registrado


    @ignore @wallentint @stage @smoke
    Scenario Outline: make a p2p request
     Given Resetear app "si"
    Given Usuario "any" inicia sesion
    Then Validar que existe una transaccion "<transactionType>"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "solicitudes" en el menu
    Then Usuario esta en pantalla "request_page"
    Examples:
      |    transactionType   |
      | P2P_INCOMING_REQUEST |
      | P2P_OUTGOING_REQUEST |
