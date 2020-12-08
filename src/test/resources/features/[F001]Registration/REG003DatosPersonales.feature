@registration @regression
Feature: Datos personales en registro


  @walletint @stage
  Scenario Outline: <descripcion> - (<testID>)

    Given Resetear app "<resetApp>"
    Given Pulsar registrar "<pulsar_registrar>"
    And Completar hasta pantalla <completar_pantalla>
    When Ingresar nombre "<nombre>"
    And Ingresar apellido "<apellido>"
    And Ingresar DNI "<dni>"
    And Seleccionar genero "<genero>"
    Then Boton circulo siguiente en pantalla "<pantalla>" esta "<status>"
    Then Mensaje de error en input de pantalla "<pantalla>" se encuentra "<error>"
    Examples:
    |   nombre    |   apellido   |     dni     |  genero  |      pantalla       |status|  error  |            descripcion             |        testID      | completar_pantalla | resetApp | pulsar_registrar |
    |     Ana     |    Garcia    |   96872123  |          | PERSONAL_INFORMATION |   f  |  false  |          No seleccionar sexo       |  C146-c97-C21209   |           2        |    si    |     true         |
    |             |    Garcia    |   96872123  |    m     | PERSONAL_INFORMATION |   f  |  false  |        No completar campo nombre   |         C141       |         0          |          |                  |
    |     Ana     |              |   96872123  |    m     | PERSONAL_INFORMATION |   f  |  false  |     No completar campo apellido    |         C142       |         0          |          |                  |
    |     Ana     |    Garcia    |             |    m     | PERSONAL_INFORMATION |   f  |  false  |        No completar campo DNI      |         C31        |         0          |          |                  |
    |      55     |    Garcia    |   96872123  |    m     | PERSONAL_INFORMATION |   f  |   true  |       Numero en campo nombre       |         C144       |         0          |          |                  |
    |      %$     |    Garcia    |   96872123  |    m     | PERSONAL_INFORMATION |   f  |   true  | Caracteres especiales campo nombre |         C144       |         0          |          |                  |
    |      Ana    |      55      |   96872123  |    m     | PERSONAL_INFORMATION |   f  |   true  |       Numero en campo apellido     |         C145       |         0          |          |                  |
    |      Ana    |      %$      |   96872123  |    m     | PERSONAL_INFORMATION |   f  |   true  |Caracteres especiales campo apellido|          C145      |         0          |          |                  |
    |      Ana    |     Garcia   |    968721   |    f     | PERSONAL_INFORMATION |   f  |   true  |       Dni menos de 7 caracteres    |         C147       |         0          |          |                  |
#

  @walletint @stage
  Scenario Outline: <descripcion> <pantalla> - (<testID>)

     Retormar proceso de registro en pantalla de datos personales(c247)

    Given Usuario esta en pantalla "<pantalla>"
    And Usuario cierra la app
    When Inicia la app
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
    |     pantalla      |           descripcion           | testID  |
    |PERSONAL_INFORMATION|  Retomar proceso de registro en |  C247   |
#
#

  @walletint @stage
  Scenario Outline: <descripcion> desde pantalla <pantalla> - (<testID>)

    Given Usuario esta en pantalla "<pantalla>"
    When Ingresar nombre "<nombre>"
    And Presiona boton de aplicaciones recientes
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
    |     pantalla      | nombre |  apellido  |  descripcion | testID  |
    |PERSONAL_INFORMATION|   Ana  |  Gonzalez  |  Background  |  C148   |
#
#
  @walletint @stage
  Scenario Outline: : <descripcion> en pantalla CUIL - (<testID>)
    Given Usuario esta en pantalla "<pantalla>"
    When Ingresar nombre "<nombre>"
    And Ingresar apellido "<apellido>"
    And Ingresar DNI "<dni>"
    And Seleccionar genero "<genero>"
    And Boton circulo siguiente en pantalla "<pantalla>" esta "<status>"
    And Click boton circulo siguiente en pantalla "<pantalla>"
    Then Usuario esta en pantalla "cuil"
    And Pulsar back en "<tipoBtn>" en pantalla "cuil"
    Then Usuario esta en pantalla "<pantalla>"
    Examples:
      |    tipoBtn  |      pantalla     |  nombre  | apellido  |    dni   | genero | status |            descripcion            |       testID   |
      |     app     |PERSONAL_INFORMATION|   Ana    | Gonzalez  | 99999999 |  f     |   t    |   Back desde el boton de la app   |  C216-C21219   |
      | dispositivo |PERSONAL_INFORMATION|   Ana    | Gonzalez  | 99999999 |  f     |   t    |Back desde el boton del dispositivo|  C216-C21219   |