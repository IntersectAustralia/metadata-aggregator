/** Copyright (c) 2011, Intersect, Australia
 *
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Intersect, Intersect's partners, nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package au.org.intersect.sydma.webapp.domain;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Role Model.
 */
@RooJavaBean
@RooToString
@RooEntity(table = "roles", finders = {"findRolesByNameEquals", "findRolesByNameLike", 
            "findRolesByDisplayNameEquals"}, persistenceUnit = "sydmaPU")
// TODO CHECKSTYLE-OFF: MagicNumber
public class Role
{

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String displayName;

    /**
     * Returns the role list for display purposes
     * 
     * @return
     */
    public static List<Role> getAssignableRoles()
    {
        return Role.findRolesByNameLike("ROLE_").getResultList();
    }

    /**
     * Enumeration of the different roles
     * 
     * @version $Rev: 29 $
     */
    public enum RoleNames
    {
        ROLE_ADMINISTRATOR, ROLE_RESEARCH_DATA_MANAGER, ROLE_RESEARCHER, ROLE_ICT_SUPPORT, ROLE_RSTUDIO_USER, ACCEPTED_TC, ACTIVE
    }
}
