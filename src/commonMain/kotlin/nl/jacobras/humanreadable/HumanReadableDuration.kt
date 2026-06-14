package nl.jacobras.humanreadable

import io.github.skeptick.libres.LibresSettings
import kotlin.math.roundToInt
import kotlin.time.Duration

/**
 * Returns the given [duration] in human-readable format.
 */
internal fun formatDuration(
    duration: Duration,
    relativeTime: RelativeTime
): String {
    val secondsAgo = duration.inWholeSeconds.toInt()
    val hoursAgo = duration.inWholeHours.toInt()
    val daysAgo = duration.inWholeDays.toInt()
    val weeksAgo = (duration.inWholeDays / 7f).roundToInt()
    val monthsAgo = (duration.inWholeDays / 30.5f).roundToInt()
    val yearsAgo = (duration.inWholeDays / 365).toInt()

    val result = when {
        secondsAgo < 60 -> {
            "$secondsAgo ${TimeUnit.Seconds.format(secondsAgo, relativeTime)}"
        }

        secondsAgo < 3600 -> {
            val minutes = duration.inWholeMinutes.toInt()
            "$minutes ${TimeUnit.Minutes.format(minutes, relativeTime)}"
        }

        daysAgo < 1 -> {
            "$hoursAgo ${TimeUnit.Hours.format(hoursAgo, relativeTime)}"
        }

        daysAgo < 7 -> {
            "$daysAgo ${TimeUnit.Days.format(daysAgo, relativeTime)}"
        }

        daysAgo < 30 -> {
            "$weeksAgo ${TimeUnit.Weeks.format(weeksAgo, relativeTime)}"
        }

        monthsAgo < 12 || yearsAgo == 0 -> {
            "$monthsAgo ${TimeUnit.Months.format(monthsAgo, relativeTime)}"
        }

        else -> {
            "$yearsAgo ${TimeUnit.Years.format(yearsAgo, relativeTime)}"
        }
    }

    return if (LibresSettings.languageCode == "ar" && (result.startsWith("1 ") || result.startsWith("2 "))) {
        result.substringAfter(" ")
    } else {
        result
    }
}

/**
 * Returns the given [duration] expressed in the specified [unit], in human-readable format.
 */
internal fun formatDuration(
    duration: Duration,
    relativeTime: RelativeTime,
    unit: DurationUnit
): String {
    val (value, timeUnit) = when (unit) {
        DurationUnit.Seconds -> duration.inWholeSeconds.toInt() to TimeUnit.Seconds
        DurationUnit.Minutes -> duration.inWholeMinutes.toInt() to TimeUnit.Minutes
        DurationUnit.Hours -> duration.inWholeHours.toInt() to TimeUnit.Hours
        DurationUnit.Days -> duration.inWholeDays.toInt() to TimeUnit.Days
        DurationUnit.Weeks -> (duration.inWholeDays / 7).toInt() to TimeUnit.Weeks
        DurationUnit.Months -> (duration.inWholeDays / 30).toInt() to TimeUnit.Months
        DurationUnit.Years -> (duration.inWholeDays / 365).toInt() to TimeUnit.Years
    }

    val result = "$value ${timeUnit.format(value, relativeTime)}"

    return if (LibresSettings.languageCode == "ar" && (result.startsWith("1 ") || result.startsWith("2 "))) {
        result.substringAfter(" ")
    } else {
        result
    }
}