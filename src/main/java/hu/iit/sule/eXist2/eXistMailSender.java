package hu.iit.sule.eXist2;

import hu.iit.sule.eXist2.model.Mail;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;

import javax.xml.crypto.Data;

public class eXistMailSender {

    private static String DB = "/db/";//root collection
    private static String uri = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private static Collection collection = null;

    public eXistMailSender(String db, String rui){
        this.DB = db;
        this.uri = rui;
        Util.initDatabaseDriver();
    }

    public boolean sendMail(String adminName, String adminPass, Mail mail) throws Exception {
        Collection old = collection;
        Util.closeCollection(collection);
        boolean result = false;
        try{
            collection = DatabaseManager.getCollection(uri + DB, adminName, adminPass);
            String query = "xquery version \"3.0\";\n" +
                    "declare variable $message {\n" +
                    "  <mail>\n" +
                    "    <from>"+ mail.getSenderName() +" &lt;"+ mail.getSenderMail() +"&gt;</from>\n" +
                    "    <to>" + mail.getReciaverMail() + "</to>\n" +
                    "    <cc>" + mail.getCcMail() + "</cc>\n" +
                    "    <bcc>" + mail.getBccMail() + "</bcc>\n" +
                    "    <subject>" + mail.getMailSubject() + "</subject>\n" +
                    "    <message>\n" +
                    "      <text>" + mail.getMailText() + "</text>\n" +
                    "      </message>\n" +
                    "  </mail>\n" +
                    "};\n" +
                    "if ( mail:send-email($message, 'localhost', ()) ) then\n" +
                    "  true()\n" +
                    "else\n" +
                    "  false()";

            result = Util.execXQuery(query,collection).equals("true");
        }catch (Exception e){
            System.out.println("eXistMailSender exception: " + e.getMessage());
        }finally {
            Util.closeCollection(collection);
            collection = old;
        }
        return result;
    }


}
