package ca.uoit.csci4100u.assign02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProductDBHelper extends SQLiteOpenHelper{

    static final int DATABASE_VERSION = 1;

    static final String TABLE = "Products";

    static final String CREATE_STATEMENT = "CREATE TABLE Products (\n" +
            "      productId int primary key,\n" +
            "      name varchar(100) not null,\n" +
            "      description varchar(255) not null,\n" +
            "      price decimal not null\n" +
            ")\n";

    static final String DROP_STATEMENT = "DROP TABLE Products";

    public ProductDBHelper(Context context) {
        super(context, "Products", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersionNum, int newVersionNum) {
        sqLiteDatabase.execSQL(DROP_STATEMENT);
        sqLiteDatabase.execSQL(CREATE_STATEMENT);
    }

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

    public void deleteProduct(String productId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, "productId = ?", new String[] { "" + productId });
    }

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

        Log.i("SQLite", "getAllProducts(): num = " + products.size());

        return products;
    }
}
