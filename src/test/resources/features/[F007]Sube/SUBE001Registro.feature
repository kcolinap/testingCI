@sube @regression
Feature: Registro de Nueva Tarjeta SUBE
  Casos de prueba para el registro de una tarjeta SUBE.

  @walletint @stage
  Scenario: Visualizar opcion SUBE desde el Menu (C1001)
  Jira ticket: https://tunubi.atlassian.net/browse/NWA-1723
    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    Then Visualizar "SUBE" en el submenu

  @walletint @stage
  Scenario: Acceder desde el menu y validar la pantalla de registro SUBE (C1023 y C990)
  Jira ticket: https://tunubi.atlassian.net/browse/NWA-1723
    When Pulsa el boton "recarga_sube" en el menu
    When Usuario esta en pantalla "cargar_sube"
    Then Pulsa en el boton cargar sube primer uso

  @walletint @stage
  Scenario Outline: <descripcion> - <testRailID>
  Jira tickets: https://tunubi.atlassian.net/browse/NWA-1726 | https://tunubi.atlassian.net/browse/NWA-1727
    Given Usuario esta en pantalla "registro_sube"
    When Ingresa un numero de tarjeta sube "<parcialidad>" y "<validez>"
    Then Se valida el boton continuar y los elementos de advertencia si el numero de la tarjeta es "<parcialidad>" y "<validez>"
    Examples:
      | testRailID           | descripcion                                                                                                 | parcialidad | validez  |
      | (C991, C993 y C1121) | Validando la tarjeta ingresada (16 DIGITOS) - Nro Correcto                                                  | completo    | valido   |
      | (C971, C992)         | Validando la tarjeta ingresada (16 DIGITOS) - Nro incorrecto - validacion boton continuar al borrar numeros | completo    | invalido |

  @walletint @stage
  Scenario: Volver al dashboard usando el boton back de la aplicacion (C998)
  Jira ticket: https://tunubi.atlassian.net/browse/NWA-1726
    Given Usuario esta en pantalla "registro sube"
    When Pulsar back en "app" en pantalla "registro_sube"
    When Usuario esta en pantalla "cargar_sube"
    When Pulsar back en "app" en pantalla "cargar_sube"
    Then Usuario esta en pantalla "dashboard"

  @walletint @stage
  Scenario: Volver al dashboard usando el boton back del dispositivo (C999)
  Jira ticket: https://tunubi.atlassian.net/browse/NWA-1726
    Given Pulsa el boton "principal" en el menu
    When Pulsa el boton "recarga_sube" en el menu
    When Usuario esta en pantalla "cargar_sube"
    Then Pulsa en el boton cargar sube primer uso
    And Usuario esta en pantalla "registro sube"
    And Pulsar back en "dispositivo" en pantalla "registro_sube"
    When Usuario esta en pantalla "cargar_sube"
    When Pulsar back en "dispositivo" en pantalla "cargar_sube"
    Then Usuario esta en pantalla "dashboard"

  @walletint @stage
  Scenario: Ingresar una tarjeta SUBE valida y continuar hasta la pantalla recarga (C1000)
  Jira ticket: https://tunubi.atlassian.net/browse/NWA-1726
    Given Pulsa el boton "principal" en el menu
    When Pulsa el boton "recarga_sube" en el menu
    When Usuario esta en pantalla "cargar_sube"
    Then Pulsa en el boton cargar sube primer uso
    And Usuario esta en pantalla "registro_sube"
    And Ingresa un numero de tarjeta sube "completo" y "valido"
    And Pulsa en el boton continuar
    Then Usuario esta en pantalla "registro_alias_sube"
    And Usuario ingresa un alias
      | juan.perez.noseque.mas.caracteres.q.24  | invalido  |   El máximo permitido es de 24 caracteres |
      | ju!                                     | invalido  |         button_disabled                   |
      | ju                                      | invalido  |         button_disabled                   |
      | ju1%&                                   | invalido  |         button_disabled                   |
      | juanperez                               | valido    |         none                              |
    And Pulsa en el boton continuar
    Then Usuario esta en pantalla "recarga_de_saldo"