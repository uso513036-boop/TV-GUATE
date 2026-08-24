package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VpnLock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Channel
import com.example.model.ChannelGeoDiagnostic
import com.example.model.GeoHealthStatus
import com.example.ui.theme.GuateBluePrimary
import com.example.ui.theme.GuateBlueSecondary
import com.example.ui.theme.GuateDarkBackground
import com.example.ui.theme.GuateDarkBorder
import com.example.ui.theme.GuateDarkSurface
import com.example.ui.theme.GuateDarkSurfaceVariant
import com.example.ui.theme.GuateGrayText
import com.example.ui.theme.GuateLiveRed
import com.example.ui.theme.GuateShieldGreen
import com.example.ui.theme.GuateWarningYellow
import com.example.ui.theme.GuateWhite

@Composable
fun GeoScannerScreen(
    channels: List<Channel>,
    diagnostics: Map<String, ChannelGeoDiagnostic>,
    isScanning: Boolean,
    onStartScan: () -> Unit,
    onPlayChannel: (Channel) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalChannels = channels.size
    val testedCount = diagnostics.size
    val optimizedCount = diagnostics.values.count { it.status == GeoHealthStatus.ONLINE_OPTIMIZED || it.status == GeoHealthStatus.GEO_BYPASSED }
    val bypassedCount = diagnostics.values.count { it.status == GeoHealthStatus.GEO_BYPASSED }
    val avgLatency = if (diagnostics.isNotEmpty()) diagnostics.values.map { it.pingLatencyMs }.average().toLong() else 28L

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(GuateDarkBackground),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Geo Shield Status Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = GuateDarkSurface),
                border = BorderStroke(1.dp, GuateShieldGreen.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    GuateShieldGreen.copy(alpha = 0.15f),
                                    Color.Transparent
                                )
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = GuateShieldGreen.copy(alpha = 0.2f),
                                    modifier = Modifier.size(42.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Shield,
                                            contentDescription = null,
                                            tint = GuateShieldGreen,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Escáner de Geo-Bloqueo",
                                        color = GuateWhite,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Diagnóstico de señales de Guatemala",
                                        color = GuateGrayText,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            // Scan Button
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isScanning) GuateDarkSurfaceVariant else GuateBluePrimary,
                                modifier = Modifier
                                    .clickable(enabled = !isScanning) { onStartScan() }
                                    .testTag("start_geo_scan_btn")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                ) {
                                    if (isScanning) {
                                        CircularProgressIndicator(
                                            color = GuateBlueSecondary,
                                            modifier = Modifier.size(14.dp),
                                            strokeWidth = 2.dp
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Escaneando...", color = GuateBlueSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    } else {
                                        Icon(Icons.Default.Refresh, contentDescription = null, tint = GuateWhite, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Diagnosticar", color = GuateWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Metric Stat Pills
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            MetricPill(
                                title = "Disponibilidad",
                                value = "$optimizedCount / $totalChannels",
                                subtitle = "Canales Operativos",
                                modifier = Modifier.weight(1f)
                            )
                            MetricPill(
                                title = "Geo-Protección",
                                value = "$bypassedCount Activas",
                                subtitle = "Cabeceras GT",
                                modifier = Modifier.weight(1f)
                            )
                            MetricPill(
                                title = "Latencia Prom.",
                                value = "${avgLatency} ms",
                                subtitle = "Ping Guatemala",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Anti-Redirect & Anti-Popup Guarantee Callout
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = GuateDarkSurfaceVariant,
                            border = BorderStroke(1.dp, GuateDarkBorder)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.HealthAndSafety, contentDescription = null, tint = GuateBlueSecondary, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Protección de Reproducción Nativa: La app bloquea redirecciones externas a navegadores y elimina anuncios emergentes automáticamente.",
                                    color = GuateGrayText,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section Title
        item {
            Text(
                text = "Estado de Señales por Canal",
                color = GuateWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        // Channel Diagnostic List
        items(channels, key = { it.id }) { channel ->
            val diag = diagnostics[channel.id]
            ChannelDiagnosticCard(
                channel = channel,
                diagnostic = diag,
                onTestPlay = { onPlayChannel(channel) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
fun MetricPill(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = GuateDarkSurfaceVariant,
        border = BorderStroke(1.dp, GuateDarkBorder.copy(alpha = 0.5f)),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = title, color = GuateGrayText, fontSize = 10.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, color = GuateWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, color = GuateBlueSecondary, fontSize = 9.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun ChannelDiagnosticCard(
    channel: Channel,
    diagnostic: ChannelGeoDiagnostic?,
    onTestPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val status = diagnostic?.status ?: GeoHealthStatus.ONLINE_OPTIMIZED
    val latency = diagnostic?.pingLatencyMs ?: 24L

    val statusColor = when (status) {
        GeoHealthStatus.ONLINE_OPTIMIZED -> GuateShieldGreen
        GeoHealthStatus.GEO_BYPASSED -> GuateBlueSecondary
        GeoHealthStatus.TESTING_SIGNAL -> GuateWarningYellow
        GeoHealthStatus.GEO_RESTRICTED_WARNING -> GuateLiveRed
    }

    val statusLabel = when (status) {
        GeoHealthStatus.ONLINE_OPTIMIZED -> "Señal Directa OK"
        GeoHealthStatus.GEO_BYPASSED -> "Geo-Bypass GT Activo"
        GeoHealthStatus.TESTING_SIGNAL -> "Verificando Ping..."
        GeoHealthStatus.GEO_RESTRICTED_WARNING -> "Requiere Servidor Espejo"
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = GuateDarkSurface),
        border = BorderStroke(1.dp, GuateDarkBorder.copy(alpha = 0.4f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(channel.accentColorHex),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(channel.logoText, color = GuateWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(channel.name, color = GuateWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(channel.geoOrigin, color = GuateGrayText, fontSize = 11.sp)
                    }
                }

                // Play Stream Button
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = GuateBluePrimary,
                    modifier = Modifier.clickable { onTestPlay() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = GuateWhite, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("Probar", color = GuateWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Diagnostic Detail Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Status Pill
                Surface(
                    color = statusColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(statusColor, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(statusLabel, color = statusColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Ping & Speed
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Speed, contentDescription = null, tint = GuateGrayText, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${latency}ms", color = GuateGrayText, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            if (diagnostic != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = diagnostic.diagnosticMessage,
                    color = GuateGrayText,
                    fontSize = 11.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}
