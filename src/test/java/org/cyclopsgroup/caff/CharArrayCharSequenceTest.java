package org.cyclopsgroup.caff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Unit test of {@link CharArrayCharSequence}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class CharArrayCharSequenceTest {
  private static void assertContentEquals(String expected, CharSequence s) {
    char[] chars = new char[s.length()];
    for (int i = 0; i < s.length(); i++) {
      chars[i] = s.charAt(i);
    }
    assertEquals(expected, new String(chars));
    assertEquals(expected, s.toString());
  }

  /**
   * Verify index out of bounds
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testCharAtOutOfBounds() {
    new CharArrayCharSequence("12345".toCharArray()).charAt(5);
  }

  /**
   * Verify index out of bounds
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testCharAtOutOfOffset() {
    new CharArrayCharSequence("12345".toCharArray()).subSequence(2, 4).charAt(3);
  }

  /**
   * Verify overflow length is not accepted
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testLengthOverflowSubSequence() {
    new CharArrayCharSequence("123".toCharArray()).subSequence(0, 4);
  }

  /**
   * Verify overflow offset is not accepted
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testOffsetOverflowSubSequence() {
    new CharArrayCharSequence("123".toCharArray()).subSequence(2, 4);
  }

  /**
   * Verify constructor with offset does right job
   */
  @Test
  public void testOffsetSubSequence() {
    assertContentEquals("34", new CharArrayCharSequence("12345".toCharArray()).subSequence(2, 4));
  }

  /**
   * Verify that full subsequence returns itself
   */
  @Test
  public void testSameSubSequence() {
    CharArrayCharSequence s = new CharArrayCharSequence("12345".toCharArray());
    assertSame(s, s.subSequence(0, 5));
  }

  /**
   * Verify {@link CharArrayCharSequence#sequenceToArray(CharSequence)}
   */
  @Test
  public void testSequenceToArray() {
    assertEquals("abc", new String(CharArrayCharSequence.sequenceToArray("abc")));
  }

  /**
   * Verify simple constructor does right job
   */
  @Test
  public void testSimpleConstructor() {
    assertContentEquals("12345", new CharArrayCharSequence("12345".toCharArray()));
  }
}
