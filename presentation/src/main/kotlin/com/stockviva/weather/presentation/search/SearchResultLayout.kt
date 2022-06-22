package com.stockviva.weather.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stockviva.weather.domain.search.Weather
import com.stockviva.weather.presentation.R

@Composable
fun SearchResultLayout(
    weather: Weather,
    onClearSearchClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_place_24),
                contentDescription = stringResource(id = R.string.search_result_location_icon_content_desc),
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(24.dp)
            )

            Text(
                text = weather.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(end = 6.dp)
            )
        }

        Text(
            text = "${weather.temp}\u2103",
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${weather.humidity} %",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = stringResource(id = R.string.search_result_humidity_label),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${weather.tempMin}℃ / ${weather.tempMax}℃",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = stringResource(id = R.string.search_result_temperature_label),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${weather.pressure} hPa",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = stringResource(id = R.string.search_result_pressure_label),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = onClearSearchClick
        ) {
            Text(
                text = stringResource(id = R.string.search_result_clear_search_button_text),
            )
        }
    }
}
