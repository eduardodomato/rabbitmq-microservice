# 🐰 RabbitMQ Microservice - Order Management System

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.13-FF6600.svg)
![H2 Database](https://img.shields.io/badge/H2-Database-yellow.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

**A modern microservice application demonstrating event-driven architecture with RabbitMQ, Spring Boot, and H2 Database**

[Features](#-features) • [Quick Start](#-quick-start) • [API Documentation](#-api-documentation) • [Architecture](#-architecture)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Prerequisites](#-prerequisites)
- [Quick Start](#-quick-start)
- [API Documentation](#-api-documentation)
- [Project Structure](#-project-structure)
- [Database Access](#-database-access)
- [RabbitMQ Management](#-rabbitmq-management)
- [Development](#-development)
- [Contributing](#-contributing)

---

## 🎯 Overview

This project is a **microservice-based order management system** that demonstrates modern software architecture patterns using:

- **Event-Driven Architecture** with RabbitMQ for asynchronous message processing
- **RESTful API** for order creation and management
- **Database Persistence** with H2 (file-based for data persistence)
- **Message Queue Integration** for order status updates and delivery notifications

The application simulates a food delivery service (like UberEats) where orders are created, persisted to a database, and published to RabbitMQ for asynchronous processing by delivery consumers.

---

## ✨ Features

- ✅ **Order Management**: Create and update orders via REST API
- ✅ **Event-Driven Processing**: Asynchronous order processing with RabbitMQ
- ✅ **Database Persistence**: File-based H2 database for data persistence
- ✅ **Message Queue Integration**: Topic exchange with routing keys for message routing
- ✅ **Consumer Service**: Automatic order status updates via message consumers
- ✅ **Docker Support**: RabbitMQ server via Docker Compose
- ✅ **H2 Console**: Web-based database management interface

---

## 🛠 Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 | Programming Language |
| **Spring Boot** | 3.5.7 | Application Framework |
| **Spring AMQP** | Latest | RabbitMQ Integration |
| **Spring Data JPA** | Latest | Database Access Layer |
| **H2 Database** | Latest | Embedded Database |
| **RabbitMQ** | 3.13 | Message Broker |
| **Lombok** | Latest | Boilerplate Reduction |
| **Maven** | Latest | Build Tool |
| **Docker** | Latest | Containerization |

---

## 🏗 Architecture

### System Architecture

```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│   Client    │───────▶│  REST API    │────────▶│   Service   │
│  (HTTP)     │         │  (Port 9070) │         │   Layer     │
└─────────────┘         └──────────────┘         └──────┬──────┘
                                                        │
                                                        ▼
                                              ┌──────────────┐
                                              │   H2 DB      │
                                              │  (Persist)   │
                                              └──────────────┘
                                                       │
                                                       ▼
                                              ┌──────────────┐
                                              │  RabbitMQ    │
                                              │  (Exchange)  │
                                              └──────┬───────┘
                                                     │
                                                     ▼
                                              ┌──────────────┐
                                              │  Consumer    │
                                              │  (Process)   │
                                              └──────────────┘
```

### Message Flow

1. **Order Creation**: Client sends POST request → Order saved to DB → Message published to RabbitMQ
2. **Order Update**: Client sends PUT request → Order updated in DB → Update message published
3. **Order Processing**: Consumer listens to queue → Processes order → Updates status

### RabbitMQ Configuration

- **Exchange**: `edu.exchange` (Topic Exchange)
- **Queue**: `edu.queue`
- **Routing Key**: `edu.key`

---

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- ☕ **Java 21** or higher
- 🐳 **Docker Desktop** (for RabbitMQ)
- 🔧 **Maven 3.6+** (or use included Maven wrapper)
- 💻 **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

---

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd rabbitmq-microservice
```

### 2. Start RabbitMQ Server

Using Docker Compose:

```bash
docker compose up -d
```

This will start RabbitMQ on:
- **AMQP Port**: `5672`
- **Management UI**: `http://localhost:15672` (guest/guest)

> 📖 See [RUNBOOK.md](RUNBOOK.md) for detailed RabbitMQ operations.

### 3. Build the Application

```bash
# Using Maven wrapper (Windows)
./mvnw.cmd clean install

# Using Maven wrapper (Linux/Mac)
./mvnw clean install

# Or using Maven directly
mvn clean install
```

### 4. Run the Application

```bash
# Using Maven wrapper
./mvnw.cmd spring-boot:run

# Or run the JAR
java -jar target/rabbitmq-0.0.1-SNAPSHOT.jar
```

The application will start on **http://localhost:9070**

### 5. Verify Installation

- ✅ Application: http://localhost:9070
- ✅ H2 Console: http://localhost:9070/h2-console
- ✅ RabbitMQ Management: http://localhost:15672

---

## 📚 API Documentation

### Base URL

```
http://localhost:9070/api/order
```

### Endpoints

#### 1. Create Order

**POST** `/api/order/{restaurantName}`

Creates a new order and publishes it to RabbitMQ.

**Path Parameters:**
- `restaurantName` (string): Name of the restaurant

**Request Body:**
```json
{
  "name": "Pizza Margherita",
  "qty": 2,
  "price": 24.99
}
```

**Response:** `200 OK`
```json
{
  "orderId": "b6238f4f-03a5-44b1-a0c7-1b15f79ddbbc",
  "name": "Pizza Margherita",
  "qty": 2,
  "price": 24.99
}
```

**Example:**
```bash
curl -X POST http://localhost:9070/api/order/UberEats \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Pizza Margherita",
    "qty": 2,
    "price": 24.99
  }'
```

#### 2. Update Order

**PUT** `/api/order/{orderId}`

Updates an existing order.

**Path Parameters:**
- `orderId` (UUID): The order ID

**Request Body:**
```json
{
  "name": "Updated Pizza Name",
  "qty": 3,
  "price": 29.99
}
```

**Response:** `200 OK`
```json
{
  "orderId": "b6238f4f-03a5-44b1-a0c7-1b15f79ddbbc",
  "name": "Updated Pizza Name",
  "qty": 3,
  "price": 29.99
}
```

**Example:**
```bash
curl -X PUT http://localhost:9070/api/order/b6238f4f-03a5-44b1-a0c7-1b15f79ddbbc \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Pizza Name",
    "qty": 3,
    "price": 29.99
  }'
```

---

## 📁 Project Structure

```
rabbitmq-microservice/
│
├── src/
│   ├── main/
│   │   ├── java/com/edu/rabbitmq/
│   │   │   ├── config/
│   │   │   │   └── MessagingConfig.java      # RabbitMQ configuration
│   │   │   ├── consumer/
│   │   │   │   └── DeliveryConsumer.java     # Message consumer
│   │   │   ├── dto/
│   │   │   │   ├── OrderDTO.java             # Data Transfer Object
│   │   │   │   └── OrderStatus.java          # Order status DTO
│   │   │   ├── entity/
│   │   │   │   └── Order.java                # JPA Entity
│   │   │   ├── mapper/
│   │   │   │   └── OrderMapper.java          # Entity-DTO mapper
│   │   │   ├── publisher/
│   │   │   │   └── OrderPublisher.java       # REST Controller
│   │   │   ├── repository/
│   │   │   │   └── OrderRepository.java      # JPA Repository
│   │   │   ├── service/
│   │   │   │   └── OrderService.java         # Business logic
│   │   │   └── RabbitmqApplication.java      # Main application
│   │   └── resources/
│   │       └── application.yml               # Configuration
│   └── test/                                 # Test files
│
├── data/                                     # H2 database files (auto-generated)
├── docker-compose.yml                        # RabbitMQ container config
├── pom.xml                                   # Maven dependencies
├── RUNBOOK.md                                # RabbitMQ operations guide
└── README.md                                 # This file
```

---

## 💾 Database Access

### H2 Console

Access the H2 database console at: **http://localhost:9070/h2-console**

**Connection Settings:**
- **JDBC URL**: `jdbc:h2:file:./data/rabbit-db`
- **Username**: `sa`
- **Password**: (leave empty)

### Database Schema

The `orders` table is automatically created with the following structure:

| Column | Type | Description |
|--------|------|-------------|
| `id` | UUID | Primary key (auto-generated) |
| `name` | VARCHAR | Order name |
| `qty` | INTEGER | Quantity |
| `price` | DOUBLE | Price |

### Query Examples

```sql
-- View all orders
SELECT * FROM orders;

-- Find order by ID
SELECT * FROM orders WHERE id = 'b6238f4f-03a5-44b1-a0c7-1b15f79ddbbc';

-- Count total orders
SELECT COUNT(*) FROM orders;
```

---

## 🐰 RabbitMQ Management

### Access Management UI

Navigate to: **http://localhost:15672**

**Default Credentials:**
- Username: `guest`
- Password: `guest`

### View Messages

1. Go to **Exchanges** → `edu.exchange`
2. Check **Queues** → `edu.queue`
3. Monitor message flow in real-time

### Manual Message Publishing

You can publish test messages via the Management UI:
1. Go to **Exchanges** → `edu.exchange`
2. Click **Publish message**
3. Set routing key: `edu.key`
4. Add JSON payload:
```json
{
  "order": {
    "orderId": "test-123",
    "name": "Test Order",
    "qty": 1,
    "price": 10.00
  },
  "status": "TEST",
  "message": "Manual test message"
}
```

---

## 🔧 Development

### Running Tests

```bash
./mvnw.cmd test
```

### Building for Production

```bash
./mvnw.cmd clean package
```

The executable JAR will be created in `target/rabbitmq-0.0.1-SNAPSHOT.jar`

### Configuration

Edit `src/main/resources/application.yml` to customize:

- **Server Port**: Change `server.port`
- **Database Path**: Modify `spring.datasource.url`
- **RabbitMQ Connection**: Configure connection settings (defaults to localhost:5672)

### Logging

Application logs show:
- Order creation/updates
- RabbitMQ message publishing
- Consumer message processing
- Database operations (if `show-sql: true`)

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. 🍴 Fork the repository
2. 🌿 Create a feature branch (`git checkout -b feature/amazing-feature`)
3. 💾 Commit your changes (`git commit -m 'Add amazing feature'`)
4. 📤 Push to the branch (`git push origin feature/amazing-feature`)
5. 🔀 Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- RabbitMQ for the powerful message broker
- H2 Database for the lightweight embedded database

---

<div align="center">

**Made with ❤️ using Spring Boot and RabbitMQ**

⭐ Star this repo if you find it helpful!

</div>

