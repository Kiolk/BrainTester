package comkiolk.github.braintester.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Random;

import comkiolk.github.braintester.MainActivity;
import comkiolk.github.braintester.Questions.Question;
import comkiolk.github.braintester.Questions.SingleChoiceQuestion;

import static comkiolk.github.braintester.utils.Utils.*;

public class GetFromDB {

    public static SingleChoiceQuestion getRandomQuestion(Context pContext) {

        SingleChoiceQuestion randomQuestion = new SingleChoiceQuestion();
        DataBaseHelper mDBHelper = new DataBaseHelper(pContext);
        SQLiteDatabase sqLiteDatabase = mDBHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(NAME_TABLE_MY_QUESTIONS, null, null, null, null, null, null);
        int sizeOfBase = 0;

        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int idColQuestion = cursor.getColumnIndex(QUESTION);
            int idColOption1 = cursor.getColumnIndex(OPTION_1);
            int idColOption2 = cursor.getColumnIndex(OPTION_2);
            int idColOption3 = cursor.getColumnIndex(OPTION_3);
            int idColOption4 = cursor.getColumnIndex(OPTION_4);
            int idColAnswer = cursor.getColumnIndex(CORRECT_ANSWER);
            do {
                ++sizeOfBase;
                Log.d(MY_LOGS, "id:" + cursor.getInt(idColIndex) +
                        ", question: " + cursor.getString(idColQuestion) +
                        ", option1: " + cursor.getString(idColOption1) +
                        ", option2: " + cursor.getString(idColOption2) +
                        ", option3: " + cursor.getString(idColOption3) +
                        ", option4 " + cursor.getString(idColOption4) +
                        ", answer: " + cursor.getString(idColAnswer));
            } while (cursor.moveToNext());
        }
        int idRandomQuestion = 0;
        if (sizeOfBase > 0) {
            idRandomQuestion = sizeOfBase - new Random().nextInt(sizeOfBase);
            if (idRandomQuestion == 0) {
                idRandomQuestion += 1;
            }
        }
        if (cursor.moveToFirst()) {
            int iterationCounter = 0;
            int idColIndex = cursor.getColumnIndex("id");
            int idColQuestion = cursor.getColumnIndex(QUESTION);
            int idColOption1 = cursor.getColumnIndex(OPTION_1);
            int idColOption2 = cursor.getColumnIndex(OPTION_2);
            int idColOption3 = cursor.getColumnIndex(OPTION_3);
            int idColOption4 = cursor.getColumnIndex(OPTION_4);
            int idColAnswer = cursor.getColumnIndex(CORRECT_ANSWER);
            do {
                ++iterationCounter;
                if (iterationCounter == idRandomQuestion) {
                    Log.d(MY_LOGS, "id:" + cursor.getInt(idColIndex) +
                            ", question: " + cursor.getString(idColQuestion) +
                            ", option1: " + cursor.getString(idColOption1) +
                            ", option2: " + cursor.getString(idColOption2) +
                            ", option3: " + cursor.getString(idColOption3) +
                            ", option4 " + cursor.getString(idColOption4) +
                            ", answer: " + cursor.getString(idColAnswer));
                    randomQuestion.setQuestion(cursor.getString(idColQuestion));
                    String[] options = {cursor.getString(idColOption1), cursor.getString(idColOption2), cursor.getString(idColOption3), cursor.getString(idColOption4)};
                    randomQuestion.setOption(options);
                    randomQuestion.setAnswer(Integer.parseInt(cursor.getString(idColAnswer)));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return randomQuestion;
    }

    public static Question getQuestionById(Context pContext, int pIdOfQuestion) {
        SingleChoiceQuestion questionById = new SingleChoiceQuestion();
        DataBaseHelper mDBHelper = new DataBaseHelper(pContext);
        SQLiteDatabase sqLiteDatabase = mDBHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(NAME_TABLE_MY_QUESTIONS, null, "id = " + pIdOfQuestion, null, null, null, null);
        cursor.moveToPosition(10);
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int idColQuestion = cursor.getColumnIndex(QUESTION);
            int idColOption1 = cursor.getColumnIndex(OPTION_1);
            int idColOption2 = cursor.getColumnIndex(OPTION_2);
            int idColOption3 = cursor.getColumnIndex(OPTION_3);
            int idColOption4 = cursor.getColumnIndex(OPTION_4);
            int idColAnswer = cursor.getColumnIndex(CORRECT_ANSWER);
            int idQuestionNumber = cursor.getColumnIndex(QUESTION_NUMBER);
            int idQuestionTopic = cursor.getColumnIndex(TOPIC_ID_QUESTION);
            do {
                Log.d(MY_LOGS, "id:" + cursor.getInt(idColIndex) +
                        ", question: " + cursor.getString(idColQuestion) +
                        ", option1: " + cursor.getString(idColOption1) +
                        ", option2: " + cursor.getString(idColOption2) +
                        ", option3: " + cursor.getString(idColOption3) +
                        ", option4 " + cursor.getString(idColOption4) +
                        ", answer: " + cursor.getString(idColAnswer) +
                        ", questionNumber: " + cursor.getString(idQuestionNumber) +
                        ", topicOfQuestion: " + cursor.getInt(idQuestionTopic));
                questionById.setQuestion(cursor.getString(idColQuestion));
                String[] options = {cursor.getString(idColOption1), cursor.getString(idColOption2), cursor.getString(idColOption3), cursor.getString(idColOption4)};
                questionById.setOption(options);
                questionById.setAnswer(Integer.parseInt(cursor.getString(idColAnswer)));
                questionById.setQuestionNumber(cursor.getInt(idQuestionNumber));
                questionById.setQuestionTopic(cursor.getInt(idQuestionTopic));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return questionById;
    }

    public static int getNumberOfRows(Context pContext) {
        int numberOfRows = 0;
        SingleChoiceQuestion questionById = new SingleChoiceQuestion();
        DataBaseHelper mDBHelper = new DataBaseHelper(pContext);
        SQLiteDatabase sqLiteDatabase = mDBHelper.getWritableDatabase();
        String[] queryStatment = new String[]{"question"};
        Cursor cursor = sqLiteDatabase.query(true, NAME_TABLE_MY_QUESTIONS, queryStatment, null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    ++numberOfRows;
                } while (cursor.moveToNext());
                Log.d(MY_LOGS, "" + numberOfRows);
            }
            cursor.close();
            sqLiteDatabase.close();
        } else {
            Log.d(MY_LOGS, "Cursor is null");
            mDBHelper.close();
        }
        return numberOfRows;
    }

    public static int indexationOfQuestions(Context pContext) {
        SingleChoiceQuestion questionById = new SingleChoiceQuestion();
        DataBaseHelper mDBHelper = new DataBaseHelper(pContext);
        SQLiteDatabase sqLiteDatabase = mDBHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(NAME_TABLE_MY_QUESTIONS, null, null, null, null, null, null);
        int cntQuestions = 0;
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex(ID_OF_QUESTION);
            do {
                ContentValues cv = new ContentValues();
                ++cntQuestions;
                cv.put(QUESTION_NUMBER, cntQuestions);
                sqLiteDatabase.update(NAME_TABLE_MY_QUESTIONS, cv, "id = ?", new String[]{"" + cursor.getInt(idColIndex)});
                Log.d(MY_LOGS, "For question with id:" + cursor.getInt(idColIndex) + " add question number " + cntQuestions);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return cntQuestions;
    }
}
