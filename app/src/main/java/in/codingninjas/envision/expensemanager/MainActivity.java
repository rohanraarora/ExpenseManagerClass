package in.codingninjas.envision.expensemanager;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,ExpenseItemClickListener, AdapterView.OnItemLongClickListener {

    ArrayList<Expense> expenses = new ArrayList<>();
    ExpenseAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);


        for(int i = 0;i<20;i++){

            Expense expense = new Expense("Expense " + i,i*100);
            expenses.add(expense);

        }

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.expense_row_layout,R.id.expenseName,expenses);

        adapter = new ExpenseAdapter(getApplicationContext(), expenses, new ExpenseItemClickListener() {
            @Override
            public void rowButtonClicked(View view, int position) {

            }
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        View view = new View(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Toast.makeText(this,expense.getName() + " " + expense.getAmount(),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        Expense expense = expenses.get(i);
        final int position = i;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setCancelable(false);
        builder.setMessage("Do you really want to delete " + expense.getName() + "?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Toast.makeText(MainActivity.this,"Ok Presses",Toast.LENGTH_LONG).show();
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

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.addExpense){

            addExpense();
        }

        return true;
    }

    private void addExpense() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.add_expense_dialog_layout,null);
        builder.setView(dialogView);

        final EditText expenseTitleEditText = dialogView.findViewById(R.id.expenseTitleEditText);
        final EditText expenseAmountEditText = dialogView.findViewById(R.id.expenseAmountTitleEditText);

        builder.setTitle("Add Expense");
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String expenseTitle,expenseAmount;

                expenseTitle = expenseTitleEditText.getText().toString();
                expenseAmount = expenseAmountEditText.getText().toString();

                Expense expense = new Expense(expenseTitle,Integer.parseInt(expenseAmount));
                expenses.add(expense);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }


    public void rowButtonClicked(View view,int position){
        Toast.makeText(this,"Buttom Pressed " + position,Toast.LENGTH_LONG).show();
    }
}
