package org.cyclopsgroup.caff.util;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

/**
 * Utilities around {@link UUID}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public final class UUIDUtils {
  /**
   * Convert byte array into UUID. Byte array is a compact form of bits in UUID
   *
   * @param bytes Byte array to convert from
   * @return UUID result
   */
  public static UUID fromBytes(byte[] bytes) {
    long mostBits = ByteUtils.readLong(bytes, 0);
    long leastBits = 0;
    if (bytes.length > 8) {
      leastBits = ByteUtils.readLong(bytes, 8);
    }
    return new UUID(mostBits, leastBits);
  };

  /**
   * Convert string into UUID. String is a compact base64 form of bits in UUID
   *
   * @param id String expression of UUID
   * @return UUID result
   */
  public static UUID fromString(String id) {
    return fromBytes(Base64.decodeBase64(id));
  }

  /**
   * Convert UUID in compact form of byte array
   *
   * @param id UUID to convert from
   * @return Byte array result
   */
  public static byte[] toBytes(UUID id) {
    byte[] bytes = new byte[16];
    ByteUtils.writeLong(id.getMostSignificantBits(), bytes, 0);
    ByteUtils.writeLong(id.getLeastSignificantBits(), bytes, 8);
    return bytes;
  }

  /**
   * Convert UUID into form of base64 string without = and +
   *
   * @param id UUID to convert from
   * @return String result
   */
  public static String toString(UUID id) {
    return Base64.encodeBase64URLSafeString(toBytes(id));
  }

  /**
   * Generate a random UUID and return as a base 64 string
   *
   * @return Base 64 string form of a random UUID generated from {@link UUID#randomUUID()}
   */
  public static String randomStringId() {
    return toString(UUID.randomUUID());
  }

  private UUIDUtils() {}
}
