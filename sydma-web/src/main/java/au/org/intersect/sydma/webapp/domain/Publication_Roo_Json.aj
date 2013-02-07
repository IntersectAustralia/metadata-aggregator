// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.domain;

import au.org.intersect.sydma.webapp.domain.Publication;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Publication_Roo_Json {
    
    public String Publication.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Publication Publication.fromJsonToPublication(String json) {
        return new JSONDeserializer<Publication>().use(null, Publication.class).deserialize(json);
    }
    
    public static String Publication.toJsonArray(Collection<Publication> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Publication> Publication.fromJsonArrayToPublications(String json) {
        return new JSONDeserializer<List<Publication>>().use(null, ArrayList.class).use("values", Publication.class).deserialize(json);
    }
    
}