package comkiolk.github.braintester;

import org.junit.Before;
import org.junit.Test;

import comkiolk.github.braintester.Questions.SingleChoiceQuestion;

import static org.junit.Assert.*;


public class SingleChoiceQuestionTest {

    SingleChoiceQuestion mQuestion;

    @Before
    public void setup() {
        mQuestion = new SingleChoiceQuestion();
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void notNullReturn(){
        assertNotNull(mQuestion.getAnswer(1));
        assertNotNull(mQuestion.getOptions(1));
        assertNotNull(mQuestion.getQuestion(1));
    }

    @Test
    public void checkReturnResult(){
        assertEquals("Capital of Grate Britain?", mQuestion.getQuestion(1));
        assertEquals("Paris", mQuestion.getOptions(1)[2]);
        assertEquals(3, mQuestion.getAnswer(1));
    }

}