@contacts @regression
Feature: Detalle de contactos

  Contempla todos los Tcs que se encuentran en la seccion de test rail

  Mobile ... Contactos->Listado ... detalle de contactos

  Background: Acceder a contactos
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"

  @walletint 
  Scenario Outline: Detalle contacto <tipoContacto> - (<testID>)
    And Permiso para ver contactos "si"
    And Usuario "<loginUser>" inicia sesion
    And Borrar contactos
    And Agregar contacto "<tipoContacto>"
    And Usuario esta en pantalla "dashboard"
    And Usuario presiona boton de contactos frecuentes
    And Permiso para ver contactos "si"
    Given Usuario esta en pantalla "con_contactos_y_permiso"
    And Validar que hay contactos "<tipoContacto>"
    When Selecciona contacto "<tipoContacto>"
    Then Usuario esta en pantalla "detalle_contacto"
    And Validar detalle contacto "<tipoContacto>"
    Examples:
      | loginUser             | tipoContacto | testID           |
      | CON_CONTACTO_NUBI     | NUBI         | C979             |
      | CON_CONTACTO_BANCARIO | BANCARIO     | C981 - C983 C948 |
      | ANY                   | NONUBI       | C980             |
