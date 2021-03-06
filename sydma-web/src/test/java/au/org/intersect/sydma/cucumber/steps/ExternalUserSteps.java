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
package au.org.intersect.sydma.cucumber.steps;

//TODO CHECKSTYLE-OFF: ImportOrderCheck

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.spring.StepDefinitions;

import au.org.intersect.sydma.webapp.domain.Role;
import au.org.intersect.sydma.webapp.domain.Role.RoleNames;
import au.org.intersect.sydma.webapp.domain.User;
import au.org.intersect.sydma.webapp.domain.UserType;

@StepDefinitions
public class ExternalUserSteps
{    
    @Then("^I create external user details for user \"([^\"]*)\"$")
    public void iCreateExternalUserDetails(String externalUser) throws IOException
    {
        User user = new User();
        user.setEmail("user@integration.test");
        user.setEnabled(true);
        user.setUserType(UserType.INTERNAL);
        user.setGivenname(externalUser);
        user.setSurname("Surname");
        user.setUsername(user.getEmail());
        user.setInstitution("Intersect");
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        user.setPassword(encoder.encodePassword("user@integration.test", null));
        user.persist();
        Role role = Role.findRolesByNameEquals(RoleNames.ROLE_RESEARCHER.toString()).getSingleResult();
        user.getRoles().add(role);
        user.merge();
    }
      
    @Then("^the username should be the same as email \"([^\"]*)\"$")
    public void theUsernameShouldBeTheSameAsEmail(String email) 
    {
        User user = User.findUsersByUsernameEquals(email).getSingleResult();
        String username = user.getUsername().toString();
        assertEquals("The username and emails differ", email, username);
    }

        
}
