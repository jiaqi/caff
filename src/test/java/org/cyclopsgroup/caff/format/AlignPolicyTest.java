package org.cyclopsgroup.caff.format;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test of {@link AlignPolicy}
 *
 * @author jiaqi
 */
public class AlignPolicyTest {
  /** Verify that trim does right job */
  @Test
  public void testTrim() {
    assertEquals("abcd", AlignPolicy.LEFT.trim("abcd00", '0'));
    assertEquals("00abc", AlignPolicy.LEFT.trim("00abc", '0'));
    assertEquals("abcd", AlignPolicy.RIGHT.trim("00abcd", '0'));
    assertEquals("abcd00", AlignPolicy.RIGHT.trim("00abcd00", '0'));
  }

  private String fillWith(String src, AlignPolicy policy) {
    char[] dest = new char[5];
    policy.fill(src, dest, 0, 5, '0');
    return new String(dest);
  }

  /** Verify fill function */
  @Test
  public void testFill() {
    assertEquals("abc00", fillWith("abc", AlignPolicy.LEFT));
    assertEquals("abcde", fillWith("abcde", AlignPolicy.LEFT));
    assertEquals("00abc", fillWith("abc", AlignPolicy.RIGHT));
    assertEquals("abcde", fillWith("abcde", AlignPolicy.RIGHT));
  }
}
