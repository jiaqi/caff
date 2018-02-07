package org.cyclopsgroup.caff.conversion;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.cyclopsgroup.caff.ABean;
import org.junit.Before;
import org.junit.Test;

/**
 * A test that covers {@link DateField} annotation based conversion
 * 
 * @author jiaqi
 */
public class DateFieldTest {
  private Converter<Date> converter;
  private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * Setting up a converter that calls {@link DateField}
   * 
   * @throws Exception
   */
  @Before
  public void setUpAnnotatedConverter() throws Exception {
    Method getBirthDay = ABean.class.getMethod("getBirthDay");
    converter = new AnnotatedConverter<Date>(Date.class, getBirthDay);
  }

  /**
   * Verify {@link DateField} correctly implies conversion rule of string to {@link Date} conversion
   */
  @Test
  public void testConverterToDate() {
    Date date = converter.fromCharacters("19781102");
    assertEquals("1978-11-02", format.format(date));
  }

  /**
   * Verify {@link DateField} correctly implies conversion rule of {@link Date} to characters
   * conversion
   * 
   * @throws ParseException Allows string to date parsing error
   */
  @Test
  public void testConvertToCharacters() throws ParseException {
    CharSequence result = converter.toCharacters(format.parse("1978-11-02"));
    assertEquals("19781102", result.toString());
  }
}
