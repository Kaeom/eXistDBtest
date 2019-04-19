package hu.iit.sule.eXist_old;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;

public class Exist {

    private static Database database;
    private static final String driver = "org.exist.xmldb.DatabaseImpl";
    private static String databaseURL = "";

    public void initDatabase() {

        try {
            Class c1 = Class.forName(driver);
            database = (Database) c1.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);
        } catch (ClassNotFoundException | XMLDBException | IllegalAccessException | InstantiationException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public ResourceSet executeXQueryAsADmin(String url, String collection, String xQuery) {
        Collection col = null;
        ResourceSet result = null;
        String DBAUser = "admin";
        String DBAPass = "admin";
        try {
            col = DatabaseManager.getCollection(url + collection, DBAUser, DBAPass);
            XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
            xqs.setProperty("indent", "yes");

            CompiledExpression compiled = xqs.compile(xQuery);
            result = xqs.execute(compiled);

        } catch (XMLDBException e) {
            e.getMessage();
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }

        return result;
    }

    public void printResourceIterator(ResourceSet result) {
        Resource res = null;
        try {
            ResourceIterator ri = result.getIterator();
            while (ri.hasMoreResources()) {
                res = ri.nextResource();
                System.out.println(res.getContent());
            }
        } catch (XMLDBException e) {
            e.getMessage();
        } finally {
            try {
                assert res != null;
                ((EXistResource) res).freeResources();
            } catch (XMLDBException xe) {
                xe.printStackTrace();
            }
        }
    }

    public static String getDatabaseURL() {
        return databaseURL;
    }

    public static void setDatabaseURL(String databaseURL) {
        Exist.databaseURL = databaseURL;
    }
}
