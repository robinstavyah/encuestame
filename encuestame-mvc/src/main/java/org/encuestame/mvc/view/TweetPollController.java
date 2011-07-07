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

package org.encuestame.mvc.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.service.imp.SecurityOperations;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.encuestame.mvc.validator.ValidateOperations;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeTweetPollNotFoundException;
import org.encuestame.utils.captcha.ReCaptchaResponse;
import org.encuestame.utils.vote.UtilVoteCaptcha;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Tweet Poll Controller support.
 * @author Picado, Juan juan@encuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 */

@Controller
public class TweetPollController extends AbstractBaseOperations {

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Tweet Poll Controller.
     * @param model model
     * @param id id tweet
     * @return view to redirect.
     */
    @RequestMapping(value = "/tweetpoll/vote/{tweetId}", method = RequestMethod.GET)
    public String tweetPollController(
        ModelMap model,
        @PathVariable String tweetId) {
        log.debug("tweetId: "+tweetId);
        String pathVote = "badTweetVote";
        if (tweetId.isEmpty()) {
            log.debug("tweet is empty");
            model.put("message", "Tweet Not Valid..");
        } else {
            tweetId = filterValue(tweetId);
            model.put("tweetId", tweetId);
            log.info("search code->"+tweetId);
            final TweetPollSwitch tweetPoll = getTweetPollService()
                    .getTweetPollDao().retrieveTweetsPollSwitch(tweetId);
            model.addAttribute("switch", tweetPoll);
            //NOTE: tweetpoll should be published to able to vote !!
            if (tweetPoll == null || !tweetPoll.getTweetPoll().getPublishTweetPoll()) {
                log.debug("tweetpoll answer not found");
                model.put("message", "Tweet Not Valid.");
            } else {
                log.info("Validate Votting");
                    final String IP = getIpClient();
                    log.info("IP" + IP);
                    if (getTweetPollService().validateTweetPollIP(IP, tweetPoll.getTweetPoll()) == null) {
                        if (!tweetPoll.getTweetPoll().getCaptcha()) {
                            getTweetPollService().tweetPollVote(tweetPoll, IP);
                            model.put("message", "Tweet Poll Voted.");
                            pathVote = "tweetVoted";
                            log.debug("VOTED");
                        } else {
                            this.createCaptcha(model, tweetId);
                            log.debug("VOTE WITH CAPTCHA");
                            pathVote = "voteCaptcha";
                        }
                    }
                    else{
                        log.debug("Tweet Vote Repeteaded.");
                        model.put("message", "Tweet Vote Repeteaded.");
                        pathVote = "repeatedTweetVote";
                    }
                    model.get("message");
            }
        }
        log.info("redirect template WHERE "+pathVote);
        return pathVote;
    }

    /**
     *
     * @param model
     * @param tweetId
     * @return
     */
    public UtilVoteCaptcha createCaptcha(final ModelMap model, final String tweetId){
        final String captcha = getReCaptcha().createRecaptchaHtml(null, null);
        final UtilVoteCaptcha captchaBean = new UtilVoteCaptcha();
        captchaBean.setCaptcha(captcha);
        captchaBean.setCodeVote(tweetId);
        model.addAttribute("captchaForm", captchaBean);
        return captchaBean;
    }

    /**
     * reCAPTCHA validate process.
     * @param req {@link HttpServletRequest}.
     * @param challenge recaptcha_challenge_field parameter
     * @param response recaptcha_response_field paremeter
     * @param code code vote
     * @param result {@link BindingResult}.
     * @param model {@link ModelMap}.
     * @return view to redirect.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(
            HttpServletRequest req,
            @RequestParam("recaptcha_challenge_field") String challenge,
            @RequestParam("recaptcha_response_field") String response,
            @RequestParam("vote_code") String code,
            @ModelAttribute("captchaForm") UtilVoteCaptcha vote,
            BindingResult result,
            Model model) {
             log.info("recaptcha_challenge_field "+challenge);
             log.info("recaptcha_rforgotesponse_field "+response);
             log.info("code "+code.toString());
             log.info("vote "+vote.toString());
             log.info("model "+model.asMap().toString());
             challenge = filterValue(challenge);
             response = filterValue(response);
             code = filterValue(code);
             vote = (UtilVoteCaptcha) model.asMap().get("captchaForm");
             log.info("vote2--> "+vote.toString());
             //security service
             final SecurityOperations securityService = getServiceManager().getApplicationServices().getSecurityService();
             //check if captcha is valid
             final ReCaptchaResponse reCaptchaResponse = getReCaptcha().checkAnswer(req.getRemoteAddr(), challenge, response);
             //validation layer
             final ValidateOperations validation = new ValidateOperations(securityService);
             validation.validateCaptcha(reCaptchaResponse, result);
             log.info("reCaptchaResponse "+reCaptchaResponse.getErrorMessage());
             log.info("reCaptchaResponse "+reCaptchaResponse.isValid());
             log.info("result.hasErrors() "+result.hasErrors());
             if (result.hasErrors()) {
                //build new reCAPTCHA
                final String errMsg = reCaptchaResponse.getErrorMessage();
                final String html = getReCaptcha().createRecaptchaHtml(errMsg, null);
                vote.setCaptcha(html);
                vote.setCodeVote(code);
                model.addAttribute("captchaForm", vote);
                result.reject("form.problems");
                //reload page.
                return "voteCaptcha";
             } else {
                 //Find Answer To Vote.
                 final TweetPollSwitch tweetPoll = getTweetPollService().getTweetPollDao()
                        .retrieveTweetsPollSwitch(code);
                 //Validate Code.
                 if (tweetPoll == null || !tweetPoll.getTweetPoll().getPublishTweetPoll()) {
                     log.debug("tweetpoll answer not found");
                     return "badTweetVote";
                     //model.addAttribute("message", "Tweet Not Valid.");
                 } else {
                     //save the vote.
                     final String IP = getIpClient();
                     log.info("IP" + IP);
                     getTweetPollService().tweetPollVote(tweetPoll, IP);
                     return "tweetVoted";
                 }
            }
    }

    /**
     * TweetPoll Controller.
     * @param model model
     * @return template
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/tweetpoll/list", method = RequestMethod.GET)
    public String tweetPollController(final ModelMap model) {
        log.debug("tweetpoll");
        return "tweetpoll";
    }

    /**
     * TweetPoll Controller.
     * @param model model
     * @return template
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/user/tweetpoll/new", method = RequestMethod.GET)
    public String newTweetPollController(final ModelMap model) {
        log.debug("tweetpoll new");
        return "tweetpoll/new";
    }

    /**
     * Display {@link TweetPoll} detail view.
     * @param model
     * @param id tweetpoll id
     * @param slug slug question name.
     * @return
     */
    @RequestMapping(value = "/tweetpoll/{id}/{slug}", method = RequestMethod.GET)
    public String detailTweetPollController(
            final ModelMap model,
            @PathVariable Long id,
            @PathVariable String slug) {
        log.debug("detailTweetPollController "+id);
        log.debug("detailTweetPollController "+slug);
        try {
            slug = filterValue(slug);
            final TweetPoll tp = getTweetPollService().getTweetPollByIdSlugName(id, slug);
            model.addAttribute("tweetpoll", ConvertDomainBean.convertTweetPollToBean(tp));
            final List<TweetPollSwitch> answers = getTweetPollService().getTweetPollSwitch(tp);
            model.addAttribute("answers", answers);
            return "tweetpoll/detail";
        } catch (EnMeTweetPollNotFoundException e) {
            log.error(e);
            e.printStackTrace();
            return "404";
        } catch (EnMeNoResultsFoundException e) {
             log.error(e);
             e.printStackTrace();
             return "404";
        }
    }
}