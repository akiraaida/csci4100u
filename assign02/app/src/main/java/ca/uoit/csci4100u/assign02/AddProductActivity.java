package ca.uoit.csci4100u.assign02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

/**
 * The 'AddProductActivity' activity which is meant to add a product to the database.
 */
public class AddProductActivity extends AppCompatActivity {

    /**
     * Member variables
     */
    private ProductDBHelper mDbHelper;

    /**
     * Constants
     */
    private static final String EMPTY_STRING = "";

    /**
     * The onCreate function which initializes the activity and sets the member variables
     * @param savedInstanceState The instance state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);

        mDbHelper = new ProductDBHelper(this);
    }

    /**
     * The onClick function for the 'Add' button which will take the user inputted information and
     * create a new database entry in the database. Using the last product's key + 1 as a unique
     * identifier (if nothing is in the database then it will default to "0"). After adding the
     * value, this activity will clear the text fields and finish.
     * @param view The view that has been clicked (the button)
     */
    public void handleAdd(View view) {
        String name = ((EditText) findViewById(R.id.inputName)).getText().toString();
        String description = ((EditText) findViewById(R.id.inputDescription)).getText().toString();
        String price = ((EditText) findViewById(R.id.inputPrice)).getText().toString();

        if (!name.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
            String productId;
            List<Product> productList = mDbHelper.getAllProducts();
            if (productList.size() > 0) {
                int lastProductId = productList.get(productList.size() - 1).getProductId();
                productId = Integer.toString(lastProductId + 1);
            } else {
                productId = "0";
            }
            mDbHelper.createProduct(productId, name, description, price);
            setResult(RESULT_OK);
            clearTextFields();
            finish();
        }
    }

    /**
     * The onClick function for the 'Cancel' button which will clear the text fields and then finish.
     * @param view The view that has been clicked (the button)
     */
    public void handleCancel(View view) {
        clearTextFields();
        finish();
    }

    /**
     * A helper function to clear the text fields by setting their text to an empty string
     */
    private void clearTextFields() {
        ((EditText) findViewById(R.id.inputName)).setText(EMPTY_STRING);
        ((EditText) findViewById(R.id.inputDescription)).setText(EMPTY_STRING);
        ((EditText) findViewById(R.id.inputPrice)).setText(EMPTY_STRING);
    }
}
