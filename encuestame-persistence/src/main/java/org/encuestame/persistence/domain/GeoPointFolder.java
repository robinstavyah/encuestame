/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.security.Account;

/**
 * Cat Location Folder.
 * This domain group
 * @author Picado, Juan juan@encuestame.org
 * @since May 15, 2010 10:23:15 PM
 * @version $Id:$
 */
@Entity
@Table(name = "geoPoint_folder")
public class GeoPointFolder {

    /**
     * Id.
     */
    private Long locationFolderId;

    /**
     * Name.
     */
    private String locationFolderName;

    /**
     * {@link GeoPointFolderType}.
     */
    private GeoPointFolderType folderType;

    /**
     * {@link Account}.
     */
    private Account account;

    /**
     * SubLocation Folder.
     */
    private GeoPointFolder subLocationFolder;

    /**
     * @return the locationFolderId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "locate_folder_id", unique = true, nullable = false)
    public Long getLocationFolderId() {
        return locationFolderId;
    }

    /**
     * @param locationFolderId the locationFolderId to set
     */
    public void setLocationFolderId(final Long locationFolderId) {
        this.locationFolderId = locationFolderId;
    }

    /**
     * @return the locationFolderName
     */
    @Column(name = "name", nullable = false)
    public String getLocationFolderName() {
        return locationFolderName;
    }

    /**
     * @param locationFolderName the locationFolderName to set
     */
    public void setLocationFolderName(final String locationFolderName) {
        this.locationFolderName = locationFolderName;
    }

    /**
     * @return the folderType
     */
    @Column(name="type")
    @Enumerated(EnumType.STRING)
    public GeoPointFolderType getFolderType() {
        return folderType;
    }

    /**
     * @param folderType the folderType to set
     */
    public void setFolderType(final GeoPointFolderType folderType) {
        this.folderType = folderType;
    }

    /**
     * @return the account
     */
    @ManyToOne()
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(final Account account) {
        this.account = account;
    }

    /**
     * @return the subLocationFolder
     */
    @ManyToOne()
    public GeoPointFolder getSubLocationFolder() {
        return subLocationFolder;
    }

    /**
     * @param subLocationFolder the subLocationFolder to set
     */
    public void setSubLocationFolder(final GeoPointFolder subLocationFolder) {
        this.subLocationFolder = subLocationFolder;
    }
}
