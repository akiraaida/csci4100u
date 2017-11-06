package ca.uoit.csci4100u.assign02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    private ProductDBHelper mDbHelper;

    private static final String EMPTY_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);

        mDbHelper = new ProductDBHelper(this);
    }

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

    public void handleCancel(View view) {
        clearTextFields();
        finish();
    }

    private void clearTextFields() {
        ((EditText) findViewById(R.id.inputName)).setText(EMPTY_STRING);
        ((EditText) findViewById(R.id.inputDescription)).setText(EMPTY_STRING);
        ((EditText) findViewById(R.id.inputPrice)).setText(EMPTY_STRING);
    }
}
