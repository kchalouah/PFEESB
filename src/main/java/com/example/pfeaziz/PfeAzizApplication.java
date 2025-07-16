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
import java.util.*;

@SpringBootApplication
public class PfeAzizApplication {

    private static final Logger logger = LoggerFactory.getLogger(PfeAzizApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PfeAzizApplication.class, args);
    }

    /*
    @Bean
    public CommandLineRunner initRoles(@Autowired User_RoleService roleService) {
        return args -> {
            logger.info("Initializing default roles...");

            String[] defaultRoles = {"ROLE_ADMIN", "ROLE_USER", "ROLE_MANAGER", "ROLE_CONTROLEUR", "ROLE_OPERATEUR"};

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
    */

    /*
    @Bean
    public CommandLineRunner initUsers(
            @Autowired App_userService userService,
            @Autowired User_RoleService roleService,
            @Autowired PasswordEncoder passwordEncoder) {
        return args -> {
            logger.info("Initializing default users...");

            if (userService.getAllUsers().size() > 0) {
                logger.info("Users already exist, skipping initialization.");
                return;
            }

            User_Role adminRole = roleService.getRoleByName("ROLE_ADMIN");
            User_Role managerRole = roleService.getRoleByName("ROLE_MANAGER");
            User_Role controleurRole = roleService.getRoleByName("ROLE_CONTROLEUR");
            User_Role operateurRole = roleService.getRoleByName("ROLE_OPERATEUR");
            User_Role userRole = roleService.getRoleByName("ROLE_USER");

            if (adminRole != null) {
                App_user admin = new App_user();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(Collections.singletonList(adminRole));
                userService.saveUser(admin);
                logger.info("Created admin user");
            }

            if (managerRole != null) {
                App_user manager = new App_user();
                manager.setUsername("manager");
                manager.setPassword(passwordEncoder.encode("manager123"));
                manager.setRoles(Collections.singletonList(managerRole));
                userService.saveUser(manager);
                logger.info("Created manager user");
            }

            if (controleurRole != null) {
                App_user controleur = new App_user();
                controleur.setUsername("controleur");
                controleur.setPassword(passwordEncoder.encode("controleur123"));
                controleur.setRoles(Collections.singletonList(controleurRole));
                userService.saveUser(controleur);
                logger.info("Created controleur user");
            }

            if (operateurRole != null) {
                App_user operateur = new App_user();
                operateur.setUsername("operateur");
                operateur.setPassword(passwordEncoder.encode("operateur123"));
                operateur.setRoles(Collections.singletonList(operateurRole));
                userService.saveUser(operateur);
                logger.info("Created operateur user");
            }

            if (userRole != null) {
                App_user user = new App_user();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRoles(Collections.singletonList(userRole));
                userService.saveUser(user);
                logger.info("Created regular user");
            }

            logger.info("Default users initialization completed.");
        };
    }
    */

    /*
    @Bean
    public CommandLineRunner initIlots(@Autowired IlotService ilotService) {
        return args -> {
            logger.info("Initializing default ilots...");
            // ...
        };
    }
    */

    /*
    @Bean
    public CommandLineRunner initMetiers(@Autowired MetierService metierService) {
        return args -> {
            logger.info("Initializing default metiers...");
            // ...
        };
    }
    */

    /*
    @Bean
    public CommandLineRunner initMachines(
            @Autowired MachineService machineService,
            @Autowired IlotService ilotService) {
        return args -> {
            logger.info("Initializing default machines...");
            // ...
        };
    }
    */

    /*
    @Bean
    public CommandLineRunner initProgrammes(
            @Autowired ProgrammeService programmeService,
            @Autowired MachineService machineService) {
        return args -> {
            logger.info("Initializing default programmes...");
            // ...
        };
    }
    */

    /*
    @Bean
    public CommandLineRunner initDemandes(
            @Autowired DemandeService demandeService,
            @Autowired App_userService userService,
            @Autowired IlotService ilotService,
            @Autowired ProgrammeService programmeService) {
        return args -> {
            logger.info("Initializing default demandes...");
            // ...
        };
    }
    */

    /*
    @Bean
    public CommandLineRunner initDemandesDelegue(
            @Autowired DemandeDelegueService demandeDelegueService,
            @Autowired App_userService userService,
            @Autowired IlotService ilotService,
            @Autowired ProgrammeService programmeService) {
        return args -> {
            logger.info("Initializing default demandes delegue...");
            // ...
        };
    }
    */

    /*
    @Bean
    public CommandLineRunner initDemandesFinale(
            @Autowired DemandeFinaleService demandeFinaleService,
            @Autowired IlotService ilotService) {
        return args -> {
            logger.info("Initializing default demandes finale...");
            // ...
        };
    }
    */

    /*
    @Bean
    public CommandLineRunner initDemandesTe(
            @Autowired DemandeTeService demandeTeService) {
        return args -> {
            logger.info("Initializing default demandes te...");
            // ...
        };
    }
    */
}
