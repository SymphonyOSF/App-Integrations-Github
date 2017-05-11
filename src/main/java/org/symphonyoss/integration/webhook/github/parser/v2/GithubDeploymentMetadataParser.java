package org.symphonyoss.integration.webhook.github.parser.v2;

import static org.symphonyoss.integration.webhook.github.GithubEventConstants
    .GITHUB_EVENT_DEPLOYMENT;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import org.symphonyoss.integration.model.yaml.IntegrationProperties;
import org.symphonyoss.integration.service.UserService;
import org.symphonyoss.integration.webhook.github.GithubEventConstants;
import org.symphonyoss.integration.webhook.github.GithubEventTags;
import org.symphonyoss.integration.webhook.github.parser.GithubParserUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by crepache on 09/05/17.
 */
@Component
public class GithubDeploymentMetadataParser extends GithubMetadataParser {


  private static final String METADATA_FILE = "metadataGithubDeployment.xml";

  private static final String TEMPLATE_FILE = "templateGithubDeployment.xml";

  public GithubDeploymentMetadataParser(UserService userService, GithubParserUtils utils, IntegrationProperties integrationProperties) {
    super(userService, utils, integrationProperties);
  }

  @Override
  public List<String> getEvents() {
    return Arrays.asList(GITHUB_EVENT_DEPLOYMENT);
  }

  @Override
  protected String getTemplateFile() {
    return TEMPLATE_FILE;
  }

  @Override
  protected String getMetadataFile() {
    return METADATA_FILE;
  }

  @Override
  protected void preProcessInputData(JsonNode input) {
    proccessURLIconIntegration(input);
    proccessUserGithub(input);
  }

  private void proccessURLIconIntegration(JsonNode node) {
    String urlIconIntegration = getURLFromIcon("github_logo.svg");

    if (!urlIconIntegration.isEmpty()) {
      ((ObjectNode) node).put(GithubEventConstants.URL_ICON_INTEGRATION, urlIconIntegration);
    }
  }

  private void proccessUserGithub(JsonNode node) {
    JsonNode nodeCreator = node.path(GithubEventTags.DEPLOYMENT_TAG).path(GithubEventTags.CREATOR_TAG);

    ((ObjectNode) nodeCreator).put(GithubEventTags.LOGIN_TAG, getGithubUserPublicName(nodeCreator));
  }

}