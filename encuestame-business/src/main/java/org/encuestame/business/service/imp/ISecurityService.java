/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service.imp;

import java.util.Collection;
import java.util.List;

import org.encuestame.business.service.SecurityService.FollowOperations;
import org.encuestame.core.service.IService;
import org.encuestame.persistence.domain.EnMePermission;
import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.security.SignUpBean;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.utils.web.UnitGroupBean;
import org.encuestame.utils.web.UnitLists;
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UnitUserBean;
import org.springframework.mail.MailSendException;

/**
 * Interface for Security Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009  11:35:01
 * @version $Id$
 */
public interface ISecurityService extends IService {

     String getDefaultUserPermission();
    /**f
     * Load list of users.
     * @return list of users with groups and permission
     * @throws Exception Exception
     * @throws EnMeExpcetion excepcion
     */
    List<UnitUserBean> loadListUsers(final String currentUsername, final Integer maxResults,
            final Integer start) throws Exception;

    /**
     * Retrieve Total Own Users.
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    Long totalOwnUsers(final String username) throws EnMeDomainNotFoundException;

    /**
     * Get User Complete Info.
     * @param currentUsername
     * @return
     * @throws EnMeDomainNotFoundException
     */
    UnitUserBean getUserCompleteInfo(final Long userId, final String currentUsername) throws EnMeDomainNotFoundException;


    /**
     * Add new Twitter Account.
     * @param account account.
     * @param username
     * @throws EnMeDomainNotFoundException
     */
     void addNewTwitterAccount(final String account, final String username) throws EnMeDomainNotFoundException;

    /**
     * Assing Group to User.
     * @param user user
     * @param group group
     * @throws EnMeDomainNotFoundException
     */
    void assingGroupFromUser(
            final Long groupId,
            final Long userId,
            final String username) throws EnMeDomainNotFoundException;

    /**
     * Search user by username.
     * @param username username
     * @return {@link UnitUserBean}
     */
    UnitUserBean searchUserByUsername(final String username);

    /**
     * Load all list of permisssions and covert to permission bean.
     * @return list of permisssions
     */
    Collection<UnitPermission> loadAllListPermission();

    /**
     * Delete Group Domain.
     * @param Long group
     */
    void deleteGroup(final Long groupId);

    /**
     * Delete user.
     * @param userBean user to delete
     * @throws EnMeDomainNotFoundException
     * @throws EnMeExpcetion exception
     */
     void deleteUser(final UnitUserBean userBean) throws EnMeDomainNotFoundException;
    /**
     * Renew password.
     * @param userBean {@link UnitUserBean}
     * @param newPassword new password
     * @return new password
     * @throws EnMeExpcetion exception
     */
    String renewPassword(final UnitUserBean userBean, final String newPassword) throws EnMeExpcetion;
    /**
     * Update a Group.
     * @param groupBean {@link UnitGroupBean}
     */
    UnitGroupBean updateGroup(UnitGroupBean groupBean) throws EnMeExpcetion;
    /**
     * Update user.
     * @param userBean user bean.
     * @throws EnMeExpcetion exception
     */
    void updateUser(final UnitUserBean userBean);
    /**
     * Create a new Group.
     * @param groupBean group bean
     * @throws EnMeDomainNotFoundException
     */
    UnitGroupBean createGroup(final UnitGroupBean groupBean , final String username) throws EnMeDomainNotFoundException;
    /**
     * Create a new Permisssion.
     * @param permissionBean {@link UnitPermission}
     */
    void createPermission(final UnitPermission permissionBean);
    /**
     * Create a user, generate password for user and send email to confirmate
     * the account.
     * @param userBean user bean
     * @throws EnMeExpcetion
     * @throws EnMeExpcetion personalize exception
     */
    void createUser(final UnitUserBean userBean,  final String username) throws EnMeExpcetion;

    /**
     * Assign permission to user.
     * @param userBean {@link UnitUserBean}
     * @param permissionBean {@link UnitPermission}
     * @throws EnMeExpcetion exception
     */
    void assignPermission(
            final UnitUserBean userBean,
            final UnitPermission permissionBean)
            throws EnMeExpcetion;

    /**
     * Remove {@link Group} from User.
     * @param userBean {@link UnitUserBean}
     * @param groupBean {@link UnitGroupBean}
     * @throws EnMeExpcetion
     */
    void removeGroupFromUser(
            final UnitUserBean userBean,
            final UnitGroupBean groupBean)
            throws EnMeExpcetion;

    /**
     * Load Permission domain and covert to permission bean.
     * @param permission permission
     * @return permission bean
     * @throws EnMeExpcetion excepcion
     */
    UnitPermission loadBeanPermission(final EnMePermission  permission)
    throws EnMeExpcetion;

    /**
     * Load domain permission.
     * @param permission permission
     * @return permission domain
     */
    Permission loadPermission(final String permission);

    /**
     * Invite some users to register in the system.
     * @param email list of users
     * @param code code
     * @throws Exception
     * @throws Exception excepcion
     */
    void inviteUser(String email, String code) throws Exception;

    /**
     * Generate hash code invitation.
     * @return generated code
     */
    String generateHashCodeInvitation();


    /**
     * Find {@link UserAccount} by UserName
     * @param username user name
     * @return {@link UserAccount}
     */
    UserAccount findUserByUserName(final String username);

    /**
     * Setter.
     * @param suspendedNotification suspended notification
     */
    void setSuspendedNotification(final Boolean suspendedNotification);

    /**
     * Update Twitter Account.
     * @param accountBean accountBean
     * @param password password
     * @param verify verify
     */
     void updateTwitterAccount(final UnitTwitterAccountBean accountBean, final String password,
                               final Boolean verify);

     /**
      * Update OAuth Secret Twitter Credentials.
      * @param accountBean
      * @param username
      */
     void updateSecretTwitterCredentials(final UnitTwitterAccountBean accountBean,
             final String username) throws EnMeExpcetion;

     /**
      * Get Twitter Account.
      * @param twitterAccountId
      * @return
      */
     UnitTwitterAccountBean getTwitterAccount(final Long twitterAccountId);


    /**
     * Load Groups by Client.
     * @return
     * @throws EnMeDomainNotFoundException
     */
    List<UnitGroupBean> loadGroups(final String currentUsername) throws EnMeDomainNotFoundException;

    /**
     * SingUp User
     * @param singUpBean {@link SignUpBean}.
     * @return {@link UnitUserBean}.
     */
    UnitUserBean singupUser(final SignUpBean singUpBean);

    /**
     * Search {@link UserAccount} by email.
     * @param email email
     * @return
     */
    List<UserAccount> searchUsersByEmail(final String email);

    /**
     * Search List of User By Username
     * @param username username
     * @return
     */
    List<UserAccount> searchUsersByUsername(final String username);

    /**
     * Load list of {@link Permission}.
     * @return list of permissions.
     */
    List<UnitPermission> loadPermissions();

    /**
     * Send password to user.
     * @param email email
     * @param password password
     * @throws MailSendException
     */
    void sendUserPassword(final String email,
            final String password)
            throws MailSendException;

    /**
     * Find {@link UserAccount} by UserName
     * @param username user name
     * @return {@link UserAccount}
     */
    UnitUserBean findUserByEmail(final String email);

    /**
     * Get User Logged Twitter Accounts.
     * @return
     * @throws EnMeDomainNotFoundException
     */
    List<UnitTwitterAccountBean> getUserLoggedTwitterAccount(final String username) throws EnMeDomainNotFoundException;

    /**
     * Get User Logged Verified Twitter Accounts.
     * @param username username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    List<UnitTwitterAccountBean> getUserLoggedVerifiedTwitterAccount(final String username) throws EnMeDomainNotFoundException;

    /**
     * Get Email List by Username.
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    List<UnitLists> getListbyUsername(final String username) throws EnMeDomainNotFoundException;

    /**
     * Update OAuth Token/Secret Social Account.
     * @param accountId
     * @param token
     * @param tokenSecret
     * @param username
     * @throws EnMeExpcetion
     */
    void updateOAuthTokenSocialAccount(final Long accountId, final String token, final String tokenSecret,
            final String username) throws EnMeExpcetion;

    /**
     * Assign Permission,
     * @param userId user id
     * @param permission {@link EnMePermission}.
     * @param loggedUse user logged.
     * @throws EnMeExpcetion exception.
     */
    void updatePermission(
            final Long userId,
            final String loggedUser,
            final EnMePermission permission,
            final String action)
            throws EnMeExpcetion;

    /**
     * Get Group by Id and User Id.
     * @param groupId
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    Group getGroupbyIdandUser(final Long groupId, final String username) throws EnMeDomainNotFoundException;

    /**
     * User Bean.
     * @param user
     * @return
     */
    void getStatsByUsers(final UnitUserBean user);

    /**
     * Get User by Group.
     * @param secGroupId
     * @param username
     * @return
     */
    Long getUserbyGroup(final Long groupId, final String username)throws EnMeDomainNotFoundException;

    /**
     * Count Users by Groups
     * @param username
     * @param groupId
     * @return
     */
    List<Object[]> countUsersbyGroups(final Long groupId, final String username);

    /**
     * Change User Status.
     * @param username
     * @throws EnmeFailOperation
     */
    void changeUserStatus(final String username) throws EnmeFailOperation;

    /**
     * Update property for user account.
     * @param property
     * @param value
     * @param username
     * @throws EnMeDomainNotFoundException
     */
    void upadteAccountProfile(
            final String property,
            final String value,
            final String username) throws EnMeDomainNotFoundException;

    /**
     * User Account Is Activated.
     * @param signUpBean
     * @return
     */
    Boolean isActivated(final SignUpBean signUpBean);

    /**
     * Add Followers.
     * @param myUser
     * @param followerUser
     * @return
     * @throws EnMeDomainNotFoundException
     */
    UserAccount addFollower(final String myUser, final String followerUser) throws EnMeDomainNotFoundException;

   /**
    * Follow Operations.
    * @param userAcc
    * @param myUsername
    * @param followerUser
    * @param operation
    * @throws EnMeDomainNotFoundException
    */
    void followOperations(final UserAccount userAcc,
            final String myUsername, final String followerUser,
            final FollowOperations operation) throws EnMeDomainNotFoundException;
}
