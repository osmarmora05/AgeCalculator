package com.example.calculatorage

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculatorage.ui.theme.CalculatorAgeTheme
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorAgeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AgeCalculator()
                }
            }
        }
    }
}

@Composable
fun AgeCalculator() {
    var inputName by remember { mutableStateOf("") }
    var inputAge by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val context  = LocalContext.current

    fun cleanFields(){
        inputName = ""
        inputAge = ""
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Age calculator", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = inputName, onValueChange = {
            inputName = it
        }, label = {
            Text(text = "Enter your name")
        })
        OutlinedTextField(value = inputAge, onValueChange = {
            inputAge = it
        }, label = {
            Text(text = "Enter your age")
        })
        Spacer(modifier = Modifier.height(10.dp))
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { cleanFields()}) {
                Text(text = "Clean fields")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {

                if (inputName.isEmpty() && inputAge.isEmpty()){
                    Toast.makeText(context,"Hey! you forgot to fill out the fields",Toast.LENGTH_SHORT).show()
                }else if (inputName.isEmpty()){
                    Toast.makeText(context,"Hey! you forgot to enter your name.",Toast.LENGTH_SHORT).show()
                }else if (inputAge.isEmpty()){
                    Toast.makeText(context,"Hey! you forgot to enter your age",Toast.LENGTH_SHORT).show()
                }else if (inputAge.toInt() <=0){
                    Toast.makeText(context,"Hey! age must be greater than 0",Toast.LENGTH_SHORT).show()
                }else{
                    showDialog = true
                }
            }) {
                Text(text = "Ok")
            }

            if(showDialog){
                AlertDialog(onDismissRequest = {
                    showDialog = false
                    cleanFields()
               }, confirmButton = {
                    Column {
                        Button(onClick = {
                            cleanFields()
                            showDialog = false
                        }) {
                            Text(text = "Ok")
                        }
                    }
                }, text = {
                    Column {
                        if (inputAge.toInt() <=17){
                            Text(text = "Hello $inputName you are a minor!")
                            Image(
                                painter = painterResource(R.drawable.under_age),
                                contentDescription = null,
                            )
                        }else{
                            Text(text = "Hello $inputName you are of legal age!")
                            Image(
                                painter = painterResource(R.drawable.adult),
                                contentDescription = null,
                            )
                        }
                    }
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgeCalculatorPreview() {
    AgeCalculator()
}
