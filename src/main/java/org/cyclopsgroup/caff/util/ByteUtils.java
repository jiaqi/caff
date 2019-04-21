package org.cyclopsgroup.caff.util;

/**
 * Utilities around byte
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public final class ByteUtils {
  private ByteUtils() {}

  /**
   * Write long value to given byte array
   *
   * @param value Value to write, it can be any long value
   * @param dest Destination byte array value is written to
   * @param offset The starting point of where the value is written
   * @throws IllegalArgumentException When input byte array doesn't have enough space to write a
   *     long
   */
  public static void writeLong(long value, byte[] dest, int offset)
      throws IllegalArgumentException {
    if (dest.length < offset + 8) {
      throw new IllegalArgumentException(
          "Destination byte array does not have enough space to write long from offset " + offset);
    }
    long t = value;
    for (int i = offset; i < offset + 8; i++) {
      dest[i] = (byte) (t & 0xff);
      t = t >> 8;
    }
  }

  /**
   * Read long value from given source byte array
   *
   * @param src Source byte array value is read from
   * @param offset The starting point where value is read from
   * @return The long value
   * @throws IllegalArgumentException When the length of input byte array conflicts offset
   */
  public static long readLong(byte[] src, int offset) throws IllegalArgumentException {
    if (src.length < offset) {
      throw new IllegalArgumentException("There's nothing to read in src from offset " + offset);
    }

    long r = 0;
    for (int i = offset, j = 0; i < src.length && j < 64; i++, j += 8) {
      r += ((long) src[i] & 0xff) << j;
    }
    return r;
  }
}
