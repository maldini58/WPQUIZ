package com.example.maldini.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Maldini on 2018-02-13.
 */
public class QuizDbAdapter {

    private static final String DATABASE_NAME = "QUIZ_DATABASE.db";
    private static final String QUIZ_TABLE = "QUIZ_TABLE ";
    private static final int DATABASE_VERSION = 200;
    private final Context mCtx;

    public static String TAG = QuizDbAdapter.class.getSimpleName();

    private DatabaseHelper mDbHelper;
    SQLiteDatabase mDb;

    public static final String KEY_ROWID = "_id";
    public static final String TITLE = "title";
    public static final String FINISHED = "finished";
    public static final String QUESTION_NUMBER = "question";
    public static final String WRONG_QUESTION_NUMBER= "wrong";
    public static final String CORRECT_QUESTION_NUMBER = "correct";
    public static final String ANSWERED_QUESTION_NUMBER = "answered";
    public static final String RESULT = "result";


    public static final String[] QUIZ_FIELDS = new String[]{
            KEY_ROWID,
            TITLE,
            FINISHED,
            QUESTION_NUMBER,
            WRONG_QUESTION_NUMBER,
            CORRECT_QUESTION_NUMBER,
            ANSWERED_QUESTION_NUMBER,
            RESULT
    };

    private static final String CREATE_TABLE_QUIZ =
            "create table "+ QUIZ_TABLE + " ("
            + KEY_ROWID + " INTEGER,"
            + TITLE + " text,"
            + FINISHED + " INTEGER,"
            +QUESTION_NUMBER + " INTEGER,"
            +WRONG_QUESTION_NUMBER + " INTEGER,"
            +CORRECT_QUESTION_NUMBER + " INTEGER,"
            +ANSWERED_QUESTION_NUMBER + " INTEGER,"
            +RESULT + " REAL"
            +")";




    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_QUIZ);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Aktualizacja bazy danych z wersji "+ oldVersion + " na wersje " + newVersion);
            db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE);
            onCreate(db);
        }
    }


    public QuizDbAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }

    public QuizDbAdapter open() throws SQLException{
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        if(mDbHelper!=null){
            mDbHelper.close();
        }
    }

    public void upgrade() throws SQLException{
        mDbHelper.onUpgrade(mDb,1,0);
    }

    public long insertQuiz(Quiz quiz){
        ContentValues newValues = new ContentValues();

        newValues.put(QuizDbAdapter.KEY_ROWID,quiz.getId());
        newValues.put(QuizDbAdapter.TITLE,quiz.getTitle());
        newValues.put(QuizDbAdapter.FINISHED,quiz.getFinished());
        newValues.put(QuizDbAdapter.RESULT, quiz.getResult());
        newValues.put(QuizDbAdapter.QUESTION_NUMBER,quiz.getQuestionNumber());
        newValues.put(QuizDbAdapter.WRONG_QUESTION_NUMBER,quiz.getWrongQuestionNumber());
        newValues.put(QuizDbAdapter.CORRECT_QUESTION_NUMBER,quiz.getCorrectQuestionNumber());
        newValues.put(QuizDbAdapter.ANSWERED_QUESTION_NUMBER, quiz.getAnsweredQuestionNumber());

        return mDb.insertWithOnConflict(QUIZ_TABLE,null,newValues,SQLiteDatabase.CONFLICT_IGNORE);
//        return mDb.insertOrThrow(QUIZ_TABLE,null,newValues);
    }

    public boolean updateQuiz(int id,Quiz quiz){
//        String[] selectionArgs = {String.valueOf(id)};
        ContentValues newValues = new ContentValues();

        newValues.put(QuizDbAdapter.KEY_ROWID,quiz.getId());
        newValues.put(QuizDbAdapter.TITLE,quiz.getTitle());
        newValues.put(QuizDbAdapter.FINISHED,quiz.getFinished());
        newValues.put(QuizDbAdapter.RESULT, quiz.getResult());
        newValues.put(QuizDbAdapter.QUESTION_NUMBER,quiz.getQuestionNumber());
        newValues.put(QuizDbAdapter.WRONG_QUESTION_NUMBER,quiz.getWrongQuestionNumber());
        newValues.put(QuizDbAdapter.CORRECT_QUESTION_NUMBER,quiz.getCorrectQuestionNumber());
        newValues.put(QuizDbAdapter.ANSWERED_QUESTION_NUMBER, quiz.getAnsweredQuestionNumber());

        return mDb.update(QUIZ_TABLE, newValues, KEY_ROWID + "=" + id, null) > 0;
    }

    public boolean deleteQuiz(int id){

        return mDb.delete(QUIZ_TABLE, KEY_ROWID + "=" + id, null) > 0;
    }

    public Cursor getQuizes(){
        return mDb.query(QUIZ_TABLE,QUIZ_FIELDS,null,null,null,null,null);
    }

    public Cursor getResults(int id){
//        return mDb.query(QUIZ_TABLE,QUIZ_FIELDS,TITLE +"="+title,null,null,null,null);
        return mDb.query(true,QUIZ_TABLE,QUIZ_FIELDS,KEY_ROWID +"="+id,null,null,null,null,null);
    }



    public Quiz getQuizFromCursor(Cursor cursor){
        Quiz quiz = new Quiz();
        quiz.setId(cursor.getInt(cursor.getColumnIndex(KEY_ROWID)));
        quiz.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
        quiz.setFinished(cursor.getInt(cursor.getColumnIndex(FINISHED)));
        quiz.setQuestionNumber(cursor.getInt(cursor.getColumnIndex(QUESTION_NUMBER)));
        quiz.setWrongQuestionNumber(cursor.getInt(cursor.getColumnIndex(WRONG_QUESTION_NUMBER)));
        quiz.setCorrectQuestionNumber(cursor.getInt(cursor.getColumnIndex(CORRECT_QUESTION_NUMBER)));
        quiz.setAnsweredQuestionNumber(cursor.getInt(cursor.getColumnIndex(ANSWERED_QUESTION_NUMBER)));
        quiz.setResult(cursor.getDouble(cursor.getColumnIndex(RESULT)));
        return(quiz);

    }


}
