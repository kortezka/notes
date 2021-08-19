package ru.geekbrains.fragmentnotes;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class CardDataMapping {

    public static class Fields{
        public final static String CAPTION = "caption";
        public final static String SHORTDES = "sdes";
        public final static String FULLDES = "fdes";
    }

    public static NoteData toCardData(String id, Map<String, Object> doc) {
      //  long indexPic = (long) doc.get(Fields.PICTURE);
        //Timestamp timeStamp = (Timestamp)doc.get(Fields.DATE);
        NoteData answer = new NoteData((String) doc.get(Fields.CAPTION),
                (String) doc.get(Fields.SHORTDES),
        //        PictureIndexConverter.getPictureByIndex((int) indexPic),
                (String)  doc.get(Fields.FULLDES));
       //         timeStamp.toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(NoteData noteData){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.CAPTION, noteData.getName());
        answer.put(Fields.SHORTDES, noteData.getDiscriptionShort());
        answer.put(Fields.FULLDES, noteData.getDiscriptionFull());
        return answer;
    }
}
