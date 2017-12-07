package comkiolk.github.braintester.TestCase;

import java.util.ArrayList;

import comkiolk.github.braintester.Questions.Question;

public class TestCase {

    private ArrayList<Question> mTestQuestions = new ArrayList<>();

    public ArrayList<Question> getTestQuestions() {
        return mTestQuestions;
    }

    public void setTestQuestions(ArrayList<Question> pTestQuestions) {
        mTestQuestions = pTestQuestions;
    }

    public void addQuestionForArray(int pIndex, Question pQuestion){
        mTestQuestions.add(pIndex, pQuestion);
    }

    public Question getOneOrderQuestion (int pIndex){
        Question oneQuestion = mTestQuestions.get(pIndex);
        return oneQuestion;
    }
}
