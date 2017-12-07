package comkiolk.github.braintester.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static comkiolk.github.braintester.utils.Utils.*;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context) {
        super(context, QUESTIONS_DB, null, VERSION_OF_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase pSQLiteDatabase) {
        Log.d(MY_LOGS, "Start method onCreateDB");
        pSQLiteDatabase.execSQL("create table " + NAME_TABLE_MY_QUESTIONS + " (" +
                "id integer primary key autoincrement," +
                "question text," +
                "option1 text," +
                "option2 text," +
                "option3 text," +
                "option4 text," +
                "correctAnswer text" +
                "questionNumber text" +
                "topicId integer);");

        Log.d(MY_LOGS, "Create table: " + NAME_TABLE_MY_QUESTIONS);

        int[] topicID = {1, 2, 3, 4};
        String[] topicDescription = {"Structure of atom", "Periodical law", "Chemical reactions", "Elements of group IA"};

        pSQLiteDatabase.execSQL("CREATE TABLE " + TABLE_TOPIC_OF_QUESTION + " ( " +
                TOPIC_ID + " INTEGER PRIMARY KEY, " +
                TOPIC_DESCRIPTION + " text);");
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i < topicID.length; ++i){
            contentValues.clear();
            contentValues.put(TOPIC_ID, topicID[i]);
            contentValues.put(TOPIC_DESCRIPTION, topicDescription[i]);
            pSQLiteDatabase.insert(TABLE_TOPIC_OF_QUESTION, null, contentValues);
        }

        Log.d(MY_LOGS, "Create table: " + TABLE_TOPIC_OF_QUESTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase pSQLiteDatabase, int pI, int pI1) {
        Log.d(MY_LOGS, "Start method onUpgrade");


       /* Example how add column in table and increase version of DB*/
        if(pI == 4 && pI1 == 5){
            pSQLiteDatabase.beginTransaction();
            try {
                pSQLiteDatabase.execSQL("alter table " + NAME_TABLE_MY_QUESTIONS + " add column " + TOPIC_ID_QUESTION + " integer;");
                Log.d(MY_LOGS, "Add column topicID");

                int[] topicID = {1, 2, 3, 4};
                String[] topicDescription = {"Structure of atom", "Periodical law", "Chemical reactions", "Elements of group IA"};

                pSQLiteDatabase.execSQL("CREATE TABLE " + TABLE_TOPIC_OF_QUESTION + " (" +
                        TOPIC_ID + " INTEGER PRIMARY KEY, " +
                        TOPIC_DESCRIPTION + " text);");
                ContentValues contentValues = new ContentValues();
                for(int i = 0; i < topicID.length; ++i){
                    contentValues.clear();
                    contentValues.put(TOPIC_ID, topicID[i]);
                    contentValues.put(TOPIC_DESCRIPTION, topicDescription[i]);
                    pSQLiteDatabase.insert(TABLE_TOPIC_OF_QUESTION, null, contentValues);
                }

                Log.d(MY_LOGS, "Create table: " + TABLE_TOPIC_OF_QUESTION);
                pSQLiteDatabase.setTransactionSuccessful();
            }finally {
                pSQLiteDatabase.endTransaction();
            }
        }
        /* Example how delete column in table (only if use fully replace table) and increase version of DB*/
        if (pI == 1 && pI1 == 2){
            pSQLiteDatabase.beginTransaction();
            try{
                pSQLiteDatabase.execSQL("CREATE table tmp " + " (" +
                        "id integer primary key autoincrement," +
                        "question text," +
                        "option1 text," +
                        "option2 text," +
                        "option3 text," +
                        "option4 text," +
                        "correctAnswer text" + ");");
                pSQLiteDatabase.execSQL("INSERT INTO tmp SELECT id, question, option1, option2, option3, option4, correctAnswer FROM " + NAME_TABLE_MY_QUESTIONS + ";");
                pSQLiteDatabase.execSQL("DROP TABLE " + NAME_TABLE_MY_QUESTIONS +";");
                pSQLiteDatabase.execSQL("create table " + NAME_TABLE_MY_QUESTIONS + " (" +
                        "id integer primary key autoincrement," +
                        "question text," +
                        "option1 text," +
                        "option2 text," +
                        "option3 text," +
                        "option4 text," +
                        "correctAnswer text" + ");");
                pSQLiteDatabase.execSQL("INSERT INTO "+ NAME_TABLE_MY_QUESTIONS +" SELECT id, question, option1, option2, option3, option4, correctAnswer FROM tmp;");
                pSQLiteDatabase.execSQL("DROP TABLE tmp");
                pSQLiteDatabase.setTransactionSuccessful();
                Log.d(MY_LOGS, "Add column questionNumber deleted");
            }finally {
                pSQLiteDatabase.endTransaction();
            }
        }

    }

}
