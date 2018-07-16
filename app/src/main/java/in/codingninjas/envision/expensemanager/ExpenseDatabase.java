package in.codingninjas.envision.expensemanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {Expense.class},version = 1)
public abstract class ExpenseDatabase extends RoomDatabase {


    abstract ExpenseDAO getExpenseDao();


}
