@sube @regression
Feature: Ingreso de Clave Nubi para confirmar la recarga de tarjeta SUBE
  Casos de prueba para el ingreso de una clave nubi y asi confirmar la recarga de una tarjeta SUBE.

  @walletint @stage
  Scenario: Loading, validacion y recarga exitosa (C1210 )
  Jira Ticket: https://tunubi.atlassian.net/browse/NWA-1734
    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    And Pulsa en el boton cargar sube primer uso
    And Ingresa un numero de tarjeta sube "completo" y "valido"
    And Pulsa en el boton continuar
    And Ingresa un alias "juanperez" sube "valido"
    And Pulsa en el boton continuar
    And Usuario selecciona para recargar el monto: "$60"
    And Presiona sobre el boton continuar en pantalla de confirmacion SUBE
    When Usuario ingresa clave nubi para flujo "SUBE_RECHARGE"
    #Then Usuario esta en pantalla "recarga_exitosa_SUBE"
    Then Se validan los elementos de la pantalla recarga exitosa
    And presiona sobre el boton entendido
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    And Pulsar action button para "DELETE_CARD"
    And Usuario esta en pantalla "cargar_sube"


#    Then Usuario elimina todas las tarjeta del listado
#    When Usuario esta en pantalla "cargar_sube"

  @ignore @walletint @stage
  Scenario: SUBE duplicada
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    And Usuario "sin_sube" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    When Usuario esta en pantalla "cargar_sube"
    Then Pulsa en el boton cargar sube primer uso
    And Usuario esta en pantalla "registro_sube"
    And Ingresa un numero de tarjeta sube "completo" y "valido"
    And Pulsa en el boton continuar
    Then Usuario esta en pantalla "registro_alias_sube"
    And Ingresa un alias "juanperez" sube "valido"
    And Pulsa en el boton continuar
    And Usuario esta en pantalla "recarga_de_saldo"
    And Usuario selecciona para recargar el monto: "$60"
    And Usuario esta en pantalla "revisar_datos_de_recarga"
    And Presiona sobre el boton continuar en pantalla de confirmacion SUBE
    And Usuario esta en pantalla "pin"
    When Usuario ingresa un numero de pin "valido"
    Then Usuario esta en pantalla "recarga_exitosa_SUBE"
    And Se validan los elementos de la pantalla recarga exitosa
    And Ir al dashboard
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    When Usuario esta en pantalla "cargar_sube"
    Then Pulsa en el boton agregar sube
    And Usuario esta en pantalla "registro_sube"
    And Ingresa un numero de tarjeta sube "completo" y "valido"
    Then Usuario esta en pantalla "error_sube"


  @ignore @walletint
  Scenario: Loading, validacion y recarga exitosa "boton entendido" (C1211)
    Given Usuario esta en pantalla "recarga_exitosa_SUBE"
    When presiona sobre el boton entendido
    Then Usuario esta en pantalla "dashboard"

  @ignore @walletint
  Scenario: Loading, validacion y recarga exitosa - error SUBE EN LISTA NEGRA (C1236)
    Given Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    Then Usuario esta en pantalla "lista_de_tarjetas_sube"
    Then Pulsa en el boton agregar sube
    And Usuario esta en pantalla "registro_sube"
    And Ingresa un numero de tarjeta sube "completo" y "lista_negra"
    And Pulsa en el boton continuar
    Then Usuario esta en pantalla "registro_alias_sube"
    And Ingresa un alias "carlosperez" sube "valido"
    And Pulsa en el boton continuar
    And Usuario esta en pantalla "recarga_de_saldo"
    And Usuario selecciona para recargar el monto: "$60"
    And Usuario esta en pantalla "revisar_datos_de_recarga"
    And Presiona sobre el boton continuar en pantalla de confirmacion SUBE
    And Usuario esta en pantalla "pin"
    When Usuario ingresa un numero de pin "valido"
    Then Usuario esta en pantalla "error_sube"
    Then Presiona sobre el boton volver al inicio

  @ignore @walletint
  Scenario: Loading, validacion y recarga exitosa - error falla de internet (C1220)
    Given Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    Then Usuario esta en pantalla "lista_de_tarjetas_sube"
    Then Pulsa en el boton agregar sube
    And Usuario esta en pantalla "registro_sube"
    And Ingresa un numero de tarjeta sube "completo" y "lista_negra"
    And Pulsa en el boton continuar
    Then Usuario esta en pantalla "registro_alias_sube"
    And Ingresa un alias "carlosperez" sube "valido"
    And Pulsa en el boton continuar
    And Usuario esta en pantalla "recarga_de_saldo"
    And Usuario selecciona para recargar el monto: "$60"
    Given Usuario esta en pantalla "revisar_datos_de_recarga"
    And Presiona sobre el boton continuar en pantalla de confirmacion SUBE
    And Usuario esta en pantalla "pin"
    When se "desactiva" el wifi del telefono
    And Usuario ingresa un numero de pin "valido"
    Then Usuario esta en pantalla "error_sube"
    Then Presiona sobre el boton volver al inicio
    Then se "activa" el wifi del telefono
    Given Pulsa el boton "principal" en el menu
    And Pulsa el boton "recarga_sube" en el menu
    Then Usuario esta en pantalla "lista_de_tarjetas_sube"
    Then Usuario elimina todas las tarjeta del listado
    When Usuario esta en pantalla "cargar_sube"