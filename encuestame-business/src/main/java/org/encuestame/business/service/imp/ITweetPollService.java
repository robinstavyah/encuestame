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

import java.util.List;

import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.exception.EnmeFailOperation;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.security.SecUserTwitterAccounts;
import org.encuestame.persistence.domain.survey.TweetPoll;
import org.encuestame.persistence.domain.survey.TweetPollResult;
import org.encuestame.persistence.domain.survey.TweetPollSwitch;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.utils.web.UnitFolder;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitTweetPollResult;

/**
 * Tweet Poll Service.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  April 02, 2010
 * @version $Id: $
 */
public interface ITweetPollService extends IMasterSurveyService{

    /**
     * Create Tweet Poll.
     * @param tweetPollBean tweet poll bean.
     * @throws EnMeExpcetion exception
     */
    void createTweetPoll(final UnitTweetPoll tweetPollBean, final Question question) throws EnMeExpcetion;

    /**
     * Generate TweetPoll Text.
     * @param tweetPoll tweetPoll
     * @param url url
     * @return tweet text
     * @throws EnMeExpcetion exception
     */
    String generateTweetPollText(final UnitTweetPoll tweetPoll, final String url) throws EnMeExpcetion;

    /**
     * Search {@link TweetPoll} by Keyword.
     * @param username username session
     * @param keyword keyword.
     * @return
     * @throws EnMeDomainNotFoundException
     * @throws EnMeExpcetion
     */
    List<UnitTweetPoll> searchTweetsPollsByKeyWord(final String username, final String keyword) throws EnMeDomainNotFoundException, EnMeExpcetion;

    /**
     * Save Tweet Id.
     * @param tweetPollBean {@link UnitTweetPoll}
     * @throws EnMeExpcetion exception
     */
    void saveTweetId(final UnitTweetPoll tweetPollBean) throws EnMeExpcetion;

    /**
     * Get Tweet Path.
     * @return tweet
     */
    String getTweetPath();

    /**
     * @return the tweetPollDao
     */
    ITweetPoll getTweetPollDao();

    /**
     * Vote on TweetPoll.
     * @param pollSwitch {@link TweetPollSwitch}
     * @param ip ip
     */
    void tweetPollVote(final TweetPollSwitch pollSwitch, final String ip);

    /**
     * Validate TweetPoll IP.
     * @param ipVote  ipVote
     * @param tweetPoll tweetPoll
     * @return {@link TweetPollResult}
     */
    TweetPollResult validateTweetPollIP(final String ipVote, final TweetPoll tweetPoll);

    /**
     * Get Results By {@link TweetPoll}.
     * @param tweetPollId tweetPoll Id
     * @return list of {@link UnitTweetPollResult}
     */
    List<UnitTweetPollResult> getResultsByTweetPollId(final Long tweetPollId) throws EnMeDomainNotFoundException;

    /**
     *
     * @param username username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    List<UnitTweetPoll> getTweetsPollsByUserName(final String username) throws EnMeDomainNotFoundException;

    /**
     * Public Multiples Tweet Accounts.
     * @param twitterAccounts List of {@link SecUserTwitterAccounts}.
     * @param tweetPoll {@link TweetPoll}.
     * @param tweetText tweet text.
     */
    String[] publicMultiplesTweetAccounts(
            final List<UnitTwitterAccountBean> twitterAccounts,
            final Long tweetPollId,
            final String tweetText);

    /**
     * Update Question Name.
     * @param questionId
     * @param questionName
     */
    void updateQuestionName(final Long questionId, final String questionName);

    /**
     * Twitter Service.
     * @return
     */
    ITwitterService getTwitterService();

    /**
     * Create TweetPoll Folder.
     * @param folderName
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    UnitFolder createTweetPollFolder(final String folderName, final String username) throws EnMeDomainNotFoundException;

    /**
     * Update TweetPoll Folder.
     * @param folderId
     * @param folderName
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    UnitFolder updateTweetPollFolder(final Long folderId, final String folderName, final String username) throws EnMeDomainNotFoundException;

    /**
     * Delete TweetPoll Folder.
     * @param folderId
     * @throws EnMeDomainNotFoundException
     */
    void deleteTweetPollFolder(final Long folderId) throws EnMeDomainNotFoundException;

    /**
     * Add TweetPoll to Folder.
     * @param folderId
     * @param username
     * @param tweetPollId
     * @throws EnMeDomainNotFoundException
     */
    void addTweetPollToFolder(final Long folderId, final String username, final Long tweetPollId) throws EnMeDomainNotFoundException;

    /**
     * Change Status TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     * @throws EnmeFailOperation
     */
    void changeStatusTweetPoll(final Long tweetPollId, final String username) throws EnMeDomainNotFoundException, EnmeFailOperation;

    /**
     * Change Allow Live Results TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     * @throws EnmeFailOperation
     */
    void changeAllowLiveResultsTweetPoll(final Long tweetPollId, final String username) throws EnMeDomainNotFoundException, EnmeFailOperation;

    /**
     * Change Allow Captcha TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     * @throws EnmeFailOperation
     */
    void changeAllowCaptchaTweetPoll(final Long tweetPollId, final String username) throws EnMeDomainNotFoundException, EnmeFailOperation;

    /**
     * Change Resume Live ResultsTweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     * @throws EnmeFailOperation
     */
    void changeResumeLiveResultsTweetPoll(final Long tweetPollId, final String username)
         throws EnMeDomainNotFoundException, EnmeFailOperation;


    /**
     * Search Scheduled TweetsPoll.
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<UnitTweetPoll> searchTweetsPollScheduled(final String username,
             final Integer maxResults, final Integer start) throws EnMeExpcetion;

    /**
     * Search Favourites TweetPolls.
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<UnitTweetPoll> searchTweetsPollFavourites(final String username,
             final Integer maxResults, final Integer start) throws EnMeExpcetion;

    /**
     * Search Tweet Polls Last Week.
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<UnitTweetPoll> searchTweetsPollsLastWeek(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion;

    /**
     * Search Tweet Polls Today.
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    List<UnitTweetPoll> searchTweetsPollsToday(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion;

    /**
     * Set Favourite TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     * @throws EnmeFailOperation
     */
    void setFavouriteTweetPoll(final Long tweetPollId, final String username) throws
           EnMeDomainNotFoundException, EnmeFailOperation;

}

