package in.codingninjas.envision.expensemanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output =  inflater.inflate(R.layout.fragment_detail, container, false);
        Bundle bundle = getArguments();

        String name = bundle.getString("name");
        int amount = bundle.getInt("amount",0);
        int id = bundle.getInt("id",-1);

        TextView nameTextView = output.findViewById(R.id.name);
        nameTextView.setText(name);

        TextView amountTextView = output.findViewById(R.id.amount);
        amountTextView.setText(amount + "");


        return output;
    }

}
