package in.codingninjas.envision.expensemanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter {

    List<Expense> items;
    LayoutInflater inflater;
    Context context;
    ExpenseItemClickListener clickListener;

    public ExpenseAdapter(@NonNull Context context, List<Expense> items,ExpenseItemClickListener listener) {
        super(context, 0,  items);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.items = items;
        this.clickListener = listener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output = inflater.inflate(R.layout.expense_row_layout,parent,false);
        TextView nameTextView = output.findViewById(R.id.expenseName);
        TextView amountTextView = output.findViewById(R.id.expenseAmount);
        Button button = output.findViewById(R.id.deleteButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              clickListener.rowButtonClicked(view,position);

            }
        });
        Expense expense = items.get(position);
        nameTextView.setText(expense.getName());
        amountTextView.setText(expense.getAmount() + "Rs");
        return output;
    }
}
