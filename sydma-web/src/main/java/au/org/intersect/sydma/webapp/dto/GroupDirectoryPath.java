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

package au.org.intersect.sydma.webapp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * A simple utility dto as the form backing object for directory path during rds request approval
 * 
 * @version $Rev: 29 $
 */
// TODO CHECKSTYLE-OFF: MagicNumber
public class GroupDirectoryPath
{
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[^?%*:|\"<>.]*", message = "Invalid character in path")
    private String dirPath;

    private String principalInvestigator;

    @Max(99999)
    @Min(1)
    private Integer amountOfStorage;

    public GroupDirectoryPath()
    {

    }

    public String getDirPath()
    {
        return dirPath;
    }

    public void setDirPath(String dirPath)
    {
        this.dirPath = dirPath;
    }

    public String getPrincipalInvestigator()
    {
        return principalInvestigator;
    }

    public void setPrincipalInvestigator(String principalInvestigator)
    {
        this.principalInvestigator = principalInvestigator;
    }

    public Integer getAmountOfStorage()
    {
        return amountOfStorage;
    }

    public void setAmountOfStorage(Integer amountOfStorage)
    {
        this.amountOfStorage = amountOfStorage;
    }
}
