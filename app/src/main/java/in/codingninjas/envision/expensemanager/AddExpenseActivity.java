package in.codingninjas.envision.expensemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddExpenseActivity extends AppCompatActivity {

    public static final String TITLE_KEY = "title";
    public static final String AMOUNT_KEY = "amount";

    public static final int ADD_RESULT_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bundle.getString("title");
    }

    public void saveExpense(View view){

        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText amountEditText = findViewById(R.id.amountEditText);

        String title = titleEditText.getText().toString();
        String amountString = amountEditText.getText().toString();

        Intent data = new Intent();
        data.putExtra(TITLE_KEY,title);
        data.putExtra(AMOUNT_KEY,amountString);

        setResult(ADD_RESULT_CODE,data);
        finish();



    }
}
