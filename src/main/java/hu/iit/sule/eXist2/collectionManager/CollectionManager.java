package hu.iit.sule.eXist2.collectionManager;

public class CollectionManager {


    public boolean createCollection(String collUri, String collName) {
        //xmldb:create-collection
    }

    public boolean addResource(String collUri, String resName, String content){
        //xmldb:store($collection-uri as xs:string, $resource-name as xs:string?, $contents as item()) as xs:string?
    }

    public boolean removeCollOrResource(String coll, String res) {
        if (res.isEmpty()) {
            //collection remove
        } else {
            //resource remove
        }
    }

    public String getDocumentData(String resUri) {
        //xmldb:document($document-uris as xs:string+) as node()
    }

    public String getChildCollections(String collUri) {
        //xmldb:get-child-collections($collection-uri as xs:string) as xs:string*
    }

    public String getChildResources(String collUri) {
        //xmldb:get-child-resources($collection-uri as item()) as xs:string*
    }

    public String getResOrCollCreateDate(String coll, String res) {
        //xmldb:created($collection-uri as xs:string, $resource as xs:string) as xs:dateTime
        if (res.isEmpty()) {
            //collection date
        } else {
            //resource date
        }
    }

    public boolean moveCollOrRes(String collFrom, String collTo, String res) {
        if (res.isEmpty()) {
            //collection move
        } else {
            //resource move
        }
    }

    public boolean copyCollOrRes(String collFrom, String collTo, String res) {
        if (res.isEmpty()) {
            //collection copy
        } else {
            //resource copy
        }
    }

    public boolean renameColl(String collUri, String newName) {
        //xmldb:rename($source-collection-uri as xs:string, $new-collection-name as xs:string) as item()
    }

    public boolean renameRes(String collUri, String res, String newName) {
        //xmldb:rename($collection-uri as xs:string, $resource as xs:string, $new-resource-name as xs:string) as item()
    }
}
