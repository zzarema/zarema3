import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");

            System.out.println("Choose");
            System.out.println("1. Add order");
            System.out.println("2. Update price");
            System.out.println("3. Delete order");
            System.out.println("Your choice:");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addRecord(connection, scanner);
                    break;
                case 2:
                    updateRecord(connection, scanner);
                    break;
                case 3:
                    deleteRecord(connection, scanner);
                    break;
                default:
                    System.out.println("Incorrect choice");
            }

            connection.close();
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addRecord(Connection connection, Scanner scanner) throws SQLException {

        System.out.println("Enter your name:");
        String name = scanner.nextLine();

        System.out.println("Enter your surname:");
        String surname = scanner.nextLine();

        System.out.println("Enter your email:");
        String email = scanner.nextLine();

        System.out.println("Enter fruit name:");
        String fruitName = scanner.nextLine();

        System.out.println("Price:");
        double price = Double.parseDouble(scanner.nextLine());

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO fruitshop (name, surname, email, fruitname, price) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, fruitName);
        preparedStatement.setDouble(5, price);

        preparedStatement.executeUpdate();

        System.out.println("Succesfully ordered!");


        preparedStatement.close();
    }

    private static void updateRecord(Connection connection, Scanner scanner) throws SQLException {

        System.out.println("Enter name of fruit to update it's price:");
        String fruitToUpdate = scanner.nextLine();

        System.out.println("New price:");
        double newPrice = Double.parseDouble(scanner.nextLine());


        PreparedStatement updateStatement = connection.prepareStatement("UPDATE fruitshop SET price = ? WHERE fruitname = ?");
        updateStatement.setDouble(1, newPrice);
        updateStatement.setString(2, fruitToUpdate);
        updateStatement.executeUpdate();

        System.out.println("Succesfully updated!");

        updateStatement.close();
    }


    private static void deleteRecord(Connection connection, Scanner scanner) throws SQLException {

        System.out.println("Enter surname to delete order:");
        String surnameToDelete = scanner.nextLine();

        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM fruitshop WHERE surname = ?");
        deleteStatement.setString(1, surnameToDelete);
        deleteStatement.executeUpdate();

        System.out.println("Succesfully deleted!");

        deleteStatement.close();
    }
}
