package com.Ecomaxgo.maxpay.kiosk_status;

public enum ModelObject {

    RED(R.string.attendance, R.layout.view_red),
    BLUE(R.string.LeaveRequest, R.layout.view_blue);


    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
