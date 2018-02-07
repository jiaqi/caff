package org.cyclopsgroup.caff.token;

/**
 * A handler that does something for each token
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public interface TokenEventHandler {
  /**
   * @param event Event that tells a token is found
   */
  void handleEvent(TokenEvent event);
}
