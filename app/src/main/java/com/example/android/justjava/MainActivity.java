package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity
{
    public int quantity = 1;
    TextView priceTextView;
    CheckBox whippedCreamCheckBox;
    CheckBox chocolateCheckBox;
    EditText nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        priceTextView = findViewById(R.id.price_text_view);
        whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        nameField = findViewById(R.id.name_field);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        String name = nameField.getText().toString();
//        Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);
//        Log.v("MainActivity", "Has chocolate: " + hasChocolate);
//        Log.v("MainActivity", "Name: " + name);

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        displayMessage(priceMessage);
        String[] email = new String[]{"aploteo@gmail.com"};

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number)
    {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
        displayPrice(quantity * 5);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number)
    {
        TextView priceTextView = findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view)
    {
        if (quantity == 100)
        {
            Toast.makeText(this, "You cannot have more than 100 coffees.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view)
    {
        if (quantity == 1)
        {
            Toast.makeText(this, "You cannot have less than 1 coffee.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message)
    {
        priceTextView.setText(message);
    }

    public int calculatePrice(boolean hasWhippedCream, boolean hasChocolate)
    {
        int basePrice = 5;

        if (hasWhippedCream)
        {
            basePrice += 1;
        }

        if (hasChocolate)
        {
            basePrice += 2;
        }

        return basePrice * quantity;
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate)
    {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.add_whipped_cream);
        priceMessage += "\n" + getString(R.string.add_chocolate);
        priceMessage += "\n" + getString(R.string.quantity);
        priceMessage += "\n" + getString(R.string.total);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}