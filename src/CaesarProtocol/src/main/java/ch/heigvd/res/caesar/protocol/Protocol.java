package ch.heigvd.res.caesar.protocol;

/**
 * @author Olivier Liechti
 */
public class Protocol {
  public static final byte CLIENT_HELLO = 0x40;
  public static final byte SERVER_HELLO = 0x5E;
  public static final byte SEND = 0x5D;
  public static final byte MESSAGE = 0x4A;
  public static final byte KEY = 0x4E;
  public static final byte LOGIN = 0x10;
  public static final byte LOGIN_OK = 0x15;
  public static final byte LOGIN_FAIL = 0x1F;
}
