# API Documentation

This document provides a comprehensive list of all API endpoints available in the PfeAziz application.

## Authentication and User Management

### User Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login a user
- `DELETE /api/auth/{id}` - Delete a user
- `GET /api/auth/roles` - Get all roles

### User Roles
- `GET /api/roles` - Get all roles
- `GET /api/roles/{id}` - Get a role by ID
- `POST /api/roles` - Create a new role
- `DELETE /api/roles/{id}` - Delete a role

## Demande Management

### Demandes
- `GET /api/demandes` - Get all demandes
- `GET /api/demandes/{id}` - Get a demande by ID
- `POST /api/demandes` - Create a new demande
- `PUT /api/demandes/{id}` - Update a demande
- `DELETE /api/demandes/{id}` - Delete a demande
- `GET /api/demandes/excel` - Export demandes to Excel
- `POST /api/demandes/batch` - Create multiple demandes

### Demandes Delegue
- `GET /api/demandes_delegue` - Get all demandes delegue
- `GET /api/demandes_delegue/{id}` - Get a demande delegue by ID
- `POST /api/demandes_delegue` - Create a new demande delegue
- `PUT /api/demandes_delegue/{id}` - Update a demande delegue
- `DELETE /api/demandes_delegue/{id}` - Delete a demande delegue
- `GET /api/demandes_delegue/excel` - Export demandes delegue to Excel
- `POST /api/demandes_delegue/batch` - Create multiple demandes delegue

### Demandes Finale
- `GET /api/demandes_finale` - Get all demandes finale
- `GET /api/demandes_finale/{id}` - Get a demande finale by ID
- `POST /api/demandes_finale` - Create a new demande finale
- `PUT /api/demandes_finale/{id}` - Update a demande finale
- `DELETE /api/demandes_finale/{id}` - Delete a demande finale
- `GET /api/demandes_finale/excel` - Export demandes finale to Excel
- `POST /api/demandes_finale/batch` - Create multiple demandes finale

### Demandes Te
- `GET /api/demandes_te` - Get all demandes te
- `GET /api/demandes_te/{id}` - Get a demande te by ID
- `POST /api/demandes_te` - Create a new demande te
- `PUT /api/demandes_te/{id}` - Update a demande te
- `DELETE /api/demandes_te/{id}` - Delete a demande te
- `GET /api/demandes_te/excel` - Export demandes te to Excel
- `POST /api/demandes_te/batch` - Create multiple demandes te

## Reference Data Management

### Ilots
- `GET /api/ilots` - Get all ilots
- `POST /api/ilots` - Create a new ilot
- `PUT /api/ilots/{id}` - Update an ilot
- `DELETE /api/ilots/{id}` - Delete an ilot

### Metiers
- `GET /api/metiers` - Get all metiers
- `POST /api/metiers` - Create a new metier
- `PUT /api/metiers/{id}` - Update a metier
- `DELETE /api/metiers/{id}` - Delete a metier

### Programmes
- `GET /api/programmes` - Get all programmes
- `POST /api/programmes` - Create a new programme
- `PUT /api/programmes/{id}` - Update a programme
- `DELETE /api/programmes/{id}` - Delete a programme

### Machines
- `GET /api/machines` - Get all machines
- `POST /api/machines` - Create a new machine
- `PUT /api/machines/{id}` - Update a machine
- `DELETE /api/machines/{id}` - Delete a machine