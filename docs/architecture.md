# Folder Structure
- backend/src/main/java/com/famhealth/{controller,service,repository,dto,entity,security,config,integration,exception}
- backend/src/main/resources/db/migration
- mobile/src/{screens,components,api}

# Data Isolation Model
- Account is the tenant boundary.
- Every profile/record/observation is linked to account.
- Family access grants are explicit and revocable.

# Safety Rules in Product
- App and chat always show disclaimer text.
- No diagnosis or treatment recommendations in generated chat responses.
