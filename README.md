# FamHealth MVP (Monorepo)

A production-style MVP for a **personal health record organizer** (not diagnosis/treatment software).

## Architecture
- `backend/`: Spring Boot REST API, PostgreSQL, Flyway, JWT auth + refresh tokens, extraction/chat provider abstractions.
- `mobile/`: React Native (Expo + TypeScript) consumer app with bottom tabs for Home/Records/Dashboard/Chat/Family.
- `docker-compose.yml`: boots backend + postgres.

## Key Capabilities
- Auth: sign up/login/refresh/logout
- Multi-profile account: self/parent/spouse/child
- Upload medical records (PDF/image), process via mock OCR/extraction pipeline
- Structured observations + review status (`NEEDS_REVIEW`, `VERIFIED`)
- Timeline + dashboard trends
- Family sharing (`viewer`/`editor`)
- Chat with own records with grounded, disclaimer-safe responses
- Privacy settings and soft-delete account

## Quick Start
### Backend
```bash
cp .env.backend.example .env.backend
docker compose up --build
```
Swagger: `http://localhost:8080/swagger-ui.html`

### Mobile
```bash
cd mobile
npm install
npm run start
```

## API Endpoints
Implemented under `/api`:
- Auth: `/auth/signup`, `/auth/login`, `/auth/refresh`, `/auth/logout`
- Profiles: `/profiles` CRUD
- Records: `/records/upload`, `/records`, `/records/{id}`, `/records/{id}/file`, `/records/{id}/process`
- Timeline/Observations/Dashboard: `/profiles/{id}/timeline`, `/profiles/{id}/observations`, `/profiles/{id}/dashboard`, `/observations/{id}/review`
- Family: `/family/share`, `/family/access`, `/family/share/{id}/revoke`
- Chat: `/chat/sessions`, `/chat/sessions/{id}/messages`
- Privacy: `/settings/privacy`, `/account/delete`

## Notes
- Informational only. Not medical advice.
- MVP uses mock services for OCR, extraction, chat, and storage signing.
- JWT issuance is implemented; request headers `X-User-Id` and `X-Account-Id` are used in this scaffold for app-layer authorization wiring.
