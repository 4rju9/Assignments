package app.netlify.dev4rju9.myapplication.authentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import app.netlify.dev4rju9.myapplication.MainActivity
import app.netlify.dev4rju9.myapplication.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpActivityCreateButton.setOnClickListener { validateCredentials() }
        binding.signUpActivityLoginButton.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            finish()
        }

    }

    private fun validateCredentials () {

        val email: String = binding.signUpActivityEmailEditText.text.toString()
        val password: String = binding.signUpActivityPassEditText.text.toString()
        val passwordConfirm: String = binding.signUpActivityConfirmPassEditText.text.toString()

        val isValidated: Boolean = validate(email, password, passwordConfirm)
        if (!isValidated) return

        createAccountInFirebase(email, password)
    }

    private fun createAccountInFirebase (email: String, password: String) {

        changeProgressBar()
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                changeProgressBar(false)
                if (!task.isSuccessful) {
                    Toast.makeText(
                        this@SignupActivity,
                        task.exception?.localizedMessage?.toString(),
                        Toast.LENGTH_SHORT,
                    ).show()
                    return@addOnCompleteListener
                }

                Toast.makeText(
                    this@SignupActivity,
                    "Authentication success.",
                    Toast.LENGTH_SHORT,
                ).show()

                val user = auth.currentUser
                MainActivity.sharedPreferences.edit()?.putBoolean("userLoggedIn", true)
                    ?.putString("uid", user?.uid)?.apply()

                startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                finish()

            }

    }

    private fun validate (email: String, password: String, passwordConfirm: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.signUpActivityEmailEditText.error = "Email is invalid"
            return false
        }
        if (password.length < 6) {
            binding.signUpActivityPassEditText.error = "Password is too short"
            return false
        }
        if (password != passwordConfirm) {
            binding.signUpActivityConfirmPassEditText.error = "Password doesn't match"
            return false
        }
        return true
    }

    private fun changeProgressBar (inProgress: Boolean = true) {
        if (inProgress) {
            binding.signUpActivityProgressBar.visibility = View.VISIBLE
            binding.signUpActivityLoginButton.visibility = View.GONE
        } else {
            binding.signUpActivityProgressBar.visibility = View.GONE
            binding.signUpActivityLoginButton.visibility = View.VISIBLE
        }
    }

}