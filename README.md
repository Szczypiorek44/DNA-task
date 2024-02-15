The goal is to implement an app that will work on a POS (point-of-sale) device that will allow to
select products from the available set and pay for them with a card.

Task 1:
As an MVP you should enhance product list with functionality to select/unselect product on the list
and ...

Task 2:
...be able to buy at least one product (MVP approach).

To perform payment you must:

- initiate purchase transaction
- call payment API using transaction identifier and card token read from reader API
- confirm purchase transaction after successful payment

My Assumptions:

Could be improved:

- Double should not be used to pass payment/purchase amount. Long/BigDecimal should be used
- Write more tests
- Libraries could be updated to latest versions