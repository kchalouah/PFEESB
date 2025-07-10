package com.example.pfeaziz;

import com.example.pfeaziz.model.*;
import com.example.pfeaziz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class PfeAzizApplication {

    private static final Logger logger = LoggerFactory.getLogger(PfeAzizApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PfeAzizApplication.class, args);
    }

    @Bean
    public CommandLineRunner initRoles(@Autowired User_RoleService roleService) {
        return args -> {
            logger.info("Initializing default roles...");

            // Define default roles
            String[] defaultRoles = {"ROLE_ADMIN", "ROLE_USER", "ROLE_MANAGER"};

            // Create roles if they don't exist
            for (String roleName : defaultRoles) {
                User_Role existingRole = roleService.getRoleByName(roleName);
                if (existingRole == null) {
                    User_Role newRole = new User_Role();
                    newRole.setRoleName(roleName);
                    roleService.saveRole(newRole);
                    logger.info("Created role: {}", roleName);
                } else {
                    logger.info("Role already exists: {}", roleName);
                }
            }

            logger.info("Default roles initialization completed.");
        };
    }

    @Bean
    public CommandLineRunner initUsers(
            @Autowired App_userService userService,
            @Autowired User_RoleService roleService,
            @Autowired PasswordEncoder passwordEncoder) {
        return args -> {
            logger.info("Initializing default users...");

            // Check if users already exist
            if (userService.getAllUsers().size() > 0) {
                logger.info("Users already exist, skipping initialization.");
                return;
            }

            // Create admin user
            App_user admin = new App_user();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setUser_roles(Collections.singletonList(roleService.getRoleByName("ROLE_ADMIN")));
            userService.saveUser(admin);
            logger.info("Created admin user");

            // Create manager user
            App_user manager = new App_user();
            manager.setUsername("manager");
            manager.setPassword(passwordEncoder.encode("manager123"));
            manager.setUser_roles(Collections.singletonList(roleService.getRoleByName("ROLE_MANAGER")));
            userService.saveUser(manager);
            logger.info("Created manager user");

            // Create regular user
            App_user user = new App_user();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setUser_roles(Collections.singletonList(roleService.getRoleByName("ROLE_USER")));
            userService.saveUser(user);
            logger.info("Created regular user");

            logger.info("Default users initialization completed.");
        };
    }

    @Bean
    public CommandLineRunner initIlots(@Autowired IlotService ilotService) {
        return args -> {
            logger.info("Initializing default ilots...");

            // Check if ilots already exist
            if (ilotService.getAllIlots().size() > 0) {
                logger.info("Ilots already exist, skipping initialization.");
                return;
            }

            // Create ilots
            String[][] ilotData = {
                {"Ilot A", "Ilot principal de production", "Zone Nord"},
                {"Ilot B", "Ilot secondaire de production", "Zone Sud"},
                {"Ilot C", "Ilot d'assemblage", "Zone Est"},
                {"Ilot D", "Ilot de finition", "Zone Ouest"}
            };

            for (String[] data : ilotData) {
                Ilot ilot = new Ilot();
                ilot.setName(data[0]);
                ilot.setDescription(data[1]);
                ilot.setLocation(data[2]);
                ilotService.saveIlot(ilot);
                logger.info("Created ilot: {}", data[0]);
            }

            logger.info("Default ilots initialization completed.");
        };
    }

    @Bean
    public CommandLineRunner initMetiers(@Autowired MetierService metierService) {
        return args -> {
            logger.info("Initializing default metiers...");

            // Check if metiers already exist
            if (metierService.getAllMetiers().size() > 0) {
                logger.info("Metiers already exist, skipping initialization.");
                return;
            }

            // Create metiers
            Object[][] metierData = {
                {"Soudure", "Métier de soudure des composants électroniques", "Électronique", 
                    Arrays.asList("Précision", "Connaissance des alliages", "Résistance à la chaleur")},
                {"Assemblage", "Assemblage des pièces mécaniques", "Mécanique", 
                    Arrays.asList("Dextérité", "Lecture de plans", "Travail d'équipe")},
                {"Contrôle Qualité", "Vérification de la qualité des produits finis", "Qualité", 
                    Arrays.asList("Attention aux détails", "Connaissance des normes", "Rigueur")},
                {"Maintenance", "Entretien et réparation des machines", "Technique", 
                    Arrays.asList("Diagnostic", "Électromécanique", "Résolution de problèmes")}
            };

            for (Object[] data : metierData) {
                Metier metier = new Metier();
                metier.setName((String) data[0]);
                metier.setDescription((String) data[1]);
                metier.setCategory((String) data[2]);
                metier.setRequiredSkills((List<String>) data[3]);
                metierService.saveMetier(metier);
                logger.info("Created metier: {}", data[0]);
            }

            logger.info("Default metiers initialization completed.");
        };
    }

    @Bean
    public CommandLineRunner initMachines(
            @Autowired MachineService machineService,
            @Autowired IlotService ilotService) {
        return args -> {
            logger.info("Initializing default machines...");

            // Check if machines already exist
            if (machineService.getAllMachines().size() > 0) {
                logger.info("Machines already exist, skipping initialization.");
                return;
            }

            // Get ilots
            List<Ilot> ilots = ilotService.getAllIlots();
            if (ilots.isEmpty()) {
                logger.warn("No ilots found, cannot create machines.");
                return;
            }

            // Create machines
            Object[][] machineData = {
                {"Machine A1", "CNC", "XYZ-1000", "SN12345", ilots.get(0)},
                {"Machine A2", "Fraiseuse", "ABC-2000", "SN23456", ilots.get(0)},
                {"Machine B1", "Tour", "DEF-3000", "SN34567", ilots.get(1)},
                {"Machine C1", "Robot Assemblage", "GHI-4000", "SN45678", ilots.get(2)},
                {"Machine D1", "Polisseuse", "JKL-5000", "SN56789", ilots.get(3)}
            };

            for (Object[] data : machineData) {
                Machine machine = new Machine();
                machine.setName((String) data[0]);
                machine.setType((String) data[1]);
                machine.setModel((String) data[2]);
                machine.setSerialNumber((String) data[3]);
                machine.setIlot((Ilot) data[4]);
                machineService.saveMachine(machine);
                logger.info("Created machine: {}", data[0]);
            }

            logger.info("Default machines initialization completed.");
        };
    }

    @Bean
    public CommandLineRunner initProgrammes(
            @Autowired ProgrammeService programmeService,
            @Autowired MachineService machineService) {
        return args -> {
            logger.info("Initializing default programmes...");

            // Check if programmes already exist
            if (programmeService.getAllProgrammes().size() > 0) {
                logger.info("Programmes already exist, skipping initialization.");
                return;
            }

            // Get machines
            List<Machine> machines = machineService.getAllMachines();
            if (machines.isEmpty()) {
                logger.warn("No machines found, cannot create programmes.");
                return;
            }

            // Create programmes
            Object[][] programmeData = {
                {"Programme CNC Standard", "Programme standard pour usinage CNC", 120, machines.get(0)},
                {"Programme Fraisage Précision", "Programme de fraisage haute précision", 90, machines.get(1)},
                {"Programme Tournage Rapide", "Programme optimisé pour tournage rapide", 60, machines.get(2)},
                {"Programme Assemblage Automatique", "Séquence d'assemblage automatisée", 180, machines.get(3)},
                {"Programme Polissage Fin", "Programme pour finition de surface", 45, machines.get(4)}
            };

            for (Object[] data : programmeData) {
                Programme programme = new Programme();
                programme.setName((String) data[0]);
                programme.setDescription((String) data[1]);
                programme.setDuration((Integer) data[2]);
                programme.setMachine((Machine) data[3]);
                programmeService.saveProgramme(programme);
                logger.info("Created programme: {}", data[0]);
            }

            logger.info("Default programmes initialization completed.");
        };
    }

    @Bean
    public CommandLineRunner initDemandes(
            @Autowired DemandeService demandeService,
            @Autowired App_userService userService,
            @Autowired IlotService ilotService,
            @Autowired MetierService metierService,
            @Autowired ProgrammeService programmeService) {
        return args -> {
            logger.info("Initializing default demandes...");

            // Check if demandes already exist
            if (demandeService.getAllDemandes().size() > 0) {
                logger.info("Demandes already exist, skipping initialization.");
                return;
            }

            // Get dependencies
            List<App_user> users = userService.getAllUsers();
            List<Ilot> ilots = ilotService.getAllIlots();
            List<Metier> metiers = metierService.getAllMetiers();
            List<Programme> programmes = programmeService.getAllProgrammes();

            if (users.isEmpty() || ilots.isEmpty() || metiers.isEmpty() || programmes.isEmpty()) {
                logger.warn("Missing dependencies, cannot create demandes.");
                return;
            }

            // Format for dates
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime now = LocalDateTime.now();

            // Create demandes
            for (int i = 0; i < 5; i++) {
                Demande demande = new Demande();
                demande.setOperateur(users.get(i % users.size()));
                demande.setControleur(users.get((i + 1) % users.size()));
                demande.setIlot(ilots.get(i % ilots.size()));
                demande.setMetier(metiers.get(i % metiers.size()));
                demande.setProgramme(programmes.get(i % programmes.size()));

                // Set dates and times
                LocalDateTime dueDate = now.plusDays(i + 1);
                demande.setDate(dueDate.format(dateFormatter));
                demande.setTime(dueDate.format(timeFormatter));

                // Set other fields
                demande.setStatus(i % 2 == 0 ? "En attente" : "En cours");
                demande.setEtq("ETQ-" + (10000 + i));
                demande.setSn("SN-" + (20000 + i));
                demande.setOf_demande("OF-" + (30000 + i));
                demande.setUrgent(i % 3 == 0);
                demande.setStartClicked(i % 2 == 0);
                demande.setFinishClicked(false);
                demande.setResultatClicked(false);
                demande.setHidden(false);

                demandeService.saveDemande(demande);
                logger.info("Created demande: {} for ilot {}", demande.getOf_demande(), demande.getIlot().getName());
            }

            logger.info("Default demandes initialization completed.");
        };
    }

    @Bean
    public CommandLineRunner initDemandesDelegue(
            @Autowired DemandeDelegueService demandeDelegueService,
            @Autowired App_userService userService,
            @Autowired IlotService ilotService,
            @Autowired MetierService metierService,
            @Autowired ProgrammeService programmeService) {
        return args -> {
            logger.info("Initializing default demandes delegue...");

            // Check if demandes delegue already exist
            if (demandeDelegueService.getAllDemandesDelegue().size() > 0) {
                logger.info("Demandes delegue already exist, skipping initialization.");
                return;
            }

            // Get dependencies
            List<App_user> users = userService.getAllUsers();
            List<Ilot> ilots = ilotService.getAllIlots();
            List<Metier> metiers = metierService.getAllMetiers();
            List<Programme> programmes = programmeService.getAllProgrammes();

            if (users.isEmpty() || ilots.isEmpty() || metiers.isEmpty() || programmes.isEmpty()) {
                logger.warn("Missing dependencies, cannot create demandes delegue.");
                return;
            }

            // Format for dates
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime now = LocalDateTime.now();

            // Create demandes delegue
            for (int i = 0; i < 5; i++) {
                DemandeDelegue demande = new DemandeDelegue();
                demande.setControleur(users.get(i % users.size()));
                demande.setIlot(ilots.get(i % ilots.size()));
                demande.setMetier(metiers.get(i % metiers.size()));
                demande.setProgramme(programmes.get(i % programmes.size()));

                // Set dates and times
                LocalDateTime dueDate = now.plusDays(i + 2);
                demande.setDate(dueDate.format(dateFormatter));
                demande.setTime(dueDate.format(timeFormatter));

                // Set other fields
                demande.setStatus(i % 2 == 0 ? "Délégué" : "En attente de délégation");
                demande.setEtq("ETQ-D-" + (10000 + i));
                demande.setSn("SN-D-" + (20000 + i));
                demande.setOf_demande("OF-D-" + (30000 + i));
                demande.setUrgent(i % 3 == 0);
                demande.setStartClicked(i % 2 == 0);
                demande.setFinishClicked(false);
                demande.setResultatClicked(false);
                demande.setHidden(false);
                demande.setNombre_produit_controle(String.valueOf(10 + i * 5));
                demande.setQuantite_controle_metier(String.valueOf(20 + i * 5));
                demande.setQuantite_non_conforme(String.valueOf(i));

                demandeDelegueService.saveDemandeDelegue(demande);
                logger.info("Created demande delegue: {} for ilot {}", demande.getOf_demande(), demande.getIlot().getName());
            }

            logger.info("Default demandes delegue initialization completed.");
        };
    }

    @Bean
    public CommandLineRunner initDemandesFinale(
            @Autowired DemandeFinaleService demandeFinaleService,
            @Autowired IlotService ilotService,
            @Autowired MetierService metierService) {
        return args -> {
            logger.info("Initializing default demandes finale...");

            // Check if demandes finale already exist
            if (demandeFinaleService.getAllDemandesFinale().size() > 0) {
                logger.info("Demandes finale already exist, skipping initialization.");
                return;
            }

            // Get dependencies
            List<Ilot> ilots = ilotService.getAllIlots();
            List<Metier> metiers = metierService.getAllMetiers();

            if (ilots.isEmpty() || metiers.isEmpty()) {
                logger.warn("Missing dependencies, cannot create demandes finale.");
                return;
            }

            // Format for dates
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime now = LocalDateTime.now();

            // Create demandes finale
            for (int i = 0; i < 5; i++) {
                DemandeFinale demande = new DemandeFinale();
                demande.setControleur("Controleur " + (i + 1));
                demande.setIlot(ilots.get(i % ilots.size()));
                demande.setMetier(metiers.get(i % metiers.size()));
                demande.setProgramme("Programme " + (i + 1));

                // Set dates and times
                LocalDateTime dueDate = now.plusDays(i + 3);
                demande.setDate(dueDate.format(dateFormatter));
                demande.setTime(dueDate.format(timeFormatter));

                // Set other fields
                demande.setStatus(i % 2 == 0 ? "Terminé" : "Archivé");
                demande.setEtq("ETQ-F-" + (10000 + i));
                demande.setSn("SN-F-" + (20000 + i));
                demande.setOf_demande("OF-F-" + (30000 + i));
                demande.setUrgent(i % 3 == 0);
                demande.setStartClicked(true);
                demande.setFinishClicked(true);
                demande.setResultatClicked(true);
                demande.setHidden(false);
                demande.setNombre_produit_controle(String.valueOf(50 + i * 10));
                demande.setQuantite_non_conforme(String.valueOf(i * 2));

                demandeFinaleService.saveDemandeFinale(demande);
                logger.info("Created demande finale: {} for ilot {}", demande.getOf_demande(), demande.getIlot().getName());
            }

            logger.info("Default demandes finale initialization completed.");
        };
    }

    @Bean
    public CommandLineRunner initDemandesTe(
            @Autowired DemandeTeService demandeTeService) {
        return args -> {
            logger.info("Initializing default demandes te...");

            // Check if demandes te already exist
            if (demandeTeService.getAllDemandesTe().size() > 0) {
                logger.info("Demandes te already exist, skipping initialization.");
                return;
            }

            // Format for dates
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime now = LocalDateTime.now();

            // Create demandes te
            for (int i = 0; i < 5; i++) {
                DemandeTe demande = new DemandeTe();
                demande.setOperateur("Operateur " + (i + 1));
                demande.setControleur("Controleur " + (i + 1));
                demande.setMachine("Machine " + (i + 1));
                demande.setIlot("Ilot " + (i + 1));
                demande.setProgramme("Programme " + (i + 1));

                // Set dates and times
                LocalDateTime dueDate = now.plusDays(i + 4);
                demande.setDate(dueDate.format(dateFormatter));
                demande.setTime(dueDate.format(timeFormatter));

                // Set other fields
                demande.setStatus(i % 2 == 0 ? "Technique" : "En attente technique");
                demande.setSn("SN-T-" + (20000 + i));
                demande.setUrgent(i % 3 == 0);
                demande.setStartClicked(i % 2 == 0);
                demande.setFinishClicked(false);
                demande.setResultatClicked(false);
                demande.setHidden(false);
                demande.setDureeAttente(String.valueOf(i * 15) + " minutes");

                demandeTeService.saveDemandeTe(demande);
                logger.info("Created demande te for operateur: {}", demande.getOperateur());
            }

            logger.info("Default demandes te initialization completed.");
        };
    }
}
