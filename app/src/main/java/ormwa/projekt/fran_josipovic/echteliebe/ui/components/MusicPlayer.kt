package ormwa.projekt.fran_josipovic.echteliebe.ui.components

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun MusicPlayer(url: String) {
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Play Button
        IconButton(
            onClick = {
                if (isPlaying) {
                    mediaPlayer?.pause()
                } else {
                    mediaPlayer = MediaPlayer().apply {
                        setDataSource(url)
                        prepare()
                        start()
                    }
                }
                isPlaying = !isPlaying
            },
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Outlined.Clear else Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(38.dp)
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            // Release the MediaPlayer when the composable is disposed
            mediaPlayer?.release()
        }
    }
}