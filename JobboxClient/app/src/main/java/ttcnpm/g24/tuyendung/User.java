package ttcnpm.g24.tuyendung;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class User extends SQLiteOpenHelper {
    public User (Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);

    }
    //Truy van khong tra ket qua: CREATE, INSERT, UPDATE, DELETE,..
    public void QuerryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);

    }
    //Truy van co tra ket qua: SELECT
    public Cursor GetData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
