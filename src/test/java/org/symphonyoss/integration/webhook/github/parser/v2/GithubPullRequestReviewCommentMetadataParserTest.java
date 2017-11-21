/**
 * Copyright 2016-2017 Symphony Integrations - Symphony LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.symphonyoss.integration.webhook.github.parser.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.symphonyoss.integration.webhook.github.GithubEventConstants
    .GITHUB_EVENT_PULL_REQUEST_REVIEW_COMMENT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.symphonyoss.integration.utils.SimpleFileUtils;
import org.symphonyoss.integration.webhook.github.parser.GithubParserException;
import org.symphonyoss.integration.webhook.github.parser.GithubParserTest;

import java.io.IOException;
import java.util.List;

/**
 * Unit test class for {@link GithubPullRequestReviewCommentMetadataParser}
 * Created by campidelli on 11/05/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class GithubPullRequestReviewCommentMetadataParserTest
    extends GithubParserTest<GithubPullRequestReviewCommentMetadataParser> {

  private static final String PAYLOAD_FILE_PR_REVIEW_COMMENT =
      "parser/pullRequestReviewComment/payload_xgithubevent_pullRequestReviewComment.json";
  private static final String EXPECTED_FILE_PR_REVIEW_COMMENT =
      "parser/pullRequestReviewComment/v2/expected_xgithub_event_pull_request_review_comment.json";

  @Override
  protected String getExpectedTemplate() throws IOException {
    return SimpleFileUtils.readFile("templates/templateGithubPullRequestReviewComment.xml");
  }

  @Override
  protected GithubPullRequestReviewCommentMetadataParser getParser() {
    return new GithubPullRequestReviewCommentMetadataParser(userService, utils,
        integrationProperties);
  }

  @Test
  public void testSupportedEvents() {
    List<String> events = getParser().getEvents();
    assertNotNull(events);
    assertEquals(1, events.size());
    assertEquals(GITHUB_EVENT_PULL_REQUEST_REVIEW_COMMENT, events.get(0));
  }

  @Test
  public void testPRReviewComment() throws IOException, GithubParserException {
    testParser(PAYLOAD_FILE_PR_REVIEW_COMMENT, EXPECTED_FILE_PR_REVIEW_COMMENT);
  }
}

