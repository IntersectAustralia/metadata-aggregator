// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.json;

import java.lang.Long;
import java.lang.String;
import java.util.List;

privileged aspect JsonFileInfo_Roo_JavaBean {
    
    public String JsonFileInfo.getNodeId() {
        return this.nodeId;
    }
    
    public void JsonFileInfo.setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    
    public String JsonFileInfo.getName() {
        return this.name;
    }
    
    public void JsonFileInfo.setName(String name) {
        this.name = name;
    }
    
    public String JsonFileInfo.getAbsolutePath() {
        return this.absolutePath;
    }
    
    public void JsonFileInfo.setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
    
    public String JsonFileInfo.getFileType() {
        return this.fileType;
    }
    
    public void JsonFileInfo.setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    public String JsonFileInfo.getModificationDate() {
        return this.modificationDate;
    }
    
    public void JsonFileInfo.setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }
    
    public Long JsonFileInfo.getSize() {
        return this.size;
    }
    
    public void JsonFileInfo.setSize(Long size) {
        this.size = size;
    }
    
    public List<String> JsonFileInfo.getAllowedActions() {
        return this.allowedActions;
    }
    
    public void JsonFileInfo.setAllowedActions(List<String> allowedActions) {
        this.allowedActions = allowedActions;
    }
    
    public String JsonFileInfo.getAnnotation() {
        return this.annotation;
    }
    
    public void JsonFileInfo.setAnnotation(String annotation) {
        this.annotation = annotation;
    }
    
    public boolean JsonFileInfo.isCanUpload() {
        return this.canUpload;
    }
    
    public void JsonFileInfo.setCanUpload(boolean canUpload) {
        this.canUpload = canUpload;
    }
    
}
