package comkiolk.github.braintester.UI.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import comkiolk.github.braintester.MainActivity;
import comkiolk.github.braintester.Questions.Question;
import comkiolk.github.braintester.R;
import comkiolk.github.braintester.TestCase.TestCase;
import comkiolk.github.braintester.database.GetFromDB;

import static comkiolk.github.braintester.MainActivity.EMPTY_TEXT;

public class TestingPage extends AppCompatActivity {

    TextView mQuestionTextView;
    TextView mNumberChoseQuestions;
    RadioButton mFirstOptionRadio;
    RadioButton mSecondOptionRadio;
    RadioButton mThirdOptionRadio;
    RadioButton mForthOptionRadio;
    Button mAddQuestion;
    Button mRemoveQuestion;
    Button mStartTest;
    int cntNumberOfQuestions = 2;
    int mQuestionsShowed = 0;
    boolean mIsTestStart;
    int mCorrectAnswerForQuestion;
    int mNumberOfCorrectAnswers;
    TestCase mCustomTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_page);

        initialization();
    }

    private void initialization() {
        mIsTestStart = false;
        mCustomTest = new TestCase();
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mFirstOptionRadio = (RadioButton) findViewById(R.id.first_option_radio_button);
        mSecondOptionRadio = (RadioButton) findViewById(R.id.second_option_radio_button);
        mThirdOptionRadio = (RadioButton) findViewById(R.id.third_option_radio_button);
        mForthOptionRadio = (RadioButton) findViewById(R.id.forth_option_radio_button);

        mAddQuestion = (Button) findViewById(R.id.add_number_of_questions_button);
        mRemoveQuestion = (Button) findViewById(R.id.remove_number_of_questions_button);
        mStartTest = (Button) findViewById(R.id.start_custom_test_button);
        mNumberChoseQuestions = (TextView) findViewById(R.id.number_chose_question_text_view);
        mNumberChoseQuestions.setText("" + cntNumberOfQuestions);

        View.OnClickListener clickButton = new View.OnClickListener() {

            @Override
            public void onClick(View pView) {
                switch (pView.getId()){
                    case R.id.add_number_of_questions_button:
                        cntNumberOfQuestions += 1;
                        mNumberChoseQuestions.setText("" + cntNumberOfQuestions);
                        break;
                    case R.id.remove_number_of_questions_button:
                        if(cntNumberOfQuestions > 0) {
                            cntNumberOfQuestions -= 1;
                            mNumberChoseQuestions.setText("" + cntNumberOfQuestions);
                        }
                        break;
                    case R.id.start_custom_test_button:
                        if(!mIsTestStart) {
                            mNumberOfCorrectAnswers = 0;
                            Question singleQuestion;
                            for (int i = 0; i < cntNumberOfQuestions; ++i) {
                                singleQuestion = GetFromDB.getRandomQuestion(TestingPage.this);
                                mCustomTest.addQuestionForArray(i, singleQuestion);
                            }
                            mStartTest.setText(R.string.NEXT_QUESTION);
                            mIsTestStart = true;
                            setQuestionOnScreen(mCustomTest.getOneOrderQuestion(0));

                        }else{
                            if(checkForCorrectAnswer(mCustomTest.getOneOrderQuestion(mQuestionsShowed))){
                                ++mNumberOfCorrectAnswers;
                            }
                            ++mQuestionsShowed;
                            if(mQuestionsShowed < cntNumberOfQuestions){
                                cleanFormForQuestion();
                                setQuestionOnScreen(mCustomTest.getOneOrderQuestion(mQuestionsShowed));
//                                ++mQuestionsShowed;
                            }else{
                                mStartTest.setText(R.string.START_TEST_BUTTON);
                                mIsTestStart = false;
                                mQuestionsShowed = 0;
                                mQuestionTextView.setText("Number of correct answers: " + mNumberOfCorrectAnswers);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        mAddQuestion.setOnClickListener(clickButton);
        mRemoveQuestion.setOnClickListener(clickButton);
        mStartTest.setOnClickListener(clickButton);
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

    private boolean checkForCorrectAnswer(Question pQuestion){
        boolean isCorrectAnswer = false;
        int answerOfQuestion = pQuestion.getAnswer(1);
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
            Toast.makeText(TestingPage.this, getString(R.string.NOTHIN_CHOOSE_TOAST), Toast.LENGTH_LONG).show();
        }
        if(answerOfQuestion - 1 == numberChoiceOfUser){
            isCorrectAnswer = true;
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
        return isCorrectAnswer;
    }
}
