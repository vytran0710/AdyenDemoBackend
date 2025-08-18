# Adyen Backend Demo

Payment integration with Adyen using Spring Boot.

---

## 🚀 Getting Started

Follow the steps below to set up and run the project locally.

### 1. Clone the repository
```bash
git https://github.com/vytran0710/AdyenDemoBackend
cd AdyenDemoBackend
```

### 2. Create configuration file
Inside root folder, create a file called **`application-dev.yml`** with the following content:

```yaml
adyen:
  apiKey: YOUR_API_KEY
  clientKey: YOUR_CLIENT_KEY
  merchantAccount: YOUR_MERCHANT_ACCOUNT
```

### 3. Run the project

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

---

## 🔑 Configuration
- **API Key**: Used for server-side authentication with Adyen.
- **Client Key**: Used for client-side authentication.
- **Merchant Account**: Your Adyen merchant account identifier.

---

## 📜 License
- This project is licensed under the [MIT License](LICENSE).

## 📝 Notes
- Keep your API keys secure.
