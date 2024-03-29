package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.components.InputField
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun TopHeader(totalPerPerson: Double = 134.0){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        color= Color(0xFFE9D7F7)
    ){
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            val total = "%.2f".format(totalPerPerson)
            Text("Total Person", style= MaterialTheme.typography.h5)
            Text("$$totalPerPerson", style=MaterialTheme.typography.h4, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainContent(){
    BillForm(){
        billAmt ->Log.d("AMT", "Main content: ${billAmt.toInt()}")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier,
             onValChange: (String)-> Unit= {}){


                 val totalBillState= remember{
                     mutableStateOf("")
                 }
                 val validState = remember(totalBillState.value){
                     totalBillState.value.toString().trim().isNotEmpty()
                 }

                 val keyboardController = LocalSoftwareKeyboardController.current

                 Surface(
                     modifier= Modifier
                         .padding(2.dp)
                         .fillMaxWidth(),
                     shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                     border = BorderStroke(width = 1.dp, color= Color.LightGray)
                 ){
                     Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
                         InputField(
                             valueState = totalBillState,
                             labelId = "Enter Bill",
                             enabled = true,
                             isSingleLine = true,
                             onAction = KeyboardActions {
                                 if(!validState) return@KeyboardActions
                                 //Todo - onvaluechanged
                                onValChange(totalBillState.value.trim())
                                 keyboardController?.hide()
                             })

                         if(!validState){
                             Row(modifier = Modifier.padding(3.dp),
                             horizontalArrangement = Arrangement.Start){
                                 Text("Split",
                                     modifier = Modifier.align(alignment = Alignment.CenterVertically))
                                 Spacer(modifier= Modifier.width(120.dp))
                                 Row(modifier=Modifier.padding(horizontal = 3.dp),
                                     horizontalArrangement = Arrangement.End
                                 ) {
                                    RoundIconButton(
                                        imageVector = Icons.Default.Add,
                                        onClick = { /*TODO*/ })
                                     RoundIconButton(
                                         imageVector = Icons.Default.,
                                         onClick = { /*TODO*/ })
                                 }
                             }
                         }

                     }
                 }



}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        TopHeader()
    }
}