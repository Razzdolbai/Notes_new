package dimon.notes_new;

/**
 * Created by 12345 on 18.04.2017.
 */

//public class MyNotes {
//}
import java.io.Serializable;

@SuppressWarnings("serial")
public class MyNotes implements Serializable{

    private long id;
    private String note;

    public MyNotes (long id, String note) {
        this.id = id;
        this.note = note;
    }

    public long getID () {return id;}
    public String getNote () {return note;}
}