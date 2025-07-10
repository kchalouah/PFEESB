# Système de Gestion des Demandes de Contrôle Qualité

## Description du Projet

Ce projet est un système de gestion des demandes de contrôle qualité pour un environnement industriel. Il permet de suivre et de gérer les demandes de contrôle qualité pour différents îlots de production, machines et métiers.

Le système est conçu pour faciliter la communication entre les opérateurs, les contrôleurs et les gestionnaires, en fournissant une plateforme centralisée pour la création, le suivi et la gestion des demandes de contrôle qualité.

## Fonctionnalités Principales

- **Gestion des Utilisateurs**: Création et gestion des utilisateurs avec différents rôles (administrateur, utilisateur, gestionnaire).
- **Gestion des Îlots**: Création et gestion des îlots de production avec leurs emplacements.
- **Gestion des Machines**: Suivi des machines associées à chaque îlot, avec leurs caractéristiques techniques.
- **Gestion des Programmes**: Configuration des programmes d'exécution pour chaque machine.
- **Gestion des Métiers**: Définition des métiers avec leurs compétences requises.
- **Gestion des Demandes**: Création et suivi des demandes de contrôle qualité, avec différents types de demandes:
  - **Demandes Standard**: Demandes de contrôle qualité standard.
  - **Demandes Déléguées**: Demandes transférées à un autre contrôleur.
  - **Demandes Finales**: Demandes complétées et archivées.
  - **Demandes Techniques**: Demandes nécessitant une intervention technique.

## Architecture du Système

Le système est basé sur une architecture client-serveur:
- **Backend**: Développé avec Spring Boot, fournissant une API RESTful pour la gestion des données.
- **Frontend**: Interface utilisateur développée avec Angular, permettant une interaction intuitive avec le système.
- **Base de Données**: Stockage des données dans une base de données relationnelle.

## Diagramme de Classes

Le système est composé des entités principales suivantes:
- **App_user**: Représente les utilisateurs du système avec leurs rôles.
- **User_Role**: Définit les rôles disponibles dans le système.
- **Ilot**: Représente un îlot de production avec ses caractéristiques.
- **Machine**: Représente une machine associée à un îlot.
- **Programme**: Définit un programme d'exécution pour une machine.
- **Metier**: Représente un métier avec ses compétences requises.
- **Demande**: Représente une demande de contrôle qualité standard.
- **DemandeDelegue**: Représente une demande déléguée à un autre contrôleur.
- **DemandeFinale**: Représente une demande complétée et archivée.
- **DemandeTe**: Représente une demande nécessitant une intervention technique.

## Diagramme de Cas d'Utilisation

Les principaux cas d'utilisation du système sont:
- **Gestion des Utilisateurs**: Création, modification et suppression des utilisateurs.
- **Gestion des Îlots**: Création, modification et suppression des îlots.
- **Gestion des Machines**: Création, modification et suppression des machines.
- **Gestion des Programmes**: Création, modification et suppression des programmes.
- **Gestion des Métiers**: Création, modification et suppression des métiers.
- **Création de Demandes**: Création de nouvelles demandes de contrôle qualité.
- **Suivi des Demandes**: Suivi de l'état des demandes en cours.
- **Délégation de Demandes**: Transfert de demandes à d'autres contrôleurs.
- **Finalisation de Demandes**: Complétion et archivage des demandes.
- **Gestion des Demandes Techniques**: Traitement des demandes nécessitant une intervention technique.

## Initialisation des Données

Le système est configuré pour initialiser automatiquement la base de données avec des données de test lors du démarrage, facilitant ainsi le développement et les tests. Ces données comprennent:
- Des utilisateurs avec différents rôles
- Des îlots de production
- Des machines associées aux îlots
- Des programmes pour les machines
- Des métiers avec leurs compétences
- Des demandes de différents types

Cette initialisation permet de tester rapidement les fonctionnalités du système sans avoir à créer manuellement toutes les données nécessaires.