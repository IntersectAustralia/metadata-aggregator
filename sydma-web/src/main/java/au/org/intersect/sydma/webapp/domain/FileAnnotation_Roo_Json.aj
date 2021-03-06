// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.domain;

import au.org.intersect.sydma.webapp.domain.FileAnnotation;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect FileAnnotation_Roo_Json {
    
    public String FileAnnotation.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static FileAnnotation FileAnnotation.fromJsonToFileAnnotation(String json) {
        return new JSONDeserializer<FileAnnotation>().use(null, FileAnnotation.class).deserialize(json);
    }
    
    public static String FileAnnotation.toJsonArray(Collection<FileAnnotation> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<FileAnnotation> FileAnnotation.fromJsonArrayToFileAnnotations(String json) {
        return new JSONDeserializer<List<FileAnnotation>>().use(null, ArrayList.class).use("values", FileAnnotation.class).deserialize(json);
    }
    
}
