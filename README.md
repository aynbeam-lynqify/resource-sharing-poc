What is an Initialization Vector (IV)?
An IV is a random or pseudorandom value used in cryptographic algorithms to provide the initial state for encryption or decryption.
It ensures that repeated usage of the same key does not allow an attacker to infer relationships between segments of the encrypted message.
For AES (Advanced Encryption Standard), the IV size is typically the same as the block size (16 bytes for AES-128 or 32 bytes for AES-256).
Generating an IV:
You can generate a random IV using a secure random number generator.
In Java, you can use SecureRandom to create a random IV.