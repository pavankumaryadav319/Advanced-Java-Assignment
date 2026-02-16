package org.example.entity;

import org.example.entity.MenuItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class RestaurentManagementSystem {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(MenuItem.class)
                .buildSessionFactory();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Restaurant Menu System ---");
            System.out.println("1. Add Menu Item");
            System.out.println("2. View All Items");
            System.out.println("3. Update Price");
            System.out.println("4. Delete Item");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            Session session = factory.openSession();
            Transaction tx = null;

            try {
                switch (choice) {

                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Price: ");
                        double price = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Enter Category: ");
                        String category = sc.nextLine();

                        System.out.print("Is Available (true/false): ");
                        boolean available = sc.nextBoolean();

                        tx = session.beginTransaction();
                        MenuItem item = new MenuItem(name, price, category, available);
                        session.persist(item);
                        tx.commit();
                        System.out.println("Item Added!");
                        break;

                    case 2:
                        List<MenuItem> list = session.createQuery("FROM MenuItem", MenuItem.class).list();
                        list.forEach(System.out::println);
                        break;

                    case 3:
                        System.out.print("Enter ID to Update: ");
                        int updateId = sc.nextInt();
                        System.out.print("Enter New Price: ");
                        double newPrice = sc.nextDouble();

                        tx = session.beginTransaction();
                        MenuItem updateItem = session.get(MenuItem.class, updateId);
                        if (updateItem != null) {
                            updateItem.setPrice(newPrice);
                            tx.commit();
                            System.out.println("Price Updated!");
                        } else {
                            System.out.println("Item Not Found!");
                            tx.rollback();
                        }
                        break;

                    case 4:
                        System.out.print("Enter ID to Delete: ");
                        int deleteId = sc.nextInt();

                        tx = session.beginTransaction();
                        MenuItem deleteItem = session.get(MenuItem.class, deleteId);
                        if (deleteItem != null) {
                            session.remove(deleteItem);
                            tx.commit();
                            System.out.println("Item Deleted!");
                        } else {
                            System.out.println("Item Not Found!");
                            tx.rollback();
                        }
                        break;

                    case 5:
                        session.close();
                        factory.close();
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid Choice!");
                }

            } catch (Exception e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
    }
}
