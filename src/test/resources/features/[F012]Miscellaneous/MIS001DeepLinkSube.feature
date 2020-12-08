@deeplink @regression

Feature: DeepLinks

    Como usuario quiero poder probar como se comporta el acceso a la funcionalidad
  respectiva utilizando los deepLink

  @walletint
  Scenario Outline: <descripcion>
    Given Resetear app "si"
    And Usuario "<userType>" inicia sesion
    When Pulsar DeepLink "<DeepLink>"
    Then Usuario esta en pantalla "<screenType>"
    Examples:
      | userType | DeepLink        | screenType                      | descripcion                                                |
      | any      | SUBE            | cargar_sube                     | Prueba de DeepLink SUBE con usuario logueado               |
      |          | SUBE            | login                           | Prueba de DeepLink SUBE con usuario deslogueado            |
      | any      | PREPAID_CARD    | pc_landing                      | Prueba de DeepLink PREPAID_CARD con usuario logueado       |
      |          | PREPAID_CARD    | login                           | Prueba de DeepLink PREPAID_CARD con usuario deslogueado    |
      | any      | REGISTER        | dashboard                       | Prueba de DeepLink REGISTER con usuario logueado           |
      |          | REGISTER        | email                           | Prueba de DeepLink REGISTER con usuario deslogueado        |
      | any      | SERVICE_PAYMENT | pago_de_servicios               | Prueba de DeepLink SERVICE_PAYMENT con usuario logueado    |
      |          | SERVICE_PAYMENT | login                           | Prueba de DeepLink SERVICE_PAYMENT con usuario deslogueado |
      | any      | CASH_IN         | cash_in_landing                 | Prueba de DeepLink CASH_IN con usuario logueado            |
      |          | CASH_IN         | login                           | Prueba de DeepLink CASH_IN con usuario deslogueado         |
      | any      | MOBILE_RECHARGE | select_compania_recarga_celular | Prueba de DeepLink MOBILE_RECHARGE con usuario logueado    |
      |          | MOBILE_RECHARGE | login                           | Prueba de DeepLink MOBILE_RECHARGE con usuario deslogueado |


  @walletint
  Scenario Outline: Prueba de DeepLink <deepLink> desde app en background
    Given Resetear app "si"
    And Usuario "any" inicia sesion
    When Pulsar desde background DeepLink de "<deepLink>"
    Then Usuario esta en pantalla "<screen>"
    Examples:
      | deepLink        | screen                          |
      | SUBE            | sube_landing_page               |
      | PREPAID_CARD    | pc_landing                      |
      | REGISTER        | dashboard                       |
      | SERVICE_PAYMENT | pago_de_servicios               |
      | CASH_IN         | cash_in_landing                 |
      | MOBILE_RECHARGE | select_compania_recarga_celular |


  @walletint
  Scenario Outline: Prueba de DeepLink <deepLink> desde flujo <flow>
    Given Resetear app "si"
    And Usuario "any" inicia sesion
    When Pulsar desde flujo "<flow>" DeepLink de "<deepLink>"
    Then Usuario esta en pantalla "<screen>"
       Examples:
      | flow            | deepLink        | screen            |
      | CASH_IN         | SUBE            | cargar_sube       |
      | PROFILE         | SUBE            | cargar_sube       |
      | MOBILE_RECHARGE | SUBE            | cargar_sube       |
      | CASH_IN         | PREPAID_CARD    | pc_landing        |
      | PROFILE         | PREPAID_CARD    | pc_landing        |
      | MOBILE_RECHARGE | PREPAID_CARD    | pc_landing        |
      | CASH_IN         | REGISTER        | dashboard         |
      | PROFILE         | REGISTER        | dashboard         |
      | MOBILE_RECHARGE | REGISTER        | dashboard         |
      | CASH_IN         | SERVICE_PAYMENT | pago_de_servicios |
      | PROFILE         | SERVICE_PAYMENT | pago_de_servicios |
      | MOBILE_RECHARGE | SERVICE_PAYMENT | pago_de_servicios |

  @walletint
  Scenario Outline: Prueba de DeepLink <deepLink> desde flujo <flow>
    Given Resetear app "si"
    And Usuario "any" inicia sesion
    When Pulsar desde flujo "<flow>" DeepLink de "<deepLink>"
    Then Usuario esta en pantalla "<screen>"
    Examples:
      | flow            | deepLink        | screen                          |
      | SUBE            | CASH_IN         | cash_in_landing                 |
      | PROFILE         | CASH_IN         | cash_in_landing                 |
      | SERVICE_PAYMENT | CASH_IN         | cash_in_landing                 |
      | SUBE            | MOBILE_RECHARGE | select_compania_recarga_celular |
      | PROFILE         | MOBILE_RECHARGE | select_compania_recarga_celular |
      | SERVICE_PAYMENT | MOBILE_RECHARGE | select_compania_recarga_celular |