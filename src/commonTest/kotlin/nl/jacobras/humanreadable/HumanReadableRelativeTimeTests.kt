package nl.jacobras.humanreadable

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.skeptick.libres.LibresSettings
import kotlin.test.Test
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class HumanReadableRelativeTimeTests {

    init {
        LibresSettings.languageCode = "en"
    }

    private val now: Instant
        get() = Clock.System.now()

    @Test
    fun inFuture() {
        assertThat(HumanReadable.timeAgo(now + 200.seconds)).isEqualTo("in 3 minutes")
    }

    @Test
    fun now() {
        assertThat(HumanReadable.timeAgo(now)).isEqualTo("now")
    }

    @Test
    fun inPast() {
        assertThat(HumanReadable.timeAgo(now - 3.days)).isEqualTo("3 days ago")
    }

    @Test
    fun fixedUnitInPast() {
        val base = now
        assertThat(HumanReadable.timeAgo(base - 3.days, DurationUnit.Hours, baseInstant = base)).isEqualTo("72 hours ago")
        assertThat(HumanReadable.timeAgo(base - 32.days, DurationUnit.Days, baseInstant = base)).isEqualTo("32 days ago")
    }

    @Test
    fun fixedUnitInFuture() {
        val base = now
        assertThat(HumanReadable.timeAgo(base + 200.seconds, DurationUnit.Seconds, baseInstant = base)).isEqualTo("in 200 seconds")
    }

    @Test
    fun fixedUnitNow() {
        val base = now
        assertThat(HumanReadable.timeAgo(base, DurationUnit.Seconds, baseInstant = base)).isEqualTo("now")
    }
}