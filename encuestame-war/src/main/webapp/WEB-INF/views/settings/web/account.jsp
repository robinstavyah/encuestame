<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="defaultMarginWrapper">
    
    <!-- 
    <h1>
        <spring:message code="settings.config.title" />
    </h1>
     --> 
    <div dojoType="encuestame.org.core.shared.utils.SettingsMenuSwitch">
           <!-- Profile Widget -->
          <div id="profile_settings" dojoType="encuestame.org.core.commons.profile.Profile" data-label="<spring:message code="settings.config.myaccount" />"></div>
          <!-- Upload Profile Image Widget -->
          <div id="upload_image_settings" dojoType="encuestame.org.core.commons.profile.UploadProfilePicture" data-label="<spring:message code="settings.config.upload.image" />"></div>
    </div>   
  <!--   <div style="width: 900px; height: 500px;">
        <div dojoType="dijit.layout.TabContainer" style="width: 100%; height: 100%;">
            <div dojoType="dijit.layout.ContentPane" title="Your Account" selected="true">
                 <enme:widget type="encuestame.org.core.commons.profile.Profile"></enme:widget>
            </div>
            <div dojoType="dijit.layout.ContentPane" title="Picture" style="border: 0 none;">
                 <div dojoType="encuestame.org.core.commons.profile.UploadProfilePicture" source="" username="${username}"></div>
           </div>
           <div dojoType="dijit.layout.ContentPane" title="Password" style="border: 0 none;"
                closable="false">
                Change password support.
           </div>
           <div dojoType="dijit.layout.ContentPane" title="Email and Notifications" style="border: 0 none;"
                closable="false">
                Email Notifications.
            </div>
            <div dojoType="dijit.layout.ContentPane" title="Public Profile" style="border: 0 none;"
                closable="false">
                Public Profile Configuration
            </div>
        </div>
    </div>-->
</div>
