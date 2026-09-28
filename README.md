# Loyalty Rewards System

A full-stack loyalty points app: Spring Boot + MySQL backend, plain HTML/JS frontend.

**No Lombok** — every model and DTO class uses hand-written constructors, getters,
and setters (plain encapsulation), so there's no IDE plugin or annotation
processor to configure. Just import and run.

## Structure

```
loyalty-rewards/
├── backend/                  Spring Boot 3 + Java 17 + Maven
│   ├── pom.xml
│   ├── schema-reference.sql  Optional manual schema (Hibernate auto-creates it too)
│   └── src/main/
│       ├── java/com/loyalty/rewards/
│       │   ├── model/        Member, RewardsConfig, Transaction, Redemption (JPA entities)
│       │   ├── repository/   Spring Data JPA repositories
│       │   ├── dto/          Request/response objects
│       │   ├── service/      Points math + validation lives here, not in the UI
│       │   ├── controller/   REST endpoints
│       │   └── exception/    Global exception handler → clean JSON error responses
│       └── resources/
│           └── application.properties
└── frontend/
    └── index.html            Single-file HTML/JS app, no build step, calls the API via fetch
```

## Endpoints

| Method | Path                        | Purpose                                   |
|--------|-----------------------------|--------------------------------------------|
| GET    | `/api/config`                | Get current rewards configuration         |
| PUT    | `/api/config`                | Update earn rate / min redeem / redeem value |
| GET    | `/api/members`                | List all members                          |
| GET    | `/api/members/{id}`           | Get one member                            |
| POST   | `/api/members`                | Create a member                           |
| PUT    | `/api/members/{id}`           | Update a member                           |
| DELETE | `/api/members/{id}`           | Delete a member                           |
| GET    | `/api/transactions`           | List all transactions (optional `?memberId=`) |
| POST   | `/api/transactions`           | Record a purchase (points computed server-side) |
| GET    | `/api/redemptions`            | List all redemptions (optional `?memberId=`) |
| POST   | `/api/redemptions`            | Redeem points (min/balance validated server-side) |
| GET    | `/api/statement/{memberId}`   | Merged transactions + redemptions, sorted by date |

Points math and validation (earn rate, minimum redemption, balance checks) all
live in the service layer — the frontend only displays data and posts requests.
The statement is not stored as its own table; it's computed on the fly by
merging transactions and redemptions for a member.

## Running it

1. **Database** — create it (or let Hibernate do it):
   ```sql
   CREATE DATABASE loyalty_rewards;
   ```
   Update `backend/src/main/resources/application.properties` with your MySQL
   username/password. Want zero DB setup first? Comment out the MySQL block in
   that file and uncomment the H2 in-memory block instead.

2. **Backend**:
   ```bash
   cd backend
   mvn spring-boot:run
   ```
   Runs on `http://localhost:8080`.

3. **Frontend** — just open `frontend/index.html` in a browser. It calls the
   API at `http://localhost:8080/api` (edit `API_BASE` in the `<script>` tag
   if you change the backend port).

## Importing into Eclipse

`File → Import… → Maven → Existing Maven Projects → Next` → browse to the
`backend` folder (the one with `pom.xml`) → Finish. No Lombok plugin needed —
this version compiles with a stock Eclipse install.

## Security note

There's no authentication layer yet — anyone who can reach `:8080` can hit
the API. Add Spring Security + JWT before deploying this anywhere beyond
localhost.
