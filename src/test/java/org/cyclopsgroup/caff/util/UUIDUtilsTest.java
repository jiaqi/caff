package org.cyclopsgroup.caff.util;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

/**
 * Test of {@link UUIDUtils}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class UUIDUtilsTest {
  /**
   * Verify encoding and decoding for 100 times with random UUIDs
   */
  @Test
  public void testHundredRandomIds() {
    for (int i = 0; i < 100; i++) {
      verifyRandomId();
    }
  }

  private void verifyRandomId() {
    UUID id = UUID.randomUUID();
    String s = UUIDUtils.toString(id);
    assertEquals(22, s.length());
    UUID newId = UUIDUtils.fromString(s);
    assertEquals("Random UUID " + id + " failed during encoding and decoding matching", id, newId);
  }
}
