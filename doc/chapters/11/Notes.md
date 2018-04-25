# Chapter 11: Diffie-Hellman

#### Section 11.10
###### Chapter 11 Exercises
 1. Assume 200 people wish to communicate securely using symmetric keys, one symmetric key for each pair of people. How many symmetric keys would this system use in total?
  - Two ways to approach this problem:
    - Combinations
      - Need to perform 200 C 2 (nCr) == 19,900
4. What problems, if any, could arise if Alice uses the same x and g^x for all her communications with Bob, and Bob uses the same y and g^y for all hist communications with Alice?
  - g^x and g^y are the public keys
  - Since the key changes are constant, eventually an attacker would be able to decrypt the public keys; given time.
5. Alice and Bob wish to agree on a 256-bit AES key. They are trying to decide between using 256-bit, 512-bit, or some other length DH public keys g^x and g^y. What would be your recommendation to them?
  - It depends on what they are trying to communicate. If they are not sending sensitive information (their perspective) then 128-bit keys would even suffice, because its not sensitive information. This would dramatically increase the speed of their communication.
  - It also depends on what hardware they are running. We want to provide as quick of a communication as possible.
