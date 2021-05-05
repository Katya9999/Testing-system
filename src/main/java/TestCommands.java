public interface TestCommands {
    void removePairQuestionAndAnswers();
    void addPairQuestionAndAnswers();
    void addAnswersToQuestion();
    void changeAnswersToQuestion();
    void deleteAnswersToQuestion(); //но чтоб осталось не меньше двух вариантов ответа
}
