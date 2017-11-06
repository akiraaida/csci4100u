package ca.uoit.csci4100u.assign02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class BrowseProductsActivity extends AppCompatActivity implements TaskListener {

    private List<Product> mProductList;
    private ProductDBHelper mDbHelper;
    private int mPosition;

    private static final int ADD_PRODUCT = 1;
    private static final String EMPTY_STRING = "";
    private static final String BASE_URL = "https://blockchain.info/tobtc?currency=CAD&value=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_products_activity);

        mDbHelper = new ProductDBHelper(this);
        mProductList = mDbHelper.getAllProducts();
        if (mProductList.size() > 0) {
            mPosition = 0;
        } else {
            mPosition = -1;
        }
        showProduct();
    }

    public void handleNext(View view) {
        if (positionIsValid(mPosition + 1)) {
            mPosition += 1;
            showProduct();
        }
    }

    public void handlePrev(View view) {
        if (positionIsValid(mPosition - 1)) {
            mPosition -= 1;
            showProduct();
        }
    }

    private boolean positionIsValid(int newPosition) {
        int numProducts = mProductList.size();
        if (newPosition >= numProducts || newPosition < 0) {
            return false;
        } else {
            return true;
        }
    }

    public void handleAddNewProduct(View view) {
        Intent intent = new Intent(BrowseProductsActivity.this, AddProductActivity.class);
        startActivityForResult(intent, ADD_PRODUCT);
    }

    public void handleDeleteProduct(View view) {
        if (mPosition != -1) {
            // Get the product id associated with the current product
            int productId = mProductList.get(mPosition).getProductId();

            // Update the local database to see if any changes have taken place before deleting something
            mProductList = mDbHelper.getAllProducts();

            // Iterate through the now updated database and delete the product if the product id is found
            for (Product product : mProductList) {
                if (product.getProductId() == productId) {
                    mDbHelper.deleteProduct(String.valueOf(productId));
                    mProductList = mDbHelper.getAllProducts();
                    mPosition -= 1;
                    if (mPosition == -1 && mProductList.size() > 0) {
                        mPosition = 0;
                    }
                    if (mPosition == -1) {
                        ((TextView) findViewById(R.id.dispName)).setText(EMPTY_STRING);
                        ((TextView) findViewById(R.id.dispDescription)).setText(EMPTY_STRING);
                        ((TextView) findViewById(R.id.dispCAD)).setText(EMPTY_STRING);
                        ((TextView) findViewById(R.id.dispBIT)).setText(EMPTY_STRING);
                    } else {
                        showProduct();
                    }
                    break;
                }
            }
        }
    }

    public void showProduct() {
        if (mPosition != -1) {
            String name = mProductList.get(mPosition).getName();
            String description = mProductList.get(mPosition).getDescription();
            float priceCAD = mProductList.get(mPosition).getPrice();
            convertToBitCoin(priceCAD);

            ((TextView) findViewById(R.id.dispName)).setText(name);
            ((TextView) findViewById(R.id.dispDescription)).setText(description);
            ((TextView) findViewById(R.id.dispCAD)).setText(Float.toString(priceCAD));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ADD_PRODUCT) {
            mPosition += 1;
            mProductList = mDbHelper.getAllProducts();
            showProduct();
        }
    }

    private void convertToBitCoin(float priceCAD) {
        String url = BASE_URL + priceCAD;

        GetBitCoinTask task = new GetBitCoinTask();
        task.setTaskListener(this);
        task.execute(new String[] {url});
    }

    @Override
    public void taskUpdater(float bitCoin) {
        ((TextView) findViewById(R.id.dispBIT)).setText(Float.toString(bitCoin));
    }
}
