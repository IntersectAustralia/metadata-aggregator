// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.domain;

import java.lang.String;

privileged aspect ResearchDataset_Roo_ToString {
    
    public String ResearchDataset.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdditionalLocationInformation: ").append(getAdditionalLocationInformation()).append(", ");
        sb.append("DatabaseInstance: ").append(getDatabaseInstance()).append(", ");
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("IsPhysical: ").append(getIsPhysical()).append(", ");
        sb.append("KeyForRifCs: ").append(getKeyForRifCs()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("PhysicalLocation: ").append(getPhysicalLocation()).append(", ");
        sb.append("PublicAccessRight: ").append(getPublicAccessRight()).append(", ");
        sb.append("PubliciseStatus: ").append(getPubliciseStatus()).append(", ");
        sb.append("ResearchProject: ").append(getResearchProject()).append(", ");
        sb.append("SubjectCode: ").append(getSubjectCode()).append(", ");
        sb.append("SubjectCode2: ").append(getSubjectCode2()).append(", ");
        sb.append("SubjectCode3: ").append(getSubjectCode3()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Advertised: ").append(isAdvertised()).append(", ");
        sb.append("NotAdvertised: ").append(isNotAdvertised()).append(", ");
        sb.append("ReadyForAdvertising: ").append(isReadyForAdvertising());
        return sb.toString();
    }
    
}
