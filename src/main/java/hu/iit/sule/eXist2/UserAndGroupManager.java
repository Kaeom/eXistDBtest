package hu.iit.sule.eXist2;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;

public class UserAndGroupManager {

    private static String DB = "/db/";//root collection
    private static String uri = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private static Collection collection = null;

    public UserAndGroupManager(String db, String rui){
        this.DB = db;
        this.uri = rui;
        Util.initDatabaseDriver();
    }

    public boolean createUser(String adminName, String adminPass, String user, String pass, String group) throws Exception {
        Collection old = collection;//save previos context
        Util.closeCollection(collection);
        boolean result = false;
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminName, adminPass);
            if (userExists(user))//first: is user already existing?
                return true;
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "\n" +
                    "if(xmldb:login(\"/db\",\"" + adminName + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:create-account(\"" + user + "\", \"" + pass + "\", \"" + group + "\", \"" + "" + "\")\n" +
                    "else\n" +
                    "\tfalse()";
            result = !Util.execXQuery(query,collection).equals("false");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.closeCollection(collection);//'logout'
            collection = old;
        }
        return result;
    }

    public boolean deleteUser(String adminName, String adminPass, String deletedUser) throws Exception {
        Collection old = collection;
        Util.closeCollection(collection);
        boolean userIsDeleted = false;
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminName, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminName + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:remove-account(\"" + deletedUser + "\")\n" +
                    "else\n" +
                    "\tfalse()";
            System.out.println(Util.execXQuery(query,collection));
            userIsDeleted = true;
        } catch (XMLDBException e) {
            System.out.println("User delete Exception: " + e.getMessage());
        } finally {
            Util.closeCollection(collection);
            collection = old;
        }

        return userIsDeleted;

    }

    public void listUsers(String adminName, String adminPass) throws Exception {
        Collection old = collection;
        Util.closeCollection(collection);
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminName, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminName + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:list-users()\n" +
                    "else\n" +
                    "\tfalse()";
            System.out.println(Util.execXQuery(query, collection));
        } catch (Exception e) {
            System.out.println("User list exception: " + e.getMessage());
        } finally {
            Util.closeCollection(collection);
            collection = old;
        }
    }

    private boolean userExists(String user) {
        boolean result = false;
        try {
            String query = "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\"; \n" +
                    "sm:user-exists(\"" + user + "\")";
            result = Util.execXQuery(query,collection).equals("true");

        } catch (Exception ignored) {

        }
        return result;
    }



    public boolean createGroup(String adminName, String adminPass, String group, String group_desc) throws Exception {
        Collection old = collection;
        Util.closeCollection(collection);
        boolean result = false;
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminName, adminPass);
            if (groupExists(group))//first: is group already existing?
                return true;
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "\n" +
                    "if(xmldb:login(\"/db\",\"" + adminName + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:create-group(\"" + group + "\",\"" + group_desc + "\")\n" +
                    "else\n" +
                    "\tfalse()";
            result = !Util.execXQuery(query,collection).equals("false");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.closeCollection(collection);//'logout'
            collection = old;
        }
        return result;
    }

    public boolean deleteGroup(String adminName, String adminPass, String deletedGroup) throws Exception {
        Collection old = collection;
        Util.closeCollection(collection);
        boolean groupIsDeleted = false;
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminName, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminName + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:remove-group(\"" + deletedGroup + "\")\n" +
                    "else\n" +
                    "\tfalse()\n";
            System.out.println(Util.execXQuery(query,collection));
            groupIsDeleted = true;
        } catch (XMLDBException e) {
            System.out.println("Group delete Exception: " + e.getMessage());
        } finally {
            Util.closeCollection(collection);
            collection = old;
        }
        return groupIsDeleted;

    }

    public void listGroup(String adminName, String adminPass) throws Exception {
        Collection old = collection;
        Util.closeCollection(collection);
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminName, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminName + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:list-groups()\n" +
                    "else\n" +
                    "\tfalse()";
            System.out.println(Util.execXQuery(query,collection));
        } catch (Exception e) {
            System.out.println("Group list exception: " + e.getMessage());
        } finally {
            Util.closeCollection(collection);
            collection = old;
        }
    }

    private boolean groupExists(String group) {
        boolean result = false;
        try {
            String query = "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "sm:group-exists(\"" + group + "\")";
            result = Util.execXQuery(query,collection).equals("true");
        } catch (Exception e) {

        }
        return result;
    }



}
