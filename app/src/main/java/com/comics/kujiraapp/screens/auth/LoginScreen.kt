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
import com.comics.kujiraapp.ui.theme.BackgroundCard
import com.comics.kujiraapp.ui.theme.PrimaryAccent
import com.comics.kujiraapp.ui.theme.SecondaryText
import com.comics.kujiraapp.viewmodels.auth.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onSignUpClicked: () -> Unit = {}, onLoginSuccess: () -> Unit = {}) {
  val viewModel: LoginViewModel = viewModel()
  val state by viewModel.state.collectAsState()

  var email by rememberSaveable { mutableStateOf("") }
  var password by rememberSaveable { mutableStateOf("") }
  var passwordVisible by rememberSaveable { mutableStateOf(false) }

  LaunchedEffect(state.success) {
    if (state.success) {
      onLoginSuccess()
    }
  }

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = (BackgroundCard)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      if (state.loading) {
        CircularProgressIndicator()
      } else {
        Spacer(modifier = Modifier.height(60.dp))
        Text("Welcome Back!", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        Text(
          "Log in to continue your adventure.",
          style = MaterialTheme.typography.bodyMedium,
          color = Color.Gray
        )
        Spacer(modifier = Modifier.height(48.dp))

        if (state.error != null) {
          Text(text = "Correo y/o contraseña incorrectos", color = Color.Red)
          Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("Email", color = SecondaryText) },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
          colors = TextFieldDefaults.colors(
            focusedIndicatorColor = PrimaryAccent,
            unfocusedIndicatorColor = PrimaryAccent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = PrimaryAccent,
            focusedLabelColor = SecondaryText,
            unfocusedLabelColor = SecondaryText,
          )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
          value = password,
          onValueChange = { password = it },
          label = { Text("Password", color = SecondaryText) },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
          trailingIcon = {
            val image = if (passwordVisible)
              Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
              Icon(
                imageVector = image,
                description,
                tint = PrimaryAccent
              )
            }
          },
          colors = TextFieldDefaults.colors(
            focusedIndicatorColor = PrimaryAccent,
            unfocusedIndicatorColor = PrimaryAccent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = PrimaryAccent,
            focusedLabelColor = SecondaryText,
            unfocusedLabelColor = SecondaryText,
          )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { /* TODO */ }, modifier = Modifier.align(Alignment.End)) {
          Text("Forgot Password?", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
          onClick = { viewModel.login(email, password) },
          modifier = Modifier.fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent)
        ) {
          Text("Login", color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("OR", color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("Don\'t have an account? ", color = Color.Gray)
          TextButton(onClick = onSignUpClicked) {
            Text("Sign Up", color = PrimaryAccent)
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
  LoginScreen()
}
