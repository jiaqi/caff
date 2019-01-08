package org.cyclopsgroup.caff.ref;

import static com.google.common.truth.Truth.assertThat;
import java.util.function.Supplier;
import org.junit.Test;

/**
 * Test case of {@link FieldValueReference}
 * 
 * @author jiaqi
 */
public class FieldValueReferenceTest {
  static class PrivateType {
    String packagePrivateField;

    @SuppressWarnings("unused")
    private String privateField;
  }

  public class PublicType {
    String publicField;
  }

  @Test
  public void testPackagePrivateAccess() {
    final PrivateType o = new PrivateType();
    verifyFieldReference("packagePrivateField", () -> o.packagePrivateField, o);
  }

  @Test(expected = AccessFailureException.class)
  public void testPrivateAccess() throws NoSuchFieldException, SecurityException {
    PrivateType o = new PrivateType();
    ValueReference<PrivateType> ref =
        ValueReference.instanceOf(PrivateType.class.getDeclaredField("privateField"));
    ref.readValue(o);
  }

  @Test
  public void testPublicAccess() {
    final PublicType o = new PublicType();
    verifyFieldReference("publicField", () -> o.publicField, o);
  }

  private <T> void verifyFieldReference(String fieldName, Supplier<String> readerFn, T owner) {
    assertThat(readerFn.get()).isNull();
    ValueReference<T> ref;
    try {
      ref = ValueReference.instanceOf(owner.getClass().getDeclaredField(fieldName));
    } catch (NoSuchFieldException | SecurityException e) {
      throw new IllegalStateException("Can't find field " + fieldName + " from object " + owner, e);
    }
    assertThat(ref.readValue(owner)).isNull();
    ref.writeValue("abc", owner);
    assertThat(readerFn.get()).isEqualTo("abc");
    assertThat(ref.readValue(owner)).isEqualTo("abc");
  }
}
