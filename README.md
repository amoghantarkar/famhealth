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

---

## Quick Start

### Prerequisites
- Docker Desktop
- Node.js 20+
- npm 10+
- Java 17 (optional if you run backend only through Docker)
- Expo CLI (`npm i -g expo eas-cli`)

### Backend
```bash
cp .env.backend.example .env.backend
docker compose up --build
```
Backend: `http://localhost:8080`
Swagger: `http://localhost:8080/swagger-ui.html`

### Mobile
```bash
cd mobile
npm install
npm run start
```
Then:
- press `a` for Android emulator, or
- press `i` for iOS simulator (macOS), or
- scan QR with Expo Go on physical phone.

### Make mobile reach backend from phone
If testing on a physical Android device, `localhost` inside the app points to the phone, not your laptop.

Use one of these:
- Android emulator: keep `http://10.0.2.2:8080/api`
- iOS simulator: `http://localhost:8080/api`
- Physical device: `http://<YOUR_LAPTOP_LAN_IP>:8080/api`

Update `mobile/src/api/client.ts` base URL accordingly.

### Quick smoke test
1. `POST /api/auth/signup`
2. Add headers to requests:
   - `X-User-Id: <userId from signup/login>`
   - `X-Account-Id: <accountId from signup/login>`
3. `POST /api/profiles`
4. `POST /api/records/upload` (multipart)
5. `POST /api/records/{id}/process`
6. `GET /api/profiles/{id}/dashboard`

---

## Build for Play Store (Android)

This repo includes `mobile/eas.json` and `mobile/app.json` for Expo EAS builds.

### 1) Prepare app identifiers
In `mobile/app.json`, verify:
- `expo.android.package` (must be globally unique in Google Play)
- `expo.name`, `expo.slug`, versioning

### 2) Login and configure EAS
```bash
cd mobile
npx expo login
npx eas build:configure
```

### 3) Build production Android App Bundle (.aab)
```bash
npx eas build --platform android --profile production
```
EAS will generate a signed `.aab` (required by Play Console).

### 4) Create Play Console listing
- Create app in Google Play Console
- Complete store listing (description, screenshots, privacy policy URL)
- Fill Data safety form
- Set content rating
- Add app access instructions if required

### 5) Upload release
- Upload generated `.aab` to Internal testing first
- Add testers and verify end-to-end flows
- Promote to Closed/Open/Production once stable

### 6) Submit directly from CLI (optional)
```bash
npx eas submit --platform android --profile production
```

---

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
- Do not market this MVP as diagnosis/treatment software.
