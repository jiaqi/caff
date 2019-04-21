package org.cyclopsgroup.caff.conversion;

import org.cyclopsgroup.caff.NormalizedValue;

/**
 * A enum for testing purpose
 *
 * @author <a href="mailto:jiaqi.guo@gmail.com">Jiaqi Guo</a>
 */
public enum NormalizedEnum implements NormalizedValue<Integer> {
  /** With ID <code>1</code> */
  X(1),
  /** With ID <code>2</code> */
  Y(2),
  /** With ID <code>3</code> */
  Z(3);
  private final int id;

  private NormalizedEnum(int id) {
    this.id = id;
  }

  @Override
  public Integer getIdentifier() {
    return id;
  }
}
