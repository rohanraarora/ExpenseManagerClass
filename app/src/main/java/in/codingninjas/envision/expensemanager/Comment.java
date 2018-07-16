package in.codingninjas.envision.expensemanager;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity()
public class Comment {

    @PrimaryKey(autoGenerate = true)
    public int id;


    public String comment;


    public String username;

}
