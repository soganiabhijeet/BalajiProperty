package com.example.balajiproperty.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SMSModel implements Parcelable {
    public void setTextToSend(String textToSend) {
        this.textToSend = textToSend;
    }

    private String textToSend;
    private Integer id;

    public Boolean getToSend() {
        return toSend;
    }

    public void setToSend(Boolean toSend) {
        this.toSend = toSend;
    }

    private Boolean toSend;

    public SMSModel(String mNavigatorName, Integer id, Boolean toSend) {
        this.textToSend = mNavigatorName;
        this.id = id;
        this.toSend = toSend;
    }

    private SMSModel(Parcel in) {
        textToSend = in.readString();
    }

    public String getNavigatorName() {
        return textToSend;
    }

    public static final Creator<SMSModel> CREATOR = new Creator<SMSModel>() {
        @Override
        public SMSModel createFromParcel(Parcel in) {
            return new SMSModel(in);
        }

        @Override
        public SMSModel[] newArray(int size) {
            return new SMSModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(textToSend);
    }
}
