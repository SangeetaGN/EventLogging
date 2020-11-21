package com.disha.quickride.userpropertiesupdate.model;

public class UserInfo {
    private D[] d;

    public D[] getD ()
    {
        return d;
    }

    public void setD (D[] d)
    {
        this.d = d;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [d = "+d+"]";
    }
}
