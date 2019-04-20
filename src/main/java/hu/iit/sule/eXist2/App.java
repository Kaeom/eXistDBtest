package hu.iit.sule.eXist2;

import hu.iit.sule.eXist2.model.Mail;

import java.util.Scanner;

public class App {

    private static String DB ="/db/"; //root collection
    private static String uri ="xmldb:exist://localhost:8080/exist/xmlrpc";

    private static UserAndGroupManager userAndGroupManager = new UserAndGroupManager(DB,uri);
    private static eXistMailSender eXistMailSender = new eXistMailSender(DB,uri);
    private static String adminUser = "admin";
    private static String adminPass = "admin";
    private static Scanner sc = new Scanner(System.in);
    private static int chose;


    public static void main(String args[]) throws Exception {

        Mail mail = new Mail();
        mail.setSenderName("Sender Name");
        mail.setSenderMail("sender@email.com");
        mail.setReciaverMail("reciever@mail.com");
        mail.setCcMail("cc@mail.com");
        mail.setBccMail("bcc@mail.com");
        mail.setMailSubject("subject");
        mail.setMailText("text");


        do {
            System.out.println("Válasszon műveletet:");
            System.out.println("1. Create User");
            System.out.println("2. Delete User");
            System.out.println("3. List Users");
            System.out.println("4. Create Group");
            System.out.println("5. Delete Group");
            System.out.println("6. List Group");
            System.out.println("7. Send Mail eXsi00t");

            chose = Integer.parseInt(sc.nextLine());

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
                case 7: {
                    eXistMailSender.sendMail(adminUser, adminPass,mail);
                }
            }
        }while(chose == 0);
    }

}
