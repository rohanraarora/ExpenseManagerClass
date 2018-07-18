package in.codingninjas.envision.expensemanager;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExpensesFragment.ExpensesFragmentCallback {

    public boolean isDualMode  = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frameLayout = findViewById(R.id.listContainer);
        if(frameLayout != null){
            isDualMode = true;
        }

    }


    @Override
    public void onExpenseSelected(Expense expense) {
        Bundle bundle = new Bundle();
        bundle.putString("name",expense.getName());
        bundle.putInt("amount",expense.getAmount());
        bundle.putInt("id",expense.getId());
        if(isDualMode){

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.listContainer,fragment);
            transaction.commit();

        }
        else {

            Intent intent = new Intent(this,DetailActivity.class);



            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
}
