package com.ddyggu.bean;

public class SuperviseSession
{
  private boolean maintainSession = false;

  public SuperviseSession(boolean maintainSession) {
    this.maintainSession = maintainSession;
  }

  public boolean isMaintainSession() {
    return this.maintainSession;
  }

  public void setMaintainSession(boolean maintainSession) {
    this.maintainSession = maintainSession;
  }
}