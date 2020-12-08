@registration @regression
Feature: Terminos y Condiciones

  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)
    Given Resetear app "<resetearApp>"
    Given Pulsar registrar "<pulsar_registrar>"
    And Completar hasta pantalla <completar_pantalla>
    Given Usuario esta en pantalla "<pantalla>"
    When Usuario pulsa check de "<checkbutton>"
    Then Validar status de boton crear cuenta "<status>"
    Examples:
      | checkbutton | status | descripcion                  | testID | pantalla | resetearApp | pulsar_registrar | completar_pantalla |
      | tyc         | t      | Check terminos y condiciones | C268   | tyc      | si          | true             | 8                  |
#
#
  @ignore @walletint @stage
  Scenario: Ingreso a pantalla de terminos y condiciones - (C817)

    Given Usuario esta en pantalla "tyc"
    And Usuario pulsa link terminos y condiciones
    Then Validar ingreso a terminos y condiciones
    When Usuario abre la app
    Then Usuario esta en pantalla "<pantalla>"

  @walletint @stage
  Scenario Outline: <descripcion> pantalla de terminos y condiciones - (<testID>)

   Retomar proceso de registro en pantalla de terminos
    y condiciones

    Given Usuario esta en pantalla "<pantalla>"
    And Usuario cierra la app
    When Inicia la app
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
    | pantalla |           descripcion           | testID  |
    |   tyc    |  Retomar proceso de registro en |  C252   |
#
#
  @walletint @stage
  Scenario Outline: <descripcion> de terminos y condiciones - (<testID>)

    Hacer background en la app desde la pantalla de terminos
    y condiciones()

    Given Usuario esta en pantalla "<pantalla>"
    And Usuario pulsa check de "tyc"
    And Presiona boton de aplicaciones recientes
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
    |pantalla|       descripcion        | testID  |
    |   tyc  |Background desde pantalla |  C283   |
#
#
  @walletint @stage
  Scenario: Validar creacion de cuenta(c268-C273-C274)
      Given Usuario esta en pantalla "tyc"
      And Usuario pulsa check de "tyc"
      Then Validar status de boton crear cuenta "t"
      When Usuario pulsa crear cuenta
      Then Validar creacion de cuenta
