/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.test.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.imp.TweetPollDao;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.QuestionAnswer;
import org.encuestame.persistence.domain.survey.TweetPoll;
import org.encuestame.persistence.domain.survey.TweetPollFolder;
import org.encuestame.persistence.domain.survey.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link TweetPollDao}..
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 13, 2010 11:57:17 PM
 * @version $Id: change to one dolar simbol
 */
public class TestTweetPollDao  extends AbstractBase{

    /** {@link UserAccount}. **/
    private UserAccount secondary;

    /** {@link QuestionAnswer}. **/
    private QuestionAnswer questionsAnswers1;

    /** {@link QuestionAnswer}. **/
    private QuestionAnswer questionsAnswers2;

    /** {@link TweetPollSwitch}. **/
    private TweetPollSwitch pollSwitch1;

    /** {@link TweetPollSwitch}. **/
    private TweetPollSwitch pollSwitch2;

    /** {@link TweetPoll}. **/
    private TweetPoll tweetPoll;

    /** {@link TweetPollFolder}. **/
    private TweetPollFolder tweetPollFolder;


    /**
     * Before.
     */
    @Before
    public void initData(){
      this.secondary = createSecondaryUser("jhon", createUser());
      final Question question = createQuestion("who I am?", "");
      this.questionsAnswers1 = createQuestionAnswer("yes", question, "12345");
      this.questionsAnswers2 = createQuestionAnswer("no", question, "12346");
      this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question);
      this.pollSwitch1 = createTweetPollSwitch(questionsAnswers1, tweetPoll);
      this.pollSwitch2 = createTweetPollSwitch(questionsAnswers2, tweetPoll);
      createTweetPollResult(pollSwitch1, "192.168.0.1");
      createTweetPollResult(pollSwitch1, "192.168.0.2");
      createTweetPollResult(pollSwitch2, "192.168.0.3");
      createTweetPollResult(pollSwitch2, "192.168.0.4");

      this.tweetPollFolder = createTweetPollFolder("First TweetPoll Folder", secondary.getAccount());

    }

    /**
     * Test retrieveTweetsPollSwitch.
     */
    @Test
    public void testRetrieveTweetsPollSwitch(){
        final TweetPollSwitch pollSwitch = getTweetPoll().retrieveTweetsPollSwitch(this.pollSwitch1.getCodeTweet());
        assertNotNull(pollSwitch);
    }

    /**
     * Test getResultsByTweetPoll.
     */
    @Test
    public void testgetResultsByTweetPoll(){
        final List<Object[]> results = getTweetPoll().getResultsByTweetPoll(tweetPoll, this.questionsAnswers1);
        assertEquals("Should be equals", 1,  results.size());
        assertEquals("Should be equals", "yes",  results.get(0)[0]);
        assertEquals("Should be equals", "2", results.get(0)[1].toString());
    }

    /**
     * Test Get Total Votes by TweetPoll
     */
    @Test
    public void testgetTotalVotesByTweetPoll(){
        final List<Object[]>  pollSwitchs = getTweetPoll().getTotalVotesByTweetPoll(this.tweetPoll.getTweetPollId());
        assertEquals("Should be equals", 2, pollSwitchs.size());
    }

    @Test
    public void testgetVotesByAnswer(){
        final Long d = getTweetPoll().getTotalVotesByTweetPollId(this.tweetPoll.getTweetPollId());
        System.out.println(d);
    }


    /**
     * Test Get TweetPoll by TweetPoll Id and User.
     */
    @Test
    public void testGetTweetPollByIdandUserId(){
        assertNotNull(tweetPoll);
        assertNotNull(secondary);
        final TweetPoll tp = getTweetPoll().getTweetPollByIdandUserId(this.tweetPoll.getTweetPollId(), secondary.getAccount().getUid());
        assertEquals("Should be equals", 1, 1);
        assertEquals("Should be equals", this.tweetPoll.getTweetPollId(), tp.getTweetPollId());
    }

    /**
     * Test Get TweetPoll Folder by FolderId and User.
     */
    @Test
    public void testGetTweetPollFolderByIdandUser(){
        assertNotNull(this.tweetPollFolder);
        final TweetPollFolder tpf = getTweetPoll().getTweetPollFolderByIdandUser(this.tweetPollFolder.getId(), secondary.getAccount().getUid());
        assertEquals("Should be equals", this.tweetPollFolder.getId(), tpf.getId());
     }

    /**
     * Test Get TweetPoll Folder by folderId.
     */
    @Test
    public void testGetTweetPollFolderById(){
        assertNotNull(tweetPollFolder);
        final TweetPollFolder tpf = getTweetPoll().getTweetPollFolderById(this.tweetPollFolder.getId());
        assertNotNull(tpf);
     }

    /**
     * Test Retrieve TweetPoll Folder by User.
     */
    @Test
    public void testRetrieveTweetPollFolderByUserId(){
        assertNotNull(tweetPollFolder);
        assertNotNull(secondary);
        final List<TweetPollFolder> tpfu = getTweetPoll().retrieveTweetPollFolderByUserId(this.secondary.getAccount().getUid());
        assertEquals("Should be equals", 1, tpfu.size());
    }

    /**
     * Test Retrieve TweetPoll by Folder.
     * @throws EnMeDomainNotFoundException
     */
    @Test
    public void testRetrieveTweetPollByFolder() throws EnMeDomainNotFoundException {
        final Long user = this.secondary.getAccount().getUid();
        assertNotNull(tweetPollFolder);
        assertNotNull(tweetPoll);
        final TweetPoll addTweetPoll = addTweetPollToFolder(this.tweetPollFolder.getId(), user, this.tweetPoll.getTweetPollId());
        assertNotNull(addTweetPoll);
        final List<TweetPoll> tpfolder = getTweetPoll().retrieveTweetPollByFolder(user, this.tweetPollFolder.getId());
        assertEquals("Should be equals", 1, tpfolder.size());
    }

    /**
     * Test Retrieve Tweets By User.
     */
    @Test
    public void testRetrieveTweetsByUserId(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final Long userId = this.secondary.getAccount().getUid();
        final List<TweetPoll> tweets = getTweetPoll().retrieveTweetsByUserId(userId, 5, 0);
        assertEquals("Should be equals", 1, tweets.size());
    }

    /**
     * Test Retrieve Tweets by
     */
    @Test
    public void testRetrieveTweetsByQuestionName(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final Long userId = this.secondary.getAccount().getUid();
        final String keyword = "Who";
        final List<TweetPoll> tweets = getTweetPoll().retrieveTweetsByQuestionName(keyword, userId, 5, 0);
        assertEquals("Should be equals", 1, tweets.size());
    }

    /**
     * Test Retrieve TweetPoll Today.
     */
    @Test
    public void testRetrieveTweetPollToday(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final Long userId = this.secondary.getAccount().getUid();
        final List<TweetPoll> tweetsToday = getTweetPoll().retrieveTweetPollToday(userId, 5, 0);
        assertEquals("Should be equals", 1, tweetsToday.size());
    }

    @Test
    public void testRetrieveTweetPollByDate(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final Long userId = this.secondary.getAccount().getUid();
        final Date initDate = new Date();
        final List<TweetPoll> tweetsByDate = getTweetPoll().retrieveTweetPollByDate(userId, initDate, 5, 0);
        assertEquals("Should be equals", 1, tweetsByDate.size());
    }

    /**
     * Test Retrieve TweetPoll Last Week
     */
    @Test
    public void testRetrieveFavouritesTweetPoll(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final Long userId = this.secondary.getAccount().getUid();
        final List<TweetPoll> favouritesTweets = getTweetPoll().retrieveFavouritesTweetPoll(userId, 5, 0);
        assertEquals("Should be equals", 1, favouritesTweets.size());
    }

    @Test
    public void testRetrieveScheduledTweetPoll(){
        assertNotNull(this.secondary);
        assertNotNull(tweetPoll);
        final Long userId = this.secondary.getAccount().getUid();
        final List<TweetPoll> scheduledTweets = getTweetPoll().retrieveScheduledTweetPoll(userId, 5, 0);
        assertEquals("Should be equals", 1, scheduledTweets.size());
    }
}
