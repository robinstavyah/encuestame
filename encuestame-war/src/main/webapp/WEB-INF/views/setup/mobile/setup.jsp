<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_init.jsp"%>
    <form:form method="post">
        <c:if test="${status != null}">
            <c:if test="${status == 'install'}">
                <div class="setup-description">
                    <%@ include file="/WEB-INF/jsp/includes/setup/setup_step1.jsp"%>
                </div>
                <input type="submit" name="_eventId_install-submit" value="Install" />
            </c:if>
            <c:if test="${state == 'upgrade'}">
                <div class="setup-description">
                    <p>
                        RELEASE NOTES for:
                        <%=EnMePlaceHolderConfigurer
                                .getProperty("app.version")%></p>
                    <div class="release-notes"></div>
                </div>
                <input type="submit" name="_eventId_upgrade-submit" value="Upgrade" />
            </c:if>
        </c:if>
        <c:if test="${status == null}">
            <h3 class="error">Oh oh ! Something wrong on (installation /
                upgrade) process.</h3>
        </c:if>
    </form:form>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_finish.jsp"%>