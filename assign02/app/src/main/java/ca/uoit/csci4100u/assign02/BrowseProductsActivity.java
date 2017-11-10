package ca.uoit.csci4100u.assign02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * The 'BrowseProductActivity' which will allow the user to browse through the products in the database
 */
public class BrowseProductsActivity extends AppCompatActivity implements TaskListener {

    /**
     * Member variables
     */
    private List<Product> mProductList;
    private ProductDBHelper mDbHelper;
    private int mPosition;

    /**
     * Constants
     */
    private static final int ADD_PRODUCT = 1;
    private static final String EMPTY_STRING = "";
    private static final String BASE_URL = "https://blockchain.info/tobtc?currency=CAD&value=";

    /**
     * The onCreate function which initiates the activity and sets the member variables. This will
     * initialize the mPosition variable which keeps track of what product should be shown
     * @param savedInstanceState The instance state of the activity
     */
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

    /**
     * The onClick function for the 'Next' button which will increment the mPosition variable if the
     * next position is valid (if there is a product to show at the incremented position)
     * @param view The view that has been clicked (the button)
     */
    public void handleNext(View view) {
        if (positionIsValid(mPosition + 1)) {
            mPosition += 1;
            showProduct();
        }
    }

    /**
     * The onClick function for the 'Previous' button which will decrement the mPosition variable if the
     * previous position is valid (if there is a product to show at the decremented position)
     * @param view The view that has been clicked (the button)
     */
    public void handlePrev(View view) {
        if (positionIsValid(mPosition - 1)) {
            mPosition -= 1;
            showProduct();
        }
    }

    /**
     * A helper function to check if the potential new mPosition value is valid or not
     * @param newPosition The newPosition for the product that the user is trying to show
     * @return True or false based on if the value is valid or not
     */
    private boolean positionIsValid(int newPosition) {
        int numProducts = mProductList.size();
        if (newPosition >= numProducts || newPosition < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * The onClick function for the 'Add' button. This will create a sub-activity for creating
     * a new product and adding it to the database
     * @param view The view that has been clicked (the button)
     */
    public void handleAddNewProduct(View view) {
        Intent intent = new Intent(BrowseProductsActivity.this, AddProductActivity.class);
        startActivityForResult(intent, ADD_PRODUCT);
    }

    /**
     * The onClick function for the 'Delete' button. This will get the product id associated with the
     * current product, update the mProductList (to make sure that it is up to date), then search for
     * the productId associated with the product in mProductList. If it finds the product then it will
     * remove it from the database and decrement the mPosition variable, displaying the previous product.
     * If the mPosition variable is no longer valid, it will move to the first product. Otherwise, it
     * will clear the text view's meaning that the mProductList is empty.
     * @param view The view that has been clicked (the button)
     */
    public void handleDeleteProduct(View view) {
        if (mPosition != -1) {
            int productId = mProductList.get(mPosition).getProductId();
            mProductList = mDbHelper.getAllProducts();

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

    /**
     * A helper function to show the current product if mPosition is valid. This will also enable
     * and disable the prev/next buttons
     */
    public void showProduct() {
        if (positionIsValid(mPosition - 1)) {
            findViewById(R.id.prev).setEnabled(true);
        } else {
            findViewById(R.id.prev).setEnabled(false);
        }

        if (positionIsValid(mPosition + 1)) {
            findViewById(R.id.next).setEnabled(true);
        } else {
            findViewById(R.id.next).setEnabled(false);
        }

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

    /**
     * The onActivityResult function which will show the new product created in the 'AddProductActivity'
     * if the result is okay. This will happen after updating the mProductList
     * @param requestCode The request code for who started the startActivityForResult
     * @param resultCode The result code if the result is okay or not
     * @param data The intent data (empty for this use case)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ADD_PRODUCT) {
            mProductList = mDbHelper.getAllProducts();
            mPosition = mProductList.size() - 1;
            showProduct();
        }
    }

    /**
     * A helper function to start the async task for getting the price in bit coin. This will also
     * set the listener so that it subscribes to the observer
     * @param priceCAD The value of the product in canadian
     */
    private void convertToBitCoin(float priceCAD) {
        String url = BASE_URL + priceCAD;

        GetBitCoinTask task = new GetBitCoinTask();
        task.setTaskListener(this);
        task.execute(new String[] {url});
    }

    /**
     * The listener function which will populate the bit coin text field after the async task completes
     * @param bitCoin The value of the product in bit coin
     */
    @Override
    public void taskUpdater(float bitCoin) {
        ((TextView) findViewById(R.id.dispBIT)).setText(Float.toString(bitCoin));
    }
}
