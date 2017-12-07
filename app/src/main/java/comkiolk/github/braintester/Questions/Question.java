package comkiolk.github.braintester.Questions;

public interface Question {

    String getQuestion(int pInt);

    String[] getOptions(int pInt);

    int getAnswer(int pInt);

    int getQuestionNumber();

    int getQuestionTopic();
}
