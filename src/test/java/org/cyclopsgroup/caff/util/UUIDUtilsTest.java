package org.cyclopsgroup.caff.util;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.UUID;
import org.junit.Test;

/**
 * Test of {@link UUIDUtils}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class UUIDUtilsTest {
  /** Verify encoding and decoding for 100 times with random UUIDs */
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
    assertThat(newId).isEqualTo(id);
  }
}
