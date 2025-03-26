package deakin.example.unitconverterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText etValue;
    private Button btnConvert;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        etValue = findViewById(R.id.etValue);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.tvResult);

        // Define available units
        String[] units = {
                "Inches", "Centimeters", "Feet", "Yards", "Miles", "Kilometers",
                "Pounds", "Kilograms", "Ounces", "Grams", "Tons",
                "Celsius", "Fahrenheit", "Kelvin"
        };

        // Set up spinners with unit options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Button Click Listener
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();
                String inputText = etValue.getText().toString().trim();

                // Check for empty input
                if (inputText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double inputValue = Double.parseDouble(inputText);

                    // Check if source and destination units are the same
                    if (fromUnit.equals(toUnit)) {
                        tvResult.setText("Same units selected. No conversion needed.");
                        return;
                    }

                    double convertedValue = convertUnits(fromUnit, toUnit, inputValue);
                    tvResult.setText(String.format("Converted Value: %.2f %s", convertedValue, toUnit));
                } catch (NumberFormatException e) {
                    tvResult.setText("Error: Please enter a valid numerical value");
                }
            }
        });

    }

    // Conversion Logic
    private double convertUnits(String from, String to, double value) {
        switch (from + " to " + to) {
            // Length Conversions
            case "Inches to Centimeters": return value * 2.54;
            case "Feet to Centimeters": return value * 30.48;
            case "Yards to Centimeters": return value * 91.44;
            case "Miles to Kilometers": return value * 1.60934;

            // Weight Conversions
            case "Pounds to Kilograms": return value * 0.453592;
            case "Ounces to Grams": return value * 28.3495;
            case "Tons to Kilograms": return value * 907.185;

            // Temperature Conversions
            case "Celsius to Fahrenheit": return (value * 1.8) + 32;
            case "Fahrenheit to Celsius": return (value - 32) / 1.8;
            case "Celsius to Kelvin": return value + 273.15;
            case "Kelvin to Celsius": return value - 273.15;

            default: return value; // No conversion needed
        }
    }
}
