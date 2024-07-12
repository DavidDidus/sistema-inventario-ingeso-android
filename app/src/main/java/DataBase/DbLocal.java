package DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbLocal extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "Bienastar Natural sql";
    public static final String TABLE_PRODUCTOS = "t_productos";
    public static final String TABLE_MATERIAS_PRIMAS = "materias_primas";



    public DbLocal(@Nullable Context context) {
        super(context,DATABASE_NOMBRE,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PRODUCTOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL, " +
                "categoria TEXT NOT NULL, " +
                "cantidad DOUBLE NOT NULL)");
        db.execSQL("CREATE TABLE " + TABLE_MATERIAS_PRIMAS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL, " +
                "unidad TEXT NOT NULL, " +
                "cantidad DOUBLE NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAS_PRIMAS);
    }
}
