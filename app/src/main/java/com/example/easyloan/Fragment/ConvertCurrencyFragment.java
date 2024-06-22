package com.example.easyloan.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.easyloan.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.HashMap;
import java.util.Map;


public class ConvertCurrencyFragment extends Fragment {

    private Spinner spinnerCountry, spinnerCountry2;
    private final String[] countryNames = {"United States (USD)", "India (INR)", "Australia (AUD)", "Canada (CAD)", "Eurozone (EUR)", "United Kingdom (GBP)"};
    private final String[] currencyCodes = {"USD", "INR", "AUD", "CAD", "EUR", "GBP"};
    private EditText editText1, editText2;
    private Map<String, Double> conversionRates;

    MaterialTextView  txt_back_emi;
    ExtendedFloatingActionButton  reset_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_convert_currency, container, false);
        spinnerCountry = view.findViewById(R.id.spiner_conutry);
        spinnerCountry2 = view.findViewById(R.id.spiner_conutry2);
        editText1 = view.findViewById(R.id.editText1);
        editText2 = view.findViewById(R.id.editText2);
        reset_button = view.findViewById(R.id.reset_button);
        txt_back_emi=view.findViewById(R.id.txt_back_emi);
        // Create an ArrayAdapter using the country names array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, countryNames);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerCountry.setAdapter(adapter);
        spinnerCountry2.setAdapter(adapter);

        // Set a listener to handle spinner item selection
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected currency code
                updateConvertedAmount();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle situation where nothing is selected (if needed)
            }
        });
        spinnerCountry2.post(new Runnable() {
            @Override
            public void run() {
                spinnerCountry2.setSelection(1); // Selecting India (INR) as default
            }
        });
        spinnerCountry2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle situation where nothing is selected (if needed)
            }
        });
        view.findViewById(R.id.convert_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConvertedAmount();
            }
        });
        conversionRates = new HashMap<>();
        conversionRates.put("USD", 1.0);
        conversionRates.put("INR", 80.0);
        conversionRates.put("AUD", 1.3);
        conversionRates.put("CAD", 1.25);
        conversionRates.put("EUR", 0.85);
        conversionRates.put("GBP", 0.72);

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
editText2.setText("");
editText1.setText("");
            }
        });

        txt_back_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        return view;
    }


    private void updateConvertedAmount() {
        try {
            String currency1 = currencyCodes[spinnerCountry.getSelectedItemPosition()];
            String currency2 = currencyCodes[spinnerCountry2.getSelectedItemPosition()];
            double amount = Double.parseDouble(editText1.getText().toString());

            // Perform currency conversion
            double convertedAmount = amount * (conversionRates.get(currency2) / conversionRates.get(currency1));

            // Display converted amount in editText2
            editText2.setText(String.format("%.2f", convertedAmount));
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
        }
    }

    private void convertCurrency() {
        // This method is no longer needed as conversion happens dynamically on spinner and editText1 changes
    }


}