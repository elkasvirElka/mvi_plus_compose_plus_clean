package com.example.mvi_plus_compose_plus_clean

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.example.mvi_plus_compose_plus_clean.presentation.MovieListFragment
import com.example.mvi_plus_compose_plus_clean.ui.theme.Mvi_plus_compose_plus_cleanTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mvi_plus_compose_plus_cleanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )

                    // Add Fragment Container in Compose Layout
                    FragmentContainer()
                }
            }
        }
    }
}

@Composable
fun FragmentContainer() {
    AndroidView(factory = { context ->
        // Create a FragmentContainerView and set it to the FragmentManager
        val fragmentContainerView = FragmentContainerView(context).apply {
            id = View.generateViewId() // Generate an ID for the FragmentContainerView
        }

        // Add the fragment to the container
        val fragment = MovieListFragment()
        (context as? AppCompatActivity)?.supportFragmentManager?.commit {
            replace(fragmentContainerView.id, fragment)
        }

        fragmentContainerView
    })
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mvi_plus_compose_plus_cleanTheme {
        Greeting("Android")
    }
}
