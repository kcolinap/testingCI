@registration @regression
Feature: Registro de PIN
#
#
  @walletint @stage
  Scenario Outline: <descripcion> pantalla registro de PIn - (<testID>)

   Retomar proceso de registro en pantalla de registro pin

    Given Resetear app "si"
    Given Pulsar registrar "true"
    And Completar hasta pantalla 7
    Given Usuario esta en pantalla "<pantalla>"
    And Usuario cierra la app
    When Inicia la app
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
      |    pantalla  |           descripcion           | testID  |
      |  reg_nubi_pass   |  Retomar proceso de registro en |  C251   |
#
#
  @walletint @stage
  Scenario Outline: <descripcion> de registro de PIN - (<testID>)

  Hacer background en la app desde la pantalla de registro de pin

    Given Usuario esta en pantalla "<pantalla>"
    When Presiona boton de aplicaciones recientes
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
      |    pantalla   |       descripcion          | testID  |
      |   reg_nubi_pass   | Background desde pantalla  |  c821   |
#
#
  @ignore @walletint @stage
  Scenario Outline: : <descripcion> en pantalla terminos y condiciones - (<testID>)

    Given Usuario esta en pantalla "<pantalla>"
    And Usuario ingresa pin "<pin>"
    And Usuario esta en pantalla "tyc"
    When Pulsar back en "<tipoBtn>" en pantalla "tyc"
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
    |    tipoBtn  |      pantalla     |  pin  |status|            descripcion                | testID  |
    |     app     |   reg_nubi_pass     | 1234  |   t  |    Back desde el boton de la app      |  C822   |
    | dispositivo |   reg_nubi_pass     | 1234  |  t   | Back desde el boton del dispositivo   |  C823   |


  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)

    Given Usuario esta en pantalla "<pantalla>"
    When Usuario ingresa pin "<pin>"
    Then Usuario esta en pantalla "tyc"
    Examples:
      |     pin      |   status    |       pantalla    |  descripcion  |       testID   |
      |     0000     |      t      |    reg_nubi_pass    |   Pin valido  |   C261 - C269  |