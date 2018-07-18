package in.codingninjas.envision.expensemanager;


import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 */
public class ExpensesFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    List<Expense> expenses = new ArrayList<>();
    ExpenseAdapter adapter;
    ExpenseDAO expenseDAO;


    public ExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View output = inflater.inflate(R.layout.fragment_expenses, container, false);

        // Inflate the layout for this fragment
        ListView listView = output.findViewById(R.id.listview);

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

        //Toast.makeText(this,expense.getName() + " " + expense.getAmount(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(),DetailActivity.class);
        Expense expense = expenses.get(i);
        Bundle bundle = new Bundle();
        bundle.putString("name",expense.getName());
        bundle.putInt("amount",expense.getAmount());
        bundle.putInt("id",expense.getId());

        intent.putExtras(bundle);
        startActivity(intent);
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




}
