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
 **/

package org.symphonyoss.integration.api.client.form;

import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA_TYPE;
import static org.junit.Assert.assertEquals;

import org.glassfish.jersey.media.multipart.MultiPart;
import org.junit.Test;
import org.symphonyoss.integration.exception.RemoteApiException;

import javax.ws.rs.client.Entity;

/**
 * Unit test for {@link MultiPartEntitySerializer}
 * Created by rsanchez on 27/03/17.
 */
public class MultiPartEntitySerializerTest {

  private MultiPartEntitySerializer serializer = new MultiPartEntitySerializer();

  @Test
  public void testSerialize() throws RemoteApiException {
    MultiPart input = new MultiPart();

    Entity result = serializer.serialize(input);

    Entity<MultiPart> expected = Entity.entity(input, MULTIPART_FORM_DATA_TYPE);

    assertEquals(expected, result);
  }
}
