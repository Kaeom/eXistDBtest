package hu.iit.sule.eXsitExamples;

import hu.iit.sule.eXist_old.Exist;
import hu.iit.sule.eXist_old.UserAndGroupManager;

public class App {


    private static String xQuery = "xquery version \"3.1\";\n" +
            "\n" +
            "import module namespace file=\"http://exist-db.org/xquery/file\";\n" +
            "\n" +
            "let $result_file := \"C:\\Result\\result.xml\"\n" +
            "let $doc := doc(\"/db/videos/videos.xml\")//videos/video\n" +
            "for $x in $doc\n" +
            "where $x/year = 1997\n" +
            "return file:serialize($x, $result_file, '',true())";

    private static final String URL = "xmldb:exist://localhost:8080/exist/xmlrpc";

    public static void main(String args[]){

        Exist exist = new Exist();
        UserAndGroupManager uagManager = new UserAndGroupManager();

        exist.initDatabase();

        /*ResourceSet rs = exist.executeXQueryAsADmin(URL,"/db/videos/",xQuery);
        exist.printResourceIterator(rs);*/

        //uagManager.createGroup(URL,"GroupTestJava","GroupTestJavaDesc");
        uagManager.createUser(URL,"TestUserFromJava","TestPassFromJava","GroupTestJava","","Test User From Java","Test User From Java Desc");
    }


}
