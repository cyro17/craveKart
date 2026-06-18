# 🍔 CraveKart

A production-grade **food delivery backend** built to demonstrate distributed systems patterns — event-driven choreography sagas, the transactional outbox, idempotent consumers, and real-time order tracking.

Built with **Spring Boot 4**, **Kafka**, **PostgreSQL**, **Redis**, and **Stripe**.

---

## Why this project exists

Most CRUD backends hide the hard parts of distributed systems behind a single database transaction. CraveKart deliberately surfaces them. It models the real coordination problem a food-delivery platform faces: a customer pays, a restaurant must be notified reliably, a delivery partner picks up, and every party sees live status — all while tolerating partial failures (Stripe down, a consumer offline, a duplicate webhook).

The result is a backend that demonstrates **how to keep services decoupled and data consistent without distributed transactions**.

---

## Architecture at a glance

```
Customer ──REST/SSE──┐
Restaurant ─WS/STOMP─┤
Delivery ───REST─────┼──► Spring Security (JWT, 4 roles)
Admin ──────REST─────┘            │
                                  ▼
                        ┌─────────────────────┐
                        │   Order Service      │
                        │   (Spring Boot)      │
                        └─────────┬───────────┘
                                  │ writes order + event in ONE transaction
                                  ▼
                        ┌─────────────────────┐
                        │ Transactional Outbox │  ◄── polled every 5s
                        └─────────┬───────────┘
                                  │ relays with aggregateId as partition key
                                  ▼
                  ┌────────────── Kafka ──────────────┐
                  │  order-created   order-confirmed  │
                  │  payment-success payment-failed   │
                  └──────┬─────────────────┬──────────┘
                         ▼                 ▼
                 Payment Saga      Restaurant Saga      Delivery Saga
                 (Stripe)          (WebSocket push)     (pickup→delivered)
```

Three **choreography-based sagas** coordinate the order lifecycle. There is no central orchestrator — each service reacts to events and emits its own, keeping services independently deployable and loosely coupled.

---

## Key engineering decisions

### Transactional Outbox over dual-write
Writing to the database **and** publishing to Kafka in the same operation risks one succeeding and the other failing. CraveKart writes the domain change and an outbox row in a **single database transaction**, then a scheduler relays outbox rows to Kafka. This guarantees at-least-once delivery without a distributed transaction.

### `aggregateId` as the Kafka partition key
Kafka only guarantees ordering **within a partition**. By keying `order-created` on `orderId` and `order-confirmed` on `restaurantId`, all events for the same aggregate land on the same partition and are processed in order.

### Idempotent consumers
At-least-once delivery means a consumer may see the same message twice. A `processed_events` table keyed by `topic-partition-offset` makes every saga step idempotent — duplicate deliveries are detected and skipped.

### SSE for customers, WebSocket/STOMP for restaurants
The transport matches the interaction:
- **Customers** get one-way, short-lived order updates → **Server-Sent Events**.
- **Restaurants** need a bidirectional, long-lived dashboard (receive orders, send accept/reject) → **WebSocket with STOMP**.

### Compensation via saga
A rejected or timed-out order triggers a `REFUND_INITIATED` compensating action rather than rolling back a distributed transaction that never existed.

---

## The payment flow

```
1. placeOrder()        save order + outbox:order-created  (one transaction)
2. OutboxScheduler     relay to Kafka, key = orderId
3. PaymentListener     create Stripe PaymentIntent, push clientSecret via SSE
4. customer pays       Stripe.js confirmPayment()
5. Stripe webhook      verify signature, publish payment-success
6. PaymentResultListener  confirm order + outbox:order-confirmed, email receipt
7. RestaurantSaga      consume order-confirmed, push to tablet via STOMP
```

Async boundaries exist only at real failure points — the Stripe API, the Stripe webhook, and guaranteed restaurant delivery. Everything else is a direct in-process call.

---

## Order state machine

```
PAYMENT_PENDING → CONFIRMED → ACCEPTED → PREPARING → READY_FOR_PICKUP → OUT_FOR_DELIVERY → DELIVERED
       │
       └──► CANCELLED / REJECTED → REFUND_INITIATED
```

---

## Tech stack

| Layer | Technology |
|---|---|
| Language / Framework | Java 21, Spring Boot 4.0 |
| Security | Spring Security, JWT, role-based access (4 roles) |
| Persistence | PostgreSQL, Spring Data JPA, Flyway migrations |
| Messaging | Apache Kafka (partitioned topics, DLQ + retry) |
| Caching | Redis |
| Payments | Stripe (PaymentIntents + webhooks) |
| Real-time | Server-Sent Events, WebSocket/STOMP |
| Build | Maven (multi-module) |

---

## Reliability features

- **Dead-letter queue** with exponential backoff (3 retries → `.DLT` topic)
- **Idempotency-Key** header support to deduplicate order placement
- **Order timeout watchdog** — a scheduler auto-cancels unconfirmed orders
- **At-least-once delivery** with idempotent consumers throughout

---

## Module structure

```
cravekart/
├── cravekart-root          # main order/payment/restaurant service
├── notification-service    # standalone email/notification consumer
├── auth-service            # authentication (in progress)
└── core                    # shared event contracts
```

---

## Getting started

### Prerequisites
- Java 21
- Docker (for PostgreSQL, Kafka, Redis)
- A Stripe test account

### Run the infrastructure
```bash
docker-compose up -d        # starts PostgreSQL, Kafka, Redis
```

### Configure
Set your Stripe keys and datasource in `application-dev.yml`:
```yaml
app:
  kafka:
    enabled: true
    bootstrap-servers: localhost:9092
stripe:
  secret-key: sk_test_...
  webhook-secret: whsec_...
```

### Run
```bash
mvn spring-boot:run
```

The service starts on `http://localhost:9091`. Flyway applies migrations automatically on startup.

### Test the flow
1. Sign up as a customer and sign in to get a JWT.
2. Connect to the SSE stream: `GET /api/notification/stream/{customerId}`.
3. Add items to cart and place an order.
4. Watch the `clientSecret` arrive over SSE, complete payment, and see live status updates flow through.

---

## Roadmap

- [ ] **Debezium CDC** to replace the polling outbox (5s → sub-second latency)
- [ ] **Redis pub/sub backplane** for SSE fan-out across multiple instances
- [ ] **Distributed tracing** with a `traceId` propagated through Kafka headers
- [ ] Delivery-partner assignment optimization
- [ ] Full integration test suite

---

## What this project demonstrates

- Designing **event-driven systems** that stay consistent under partial failure
- Choosing the **right consistency tool** — outbox over dual-write, idempotency over exactly-once fantasy, partition keys for ordering
- Matching **transport to interaction** — SSE vs WebSocket by use case
- Modeling a real domain as a **state machine** with compensating actions
- Writing **production-minded** code: retries, DLQs, idempotency, schema versioning

---

## License
