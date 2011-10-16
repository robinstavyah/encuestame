/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.service.imp;

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMePollNotFoundException;
import org.encuestame.persistence.exception.EnMeTweetPollNotFoundException;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.UnitLists;

/**
 * Poll Service Interface.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since May 16, 2010
 * @version $Id: $
 */
public interface IPollService extends IMasterSurveyService{

    /**
     * Create poll.
     * @param questionName
     * @param answers
     * @param showResults
     * @param commentOption
     * @param notification
     * @return
     * @throws EnMeExpcetion
     */
     Poll createPoll(final String questionName, final String[] answers, final Boolean showResults,
                final String commentOption, final Boolean notification) throws EnMeExpcetion;

    /**
     * List Poll by Question.
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeNoResultsFoundException
     */
     List<PollBean> listPollbyQuestionKeyword(final String keyword, final Integer maxResults,
                final Integer start) throws EnMeNoResultsFoundException;
   /**
    * List Poll by User Id.
    * @param maxResults
    * @param start
    * @return
    * @throws EnMeNoResultsFoundException
    */
    List<PollBean> listPollByUser(final Integer maxResults, final Integer start) throws EnMeNoResultsFoundException;

    /**
     * Update Question Poll.
     * @param pollId
     * @param question
     * @return
     * @throws EnMeExpcetion
     */
    PollBean updateQuestionPoll(final Long pollId, final Question question)
    throws EnMeExpcetion;

    /**
    * Public Poll by List.
    * @param Poll
    * @param emailList
    */
    void publicPollByList(final Poll Poll , final UnitLists emailList);

    /**
    * Remove PollFolder.
    * @param folderId
    * @throws EnMeNoResultsFoundException
    */
    void removePollFolder(final Long folderId) throws EnMeNoResultsFoundException;

    /**
     * Create Poll Folder.
     * @param folderName
     * @return
     * @throws EnMeNoResultsFoundException
     */
    FolderBean createPollFolder(final String folderName) throws EnMeNoResultsFoundException;

    /**
    * Update FolderName.
    * @param folderId
    * @param newFolderName
    * @param username
    * @return
    * @throws EnMeNoResultsFoundException
    */
    FolderBean updateFolderName(final Long folderId,
          final String newFolderName,
          final String username) throws EnMeNoResultsFoundException;

    /**
    * Retrieve Folder Poll.
    * @param username
    * @return
    * @throws EnMeNoResultsFoundException exception
    */
    List<FolderBean> retrieveFolderPoll() throws EnMeNoResultsFoundException;

    /**
    * Get Polls by Folder.
    * @param folder
    * @return
    * @throws EnMeNoResultsFoundException
    */
    List<PollBean> getPollsByFolder(final FolderBean folder) throws EnMeNoResultsFoundException;

    /**
    *
    * @param keywordQuestion
    * @param maxResults
    * @param start
    * @return
    * @throws EnMeExpcetion
    */
    List<PollBean> searchPollByKeyword(final String keywordQuestion, final Integer maxResults,
        final Integer start) throws EnMeExpcetion;

    /**
     * Search Polls By Folder Id.
     * @param folderId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<PollBean> searchPollsByFolder(final Long folderId, final String username) throws EnMeNoResultsFoundException;

    /**
     * Add poll to folder.
     * @param folderId
     * @param pollId
     * @throws EnMeNoResultsFoundException
     */
    void addPollToFolder(final Long folderId, final Long pollId) throws EnMeNoResultsFoundException;

    /**
     * Get poll foder by id and user.
     * @param folderId
     * @param userAccount
     * @return
     */
    PollFolder getPollFolderByFolderIdandUser(final Long folderId, final UserAccount userAccount);

    /**
     * Get polls by date.
     * @param date
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<PollBean> getPollsbyDate(final Date date,
            final Integer maxResults, final Integer start) throws EnMeNoResultsFoundException;

    /**
     * Get poll by poll id and User id.
     * @param pollId
     * @param account
     * @return
     * @throws EnMeTweetPollNotFoundException
     */
    Poll getPollById(final Long pollId, final UserAccount account) throws EnMeNoResultsFoundException;

    /**
     * Get poll by pollId.
     * @param pollId
     * @return
     * @throws EnMeTweetPollNotFoundException
     */
    Poll getPollById(final Long pollId) throws EnMePollNotFoundException;

    /**
     * Get polls by userName.
     * @param username
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeNoResultsFoundException
     */
    List<PollBean> getPollsByUserName(final String username, final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException;


    /**
     * Create Poll published Notification.
     * @param tweetPoll
     * @throws EnMeNoResultsFoundException
     */
    void createPollNotification(final Poll poll) throws EnMeNoResultsFoundException;

    /**
     * Get published polls.
     * @param maxResults
     * @param start
     * @param range
     * @return
     * @throws EnMeTweetPollNotFoundException
     * @throws EnMePollNotFoundException
     */
    List<Poll> getPollsByRange(final Integer maxResults, final Integer start, final Date range);
}
