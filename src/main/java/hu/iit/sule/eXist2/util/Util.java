package hu.iit.sule.eXist2.util;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.transform.OutputKeys;

public class Util {

    public void initDatabaseDriver(String driver){
        try{
            Class aClass = Class.forName(driver);
            //System.out.println("aClass.getName() = " + aClass.getName());
            Database database = (Database) aClass.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);
        }catch (Exception e){
            System.out.println("Database Initialization exception: " + e.getMessage());
        }
    }

    public void closeCollection(Collection collection) throws Exception {
        if (collection != null)
            collection.close();
    }

    public String execXQuery(String query, Collection collection) throws Exception {
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
