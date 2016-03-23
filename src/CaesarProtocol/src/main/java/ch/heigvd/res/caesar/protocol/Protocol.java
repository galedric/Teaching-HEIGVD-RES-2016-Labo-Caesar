package ch.heigvd.res.caesar.protocol;

/**
 *
 * @author Olivier Liechti
 */
public class Protocol {
  public static final int CLIENT_HELLO = 0xCE;
  public static final int SERVER_HELLO = 0x5E;
  public static final int SEND = 0x5D;
  public static final int MESSAGE = 0xCA;
  public static final int KEY = 0xCE;
  public static final int LOGIN = 0x10;
  public static final int LOGIN_OK = 0x15;
  public static final int LOGIN_FAIL = 0x1F;
}
