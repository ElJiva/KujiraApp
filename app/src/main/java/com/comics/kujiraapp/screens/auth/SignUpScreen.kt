package com.comics.kujiraapp.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.comics.kujiraapp.ui.theme.PrimaryAccent
import com.comics.kujiraapp.ui.theme.SecondaryText
import com.comics.kujiraapp.viewmodels.auth.SignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
  onLoginClicked: () -> Unit = {}
) {

  val viewModel: SignUpViewModel = viewModel()
  val state by viewModel.state.collectAsState()

  var username by rememberSaveable { mutableStateOf("") }
  var email by rememberSaveable { mutableStateOf("") }
  var password by rememberSaveable { mutableStateOf("") }
  var confirmPassword by rememberSaveable { mutableStateOf("") }
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

  LaunchedEffect(state.success) {
    if (state.success) {
      onLoginClicked()
    }
  }

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color(0xFF121212)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {

      Text(
        "Create Your Account",
        style = MaterialTheme.typography.headlineMedium,
        color = SecondaryText
      )

      Spacer(modifier = Modifier.height(48.dp))

      OutlinedTextField(
        value = username,
        onValueChange = { username = it },
        label = { Text("UserName", color = SecondaryText) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
      )

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Email", color = SecondaryText) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
      )

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Password", color = SecondaryText) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
          IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(
              imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
              contentDescription = null
            )
          }
        }
      )

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(
        value = confirmPassword,
        onValueChange = { confirmPassword = it },
        label = { Text("Confirm Password", color = SecondaryText) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
          IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
            Icon(
              imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
              contentDescription = null
            )
          }
        }
      )

      Spacer(modifier = Modifier.height(24.dp))

      state.error?.let {
        Text(text = it, color = Color.Red)
        Spacer(modifier = Modifier.height(12.dp))
      }

      Button(
        onClick = {
          if (password == confirmPassword) {
            viewModel.signUp(username, email, password, confirmPassword)
          }
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = !state.loading,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
      ) {
        if (state.loading) {
          CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
        } else {
          Text("Register", color = Color.White)
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("Already have an account? ", color = Color.Gray)
        TextButton(onClick = onLoginClicked) {
          Text("Log In", color = Color.Red)
        }
      }
    }
  }
}


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
  SignUpScreen()
}
