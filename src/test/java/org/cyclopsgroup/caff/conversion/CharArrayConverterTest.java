package org.cyclopsgroup.caff.conversion;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link CharArrayConverter}
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public class CharArrayConverterTest {
  /** Verify two way conversion */
  @Test
  public void testConversion() {
    CharArrayConverter c = new CharArrayConverter();

    assertEquals("abc", new String(c.fromCharacters("abc")));
    assertEquals("abc", c.toCharacters("abc".toCharArray()).toString());
  }
}
