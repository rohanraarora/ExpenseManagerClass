package in.codingninjas.envision.expensemanager;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "expenses_comments",foreignKeys = {
        @ForeignKey(entity = Expense.class,parentColumns = {"id"},childColumns = {"expenseId"},onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Comment.class,parentColumns = {"id"},childColumns = {"commentId"})
})
public class ExpenseComment {

    @PrimaryKey(autoGenerate = true)
    int id;

    int expenseId;

    int commentId;

}
