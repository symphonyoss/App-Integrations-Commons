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

package org.symphonyoss.integration.authorization.oauth.v1;

import static org.junit.Assert.assertEquals;
import static org.symphonyoss.integration.authorization.oauth.v1.OAuth1ProviderTest.CONSUMER_KEY;
import static org.symphonyoss.integration.authorization.oauth.v1.OAuth1ProviderTest.PRIVATE_KEY;
import static org.symphonyoss.integration.authorization.oauth.v1.OAuth1ProviderTest
    .REQUEST_TEMPORARY_TOKEN_URL;
import static org.symphonyoss.integration.authorization.oauth.v1.OAuth1ProviderTest.TOKEN;

import com.google.api.client.http.HttpResponseException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for {@link OAuth1GetAccessToken}.
 * Created by campidelli on 25-jul-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class OAuth1GetAccessTokenTest {

  @Test
  public void testConstructor() {
    OAuth1GetAccessToken token = new OAuth1GetAccessToken(
        REQUEST_TEMPORARY_TOKEN_URL, CONSUMER_KEY, PRIVATE_KEY,
        TOKEN, StringUtils.EMPTY);

    assertEquals(CONSUMER_KEY, token.consumerKey);
    assertEquals(TOKEN, token.temporaryToken);
    assertEquals(StringUtils.EMPTY, token.verifier);
  }

  @Test(expected = HttpResponseException.class)
  public void testInvalidExecution() throws Exception {
    OAuth1GetAccessToken token = new OAuth1GetAccessToken(
        REQUEST_TEMPORARY_TOKEN_URL, CONSUMER_KEY, PRIVATE_KEY,
        TOKEN, StringUtils.EMPTY);
    token.getValue();
  }
}
