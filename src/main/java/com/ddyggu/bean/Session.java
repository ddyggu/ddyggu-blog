package com.ddyggu.bean;

public class Session
{
  private boolean maintainSession = false;

  public Session(boolean maintainSession) {
    this.maintainSession = maintainSession;
  }

  public boolean isMaintainSession() {
    return this.maintainSession;
  }

  public void setMaintainSession(boolean maintainSession) {
    this.maintainSession = maintainSession;
  }
}
