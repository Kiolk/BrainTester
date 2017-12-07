package comkiolk.github.braintester.Questions;

public class SingleChoiceQuestion implements Question {

    private String mQuestion;
    private String[] mOption = new String[4];
    private int mAnswer;
    private int mQuestionNumber;
    private int mQuestionTopic;

    @Override
    public int getQuestionTopic() {
        return mQuestionTopic;
    }

    public void setQuestionTopic(int pQuestionTopic) {
        mQuestionTopic = pQuestionTopic;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String pQuestion) {
        mQuestion = pQuestion;
    }

    public int getQuestionNumber() {
        return mQuestionNumber;
    }

    public void setQuestionNumber(int pQuestionNumber) {
        mQuestionNumber = pQuestionNumber;
    }

    public String[] getOption() {
        return mOption;
    }

    public void setOption(String[] pOption) {
        mOption = pOption;
    }

    public int getAnswer() {
        return mAnswer;
    }

    public void setAnswer(int pAnswer) {
        mAnswer = pAnswer;
    }

    @Override
    public String getQuestion(int pInt) {
        return mQuestion;
    }

    @Override
    public String[] getOptions(int pInt) {
        return mOption;
    }

    @Override
    public int getAnswer(int pInt) {
        return mAnswer;
    }

}
