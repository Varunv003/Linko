# Linko: Your Self-Hosted, High-Performance URL Shortener 🚀

## Problem Statement

Long, messy URLs are everywhere—making links hard to share, remember, and track. Public URL shorteners solve this, but at a cost: you lose control over your data, risk broken links, and can't build your own brand.  
**Linko** is the solution: an open-source, self-hosted URL shortener that gives you privacy, speed, and total control.

---

## ✨ Features

- **Secure User Authentication:** Multi-user support with JWT-based registration and login.
- **Blazing Fast Redirects:** Redis caching for instant link resolution and reduced database load.
- **Robust Rate Limiting:** Bucket4j integration protects your API from abuse.
- **Containerized Deployment:** One-command setup with Docker & Docker Compose.
- **Developer-Friendly REST API:** Clean, documented endpoints for easy integration.
- **Full Analytics:** Track clicks and user engagement.

---

## 🛠️ Tech Stack

- **Backend:** Java 21, Spring Boot 3
- **Database:** MySQL 8.0
- **Cache:** Redis
- **Security:** Spring Security & JWT
- **Rate Limiting:** Bucket4j
- **Build Tool:** Maven
- **Containerization:** Docker & Docker Compose

---

## 🚀 Getting Started

### Prerequisites

- [Docker Desktop](https://www.docker.com/products/docker-desktop) (includes Docker Compose)

### 1. Clone the Repository

```sh
git clone https://github.com/Varunv003/linko.git
cd linko
```

### 2. Configure Your Environment

Copy the example environment file and edit your secrets:

```sh
cp .env.example .env
```

Edit `.env` and set strong passwords for `MYSQL_PASSWORD` and `MYSQL_ROOT_PASSWORD`.

### 3. Run the Application

Build and start the entire stack:

```sh
docker-compose up --build
```

The first run may take a few minutes to download images and build the app.  
Once complete, access Linko at: [http://localhost:8080](http://localhost:8080)

---

## 🧪 API Testing with Postman

Test all endpoints with our ready-to-use Postman collection!

[![Run in Postman](https://run.pstmn.io/button.svg)](https://www.postman.com/varun1-1613/workspace/linko-public/collection/32840967-98c252be-4ac2-46e0-baad-43b4d677de8e?action=share&creator=32840967)

> **Tip:** Use the Postman Desktop App for best results.  
> If using the web UI, you may need to install the [Postman Agent](https://www.postman.com/downloads/postman-agent/) to access localhost.

### Manual Import Method

If you prefer, you can manually import the Postman collection:

1. Open Postman and click **File > Import**.
2. Import the collection file located at:  
   ```
   postman/linko-api.postman_collection.json
   ```
3. (Optional) Import the environment file if present:  
   ```
   postman/linko-local.postman_environment.json
   ```
4. In the top-right corner of Postman, select the "Linko Local Dev" environment from the dropdown to activate it.
5. You are now ready to send requests to the running application!

---

## 🛑 Stopping the Application

To stop all running containers:

```sh
docker-compose down
```

---

## 📚 Documentation & Support

- **API Docs:** See the Postman collection above for full endpoint documentation.
- **Issues:** [Open an issue](https://github.com/Varunv003/linko/issues) for bugs or feature requests.
- **Contributions:** PRs welcome! See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

---

## 💡 Why Self-Host Linko?

- **Own your data:** No third-party tracking or analytics.
- **Brand your links:** Use your own domain for trust and recognition.
- **Unlimited usage:** No quotas, no ads, no hidden fees.

---

**Linko** — Shorten, share, and track your links with confidence

