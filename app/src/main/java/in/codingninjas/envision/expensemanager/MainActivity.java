package in.codingninjas.envision.expensemanager;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.List;

public class MainActivity extends AppCompatActivity  {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        View view = new View(this);
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

//                String expenseTitle,expenseAmount;
//
//                expenseTitle = expenseTitleEditText.getText().toString();
//                expenseAmount = expenseAmountEditText.getText().toString();
//
//                Expense expense = new Expense(expenseTitle,Integer.parseInt(expenseAmount));
//                expenseDAO.addExpenses(expense);
//
//                expenses.add(expense);
//                adapter.notifyDataSetChanged();
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
