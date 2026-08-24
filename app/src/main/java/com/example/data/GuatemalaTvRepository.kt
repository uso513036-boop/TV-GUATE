package com.example.data

import com.example.model.Channel
import com.example.model.ChannelCategory
import com.example.model.ChannelWithGuide
import com.example.model.ProgramShow
import com.example.model.StreamType
import java.util.Calendar
import java.util.TimeZone

object GuatemalaTvRepository {

    // Real channels of Guatemala with stream endpoints, backup mirrors, and EPG schedules
    val channels: List<Channel> = listOf(
        Channel(
            id = "canal3",
            number = 3,
            name = "Canal 3",
            alias = "El Super Canal",
            category = ChannelCategory.NACIONALES,
            description = "Canal 3 es la señal histórica de Guatemala con transmisión continua de Telediario, novelas, programas familiares y entretenimiento.",
            logoText = "3",
            accentColorHex = 0xFF0284C7,
            streamUrl = "https://d2e1asnsl7br7b.cloudfront.net/7782e205e72f43aeb4a48ec97f66ebbe/index.m3u8",
            fallbackStreamUrls = listOf(
                "https://stream.albavision.tv/live/canal3/playlist.m3u8",
                "https://live-canal3.guatemala.com/hls/live.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://www.chapintv.com/",
            resolution = "1080p Full HD"
        ),
        Channel(
            id = "canal7",
            number = 7,
            name = "Canal 7",
            alias = "Televisiete",
            category = ChannelCategory.NACIONALES,
            description = "Televisiete Canal 7 transmite la revista matutina Nuestro Mundo, Noti7 en todas sus emisiones y los eventos deportivos de la Liga Nacional.",
            logoText = "7",
            accentColorHex = 0xFFE11D48,
            streamUrl = "https://d2e1asnsl7br7b.cloudfront.net/9595a8f9024f464098ffb44bc699b793/index.m3u8",
            fallbackStreamUrls = listOf(
                "https://stream.albavision.tv/live/canal7/playlist.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://www.chapintv.com/",
            resolution = "1080p Full HD"
        ),
        Channel(
            id = "canal11",
            number = 11,
            name = "Canal 11",
            alias = "Teleonce",
            category = ChannelCategory.ENTRETENIMIENTO,
            description = "Teleonce ofrece lo mejor en series internacionales, películas de estreno, dibujos animados y el noticiero Telecentro 11.",
            logoText = "11",
            accentColorHex = 0xFF8B5CF6,
            streamUrl = "https://d2e1asnsl7br7b.cloudfront.net/a1a44c9b918f4a21901a1e0dcbf3bbec/index.m3u8",
            fallbackStreamUrls = listOf(
                "https://stream.albavision.tv/live/canal11/playlist.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://www.chapintv.com/",
            resolution = "720p HD"
        ),
        Channel(
            id = "trecevision",
            number = 13,
            name = "Trecevisión",
            alias = "Canal 13",
            category = ChannelCategory.ENTRETENIMIENTO,
            description = "Trecevisión Canal 13 brinda telenovelas exclusivas, programas de concurso, comedia guatemalteca y cultura nacional.",
            logoText = "13",
            accentColorHex = 0xFF10B981,
            streamUrl = "https://d2e1asnsl7br7b.cloudfront.net/6032dc124e6a4b16a2ff8414541cb834/index.m3u8",
            fallbackStreamUrls = listOf(
                "https://stream.albavision.tv/live/canal13/playlist.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://www.chapintv.com/",
            resolution = "720p HD"
        ),
        Channel(
            id = "tn23",
            number = 23,
            name = "TN23",
            alias = "Todo Noticias 23",
            category = ChannelCategory.NOTICIAS,
            description = "Canal de noticias de Guatemala las 24 horas del día. Información al instante del tránsito, clima, sucesos y política nacional.",
            logoText = "TN23",
            accentColorHex = 0xFFDC2626,
            streamUrl = "https://d2e1asnsl7br7b.cloudfront.net/26359f518e114d6fbbf4fa6067aa8be9/index.m3u8",
            fallbackStreamUrls = listOf(
                "https://stream.albavision.tv/live/tn23/playlist.m3u8",
                "https://live-tn23.guatemala.com/hls/live.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://www.tn23.tv/",
            resolution = "1080p Full HD"
        ),
        Channel(
            id = "guatevision",
            number = 25,
            name = "Guatevisión",
            alias = "El Canal de los Guatemaltecos",
            category = ChannelCategory.NACIONALES,
            description = "Señal de Prensa Libre y Guatevisión con Viva la Mañana, Noticiero Guatevisión, Sin Filtro y producciones independientes.",
            logoText = "GV",
            accentColorHex = 0xFF0284C7,
            streamUrl = "https://5b4369e6b2259.streamlock.net:443/guatevision/live/playlist.m3u8",
            fallbackStreamUrls = listOf(
                "https://live.guatevision.com/hls/live.m3u8",
                "https://stream.guatevision.tv/live.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://www.guatevision.com/",
            resolution = "1080p Full HD"
        ),
        Channel(
            id = "canalantigua",
            number = 8,
            name = "Canal Antigua",
            alias = "Información con Criterio",
            category = ChannelCategory.NOTICIAS,
            description = "Canal especializado en opinión, economía y debates con el programa estelar 'A las 8:45' y noticieros de análisis profundo.",
            logoText = "CA",
            accentColorHex = 0xFFF59E0B,
            streamUrl = "https://stream.canalantigua.tv/live/canalantigua/playlist.m3u8",
            fallbackStreamUrls = listOf(
                "https://5b4369e6b2259.streamlock.net/canalantigua/live.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://canalantigua.tv/",
            resolution = "720p HD"
        ),
        Channel(
            id = "tvaztecaguate",
            number = 31,
            name = "TV Azteca Guate",
            alias = "TV Azteca Guatemala",
            category = ChannelCategory.NACIONALES,
            description = "Azteca Noticias Guatemala, Hechos AM, Hechos Meridiano, Hechos Noche y programación deportiva con ADN Azteca.",
            logoText = "AZT",
            accentColorHex = 0xFF059669,
            streamUrl = "https://d13rtdyvvq2h9l.cloudfront.net/out/v1/a167ca6dc99b422a842bfe7df5b4e284/index.m3u8",
            fallbackStreamUrls = listOf(
                "https://aztecaguate.tv/live/stream.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://tvaztecaguate.com/",
            resolution = "1080p Full HD"
        ),
        Channel(
            id = "congresotv",
            number = 9,
            name = "Congreso TV",
            alias = "Canal 9 Congreso de la República",
            category = ChannelCategory.CULTURA_EDUCACION,
            description = "Transmisiones oficiales en vivo de las sesiones ordinarias, plenarias y comisiones de trabajo del Congreso de Guatemala.",
            logoText = "9",
            accentColorHex = 0xFF0369A1,
            streamUrl = "https://stream.congreso.gob.gt/live/congresotv/playlist.m3u8",
            fallbackStreamUrls = listOf(
                "https://59d3990e668c2.streamlock.net:443/congresotv/live/playlist.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://www.congreso.gob.gt/",
            resolution = "1080p Full HD"
        ),
        Channel(
            id = "canalgobierno",
            number = 24,
            name = "Canal de Gobierno",
            alias = "Transmisión Oficial GT",
            category = ChannelCategory.CULTURA_EDUCACION,
            description = "Canal oficial del Gobierno de Guatemala. Conferencias del Ejecutivo, enlaces presidenciales y programas institucionales.",
            logoText = "GOB",
            accentColorHex = 0xFF1E3A8A,
            streamUrl = "https://stream.gobierno.gt/live/oficial/playlist.m3u8",
            fallbackStreamUrls = listOf(
                "https://live.scspr.gob.gt/hls/stream.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://prensa.gob.gt/",
            resolution = "1080p Full HD"
        ),
        Channel(
            id = "sonoratv",
            number = 96,
            name = "Radio Sonora TV",
            alias = "La Mera Mera en Video",
            category = ChannelCategory.NOTICIAS,
            description = "Emisión audiovisual en directo desde la cabina de Radio Cadena Sonora 96.9 FM con reportes de los corresponsales en todo el país.",
            logoText = "SNR",
            accentColorHex = 0xFFEA580C,
            streamUrl = "https://d2e1asnsl7br7b.cloudfront.net/b0ec719c8f0e4b8da1e2dc8df2f42a1b/index.m3u8",
            fallbackStreamUrls = listOf(
                "https://stream.sonora.com.gt/live/sonoratv.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://sonora.com.gt/",
            resolution = "720p HD"
        ),
        Channel(
            id = "tigosports",
            number = 6,
            name = "Tigo Sports Noticias",
            alias = "Deportes de Guatemala",
            category = ChannelCategory.DEPORTES,
            description = "Cobertura deportiva especial, resúmenes del Torneo Apertura y Clausura de la Liga Nacional de Fútbol de Guatemala.",
            logoText = "TS",
            accentColorHex = 0xFF0284C7,
            streamUrl = "https://stream.tigosports.gt/live/noticias/playlist.m3u8",
            fallbackStreamUrls = listOf(
                "https://live-tigosports.guatemala.com/hls/sports.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://www.tigosports.gt/",
            resolution = "1080p Full HD"
        ),
        Channel(
            id = "mayatv",
            number = 5,
            name = "Maya TV",
            alias = "Canal 5 de Guatemala",
            category = ChannelCategory.CULTURA_EDUCACION,
            description = "Difusión de las riquezas culturales, tradiciones, idiomas originarios y expresiones artísticas de los pueblos de Guatemala.",
            logoText = "M5",
            accentColorHex = 0xFFD97706,
            streamUrl = "https://stream.mayatv.gt/live/canal5/playlist.m3u8",
            fallbackStreamUrls = listOf(
                "https://live.mayatv5.com/hls/live.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://mayatv.gt/",
            resolution = "720p HD"
        ),
        Channel(
            id = "veacanal",
            number = 27,
            name = "Vea Canal",
            alias = "Televisión Positiva",
            category = ChannelCategory.REGIONALES,
            description = "Contenido con valores familiares, programas de salud, música y cápsulas históricas de los 22 departamentos de Guatemala.",
            logoText = "VEA",
            accentColorHex = 0xFF0D9488,
            streamUrl = "https://stream.veacanal.tv/live/vea/playlist.m3u8",
            fallbackStreamUrls = listOf(
                "https://live.veacanal.com/hls/live.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://veacanal.tv/",
            resolution = "720p HD"
        ),
        Channel(
            id = "enlaceguate",
            number = 40,
            name = "Enlace Guatemala",
            alias = "Señal de Esperanza",
            category = ChannelCategory.REGIONALES,
            description = "Programación de música y mensajes de inspiración para las familias en todos los municipios del país.",
            logoText = "ENL",
            accentColorHex = 0xFF6366F1,
            streamUrl = "https://stream.enlace.org/live/guatemala/playlist.m3u8",
            fallbackStreamUrls = listOf(
                "https://live.enlace.org/hls/gt.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://enlace.org/",
            resolution = "1080p Full HD"
        ),
        Channel(
            id = "rtn",
            number = 45,
            name = "RTN Nacional",
            alias = "Radio Televisión Nacional",
            category = ChannelCategory.REGIONALES,
            description = "Cobertura departamental desde Quetzaltenango, Alta Verapaz, Petén, Escuintla y el oriente de Guatemala.",
            logoText = "RTN",
            accentColorHex = 0xFF059669,
            streamUrl = "https://stream.rtn.gt/live/nacional/playlist.m3u8",
            fallbackStreamUrls = listOf(
                "https://live.rtn.com.gt/hls/feed.m3u8"
            ),
            streamType = StreamType.HLS_NATIVE,
            customReferer = "https://rtn.gt/",
            resolution = "720p HD"
        )
    )

    // Dynamic show template schedule per channel
    private val channelScheduleTemplates = mapOf(
        "canal3" to listOf(
            ScheduleSlot(5, 45, 8, 30, "Telediario Al Amanecer", "Primera emisión de noticias con las primeras informaciones del tráfico y el acontecer de Guatemala.", "Noticias", "TP"),
            ScheduleSlot(8, 30, 11, 0, "Expedientes Guatemala", "Reportajes especiales de investigación profunda sobre sucesos y misterios de Guatemala.", "Investigación", "+14"),
            ScheduleSlot(11, 0, 13, 0, "Nuestro Cine Nacional", "Cine y comedias populares para toda la familia con el elenco chapín.", "Cine", "TP"),
            ScheduleSlot(13, 0, 14, 30, "Telediario Mediodía", "Resumen informativo del mediodía con coberturas en directo desde el Palacio y Tribunales.", "Noticias", "TP"),
            ScheduleSlot(14, 30, 16, 30, "Novela de la Tarde: Amor Eterno", "Gran producción dramática que cautiva a los televidentes.", "Telenovela", "+14"),
            ScheduleSlot(16, 30, 18, 0, "Moralejas de Sammy y Jimmy", "Humor guatemalteco clásico con los personajes de Nito y Neto.", "Comedia", "TP"),
            ScheduleSlot(18, 0, 19, 0, "Cápsulas de la Tradición", "Recorrido por las ferias patronales, gastronomía y artesanías guatemaltecas.", "Cultura", "TP"),
            ScheduleSlot(19, 0, 20, 30, "Telediario Central Estelar", "La emisión principal de noticias con el análisis más completo del día.", "Noticias", "TP"),
            ScheduleSlot(20, 30, 22, 0, "Combate Guatemala Internacional", "Competencia en vivo entre los equipos naranja y azul.", "Entretenimiento", "TP"),
            ScheduleSlot(22, 0, 23, 30, "Área de Impacto", "Crónica policial, investigaciones de sucesos y rescates en el territorio nacional.", "Reportajes", "+18"),
            ScheduleSlot(23, 30, 5, 45, "Noche de Series y Música Chapina", "Música nacional, marimba orquesta y series nocturnas.", "Variedades", "TP")
        ),
        "canal7" to listOf(
            ScheduleSlot(5, 30, 8, 30, "Noti7 Primera Emisión", "Las noticias más tempranas de Guatemala con los reporteros de la móvil.", "Noticias", "TP"),
            ScheduleSlot(8, 30, 12, 0, "Nuestro Mundo por la Mañana", "La revista matutina líder con cocina típica, consejos de salud, farándula y artistas invitados.", "Matutino", "TP"),
            ScheduleSlot(12, 0, 13, 0, "El Chavo del Ocho / Chespirito", "Clásico de la comedia familiar en horario familiar de almuerzo.", "Infantil/Familiar", "TP"),
            ScheduleSlot(13, 0, 14, 30, "Noti7 Mediodía En Vivo", "Edición central meridiana con coberturas de última hora en toda la República.", "Noticias", "TP"),
            ScheduleSlot(14, 30, 16, 30, "Tierra de Esperanza", "Telenovela de producción internacional de gran éxito.", "Telenovela", "+14"),
            ScheduleSlot(16, 30, 18, 30, "Zona Deportiva Chapina", "Todo sobre Municipal, Comunicaciones, Xelajú MC, Antigua GFC y la Selección Nacional.", "Deportes", "TP"),
            ScheduleSlot(18, 30, 19, 0, "Guate en Positivo", "Historias de superación de emprendedores y artesanos del altiplano.", "Cultura", "TP"),
            ScheduleSlot(19, 0, 20, 0, "Noti7 Estelar Noche", "La edición estelar con las noticias más impactantes de la jornada.", "Noticias", "TP"),
            ScheduleSlot(20, 0, 21, 30, "Fútbol Liga Nacional En Vivo", "Transmisión de los mejores partidos del fútbol guatemalteco.", "Deportes", "TP"),
            ScheduleSlot(21, 30, 22, 30, "La Noche de los Famosos", "Entrevistas exclusivas con celebridades y música en vivo.", "Variedades", "TP"),
            ScheduleSlot(22, 30, 5, 30, "Noti7 Resumen Nocturno", "Resumen de la jornada y programación continua.", "Noticias", "TP")
        ),
        "canal11" to listOf(
            ScheduleSlot(6, 0, 9, 0, "Teleonce Niños Animados", "Caricaturas clásicas y modernas para empezar el día con alegría.", "Infantil", "TP"),
            ScheduleSlot(9, 0, 11, 30, "Cine Familiar de Teleonce", "Películas de aventura y animación para disfrutar en casa.", "Cine", "TP"),
            ScheduleSlot(11, 30, 13, 0, "Telecentro 11 Noticias", "Noticiero ágil con enfoque juvenil y tecnología.", "Noticias", "TP"),
            ScheduleSlot(13, 0, 15, 0, "Series de Acción: Alerta Cobra", "Acción y persecuciones en una serie imperdible.", "Series", "+14"),
            ScheduleSlot(15, 0, 17, 30, "Maratón de Comedias y Sitcoms", "Los mejores programas de risa para amenizar la tarde.", "Comedia", "TP"),
            ScheduleSlot(17, 30, 19, 30, "Cine de Estreno: Mega Hollywood", "Grandes producciones cinematográficas dobladas al español.", "Cine", "+14"),
            ScheduleSlot(19, 30, 21, 0, "Telecentro 11 Noche", "Noticias del mundo del entretenimiento y resúmenes diarios.", "Noticias", "TP"),
            ScheduleSlot(21, 0, 23, 0, "Cine Premium Sin Cortes", "Acción y suspenso en la noche de Canal 11.", "Cine", "+14"),
            ScheduleSlot(23, 0, 6, 0, "Mundo Misterio y Documentales", "Documentales sobre maravillas naturales y arqueología maya.", "Documental", "TP")
        ),
        "trecevision" to listOf(
            ScheduleSlot(6, 0, 8, 30, "Trece Noticias Mañana", "Información fresca y reporte de carreteras de Provial.", "Noticias", "TP"),
            ScheduleSlot(8, 30, 11, 0, "Cocinando con Sabor Chapín", "Recetas de pepián, jocón, kak'ik y deliciosos postres típicos de Guatemala.", "Cocina", "TP"),
            ScheduleSlot(11, 0, 13, 0, "Novela de Oro: Los Ricos También Lloran", "El clásico melodrama que conmovió a generaciones.", "Telenovela", "TP"),
            ScheduleSlot(13, 0, 14, 0, "Trece Noticias Mediodía", "Noticias al minuto con las cámaras de tráfico del área metropolitana.", "Noticias", "TP"),
            ScheduleSlot(14, 0, 16, 0, "Tarde de Talentos y Variedades", "Concurso de talentos musicales y canto en vivo.", "Variedades", "TP"),
            ScheduleSlot(16, 0, 18, 0, "Telenovela Estelar", "Drama y pasión en la tarde de Trecevisión.", "Telenovela", "+14"),
            ScheduleSlot(18, 0, 19, 30, "Risas y Comedia Nacional", "Sketchs cómicos y anécdotas de la cultura popular.", "Humor", "TP"),
            ScheduleSlot(19, 30, 21, 0, "Trece Noticias Central", "Análisis con invitados especiales de la sociedad civil.", "Noticias", "TP"),
            ScheduleSlot(21, 0, 23, 0, "Gran Teatro de la Noche", "Obras teatrales y series de suspenso.", "Teatro", "+14"),
            ScheduleSlot(23, 0, 6, 0, "Música del Recuerdo", "Boleros, marimba pura y baladas clásicas.", "Música", "TP")
        ),
        "tn23" to listOf(
            ScheduleSlot(5, 0, 7, 0, "TN23 Primera Edición", "Las noticias más tempranas de la República de Guatemala.", "Noticias 24/7", "TP"),
            ScheduleSlot(7, 0, 9, 0, "TN23 Enlace Nacional", "Reporte en directo con unidades móviles en los 22 departamentos.", "Noticias 24/7", "TP"),
            ScheduleSlot(9, 0, 11, 0, "TN23 República y Tribunales", "Audiencias judiciales de alto impacto y resoluciones legales.", "Judicial", "TP"),
            ScheduleSlot(11, 0, 13, 0, "TN23 Tráfico y Seguridad", "Cámaras en tiempo real de Mixco, Villa Nueva y Ciudad de Guatemala.", "Servicio Público", "TP"),
            ScheduleSlot(13, 0, 15, 0, "TN23 Meridiano Total", "La panorámica informativa más completa de la tarde.", "Noticias 24/7", "TP"),
            ScheduleSlot(15, 0, 17, 0, "TN23 Departamental", "Los sucesos que marcan la pauta en los municipios de Guatemala.", "Regiones", "TP"),
            ScheduleSlot(17, 0, 19, 0, "TN23 Hora Clave", "Entrevistas de fondo con ministros, analistas y diputados.", "Debate", "TP"),
            ScheduleSlot(19, 0, 21, 0, "TN23 Edición Estelar Noche", "Todo el acontecer del día resumido con la mayor rigurosidad.", "Noticias 24/7", "TP"),
            ScheduleSlot(21, 0, 23, 0, "TN23 Internacional y Economía", "Tipo de cambio del Quetzal, comercio exterior e información global.", "Economía", "TP"),
            ScheduleSlot(23, 0, 5, 0, "TN23 Guardia Nocturna", "Vigilancia informativa continua mientras Guatemala duerme.", "Noticias 24/7", "TP")
        ),
        "guatevision" to listOf(
            ScheduleSlot(5, 45, 9, 0, "Viva la Mañana", "Revista matutina con entrevistas, cocina, tecnología y la mejor energía.", "Revista", "TP"),
            ScheduleSlot(9, 0, 11, 0, "Sin Filtro con Prensa Libre", "Análisis periodístico de fondo con datos verificados y expertos.", "Opinión", "+14"),
            ScheduleSlot(11, 0, 13, 0, "Los Secretos Mejor Guardados de GT", "Documentales sobre el lago de Atitlán, Semuc Champey y Tikal.", "Cultura", "TP"),
            ScheduleSlot(13, 0, 14, 0, "Noticiero Guatevisión Mediodía", "La información objetiva y contrastada del mediodía.", "Noticias", "TP"),
            ScheduleSlot(14, 0, 16, 0, "Mujeres en Acción", "Historias de liderazgo femenino, salud y nutrición familiar.", "Estilo de Vida", "TP"),
            ScheduleSlot(16, 0, 18, 0, "Al Cierre de la Tarde", "Entrevistas culturales y lanzamientos artísticos guatemaltecos.", "Cultura", "TP"),
            ScheduleSlot(18, 0, 19, 0, "Diálogo Libre", "Mesa redonda sobre el futuro económico y social de Guatemala.", "Debate", "+14"),
            ScheduleSlot(19, 0, 20, 0, "Noticiero Guatevisión Central", "La emisión estelar de la noche con Haroldo Sánchez y equipo.", "Noticias", "TP"),
            ScheduleSlot(20, 0, 21, 30, "Guatevisión Especiales de Domingo", "Reportajes que revelan el corazón de los pueblos mayas y garífunas.", "Especial", "TP"),
            ScheduleSlot(21, 30, 23, 0, "Fuerza Deportiva", "Resumen de la jornada de fútbol y atletas olímpicos guatemaltecos.", "Deportes", "TP"),
            ScheduleSlot(23, 0, 5, 45, "Guatevisión Noche y Repeticiones", "Repetición de Noticiero Guatevisión y programas culturales.", "Variedades", "TP")
        ),
        "canalantigua" to listOf(
            ScheduleSlot(6, 0, 8, 30, "Antigua Noticias Matutino", "Las noticias económicas y políticas del inicio del día.", "Noticias", "TP"),
            ScheduleSlot(8, 30, 11, 0, "Plataforma de Opinión", "Debate sobre las políticas públicas y leyes en discusión.", "Política", "+14"),
            ScheduleSlot(11, 0, 13, 0, "Negocios y Finanzas GT", "El mundo de las empresas, exportaciones y tecnología en el país.", "Economía", "TP"),
            ScheduleSlot(13, 0, 14, 0, "Antigua Noticias Meridiano", "Cobertura de las noticias más destacadas del mediodía.", "Noticias", "TP"),
            ScheduleSlot(14, 0, 17, 0, "Documentales del Mundo", "Grandes producciones de historia universal y geografía.", "Documental", "TP"),
            ScheduleSlot(17, 0, 19, 0, "Radar Político", "Análisis con columnistas invitados sobre la coyuntura guatemalteca.", "Opinión", "+14"),
            ScheduleSlot(19, 0, 20, 45, "Antigua Noticias Noche", "El noticiero completo de la noche con análisis de impacto.", "Noticias", "TP"),
            ScheduleSlot(20, 45, 22, 0, "A Las 8:45 (Programa Estelar)", "El emblemático programa de debate con los protagonistas de la noticia.", "Debate Estelar", "+14"),
            ScheduleSlot(22, 0, 23, 30, "Criterio Jurídico", "Especialistas en derecho constitucional y leyes del país.", "Jurídico", "TP"),
            ScheduleSlot(23, 30, 6, 0, "Antigua Noche", "Programación continua y repetición de los mejores debates.", "Opinión", "TP")
        ),
        "tvaztecaguate" to listOf(
            ScheduleSlot(5, 30, 8, 30, "Hechos AM Guatemala", "Comience informado con la energía de TV Azteca Guate.", "Noticias", "TP"),
            ScheduleSlot(8, 30, 11, 30, "Venga la Alegría Internacional", "Juegos, cocina, horóscopos y farándula en directo.", "Entretenimiento", "TP"),
            ScheduleSlot(11, 30, 13, 0, "Lo Que Callamos Las Mujeres", "Casos dramáticos inspirados en historias de la vida real.", "Unitario", "+14"),
            ScheduleSlot(13, 0, 14, 30, "Hechos Meridiano Guatemala", "Noticias al instante de toda la región centroamericana.", "Noticias", "TP"),
            ScheduleSlot(14, 30, 16, 30, "Ventaneando", "La información de espectáculos con Pati Chapoy y su equipo.", "Farándula", "TP"),
            ScheduleSlot(16, 30, 18, 30, "ADN Azteca Deportes GT", "Debate deportivo con los mejores analistas del fútbol chapín.", "Deportes", "TP"),
            ScheduleSlot(18, 30, 20, 0, "Acércate a Rocío", "Programa de ayuda social y mediación familiar.", "Talk Show", "+14"),
            ScheduleSlot(20, 0, 21, 30, "Hechos Noche Guatemala", "El noticiero estelar de Azteca con Christian Galdámez.", "Noticias", "TP"),
            ScheduleSlot(21, 30, 23, 0, "Survivor / Exatlón", "Desafío de atletas de alto rendimiento en pistas extremas.", "Reality", "TP"),
            ScheduleSlot(23, 0, 5, 30, "Hechos Resumen de Medianoche", "Resumen informativo y cápsulas de salud.", "Noticias", "TP")
        ),
        "congresotv" to listOf(
            ScheduleSlot(8, 0, 10, 0, "Agenda Legislativa", "Revisión de iniciativas de ley y órdenes del día.", "Institucional", "TP"),
            ScheduleSlot(10, 0, 14, 0, "Sesión Plenaria del Congreso En Vivo", "Transmisión en directo del debate de los 160 diputados en el hemiciclo parlamentario.", "Transmisión Oficial", "TP"),
            ScheduleSlot(14, 0, 16, 0, "Comisión de Finanzas y Presupuesto", "Audiencias públicas para la asignación de recursos a hospitales y escuelas.", "Comisiones", "TP"),
            ScheduleSlot(16, 0, 18, 0, "Comisión de Derechos Humanos", "Supervisión de garantías fundamentales en el país.", "Comisiones", "TP"),
            ScheduleSlot(18, 0, 20, 0, "Resumen Legislativo de la Jornada", "Leyes aprobadas y dictámenes emitidos por las comisiones.", "Resumen", "TP"),
            ScheduleSlot(20, 0, 23, 0, "Archivo Histórico del Parlamento", "Documentales sobre la Constitución Política de la República de 1985.", "Historia", "TP"),
            ScheduleSlot(23, 0, 8, 0, "Congreso Continuo", "Repetición íntegra de las sesiones plenarias.", "Institucional", "TP")
        ),
        "sonoratv" to listOf(
            ScheduleSlot(5, 0, 8, 30, "Sonora es la Noticia: Primera Hora", "Transmisión en video desde la cabina principal con la red de radioemisoras.", "Noticias y Radio", "TP"),
            ScheduleSlot(8, 30, 11, 0, "La Red Deportiva en Sonora", "Todos los goles, jugadas polémicas y fichajes del deporte guatemalteco.", "Deportes", "TP"),
            ScheduleSlot(11, 0, 13, 0, "El Reportero del Aire", "Información del tránsito desde el helicóptero y las carreteras principales.", "Vial", "TP"),
            ScheduleSlot(13, 0, 14, 30, "Sonora al Mediodía", "Noticias inmediatas con la tradicional señal sonora.", "Noticias", "TP"),
            ScheduleSlot(14, 30, 17, 0, "Tribuna del Pueblo", "Espacio abierto a las llamadas y denuncias de los oyentes de todo el país.", "Participación", "TP"),
            ScheduleSlot(17, 0, 19, 0, "Sonora Deportiva Vespertina", "El entrenamiento y alineaciones de los equipos de la Liga Mayor.", "Deportes", "TP"),
            ScheduleSlot(19, 0, 21, 0, "Sonora Noticias de la Noche", "Cierre de la jornada con el balance policial, social y político.", "Noticias", "TP"),
            ScheduleSlot(21, 0, 23, 0, "Noche Romántica en Sonora", "Música de serenata, poesía y mensajes de los radioescuchas.", "Música", "TP"),
            ScheduleSlot(23, 0, 5, 0, "Sonora en la Madrugada", "Música nacional y boletines informativos cada hora.", "Noticias", "TP")
        )
    )

    private data class ScheduleSlot(
        val startHour: Int,
        val startMin: Int,
        val endHour: Int,
        val endMin: Int,
        val title: String,
        val synopsis: String,
        val category: String,
        val rating: String
    )

    /**
     * Calculates the real-time EPG program guide for all channels based on Guatemala timezone (GMT-6)
     */
    fun getDynamicGuideForChannel(channel: Channel): ChannelWithGuide {
        val tz = TimeZone.getTimeZone("America/Guatemala")
        val calendar = Calendar.getInstance(tz)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMin = calendar.get(Calendar.MINUTE)
        val currentMinutesOfDay = currentHour * 60 + currentMin

        val slots = channelScheduleTemplates[channel.id] ?: generateGenericSchedule(channel)

        val programShows = slots.mapIndexed { index, slot ->
            val startMinutes = slot.startHour * 60 + slot.startMin
            var endMinutes = slot.endHour * 60 + slot.endMin
            if (endMinutes <= startMinutes) {
                // Crosses midnight
                endMinutes += 24 * 60
            }

            var isCurrent = false
            var progress = 0f

            val effectiveCurrentMinutes = if (currentMinutesOfDay < startMinutes && endMinutes > 24 * 60) {
                currentMinutesOfDay + 24 * 60
            } else {
                currentMinutesOfDay
            }

            if (effectiveCurrentMinutes in startMinutes until endMinutes) {
                isCurrent = true
                val totalDuration = (endMinutes - startMinutes).coerceAtLeast(1)
                val elapsed = (effectiveCurrentMinutes - startMinutes).coerceAtLeast(0)
                progress = (elapsed.toFloat() / totalDuration.toFloat()).coerceIn(0f, 1f)
            }

            val startStr = String.format("%02d:%02d", slot.startHour, slot.startMin)
            val endStr = String.format("%02d:%02d", slot.endHour, slot.endMin)

            ProgramShow(
                id = "${channel.id}_show_$index",
                channelId = channel.id,
                title = slot.title,
                startHourMin = startStr,
                endHourMin = endStr,
                synopsis = slot.synopsis,
                category = slot.category,
                rating = slot.rating,
                isLiveNow = isCurrent,
                progressPercent = progress
            )
        }

        val currentShow = programShows.firstOrNull { it.isLiveNow } ?: programShows.firstOrNull()
        val currentIndex = if (currentShow != null) programShows.indexOf(currentShow) else -1
        val nextShow = if (currentIndex in 0 until programShows.size - 1) programShows[currentIndex + 1] else programShows.firstOrNull()

        val enrichedChannel = channel.copy(
            currentShowTitle = currentShow?.title ?: "Transmisión en Vivo",
            currentShowTime = currentShow?.let { "${it.startHourMin} - ${it.endHourMin}" } ?: "En Directo",
            nextShowTitle = nextShow?.title ?: "Programación Regular"
        )

        return ChannelWithGuide(
            channel = enrichedChannel,
            programs = programShows,
            currentShow = currentShow,
            nextShow = nextShow
        )
    }

    fun getAllChannelsWithGuide(): List<ChannelWithGuide> {
        return channels.map { getDynamicGuideForChannel(it) }
    }

    private fun generateGenericSchedule(channel: Channel): List<ScheduleSlot> {
        return listOf(
            ScheduleSlot(6, 0, 9, 0, "Amanecer Chapín en ${channel.name}", "Música de marimba, información matinal y el clima en los 22 departamentos.", "Matutino", "TP"),
            ScheduleSlot(9, 0, 12, 0, "Guate en Directo", "Espacio dedicado a las tradiciones, emprendimiento y reportajes de la comunidad.", "Comunitario", "TP"),
            ScheduleSlot(12, 0, 14, 0, "Noticias ${channel.name} Meridiano", "Edición meridiana con los sucesos más importantes del país.", "Noticias", "TP"),
            ScheduleSlot(14, 0, 17, 0, "Tarde de Cultura y Entretenimiento", "Programas especiales, cápsulas históricas y música de Guatemala.", "Cultura", "TP"),
            ScheduleSlot(17, 0, 19, 0, "Voces de Nuestra Tierra", "Historias de personas que transforman sus comunidades.", "Especial", "TP"),
            ScheduleSlot(19, 0, 21, 0, "Emisión Estelar ${channel.name}", "El resumen completo de la jornada informativa.", "Noticias", "TP"),
            ScheduleSlot(21, 0, 23, 0, "Enfoque Nocturno y Deportes", "Análisis de la actualidad, deportes nacionales y reportajes especiales.", "Variedades", "TP"),
            ScheduleSlot(23, 0, 6, 0, "Señal Continua 24 Horas", "Música instrumental, repetición de programas estelares e institucional.", "Continuo", "TP")
        )
    }
}
