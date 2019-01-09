package org.cyclopsgroup.caff.ref.test;

import static com.google.common.truth.Truth.assertThat;
import java.util.function.Supplier;
import org.cyclopsgroup.caff.ref.ValueReference;
import org.junit.Test;

public class FieldValueReferenceTest {
  private static class PrivateType {
    String packagePrivateField;
    private String privateField;
  }

  public class PublicType {
    public String publicField;
  }

  @Test
  public void testPackagePrivateAccess() {
    PrivateType o = new PrivateType();
    verifyFieldAccess("packagePrivateField", () -> o.packagePrivateField, o);
  }

  @Test
  public void testPrivateAccess() throws NoSuchFieldException, SecurityException {
    PrivateType o = new PrivateType();
    verifyFieldAccess("privateField", () -> o.privateField, o);
  }

  @Test
  public void testPublicAccess() {
    PublicType o = new PublicType();
    verifyFieldAccess("publicField", () -> o.publicField, o);
  }

  private <T> void verifyFieldAccess(String fieldName, Supplier<String> readerFn, T owner) {
    assertThat(readerFn.get()).isNull();
    ValueReference<T> ref;
    try {
      ref = ValueReference.instanceOf(owner.getClass().getDeclaredField(fieldName));
    } catch (NoSuchFieldException | SecurityException e) {
      throw new IllegalStateException("Can't find field " + fieldName + " from object " + owner, e);
    }
    assertThat(ref.isReadable()).isTrue();
    assertThat(ref.isWritable()).isTrue();
    assertThat(ref.readValue(owner)).isNull();
    ref.writeValue("abc", owner);
    assertThat(readerFn.get()).isEqualTo("abc");
    assertThat(ref.readValue(owner)).isEqualTo("abc");
  }
}
