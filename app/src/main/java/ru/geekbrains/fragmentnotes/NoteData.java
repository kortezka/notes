package ru.geekbrains.fragmentnotes;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteData implements Parcelable {
    private String name;
    private String discriptionShort;
    private String discriptionFull;


    public NoteData(String name, String discriptionShort, String discriptionFull) {
        this.name = name;
        this.discriptionShort = discriptionShort;
        this.discriptionFull = discriptionFull;
    }


    public String getDiscriptionShort() {
        return discriptionShort;
    }

    public String getName() {
        return name;
    }

    public String getDiscriptionFull() {
        return discriptionFull;
    }

    protected NoteData(Parcel in) {
        discriptionShort = in.readString();
        name = in.readString();
        discriptionFull = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(discriptionShort);
        dest.writeString(name);
        dest.writeString(discriptionFull);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteData> CREATOR = new Creator<NoteData>() {
        @Override
        public NoteData createFromParcel(Parcel in) {
            return new NoteData(in);
        }

        @Override
        public NoteData[] newArray(int size) {
            return new NoteData[size];
        }
    };
}
