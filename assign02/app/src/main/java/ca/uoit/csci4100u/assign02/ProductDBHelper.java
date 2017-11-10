package ca.uoit.csci4100u.assign02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * The database helper class which will be used to perform actions on the database
 */
public class ProductDBHelper extends SQLiteOpenHelper{

    /**
     * Constants
     */
    static final int DATABASE_VERSION = 1;
    static final String TABLE = "Products";
    static final String CREATE_STATEMENT = "CREATE TABLE Products (\n" +
            "      productId int primary key,\n" +
            "      name varchar(100) not null,\n" +
            "      description varchar(255) not null,\n" +
            "      price decimal not null\n" +
            ")\n";
    static final String DROP_STATEMENT = "DROP TABLE Products";

    /**
     * The constructor of the database helper
     * @param context The context of the activity which will use this helper
     */
    public ProductDBHelper(Context context) {
        super(context, "Products", null, DATABASE_VERSION);
    }

    /**
     * The onCreate function which will create the database
     * @param sqLiteDatabase The database reference object
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STATEMENT);
    }

    /**
     * The onUpgrade function which will drop the database and recreate it if the new version is
     * greater than the old version
     * @param sqLiteDatabase The database reference object
     * @param oldVersionNum The old version of the database
     * @param newVersionNum The new version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersionNum, int newVersionNum) {
        sqLiteDatabase.execSQL(DROP_STATEMENT);
        sqLiteDatabase.execSQL(CREATE_STATEMENT);
    }

    /**
     * A helper function meant to create a product and input it into the database
     * @param productId The product id associated with the product
     * @param name The name of the product
     * @param description The description of the product
     * @param price The price of the product in Canadian
     */
    public void createProduct(String productId,
                            String name,
                            String description,
                            String price) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("productId", productId);
        newValues.put("name", name);
        newValues.put("description", description);
        newValues.put("price", price);
        db.insert(TABLE, null, newValues);
    }

    /**
     * A helper function that will delete the product from the database based on the productId
     * @param productId
     */
    public void deleteProduct(String productId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, "productId = ?", new String[] { "" + productId });
    }

    /**
     * A helper function that will get all of the products from the database
     * @return All of the product objects as a list
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"productId", "name", "description", "price"};
        String where = "";
        String[] whereArgs = new String[] { };
        Cursor cursor = db.query(TABLE, columns, where, whereArgs,"","","");

        cursor.moveToFirst();
        do {
            if (!cursor.isAfterLast()) {
                int productId = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                float price = cursor.getFloat(3);

                Product product = new Product(productId, name, description, price);
                products.add(product);
            }
            cursor.moveToNext();
        } while (!cursor.isAfterLast());
        return products;
    }
}
