package org.cyclopsgroup.caff.format;

import java.util.Date;
import org.cyclopsgroup.caff.conversion.DateField;

/**
 * A bean for CSV format testing
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
@CSVType(fields = 5)
public class CSVBean {
  private String firstName;

  /** A public field */
  @CSVField(position = 0)
  public String lastName;

  private int age;

  private Date birthDay;

  /** @return A date field with {@link DateField} */
  @DateField(format = "yyyyMMdd")
  @CSVField(position = 4)
  public Date getBirthDay() {
    return birthDay;
  }

  /** @param birthDay A date field */
  public void setBirthDay(Date birthDay) {
    this.birthDay = birthDay;
  }

  /** @return A string field */
  @CSVField(position = 1)
  public String getFirstName() {
    return firstName;
  }

  /** @param firstName A string field */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /** @return An integer field */
  @CSVField(position = 3)
  public int getAge() {
    return age;
  }

  /** @param age An integer field */
  public void setAge(int age) {
    this.age = age;
  }
}
