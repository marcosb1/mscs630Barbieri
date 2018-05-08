# Security Algorithms & Protocols
## MSCS-630L
### Marcos Barbieri
#### Chapter ?
##### Congruences
- Example:
    - `7 === 3 mod 4`
#### Chapter 9: Public Key Cryptography
##### Preview
- A major drawback of all the symmetric key crypto-systems:
    - The keys must be distributed to all participating parties before any secure communication can take place (can be impractical)
    - Can be circumvented thanks to **public key cryptography** or **asymmetric key cryptography**
- How can one build a public crypto-system?
    - **DEFINITION 9.1**
        - A one-way function is any bijective function *f: D --> R* such that any of its values, *f(x)*, is easy to compute, but whose inverse function values, *f^-1(y)*, are computationally intractable to compute.
##### The Diffie-Hellman Key Exchange
- **The Diffie-Hellman Key Exchange** allows remote users to establish a secure key over insecure channels.
    - Relies on a one-way function
    - **ALGORITHM 9.1** *(The Diffie-Hellman Exchange Protocol)*:
        - *Purpose*: Alice and Bob need to create a secret key between them using a public (insecure) communication channel
        - *Requirements*: A large prime number *p*, and a primitive root `g (mod p)`; both *g* and *p* can be made public.
            - ***NOTE***: All modular powers in this algorithm should be computed with fast modular exponentiation
        - *Step 1*: Alice and Bob select integers *a* and *b*, respectively, (preferably) randomly) selected
    - Public key is used for encryption
    - Private key is used for decryption
- **The RSA Public Key Crypto-System**
    - **ALGORITHM 9.2** *(THE RSA Public Key Crypto-System)*:
        - *Purpose*: Alice needs to send Bob a private message, but they have not yet met to exchange any crypto-system keys.
        - *Requirements*: Bob will first need two different prime numbers `p != q`, that he should keep secret. He multiplies these primes to obtain an integer `n = pq`; *n* will be made public. He selects (preferably randomly) an integer *d* that is relatively prime to `rho(n) = (p - 1)(q - 1)`. He then computes (using the extended Euclidean Algorithm 2.2) the inverse *e* of *d* `(mod rho(n))`
        - *Plaintext, Ciphertext Spaces*: `P = C = Z_n` where `n = pq`, with *p*, and *q* prime numbers.
        - *Keyspace*: `K = {(p,q,d,e):de=1(mod|o(n)), where n = pq}`. From a system key `k = (p,q,d,e)`, Bob's private key is *(n,d)* and the corresponding public key is *(n,e)*. The parameter *e* is called the **encryption exponent**, and the parameter *d* is called the **decryption exponent**
        - *Encryption Scheme*: `e_k: P --> C_n` defined by `e_k(P) === P^e(mod(n))`.
        - *Decryption Scheme*: `d_k: C --> P_n` defined by `d_k(C) === C^e(mod(n))`.
#### Glossary
##### Terminology
- **Primitive Root**
##### Symbols
- `===` is the symbol representing **Congruence**
- `!=` is the symbol representing "not equal to"
