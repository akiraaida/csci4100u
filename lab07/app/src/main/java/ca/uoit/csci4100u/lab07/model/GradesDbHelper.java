package ca.uoit.csci4100u.lab07.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GradesDbHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;

    static final String TABLE = "Grades";

    static final String CREATE_STATEMENT = "CREATE TABLE Grades (\n" +
            "      studentId int primary key ,\n" +
            "      courseComponent varchar(100) not null,\n" +
            "      mark decimal not null\n" +
            ")\n";

    static final String DROP_STATEMENT = "DROP TABLE Grades";

    public GradesDbHelper(Context context) {
        super(context, "Grades", null, DATABASE_VERSION);
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

    public void createGrade(String studentId,
                             String courseComponent,
                             String mark) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("studentId", studentId);
        newValues.put("courseComponent", courseComponent);
        newValues.put("mark", mark);
        db.insert(TABLE, null, newValues);
    }

    public void deleteGrade(String studentId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, "studentId = ?", new String[] { "" + studentId });
    }

    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"studentId", "courseComponent", "mark"};
        String where = "";
        String[] whereArgs = new String[] { };
        Cursor cursor = db.query(TABLE, columns, where, whereArgs,"","","");

        cursor.moveToFirst();
        do {
            if (!cursor.isAfterLast()) {
                int studentId = cursor.getInt(0);
                String courseComponent = cursor.getString(1);
                float mark = cursor.getFloat(2);

                Grade grade = new Grade(studentId, courseComponent, mark);
                grades.add(grade);
            }
            cursor.moveToNext();
        } while (!cursor.isAfterLast());

        Log.i("SQLite", "getAllContacts(): num = " + grades.size());

        return grades;
    }
}
