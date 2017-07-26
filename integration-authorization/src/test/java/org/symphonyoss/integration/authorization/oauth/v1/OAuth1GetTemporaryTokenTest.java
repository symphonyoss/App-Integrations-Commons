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
import static org.symphonyoss.integration.authorization.oauth.v1.OAuth1ProviderTest
    .AUTHORIZATION_CALLBACK_URL;
import static org.symphonyoss.integration.authorization.oauth.v1.OAuth1ProviderTest.CONSUMER_KEY;
import static org.symphonyoss.integration.authorization.oauth.v1.OAuth1ProviderTest.PRIVATE_KEY;
import static org.symphonyoss.integration.authorization.oauth.v1.OAuth1ProviderTest
    .REQUEST_TEMPORARY_TOKEN_URL;

import com.google.api.client.http.HttpResponseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

/**
 * Unit tests for {@link OAuth1GetTemporaryToken}.
 * Created by campidelli on 25-jul-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class OAuth1GetTemporaryTokenTest {

  @Test
  public void testConstructor() {
    OAuth1GetTemporaryToken token = new OAuth1GetTemporaryToken(
        REQUEST_TEMPORARY_TOKEN_URL, CONSUMER_KEY, PRIVATE_KEY,
        AUTHORIZATION_CALLBACK_URL);

    assertEquals(CONSUMER_KEY, token.consumerKey);
    assertEquals(AUTHORIZATION_CALLBACK_URL, token.callback);
  }

  @Test(expected = HttpResponseException.class)
  public void testInvalidExecution() throws Exception {
    OAuth1GetTemporaryToken token = new OAuth1GetTemporaryToken(
        REQUEST_TEMPORARY_TOKEN_URL, CONSUMER_KEY, PRIVATE_KEY,
        AUTHORIZATION_CALLBACK_URL);
    token.getValue();
  }

  @Test(expected = OAuth1Exception.class)
  public void testInvalidPrivateKey() {
    new OAuth1GetTemporaryToken(REQUEST_TEMPORARY_TOKEN_URL, CONSUMER_KEY, "?",
        AUTHORIZATION_CALLBACK_URL);
  }
}
