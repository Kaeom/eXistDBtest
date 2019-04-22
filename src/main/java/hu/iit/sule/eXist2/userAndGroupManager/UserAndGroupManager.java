package hu.iit.sule.eXist2.userAndGroupManager;
import hu.iit.sule.eXist2.util.Util;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;

public class UserAndGroupManager {

    private static final String DB = "/db/";//root collection
    private static Util util = new Util();

    private String uri = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private Collection collection = null;
    private String adminUser = "admin";
    private String adminPass = "admin";

    public UserAndGroupManager(String rui,String adminUser, String adminPass){
        //util.initDatabaseDriver("org.exist.xmldb.DatabaseImpl");
        this.uri = rui;
        this.adminUser = adminUser;
        this.adminPass = adminPass;
    }

    public boolean createUser(String user, String pass, String group) throws Exception {
        Collection old = collection;//save previos context
        util.closeCollection(collection);
        boolean result = false;
        try {
            collection = DatabaseManager.getCollection(uri + DB);
            if (userExists(user))//first: is user already existing?
                return true;
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "\n" +
                    "if(xmldb:login(\"/db\",\"" + adminUser + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:create-account(\"" + user + "\", \"" + pass + "\", \"" + group + "\", \"" + "" + "\")\n" +
                    "else\n" +
                    "\tfalse()";
            result = !util.execXQuery(query,collection).equals("false");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            util.closeCollection(collection);//'logout'
            collection = old;
        }
        return result;
    }

    public boolean deleteUser(String deletedUser) throws Exception {
        Collection old = collection;
        util.closeCollection(collection);
        boolean userIsDeleted = false;
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminUser, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminUser + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:remove-account(\"" + deletedUser + "\")\n" +
                    "else\n" +
                    "\tfalse()";
            System.out.println(util.execXQuery(query,collection));
            userIsDeleted = true;
        } catch (XMLDBException e) {
            System.out.println("User delete Exception: " + e.getMessage());
        } finally {
            util.closeCollection(collection);
            collection = old;
        }

        return userIsDeleted;

    }

    public void listUsers() throws Exception {
        Collection old = collection;
        util.closeCollection(collection);
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminUser, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminUser + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:list-users()\n" +
                    "else\n" +
                    "\tfalse()";
            System.out.println(util.execXQuery(query, collection));
        } catch (Exception e) {
            System.out.println("User list exception: " + e.getMessage());
        } finally {
            util.closeCollection(collection);
            collection = old;
        }
    }

    private boolean userExists(String user) {
        boolean result = false;
        try {
            String query = "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\"; \n" +
                    "sm:user-exists(\"" + user + "\")";
            result = util.execXQuery(query,collection).equals("true");

        } catch (Exception ignored) {

        }
        return result;
    }



    public boolean createGroup(String group, String group_desc) throws Exception {
        Collection old = collection;
        util.closeCollection(collection);
        boolean result = false;
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminUser, adminPass);
            if (groupExists(group))//first: is group already existing?
                return true;
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "\n" +
                    "if(xmldb:login(\"/db\",\"" + adminUser + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:create-group(\"" + group + "\",\"" + group_desc + "\")\n" +
                    "else\n" +
                    "\tfalse()";
            result = !util.execXQuery(query,collection).equals("false");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            util.closeCollection(collection);//'logout'
            collection = old;
        }
        return result;
    }

    public boolean deleteGroup(String deletedGroup) throws Exception {
        Collection old = collection;
        util.closeCollection(collection);
        boolean groupIsDeleted = false;
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminUser, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminUser + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:remove-group(\"" + deletedGroup + "\")\n" +
                    "else\n" +
                    "\tfalse()\n";
            System.out.println(util.execXQuery(query,collection));
            groupIsDeleted = true;
        } catch (XMLDBException e) {
            System.out.println("Group delete Exception: " + e.getMessage());
        } finally {
            util.closeCollection(collection);
            collection = old;
        }
        return groupIsDeleted;

    }

    public void listGroup() throws Exception {
        Collection old = collection;
        util.closeCollection(collection);
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminUser, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminUser + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:list-groups()\n" +
                    "else\n" +
                    "\tfalse()";
            System.out.println(util.execXQuery(query,collection));
        } catch (Exception e) {
            System.out.println("Group list exception: " + e.getMessage());
        } finally {
            util.closeCollection(collection);
            collection = old;
        }
    }

    private boolean groupExists(String group) {
        boolean result = false;
        try {
            String query = "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "sm:group-exists(\"" + group + "\")";
            result = util.execXQuery(query,collection).equals("true");
        } catch (Exception e) {

        }
        return result;
    }
}
