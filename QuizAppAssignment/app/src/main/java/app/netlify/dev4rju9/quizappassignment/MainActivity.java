package app.netlify.dev4rju9.quizappassignment;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import app.netlify.dev4rju9.quizappassignment.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private int score = 0, totalQuestion = QuestionAnswers.question.length, currentQuestionIndex = 0;
    private String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ansA.setOnClickListener(this);
        binding.ansB.setOnClickListener(this);
        binding.ansC.setOnClickListener(this);
        binding.ansD.setOnClickListener(this);
        binding.submitButton.setOnClickListener(this);

        loadNewQuestion();

    }

    private void loadNewQuestion () {

        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        binding.totalQuestion.setText((currentQuestionIndex+1)+ "/" + totalQuestion);
        binding.question.setText(QuestionAnswers.question[currentQuestionIndex]);
        binding.ansA.setText(QuestionAnswers.choices[currentQuestionIndex][0]);
        binding.ansB.setText(QuestionAnswers.choices[currentQuestionIndex][1]);
        binding.ansC.setText(QuestionAnswers.choices[currentQuestionIndex][2]);
        binding.ansD.setText(QuestionAnswers.choices[currentQuestionIndex][3]);
    }

    private void finishQuiz () {

        String passStatus = "";
        if (score > totalQuestion * 0.60) passStatus = "Passed!";
        else passStatus = "Failed!!";

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("You got " + score + " out of " + totalQuestion + " right.")
                .setPositiveButton("Restart", (dialog, which) -> restartQuiz())
                .setCancelable(false)
                .show();

    }

    private void restartQuiz () {
        score = currentQuestionIndex = 0;
        loadNewQuestion();
    }

    private void resetAnswerButtons () {
        binding.ansA.setBackgroundColor(Color.WHITE);
        binding.ansB.setBackgroundColor(Color.WHITE);
        binding.ansC.setBackgroundColor(Color.WHITE);
        binding.ansD.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onClick(View view) {

        // changing all answer buttons to white.
        resetAnswerButtons();

        Button button = (Button) view;
        if (button.getId()==R.id.submit_button) {
            // User has clicked the submit button.
            if (selectedAnswer.equals(QuestionAnswers.correctAnswers[currentQuestionIndex])) {
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();
        } else {
            // User has selected an answer.
            selectedAnswer = button.getText().toString();
            button.setBackgroundColor(Color.YELLOW);
        }

    }

}

class QuestionAnswers {

    public static String[] question = {
            "Which company owns the Android?",
            "Which one is not the programming language?",
            "Will i be hired?"
    };
    public static String[][] choices = {
            {"Google", "Apple", "Nokia", "Samsung"},
            {"Java", "Kotlin", "Notepad", "Python"},
            {"Maybe Yes", "Yes", "Definitely", "100% Yes"}
    };
    public static String[] correctAnswers = {
            "Google",
            "Notepad",
            "100% Yes"
    };

}