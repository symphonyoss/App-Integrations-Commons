package org.symphonyoss.integration.parser.model;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link HashTag}
 * Created by crepache on 20/06/17.
 */
public class HashTagTest {

  private static final String EXPECTED_HASH_TAG = "<hash tag=\"#HashTag\"/>";
  private static final String MOCK_HASH_TAG = "#HashTag";

  @Test
  public void testHashTag() {
    HashTag hashTag = new HashTag(MOCK_HASH_TAG);

    Assert.assertEquals(EXPECTED_HASH_TAG, hashTag.toString());
  }

  @Test
  public void testHashTagNull() {
    HashTag hashTag = new HashTag(null);

    Assert.assertEquals(StringUtils.EMPTY, hashTag.toString());
  }

}
