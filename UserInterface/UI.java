package UserInterface;

import domain.Consommation;
import domain.User;
import service.ConsommationService;
import service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UI {
    private UserService userService;
    private ConsommationService consommationService;
    private Scanner scanner;

    public UI() {
        this.userService = new UserService();
        this.consommationService = new ConsommationService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Gérer les utilisateurs");
            System.out.println("2. Gérer les consommations");
            System.out.println("3. Afficher les rapports");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    gererUtilisateurs();
                    break;
                case 2:
                    gererConsommations();
                    break;
                case 3:
                    afficherRapports();
                    break;
                case 4:
                    running = false;
                    System.out.println("Au revoir!");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void gererUtilisateurs() {
        System.out.println("\n--- Gestion des Utilisateurs ---");
        System.out.println("1. Créer un utilisateur");
        System.out.println("2. Afficher tous les utilisateurs");
        System.out.println("3. Mettre à jour un utilisateur");
        System.out.println("4. Supprimer un utilisateur");
        System.out.print("Choisissez une option : ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                creerUtilisateur();
                break;
            case 2:
                afficherUtilisateurs();
                break;
            case 3:
                mettreAJourUtilisateur();
                break;
            case 4:
                supprimerUtilisateur();
                break;
            default:
                System.out.println("Option invalide.");
        }
    }

    private void creerUtilisateur() {
        System.out.print("Nom de l'utilisateur : ");
        String nom = scanner.nextLine();
        System.out.print("Age de l'utilisateur : ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        User user = userService.createUser(nom, age);
        System.out.println("Utilisateur créé avec l'ID : " + user.getId());
    }

    private void afficherUtilisateurs() {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Nom: " + user.getNom() + ", Age: " + user.getAge());
        }
    }

    private void mettreAJourUtilisateur() {
        System.out.print("ID de l'utilisateur à mettre à jour : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        userService.getUserById(id).ifPresentOrElse(user -> {
            System.out.print("Nouveau nom (laisser vide pour ne pas changer) : ");
            String newNom = scanner.nextLine();
            if (!newNom.isEmpty()) {
                user.setNom(newNom);
            }

            System.out.print("Nouvel âge (0 pour ne pas changer) : ");
            int newAge = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (newAge != 0) {
                user.setAge(newAge);
            }

            userService.updateUser(user);
            System.out.println("Utilisateur mis à jour.");
        }, () -> System.out.println("Utilisateur non trouvé."));
    }

    private void supprimerUtilisateur() {
        System.out.print("ID de l'utilisateur à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        userService.deleteUser(id);
        System.out.println("Utilisateur supprimé (si existant).");
    }

    private void gererConsommations() {
        System.out.println("\n--- Gestion des Consommations ---");
        System.out.println("1. Ajouter une consommation");
        System.out.println("2. Afficher les consommations d'un utilisateur");
        System.out.print("Choisissez une option : ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                ajouterConsommation();
                break;
            case 2:
                afficherConsommationsUtilisateur();
                break;
            default:
                System.out.println("Option invalide.");
        }
    }

    private void ajouterConsommation() {
        System.out.print("ID de l'utilisateur : ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Type de consommation (TRANSPORT, LOGEMENT, ALIMENTATION) : ");
        String type = scanner.nextLine().toUpperCase();

        System.out.print("Date (YYYY-MM-DD) : ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);

        switch (type) {
            case "TRANSPORT":
                System.out.print("Distance parcourue : ");
                double distance = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Type de véhicule : ");
                String vehicule = scanner.nextLine();
                consommationService.addTransport(userId, date, distance, vehicule);
                break;
            case "LOGEMENT":
                System.out.print("Consommation d'énergie : ");
                double energie = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Type d'énergie : ");
                String typeEnergie = scanner.nextLine();
                consommationService.addLogement(userId, date, energie, typeEnergie);
                break;
            case "ALIMENTATION":
                System.out.print("Type d'aliment : ");
                String aliment = scanner.nextLine();
                System.out.print("Quantité : ");
                double quantite = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                consommationService.addAlimentation(userId, date, aliment, quantite);
                break;
            default:
                System.out.println("Type de consommation invalide.");
                return;
        }
        System.out.println("Consommation ajoutée avec succès.");
    }

    private void afficherConsommationsUtilisateur() {
        System.out.print("ID de l'utilisateur : ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<Consommation> consommations = consommationService.getUserConsommations(userId);
        for (Consommation c : consommations) {
            System.out.println("Date: " + c.getDate() + ", Type: " + c.getClass().getSimpleName() + ", Impact: " + c.calculerImpact());
        }
    }

    private void afficherRapports() {
        System.out.println("\n--- Rapports ---");
        System.out.println("1. Impact total d'un utilisateur");
        System.out.println("2. Impact sur une période");
        System.out.print("Choisissez une option : ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("ID de l'utilisateur : ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                double totalImpact = consommationService.calculateTotalImpact(userId);
                System.out.println("Impact total : " + totalImpact + " KgCO2eq");
                break;
            case 2:
                System.out.print("Date de début (YYYY-MM-DD) : ");
                LocalDate start = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
                System.out.print("Date de fin (YYYY-MM-DD) : ");
                LocalDate end = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
                double impactPeriode = consommationService.calculateImpactByDateRange(userId, start, end);
                System.out.println("Impact sur la période : " + impactPeriode + " KgCO2eq");
                break;
            default:
                System.out.println("Option invalide.");
        }
    }
}