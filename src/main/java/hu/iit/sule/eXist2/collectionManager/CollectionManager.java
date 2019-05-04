package hu.iit.sule.eXist2.collectionManager;

public class CollectionManager {


    public void createCollection(String collUri, String collName) {
        //xmldb:create-collection
    }

    public void addResource(String collUri, String resName, String content){
        //xmldb:store($collection-uri as xs:string, $resource-name as xs:string?, $contents as item()) as xs:string?
    }

    public void removeCollOrResource(String coll, String res) {
        if (res.isEmpty()) {
            //collection remove
        } else {
            //resource remove
        }
    }

    public void getDocumentData(String res) {
        //xmldb:document($document-uris as xs:string+) as node()
    }

    public void getChildCollections() {
        //xmldb:get-child-collections($collection-uri as xs:string) as xs:string*
    }

    public void getChildResources() {
        //xmldb:get-child-resources($collection-uri as item()) as xs:string*
    }

    public void getResOrCollCreateDate(String coll, String res) {
        //xmldb:created($collection-uri as xs:string, $resource as xs:string) as xs:dateTime
        if (res.isEmpty()) {
            //collection date
        } else {
            //resource date
        }
    }

    public void moveCollOrRes(String collFrom, String collTo, String res) {
        if (res.isEmpty()) {
            //collection move
        } else {
            //resource move
        }
    }

    public void copyCollOrRes(String collFrom, String collTo, String res) {
        if (res.isEmpty()) {
            //collection copy
        } else {
            //resource copy
        }
    }

    public void renameColl(String collUri, String newName) {
        //xmldb:rename($source-collection-uri as xs:string, $new-collection-name as xs:string) as item()
    }

    public void renameRes(String collUri, String res, String newName) {
        //xmldb:rename($collection-uri as xs:string, $resource as xs:string, $new-resource-name as xs:string) as item()
    }
}
