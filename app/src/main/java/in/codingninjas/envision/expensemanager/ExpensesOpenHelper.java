package in.codingninjas.envision.expensemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpensesOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "expenses_db";
    public static final int VERSION = 1;


    public ExpensesOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String expensesSql = "CREATE TABLE " + Contract.Expense.TABLE_NAME  + " (  " +
                Contract.Expense.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                Contract.Expense.COLUMN_NAME  + " TEXT , " +
                Contract.Expense.COLUMN_AMOUNT + " INTEGER )";
        sqLiteDatabase.execSQL(expensesSql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
