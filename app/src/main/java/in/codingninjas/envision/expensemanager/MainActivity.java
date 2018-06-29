package in.codingninjas.envision.expensemanager;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {



    ArrayList<Expense> expenses = new ArrayList<>();
    ExpenseAdapter adapter;

    public static final int ADD_EXPENSE_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);

        ExpensesOpenHelper openHelper = ExpensesOpenHelper.getInstance(getApplicationContext());
        SQLiteDatabase database = openHelper.getReadableDatabase();
        int amountGreaterThan = 0;
        String[] selectionArgument = {amountGreaterThan + "",};
        String[] columns = {Contract.Expense.COLUMN_NAME,Contract.Expense.COLUMN_AMOUNT,Contract.Expense.COLUMN_ID};
        Cursor cursor = database.query(Contract.Expense.TABLE_NAME,columns,  "amount > ?",selectionArgument,null,null,null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(Contract.Expense.COLUMN_NAME));
            int amount = cursor.getInt(cursor.getColumnIndex(Contract.Expense.COLUMN_AMOUNT));
            long id = cursor.getLong(cursor.getColumnIndex(Contract.Expense.COLUMN_ID));
            Expense expense = new Expense(name,amount);
            expense.setId(id);
            expenses.add(expense);
        }
        cursor.close();


//
//        for(int i = 0;i<20;i++){
//
//            Expense expense = new Expense("Expense " + i,i*100);
//            expenses.add(expense);
//
//        }

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.expense_row_layout,R.id.expenseName,expenses);

        adapter = new ExpenseAdapter(this,expenses);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        View view = new View(this);



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Expense expense = expenses.get(i);


        final int position = i;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setCancelable(false);
        builder.setMessage("Do you really want to delete " + expense.getName() + "?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Toast.makeText(MainActivity.this,"Ok Presses",Toast.LENGTH_LONG).show();
                ExpensesOpenHelper openHelper = ExpensesOpenHelper.getInstance(getApplicationContext());
                SQLiteDatabase database = openHelper.getWritableDatabase();

                long id = expense.getId();
                String[] selectionArgs = {id + ""};

                database.delete(Contract.Expense.TABLE_NAME,Contract.Expense.COLUMN_ID + " = ?",selectionArgs);


                expenses.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        
//

        //Toast.makeText(this,expense.getName() + " " + expense.getAmount(),Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addExpenseAction){
            Bundle bundle = new Bundle();
            bundle.putString("title","abc");
            bundle.putInt("age",10);

            Intent intent = new Intent(this,AddExpenseActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent,ADD_EXPENSE_REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity","Activity Result called");
        if(requestCode == ADD_EXPENSE_REQUEST_CODE){
            if(resultCode == AddExpenseActivity.ADD_RESULT_CODE){
                String title = data.getStringExtra(AddExpenseActivity.TITLE_KEY);
                String amountString = data.getStringExtra(AddExpenseActivity.AMOUNT_KEY);
                int amount = Integer.parseInt(amountString);
                Expense expense = new Expense(title,amount);

                ExpensesOpenHelper openHelper = ExpensesOpenHelper.getInstance(this);
                SQLiteDatabase database = openHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put(Contract.Expense.COLUMN_NAME,expense.getName());
                contentValues.put(Contract.Expense.COLUMN_AMOUNT,expense.getAmount());

                long id = database.insert(Contract.Expense.TABLE_NAME,null,contentValues);
                if (id > -1L){
                    expense.setId(id);

                    expenses.add(expense);
                    adapter.notifyDataSetChanged();
                }

            }
        }




    }
}
