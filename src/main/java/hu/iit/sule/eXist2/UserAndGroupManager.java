package hu.iit.sule.eXist2;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.transform.OutputKeys;

public class UserAndGroupManager {

    private static final String DB = "/db/";//root collection
    private static String uri = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private static Collection collection = null;

    public UserAndGroupManager(){
        initDatabaseDriver();
    }

    public boolean createUser(String adminName, String adminPass, String user, String pass, String group) throws Exception {
        Collection old = collection;//save previos context
        closeCollection();
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
            result = !execXQuery(query).equals("false");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCollection();//'logout'
            collection = old;
        }
        return result;
    }

    public boolean deleteUser(String adminName, String adminPass, String deletedUser) throws Exception {
        Collection old = collection;
        closeCollection();
        boolean userIsDeleted = false;
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminName, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminName + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:remove-account(\"" + deletedUser + "\")\n" +
                    "else\n" +
                    "\tfalse()";
            System.out.println(execXQuery(query));
            userIsDeleted = true;
        } catch (XMLDBException e) {
            System.out.println("User delete Exception: " + e.getMessage());
        } finally {
            closeCollection();
            collection = old;
        }

        return userIsDeleted;

    }

    public void listUsers(String adminName, String adminPass) throws Exception {
        Collection old = collection;
        closeCollection();
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminName, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminName + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:list-users()\n" +
                    "else\n" +
                    "\tfalse()";
            System.out.println(execXQuery(query));
        } catch (Exception e) {
            System.out.println("User list exception: " + e.getMessage());
        } finally {
            closeCollection();
            collection = old;
        }
    }

    private boolean userExists(String user) {
        boolean result = false;
        try {
            String query = "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\"; \n" +
                    "sm:user-exists(\"" + user + "\")";
            result = execXQuery(query).equals("true");

        } catch (Exception ignored) {

        }
        return result;
    }



    public boolean createGroup(String adminName, String adminPass, String group, String group_desc) throws Exception {
        Collection old = collection;
        closeCollection();
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
            result = !execXQuery(query).equals("false");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeCollection();//'logout'
            collection = old;
        }
        return result;
    }

    public boolean deleteGroup(String adminName, String adminPass, String deletedGroup) throws Exception {
        Collection old = collection;
        closeCollection();
        boolean groupIsDeleted = false;
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminName, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminName + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:remove-group(\"" + deletedGroup + "\")\n" +
                    "else\n" +
                    "\tfalse()\n";
            System.out.println(execXQuery(query));
            groupIsDeleted = true;
        } catch (XMLDBException e) {
            System.out.println("Group delete Exception: " + e.getMessage());
        } finally {
            closeCollection();
            collection = old;
        }
        return groupIsDeleted;

    }

    public void listGroup(String adminName, String adminPass) throws Exception {
        Collection old = collection;
        closeCollection();
        try {
            collection = DatabaseManager.getCollection(uri + DB, adminName, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "if(xmldb:login(\"/db\",\"" + adminName + "\", \"" + adminPass + "\" ,false())) then\n" +
                    "    sm:list-groups()\n" +
                    "else\n" +
                    "\tfalse()";
            System.out.println(execXQuery(query));
        } catch (Exception e) {
            System.out.println("Group list exception: " + e.getMessage());
        } finally {
            closeCollection();
            collection = old;
        }
    }

    private boolean groupExists(String group) {
        boolean result = false;
        try {
            String query = "import module namespace sm=\"http://exist-db.org/xquery/securitymanager\";\n" +
                    "sm:group-exists(\"" + group + "\")";
            result = execXQuery(query).equals("true");
        } catch (Exception e) {

        }
        return result;
    }


    private void initDatabaseDriver(){
        try{
            Class aClass = Class.forName("org.exist.xmldb.DatabaseImpl");
            //System.out.println("aClass.getName() = " + aClass.getName());
            Database database = (Database) aClass.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);
        }catch (Exception e){
            System.out.println("Database Initialization exception: " + e.getMessage());
        }

    }

    private void closeCollection() throws Exception {
        if (collection != null)
            collection.close();
    }


    private String execXQuery(String query) throws Exception {
        XQueryService service = (XQueryService) collection.getService("XQueryService", "1.0");
        service.setProperty(OutputKeys.INDENT, "yes");
        service.setProperty(OutputKeys.ENCODING, "UTF-8");
        CompiledExpression compiled = service.compile(query);
        ResourceSet result = service.execute(compiled);//service.query(res,query);//since the queries will be simple, compilation should not bee needed
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (int) result.getSize(); i++) {
            XMLResource r = (XMLResource) result.getResource((long) i);
            sb.append(r.getContent().toString()).append("\n");
        }
        return sb.toString().trim();
    }



}
