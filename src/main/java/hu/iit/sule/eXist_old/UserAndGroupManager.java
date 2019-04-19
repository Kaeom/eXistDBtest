package hu.iit.sule.eXist_old;

public class UserAndGroupManager {

        Exist exist = new Exist();
        private static String groupsCollection = "/db/system/security/exist/groups/";
        private static String usersCollection = "/db/system/security/exist/accounts/";

       public void createGroup(String databaseUrl, String name, String description){

           String xQuery = "xquery version \"3.1\";\n" +
                   "sm:create-group(\""+ name +"\", \""+description+"\")";

           String xQuery2 = "xquery version \"3.1\";\n" +
                   "let $exist := sm:group-exists(\" " + name + " \")\n" +
                   "return $exist";
           exist.initDatabase();

           //ResourceSet rs = exist.executeXQueryAsADmin(databaseUrl,groupsCollection,xQuery2);
           //exist.printResourceIterator(rs);

           exist.executeXQueryAsADmin(databaseUrl,groupsCollection,xQuery);


       }

       public void createUser(String databaseUrl, String username, String pass, String p_group, String o_group, String full_name, String desc){

           String xQuery = "xquery version \"3.1\";\n" +
                   "return  sm:create-account("+ username +", "+ pass +", "+ p_group +", " + o_group+ ", "+ full_name+", "+ desc +")\n";
            exist.initDatabase();
            exist.executeXQueryAsADmin(databaseUrl,usersCollection,xQuery);
       }

       public void deleteGroup(){

       }

       public void deleteUser(){

       }

       public void listGroups(){

       }

       public void listUsers(){

       }

       public void changeUserPass(){

       }

}
