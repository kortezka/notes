package ru.geekbrains.fragmentnotes;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private String type;
    private int discription;

    public Note(String type, int discription) {
        this.type = type;
        this.discription = discription;
    }

    public String getType() {
        return type;
    }

    public int getDiscription() {
        return discription;
    }

    protected Note(Parcel in) {
        type = in.readString();
        discription = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeInt(discription);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
