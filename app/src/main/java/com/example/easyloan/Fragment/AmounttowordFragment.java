package com.example.easyloan.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easyloan.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;


public class AmounttowordFragment extends Fragment {

    private EditText amountInput;
    private EditText wordAmount;

    TextToSpeech textToSpeech;
    private ExtendedFloatingActionButton outputButton,reset_button;
    ImageView voice;
    MaterialTextView txt_back_emi;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amounttoword, container, false);

        // Find voice button and EditText from layout
        amountInput = view.findViewById(R.id.amountvoice);
        wordAmount = view.findViewById(R.id.word_amount);
        outputButton = view.findViewById(R.id.output_button);
        reset_button=view.findViewById(R.id.reset_button);
        txt_back_emi=view.findViewById(R.id.txt_back_emi);
        voice=view.findViewById(R.id.speak);
        txt_back_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        outputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertAmountToWords();
            }
        });
     reset_button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             wordAmount.setText("");
                     amountInput.setText("");
         }
     });

     textToSpeech =new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
         @Override
         public void onInit(int status) {
        if (status != TextToSpeech.ERROR){
            textToSpeech.setLanguage(Locale.UK);

        }
         }
     });

     voice.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             textToSpeech.speak(amountInput.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
         }
     });
        return view;
    }
    private void convertAmountToWords() {
        String amountStr = amountInput.getText().toString().trim();
        if (TextUtils.isEmpty(amountStr)) {
            Toast.makeText(getActivity(), "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            long amount = Long.parseLong(amountStr);
            String amountInWords = convertNumberToWords(amount);
            wordAmount.setText(amountInWords);
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Invalid amount entered", Toast.LENGTH_SHORT).show();
        }
    }
    private String convertNumberToWords(long number) {
        if (number == 0) {
            return "zero";
        }

        String[] units = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
        String[] tens = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
        String[] thousands = {"", "thousand", "million", "billion", "trillion"};

        StringBuilder words = new StringBuilder();

        int place = 0;

        do {
            int n = (int) (number % 1000);
            if (n != 0) {
                String str = convertThreeDigitNumberToWords(n);
                words.insert(0, str + " " + thousands[place] + " ");
            }
            place++;
            number /= 1000;
        } while (number > 0);

        return words.toString().trim();
    }

    private String convertThreeDigitNumberToWords(int number) {
        String[] units = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
        String[] tens = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

        StringBuilder words = new StringBuilder();

        if (number >= 100) {
            words.append(units[number / 100]).append(" hundred ");
            number %= 100;
        }

        if (number >= 20) {
            words.append(tens[number / 10]).append(" ");
            number %= 10;
        }

        if (number > 0) {
            words.append(units[number]);
        }

        return words.toString().trim();
    }

}
