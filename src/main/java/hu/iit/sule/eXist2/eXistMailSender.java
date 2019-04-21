package hu.iit.sule.eXist2;

import hu.iit.sule.eXist2.model.Mail;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;

import javax.xml.crypto.Data;

public class eXistMailSender {

    private static final String DB = "/db/";//root collection
    private String uri = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private static Collection collection = null;
    private static Util util = new Util();

    public eXistMailSender(String rui){
        this.uri = rui;
        //util.initDatabaseDriver("org.exist.xmldb.DatabaseImpl");
    }

    public boolean sendMail(String adminName, String adminPass, Mail mail) throws Exception {
        Collection old = collection;
        util.closeCollection(collection);
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

            result = util.execXQuery(query,collection).equals("true");
        }catch (Exception e){
            System.out.println("eXistMailSender exception: " + e.getMessage());
        }finally {
            util.closeCollection(collection);
            collection = old;
        }
        return result;
    }


}
