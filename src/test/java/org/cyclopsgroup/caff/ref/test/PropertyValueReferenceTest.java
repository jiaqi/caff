package org.cyclopsgroup.caff.ref.test;

import static com.google.common.truth.Truth.assertThat;
import java.util.function.Supplier;
import org.cyclopsgroup.caff.ref.ValueReference;
import org.junit.Test;

public class PropertyValueReferenceTest {
  private static class PrivateType {
    private String packagePrivateField, privateField;

    @SuppressWarnings("unused")
    String getPackagePrivateField() {
      return packagePrivateField;
    }

    @SuppressWarnings("unused")
    private String getPrivateField() {
      return privateField;
    }

    @SuppressWarnings("unused")
    void setPackagePrivateField(String packagePrivateField) {
      this.packagePrivateField = packagePrivateField;
    }

    @SuppressWarnings("unused")
    private void setPrivateField(String privateField) {
      this.privateField = privateField;
    }
  }

  public static class PublicType {
    private String publicField;

    public String getPublicField() {
      return publicField;
    }

    public void setPublicField(String publicField) {
      this.publicField = publicField;
    }
  }

  @Test
  public void testPackagePrivateAccess() {
    PrivateType o = new PrivateType();
    verifyPropertyAccess(o, () -> o.packagePrivateField, "packagePrivateField");
  }

  @Test
  public void testPrivateAccess() {
    PrivateType o = new PrivateType();
    verifyPropertyAccess(o, () -> o.privateField, "privateField");
  }

  /**
   * Verify property value reference correctly reference getter and setter
   * 
   * @throws Exception Allows all exceptions
   */
  @Test
  public void testPublicAccess() {
    PublicType o = new PublicType();
    verifyPropertyAccess(o, o::getPublicField, "publicField");
  }

  @SuppressWarnings("unchecked")
  private <T> void verifyPropertyAccess(T owner, Supplier<String> readFn, String propertyName) {
    ValueReference<T> ref =
        ValueReference.forProperty(propertyName, (Class<T>) owner.getClass(), String.class);
    assertThat(ref.isReadable()).isTrue();
    assertThat(ref.isWritable()).isTrue();
    assertThat(ref.readValue(owner)).isNull();
    assertThat(readFn.get()).isNull();
    ref.writeValue("abc", owner);
    assertThat(ref.readValue(owner)).isEqualTo("abc");
    assertThat(readFn.get()).isEqualTo("abc");
  }
}
