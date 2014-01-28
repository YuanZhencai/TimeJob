package com.ibm.filenet.helper.pe;

public class BPMException extends Exception
{
  public BPMException()
  {
  }

  public BPMException(String msg)
  {
    super(msg);
  }

  public BPMException(String firstMsg, Object[] otherMsgs) {
    this(firstMsg, toStrings(otherMsgs));
  }

  public BPMException(String firstMsg, String[] otherMsgs)
  {
    super(constructMsg(firstMsg, otherMsgs));
  }

  public BPMException(Throwable cause)
  {
    super(cause);
  }

  public BPMException(String msg, Throwable cause)
  {
    super(msg, cause);
  }

  private static String constructMsg(String firstMsg, String[] otherMsgs)
  {
    StringBuffer strBuf = new StringBuffer();
    strBuf.append(firstMsg + "\n");
    for (int i = 0; i < otherMsgs.length; ++i) {
      strBuf.append(otherMsgs[i] + "\n");
    }
    return strBuf.toString();
  }

  private static String[] toStrings(Object[] objs)
  {
    int length = objs.length;
    String[] strs = new String[length];
    for (int i = 0; i < length; ++i) {
      strs[i] = objs[i].toString();
    }
    return strs;
  }
}