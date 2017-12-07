package comkiolk.github.braintester;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import comkiolk.github.braintester.Questions.Question;
import comkiolk.github.braintester.UI.activities.TestingPage;
import comkiolk.github.braintester.UI.fragments.TestFragment;
import comkiolk.github.braintester.database.GetFromDB;

public class MainActivity extends AppCompatActivity {

    public static final String EMPTY_TEXT = "";
    TextView mQuestionTextView;
    TextView mNumberOfQuestions;
    RadioButton mFirstOptionRadio;
    RadioButton mSecondOptionRadio;
    RadioButton mThirdOptionRadio;
    RadioButton mForthOptionRadio;
    Button mCheckAnswerButton;
    Button mStartTest;
    Button mRandomQuestionButton;
    Button mCreateTest;
    Button mStartTesting;
    int mCorrectAnswerForQuestion;
    int mCounterOfQuestion = 1;
    TestFragment mTestFragment;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

    }

    private void initialization() {
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mNumberOfQuestions = (TextView) findViewById(R.id.number_of_question_text_view);
        mNumberOfQuestions.setText(getString(R.string.TOTAL_NUMBER_OF_QUESTIONS) + GetFromDB.indexationOfQuestions(this));
        mFirstOptionRadio = (RadioButton) findViewById(R.id.first_option_radio_button);
        mSecondOptionRadio = (RadioButton) findViewById(R.id.second_option_radio_button);
        mThirdOptionRadio = (RadioButton) findViewById(R.id.third_option_radio_button);
        mForthOptionRadio = (RadioButton) findViewById(R.id.forth_option_radio_button);
        mCheckAnswerButton = (Button) findViewById(R.id.chek_answer_button);
        mStartTest = (Button) findViewById(R.id.start_test_button);
        mRandomQuestionButton = (Button) findViewById(R.id.random_question_button);
        mCreateTest = (Button) findViewById(R.id.create_test_button);
        mStartTesting = (Button) findViewById(R.id.start_testing_button);

        mTestFragment = new TestFragment();
        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.test_frame_layout, mTestFragment);
        mFragmentTransaction.commit();

        View.OnClickListener clickBtn = new View.OnClickListener() {

            @Override
            public void onClick(View pView) {
//                int mCorrectAnswerForQuestion;
//                Question firstQuestion = new GetFromDB().getRandomQuestion(MainActivity.this);
//                ShowQuestion showQuestion;
                switch (pView.getId()) {
                    case R.id.chek_answer_button:
                        int numberChoiceOfUser = 0;
                        if (mFirstOptionRadio.isChecked()) {
                            numberChoiceOfUser = 0;
                        } else if (mSecondOptionRadio.isChecked()) {
                            numberChoiceOfUser = 1;
                        } else if (mThirdOptionRadio.isChecked()) {
                            numberChoiceOfUser = 2;
                        } else if (mForthOptionRadio.isChecked()) {
                            numberChoiceOfUser = 3;
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.NOTHIN_CHOOSE_TOAST), Toast.LENGTH_LONG).show();
                        }
                        if(mCorrectAnswerForQuestion - 1 == numberChoiceOfUser){
                            if(numberChoiceOfUser == 0){
                                mFirstOptionRadio.setBackgroundColor(Color.GREEN);
                            }else if(numberChoiceOfUser == 1){
                                mSecondOptionRadio.setBackgroundColor(Color.GREEN);
                            }else if(numberChoiceOfUser == 2){
                                mThirdOptionRadio.setBackgroundColor(Color.GREEN);
                            }else if(numberChoiceOfUser == 3){
                                mForthOptionRadio.setBackgroundColor(Color.GREEN);
                            }else{

                            }
                        } else {
                            if(numberChoiceOfUser == 0){
                                mFirstOptionRadio.setBackgroundColor(Color.RED);
                            }else if(numberChoiceOfUser == 1){
                                mSecondOptionRadio.setBackgroundColor(Color.RED);
                            }else if(numberChoiceOfUser == 2){
                                mThirdOptionRadio.setBackgroundColor(Color.RED);
                            }else if(numberChoiceOfUser == 3){
                                mForthOptionRadio.setBackgroundColor(Color.RED);
                            }else{

                            }
                        }
                        break;
                    case R.id.start_test_button:
                        cleanFormForQuestion();
                        Question firstQuestion;
                        do {
                            firstQuestion = new GetFromDB().getQuestionById(MainActivity.this, mCounterOfQuestion);
                            ++mCounterOfQuestion;
                        }while (firstQuestion.getQuestion(1) == null && mCounterOfQuestion < 1000 );
                        if(mCounterOfQuestion >= 1000){
                            mCounterOfQuestion = 1;
                            Toast.makeText(MainActivity.this, R.string.COMPLETE_ALL_QUESTIONS, Toast.LENGTH_LONG).show();
                            break;
                        }
                        setQuestionOnScreen(firstQuestion);
                        break;
                    case R.id.random_question_button:
                        cleanFormForQuestion();
                        Question randomQuestion = new GetFromDB().getRandomQuestion(MainActivity.this);
                        setQuestionOnScreen(randomQuestion);
                        GetFromDB.getNumberOfRows(MainActivity.this);
//                        GetFromDB.indexationOfQuestions(MainActivity.this);
                        break;
                    case R.id.create_test_button:
                        Intent intent = new Intent(MainActivity.this, CreateTest.class);
                        startActivity(intent);
                        break;
                    case R.id.start_testing_button:
                        Intent intentTestingPage = new Intent(MainActivity.this, TestingPage.class);
                        startActivity(intentTestingPage);
                        break;
                    default:
                        break;
                }
            }
        };

        mCheckAnswerButton.setOnClickListener(clickBtn);
        mStartTest.setOnClickListener(clickBtn);
        mCreateTest.setOnClickListener(clickBtn);
        mRandomQuestionButton.setOnClickListener(clickBtn);
        mStartTesting.setOnClickListener(clickBtn);
    }

    private void setQuestionOnScreen(Question pFirstQuestion) {
        mQuestionTextView.setText(pFirstQuestion.getQuestion(1));
        mFirstOptionRadio.setText(pFirstQuestion.getOptions(1)[0]);
        mSecondOptionRadio.setText(pFirstQuestion.getOptions(1)[1]);
        mThirdOptionRadio.setText(pFirstQuestion.getOptions(1)[2]);
        mForthOptionRadio.setText(pFirstQuestion.getOptions(1)[3]);
        mCorrectAnswerForQuestion = pFirstQuestion.getAnswer(1);
    }

    private void cleanFormForQuestion(){
        mQuestionTextView.setText(EMPTY_TEXT);
        mFirstOptionRadio.setText(EMPTY_TEXT);
        mFirstOptionRadio.setBackgroundColor(Color.WHITE);
        mFirstOptionRadio.setChecked(false);
        mSecondOptionRadio.setText(EMPTY_TEXT);
        mSecondOptionRadio.setBackgroundColor(Color.WHITE);
        mSecondOptionRadio.setChecked(false);
        mThirdOptionRadio.setText(EMPTY_TEXT);
        mThirdOptionRadio.setBackgroundColor(Color.WHITE);
        mThirdOptionRadio.setChecked(false);
        mForthOptionRadio.setText(EMPTY_TEXT);
        mForthOptionRadio.setBackgroundColor(Color.WHITE);
        mForthOptionRadio.setChecked(false);

    }

    class ShowQuestion {

        Question mQuestion;

        int mNumberOfQuestion;

        int mCorrectAnswer;

        private ShowQuestion(Question pQuestion, int pNumberOfQuestion) {
            mQuestion = pQuestion;
            mNumberOfQuestion = pNumberOfQuestion;



            mCorrectAnswer = mQuestion.getAnswer(pNumberOfQuestion);

        }

        public int checkCorrectAnswer() {
            return mCorrectAnswer;
        }

        public boolean isCorrectAnswer(int pUserAnswer){
            if (pUserAnswer == mCorrectAnswer){
                return true;
            }
            return false;
        }
    }
}
