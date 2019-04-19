package hu.iit.sule.eXist2;

import java.util.Scanner;

public class App {

    private static UserAndGroupManager userAndGroupManager = new UserAndGroupManager();
    private static String adminUser = "admin";
    private static String adminPass = "admin";
    private static Scanner sc = new Scanner(System.in);
    private static int chose;


    public static void main(String args[]) throws Exception {

        do {
            System.out.println("Válasszon műveletet:");
            System.out.println("1. Create User");
            System.out.println("2. Delete User");
            System.out.println("3. List Users");
            System.out.println("4. Create Group");
            System.out.println("5. Delete Group");
            System.out.println("6. List Group");

            int chose = Integer.parseInt(sc.nextLine());

            switch (chose) {
                case 1: {
                    userAndGroupManager.createUser(adminUser, adminPass, "UserFromJava", "UserFromJava", "admin");
                }
                case 2: {
                    userAndGroupManager.deleteUser(adminUser, adminPass, "UserFromJava");
                }
                case 3: {
                    userAndGroupManager.listUsers(adminUser, adminPass);
                }
                case 4: {
                    userAndGroupManager.createGroup(adminUser, adminPass, "JavaGroup", "JavaGroupDesc");
                }
                case 5: {
                    userAndGroupManager.deleteGroup(adminUser, adminPass, "JavaGroup");
                }
                case 6: {
                    userAndGroupManager.listGroup(adminUser, adminPass);
                }
            }
        }while(chose == 0);
    }

}
