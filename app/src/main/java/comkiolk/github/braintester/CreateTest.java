package comkiolk.github.braintester;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import comkiolk.github.braintester.Questions.Question;
import comkiolk.github.braintester.Questions.SingleChoiceQuestion;
import comkiolk.github.braintester.database.GetFromDB;

import static comkiolk.github.braintester.utils.Utils.*;

public class CreateTest extends AppCompatActivity {

//    static final int VERSION_OF_DB = 1;
//    static final String QUESTIONS_DB = "questionsDB";

//    public static final String QUESTION = "question";
//    public static final String OPTION_1 = "option1";
//    public static final String OPTION_2 = "option2";
//    public static final String OPTION_3 = "option3";
//    public static final String OPTION_4 = "option4";
//    public static final String CORRECT_ANSWER = "correctAnswer";
//    public static final String NAME_TABLE_MY_QUESTIONS = "myQuestions";
    EditText mIdEdit;
    EditText mQuestionEdit;
    EditText mOption1Edit;
    EditText mOption2Edit;
    EditText mOption3Edit;
    EditText mOption4Edit;
    EditText mCorrectAnswer;
    EditText mTopicQuestion;
    Button mAddButton;
    Button mGetButton;
    Button mUpdateButton;
    Button mDeleteButton;
    Button mDeleteRawButton;
//    Button mTestMethod;
    Button mFindByID;
    DataBaseHelper mBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
        initialization();
    }

    private void initialization() {
        mIdEdit = (EditText) findViewById(R.id.id_edit_text);
        mQuestionEdit = (EditText) findViewById(R.id.question_edit_text);
        mOption1Edit = (EditText) findViewById(R.id.option_1_edit_text);
        mOption2Edit = (EditText) findViewById(R.id.option_2_edit_text);
        mOption3Edit = (EditText) findViewById(R.id.option_3_edit_text);
        mOption4Edit = (EditText) findViewById(R.id.option_4_edit_text);
        mCorrectAnswer = (EditText) findViewById(R.id.correct_answer_edit_text);
        mTopicQuestion = (EditText) findViewById(R.id.topic_of_question_edit_text);
        mAddButton = (Button) findViewById(R.id.add_new_test_button);
        mGetButton = (Button) findViewById(R.id.get_test_button);
        mUpdateButton = (Button) findViewById(R.id.update_test_button);
        mDeleteButton = (Button) findViewById(R.id.delete_test_button);
        mDeleteRawButton = (Button) findViewById(R.id.delete_one_raw_button);
        mFindByID = (Button) findViewById(R.id.find_question_button);
        mBaseHelper = new DataBaseHelper(this);

        View.OnClickListener clickBtn = new View.OnClickListener() {

            @Override
            public void onClick(View pView) {
                ContentValues mValues = new ContentValues();
                String id = mIdEdit.getText().toString();
                String question = mQuestionEdit.getText().toString();
                String option1 = mOption1Edit.getText().toString();
                String option2 = mOption2Edit.getText().toString();
                String option3 = mOption3Edit.getText().toString();
                String option4 = mOption4Edit.getText().toString();
                String correctAnswer = mCorrectAnswer.getText().toString();
                String topicOfQuestion = mTopicQuestion.getText().toString();

                SQLiteDatabase sqLiteDatabase = mBaseHelper.getWritableDatabase();

                switch (pView.getId()) {
                    case R.id.add_new_test_button:
                        mValues.put(QUESTION, question);
                        mValues.put(OPTION_1, option1);
                        mValues.put(OPTION_2, option2);
                        mValues.put(OPTION_3, option3);
                        mValues.put(OPTION_4, option4);
                        mValues.put(CORRECT_ANSWER, correctAnswer);
                        mValues.put(TOPIC_ID_QUESTION, topicOfQuestion);
                        Long rawId = sqLiteDatabase.insert(NAME_TABLE_MY_QUESTIONS, null, mValues);
                        break;
                    case R.id.get_test_button:
                        Cursor cursor = sqLiteDatabase.query(NAME_TABLE_MY_QUESTIONS, null, null, null, null, null, null);
                        if (cursor.moveToFirst()) {
                            int idColIndex = cursor.getColumnIndex("id");
                            int idColQuestion = cursor.getColumnIndex(QUESTION);
                            int idColOption1 = cursor.getColumnIndex(OPTION_1);
                            int idColOption2 = cursor.getColumnIndex(OPTION_2);
                            int idColOption3 = cursor.getColumnIndex(OPTION_3);
                            int idColOption4 = cursor.getColumnIndex(OPTION_4);
                            int idColAnswer = cursor.getColumnIndex(CORRECT_ANSWER);
                            int idQuestionNumber = cursor.getColumnIndex(QUESTION_NUMBER);
                            do {
                                Log.d(MY_LOGS, "id:" + cursor.getInt(idColIndex) +
                                        ", question: " + cursor.getString(idColQuestion) +
                                        ", option1: " + cursor.getString(idColOption1) +
                                        ", option2: " + cursor.getString(idColOption2) +
                                        ", option3: " + cursor.getString(idColOption3) +
                                        ", option4 " + cursor.getString(idColOption4) +
                                        ", answer: " + cursor.getString(idColAnswer) +
                                        ", questionNumber: " + cursor.getString(idQuestionNumber));
                            } while (cursor.moveToNext());
                        }
                        cursor.close();
                        break;
                    case R.id.update_test_button:
                        if(id.equalsIgnoreCase("")){
                            break;
                        }
                        mValues.put(QUESTION, question);
                        mValues.put(OPTION_1, option1);
                        mValues.put(OPTION_2, option2);
                        mValues.put(OPTION_3, option3);
                        mValues.put(OPTION_4, option4);
                        mValues.put(CORRECT_ANSWER, correctAnswer);
                        mValues.put(TOPIC_ID_QUESTION, topicOfQuestion);

                        int updateRaw = sqLiteDatabase.update(NAME_TABLE_MY_QUESTIONS, mValues, "id = ?", new String[]{id});

                        Log.d(MY_LOGS, "Information update in raw: " + updateRaw);
                        break;
                    case R.id.delete_test_button:
                        int deleteRaw = sqLiteDatabase.delete(NAME_TABLE_MY_QUESTIONS, null, null);
                        Log.d(MY_LOGS, "Number deleted raw: " + deleteRaw);
                        break;
                    case R.id.delete_one_raw_button:
                        if(id.equalsIgnoreCase("")) {
                            break;
                        }
                        int delRaw = sqLiteDatabase.delete(NAME_TABLE_MY_QUESTIONS, "id = " + id, null);

                        Log.d(MY_LOGS, "Deleted raw: " + delRaw);
                        break;
//                    case R.id.test_method_button:
//                        new GetFromDB().getRandomQuestion(CreateTest.this);
//                        break;
                    case R.id.find_question_button:
                        if (!mIdEdit.getText().toString().equals("")){
                            int numberOfQuestion = Integer.parseInt(mIdEdit.getText().toString());
                            Question questionById = GetFromDB.getQuestionById(CreateTest.this, numberOfQuestion);
                            mQuestionEdit.setText(questionById.getQuestion(1));
                            String[] options = questionById.getOptions(1);
                            mOption1Edit.setText(options[0]);
                            mOption2Edit.setText(options[1]);
                            mOption3Edit.setText(options[2]);
                            mOption4Edit.setText(options[3]);
                            mCorrectAnswer.setText("" + questionById.getAnswer(1));
                            String topicQuestion = "" + questionById.getQuestionTopic();
                            mTopicQuestion.setText(topicQuestion);
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        mAddButton.setOnClickListener(clickBtn);
        mGetButton.setOnClickListener(clickBtn);
        mUpdateButton.setOnClickListener(clickBtn);
        mDeleteButton.setOnClickListener(clickBtn);
        mDeleteRawButton.setOnClickListener(clickBtn);
//        mTestMethod.setOnClickListener(clickBtn);
        mFindByID.setOnClickListener(clickBtn);
    }

    class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, QUESTIONS_DB, null, VERSION_OF_DB);
        }

        @Override
        public void onCreate(SQLiteDatabase pSQLiteDatabase) {
            pSQLiteDatabase.execSQL("create table " + NAME_TABLE_MY_QUESTIONS + " (" +
                    "id integer primary key autoincrement," +
                    "question text," +
                    "option1 text," +
                    "option2 text," +
                    "option3 text," +
                    "option4 text," +
                    "correctAnswer text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase pSQLiteDatabase, int pI, int pI1) {

        }
    }

    public SingleChoiceQuestion getQuestionFromBase(int pId) {
        SQLiteDatabase sqLiteDatabase = mBaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(NAME_TABLE_MY_QUESTIONS, null, null, null, null, null, null);
        int totalNumber = 0;
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int idColQuestion = cursor.getColumnIndex(QUESTION);
            int idColOption1 = cursor.getColumnIndex(OPTION_1);
            int idColOption2 = cursor.getColumnIndex(OPTION_2);
            int idColOption3 = cursor.getColumnIndex(OPTION_3);
            int idColOption4 = cursor.getColumnIndex(OPTION_4);
            int idColAnswer = cursor.getColumnIndex(CORRECT_ANSWER);
            do {
                ++totalNumber;
                if (pId == cursor.getInt(idColIndex)) {
                    SingleChoiceQuestion question = new SingleChoiceQuestion();
                    question.setQuestion(cursor.getString(idColQuestion));
                    String array[] = new String[4];
                    array[0] = cursor.getString(idColOption1);
                    array[1] = cursor.getString(idColOption2);
                    array[2] = cursor.getString(idColOption3);
                    array[3] = cursor.getString(idColOption4);
                    question.setOption(array);
                    int answer = Integer.parseInt(cursor.getString(idColAnswer));
                    question.setAnswer(answer);
                    return question;
                }
            } while (cursor.moveToNext());
        }
        return null;
    }
}
