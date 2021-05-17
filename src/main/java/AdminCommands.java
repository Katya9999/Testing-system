public interface AdminCommands {
    void removeTest(String testName, TestBuilder testBuilder);
    void addQuestion(TestBuilder testBuilder); //добавить вопрос с вариантами ответов на него к существующему тесту
    void addAnswer(String testName, TestBuilder testBuilder); //добавить дополнительные ответы к вопросу
    void addCorrectAnswersToQuestions(TestBuilder testBuilder); //используется при создании теста
    void deleteStudent(int studentNumber);
    void showStudentList();
    void deleteQuestion(TestBuilder testBuilder);
    void deleteAnswer(TestBuilder testBuilder);
}
