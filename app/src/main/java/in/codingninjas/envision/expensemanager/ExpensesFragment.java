package in.codingninjas.envision.expensemanager;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 */
public class ExpensesFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    List<Expense> expenses = new ArrayList<>();
    ExpenseAdapter adapter;
    ExpenseDAO expenseDAO;
    ListView listView;

    ExpensesFragmentCallback listener;


    public ExpensesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ExpensesFragmentCallback){
            listener = (ExpensesFragmentCallback) context;
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.addExpense){

            addExpense();
        }

        return true;
    }

    private void addExpense() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                expenseDAO.addExpenses(expense);

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View output = inflater.inflate(R.layout.fragment_expenses, container, false);


        // Inflate the layout for this fragment
        listView = output.findViewById(R.id.listview);

        ExpenseDatabase database = Room.databaseBuilder(getContext().getApplicationContext(),ExpenseDatabase.class,"expenses_db").allowMainThreadQueries().build();
        expenseDAO = database.getExpenseDao();
        expenses = expenseDAO.getExpenses();

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.expense_row_layout,R.id.expenseName,expenses);

        adapter = new ExpenseAdapter(getContext(), expenses, new ExpenseItemClickListener() {
            @Override
            public void rowButtonClicked(View view, int position) {

            }
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);


        return output;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        listView.setItemChecked(i,true);
        Expense expense = expenses.get(i);
        if(listener != null){
            listener.onExpenseSelected(expense);
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        Expense expense = expenses.get(i);
        final int position = i;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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


    public interface ExpensesFragmentCallback {

        void onExpenseSelected(Expense expense);

    }




}
