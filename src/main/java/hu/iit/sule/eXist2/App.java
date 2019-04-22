package hu.iit.sule.eXist2;

import hu.iit.sule.eXist2.history.ResourceVersionManager;
import hu.iit.sule.eXist2.history.model.eXistVersionModel;
import hu.iit.sule.eXist2.mailSenders.eXistMailSender;
import hu.iit.sule.eXist2.model.Mail;
import hu.iit.sule.eXist2.userAndGroupManager.UserAndGroupManager;
import hu.iit.sule.eXist2.util.Util;

import java.util.ArrayList;
import java.util.Scanner;

public class App {

    private static String uri ="xmldb:exist://localhost:8080/exist/xmlrpc";
    private static String adminUser = "admin";
    private static String adminPass = "admin";

    private static ResourceVersionManager resourceVersionManager = new ResourceVersionManager("xmldb:exist://localhost:8080/exist/xmlrpc","admin","admin");

    private static UserAndGroupManager userAndGroupManager = new UserAndGroupManager(uri,adminUser, adminPass);
    private static hu.iit.sule.eXist2.mailSenders.eXistMailSender eXistMailSender = new eXistMailSender(uri);
    private static Scanner sc = new Scanner(System.in);
    private static int chose;


    public static void main(String args[]) throws Exception {

        Util util = new Util();
        util.initDatabaseDriver("org.exist.xmldb.DatabaseImpl");

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
            System.out.println("7. Send Mail eXsit");
            System.out.println("8. Test History");
            System.out.println("9. Test version query");

            chose = Integer.parseInt(sc.nextLine());

            switch (chose) {
                case 1: {
                    userAndGroupManager.createUser("UserFromJava", "UserFromJava", "admin");
                    break;
                }
                case 2: {
                    userAndGroupManager.deleteUser("UserFromJava");
                    break;
                }
                case 3: {
                    userAndGroupManager.listUsers();
                    break;
                }
                case 4: {
                    userAndGroupManager.createGroup("JavaGroup", "JavaGroupDesc");
                    break;
                }
                case 5: {
                    userAndGroupManager.deleteGroup("JavaGroup");
                    break;
                }
                case 6: {
                    userAndGroupManager.listGroup();
                    break;
                }
                case 7: {
                    eXistMailSender.sendMail(adminUser, adminPass,mail);
                    break;
                }
                case 8: {
                    testHistory();
                    break;
                }
                case 9: {
                    System.out.println("Version number: ");
                    int version = Integer.parseInt(sc.nextLine());
                    resourceVersionManager.getChanges("/db/history_test/videos.xml",version);
                    break;
                }
            }
        }while(chose == 0);
    }


    private static void testHistory() throws Exception {

        ArrayList<eXistVersionModel> evmList = resourceVersionManager.getResourceHistory("/db/history_test/videos.xml");
        //print evmList
        for (eXistVersionModel e: evmList) {
            System.out.println("" + e.getRev() + " " + e.getDate() + " " + e.getUser());
        }
    }

}
