# GymTrackerAI – Backend

---

## Descrizione Generale

Il backend è realizzato in **Java con Spring Boot**. Gestisce:
- Autenticazione utente (JWT)
- Registrazione e login
- Salvataggio, aggiornamento e recupero delle sessioni di allenamento
- Comunicazione con il servizio esterno di Machine Learning

---

## Architettura del Sistema

- **Framework**: Spring Boot  
- **Database**: MySQL  
- **ORM**: Spring Data JPA  
- **Autenticazione**: JWT (access token)  
- **Chiamate esterne**: RestTemplate → Servizio ML Flask  
- **Struttura**:
  - `controller`: espone API REST  
  - `service`: logica di business  
  - `repository`: accesso al DB  
  - `entity`: mapping JPA  
  - `security`: JWT filter, auth manager, exception handler

---

## Funzionalità Backend

- Login, Registrazione, Recupero password (JWT)
- Salvataggio sessioni allenamento aggregate (una per esercizio/giorno)
- Eliminazione sessioni di un giorno specifico
- Recupero storico allenamenti utente
- Comunicazione HTTP con il modello ML in Docker

---

## API Principali

- `POST /api/v1/login`
- `POST /api/v1/registration`
- `POST /api/v1/workouts`: salva allenamento
- `GET /api/v1/workouts/{userId}`: recupera storico
- `DELETE /api/v1/workouts/{userId}/{date}`: elimina sessioni del giorno

---

## Repository Correlati

- Frontend: [github.com/tuo-user/gymtracker-frontend](https://github.com/tuo-user/gymtracker-frontend)
- Backend (questo repo): [github.com/tuo-user/gymtracker-backend](https://github.com/tuo-user/gymtracker-backend)
- Machine Learning: [github.com/tuo-user/gymtracker-ml](https://github.com/tuo-user/gymtracker-ml)