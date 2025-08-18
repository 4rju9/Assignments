package app.netlify.dev4rju9.myapplication.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.netlify.dev4rju9.myapplication.MainActivity
import app.netlify.dev4rju9.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginActivityLoginButton.setOnClickListener { validateCredentials() }
        binding.loginActivityCreateAccountButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            finish()
        }

    }

    private fun validateCredentials () {

        val email: String = binding.loginActivityEmailEditText.text.toString()
        val password: String = binding.loginActivityPassEditText.text.toString()

        val isValidated: Boolean = validate(email, password)
        if (!isValidated) return

        loginAccountInFirebase(email, password)
    }

    private fun loginAccountInFirebase (email: String, password: String) {

        changeProgressBar()
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                changeProgressBar(false)
                if (!task.isSuccessful) {
                    Toast.makeText(
                        this@LoginActivity,
                        task.exception?.localizedMessage?.toString(),
                        Toast.LENGTH_SHORT,
                    ).show()
                    return@addOnCompleteListener
                }

                Toast.makeText(
                    this@LoginActivity,
                    "Authentication success.",
                    Toast.LENGTH_SHORT,
                ).show()

                val user = auth.currentUser
                MainActivity.sharedPreferences.edit()?.putBoolean("userLoggedIn", true)
                    ?.putString("uid", user?.uid)?.apply()

                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()

            }

    }

    private fun validate (email: String, password: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.loginActivityEmailEditText.error = "Email is invalid"
            return false
        }
        if (password.length < 6) {
            binding.loginActivityPassEditText.error = "Password is too short"
            return false
        }
        return true
    }

    private fun changeProgressBar (inProgress: Boolean = true) {
        if (inProgress) {
            binding.loginActivityProgressBar.visibility = View.VISIBLE
            binding.loginActivityLoginButton.visibility = View.GONE
        } else {
            binding.loginActivityProgressBar.visibility = View.GONE
            binding.loginActivityLoginButton.visibility = View.VISIBLE
        }
    }

}