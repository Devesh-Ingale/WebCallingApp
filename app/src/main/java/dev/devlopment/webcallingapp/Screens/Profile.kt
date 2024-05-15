package dev.devlopment.webcallingapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.devlopment.webcallingapp.R
import dev.devlopment.webcallingapp.ViewModels.AuthViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Profile(viewModel: AuthViewModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(android.graphics.Color.parseColor("#f2f1f6"))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(modifier = Modifier
            .height(250.dp)
            .background(color = Color(android.graphics.Color.parseColor("#32357a")))) {
            val (topImg,pofile,title,back,pen)=createRefs()
            Image(painter = painterResource(id = R.drawable.arc_3), contentDescription =null,
                Modifier
                    .fillMaxWidth()
                    .constrainAs(topImg) {
                        bottom.linkTo(parent.bottom)
                    }
            )

            Image(painter = painterResource(id = R.drawable.user_2), contentDescription =null,
                Modifier
                    .fillMaxWidth()
                    .constrainAs(pofile) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Text(text = "Profile",
                style = TextStyle(Color.White,
                    fontSize = 30.sp),
                modifier = Modifier.constrainAs(title){
                    top.linkTo(parent.top, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
        Text(text = "Devesh Ingale",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp),
            color = Color(android.graphics.Color.parseColor("#32357a"))
        )
        Text(text = "deveshingale05@gmail.com",
            fontSize = 18.sp,
            color = Color(android.graphics.Color.parseColor("#747679"))
        )

       LazyColumn {

       }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 10.dp)
            .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.btn_1), contentDescription = null,
                    modifier =Modifier.padding(end = 5.dp)
                )
            }
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
               ) {
                Text(text = "notification",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { /*Todo*/ }
                )
            }


        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
            .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.btn_2), contentDescription = null,
                    modifier =Modifier.padding(end = 5.dp)
                )
            }
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
            ) {
                Text(text = "Calender",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { /*Todo*/ }
                )
            }


        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
            .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.btn_3), contentDescription = null,
                    modifier =Modifier.padding(end = 5.dp)
                )
            }
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
            ) {
                Text(text = "Location",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { /*Todo*/ }
                )
            }


        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
            .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.btn_4), contentDescription = null,
                    modifier =Modifier.padding(end = 5.dp)
                )
            }
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
            ) {
                Text(text = "Contacts",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { /*Todo*/ }
                )
            }


        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
            .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.btn_5), contentDescription = null,
                    modifier =Modifier.padding(end = 5.dp)
                )
            }
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
            ) {
                Text(text = "Phone",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { /*Todo*/ }
                )
            }


        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
            .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.btn_6), contentDescription = null,
                    modifier =Modifier.padding(end = 5.dp)
                )
            }
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
                .clickable { viewModel.logout() },
            ) {
                Text(text = "Logout",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }


        }



    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Profile(AuthViewModel())
}