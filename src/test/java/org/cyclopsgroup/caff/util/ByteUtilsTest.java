package org.cyclopsgroup.caff.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test of {@link ByteUtils}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class ByteUtilsTest {
  private void readWriteAndVerify(long value) {
    byte[] bytes = new byte[20];
    ByteUtils.writeLong(value, bytes, 10);
    long r = ByteUtils.readLong(bytes, 10);
    assertEquals(value, r);
  }

  /** Verify with long value 100 */
  @Test
  public void testSmallValue() {
    readWriteAndVerify(100L);
  }

  /** Verify with {@link Long#MAX_VALUE} */
  @Test
  public void testMaxValue() {
    readWriteAndVerify(Long.MAX_VALUE);
  }

  /** Verify with {@link Long#MIN_VALUE} */
  @Test
  public void testMinValue() {
    readWriteAndVerify(Long.MIN_VALUE);
  }

  /** Verify with Zero */
  @Test
  public void testZero() {
    readWriteAndVerify(0);
  }
}
