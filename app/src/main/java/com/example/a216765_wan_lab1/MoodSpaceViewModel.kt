package com.example.a216765_wan_lab1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SimpleActivity(val title: String, val time: String)

data class MentalHealthResult(
    val status: String,
    val emoji: String,
    val message: String,
    val advice: String,
    val color: String,
    val time: String
)

data class MoodSpaceUiState(
    val displayedName: String = "",
    val moodDone: Boolean = false,
    val gratitudeDone: Boolean = false,
    val sleepDone: Boolean = false,
    val selectedMood: String = "",
    val inBedTime: String = "11:00 PM",
    val outOfBedTime: String = "7:00 AM",
    val sleepDuration: String = "8 hr",
    val readMessages: Set<Int> = emptySet(),
    val showAllGoalsPopup: Boolean = false,
    val showMentalHealthPopup: Boolean = false,
    val showShakePopup: Boolean = false,
    val activities: List<SimpleActivity> = emptyList(),
    val mentalHealthHistory: List<MentalHealthResult> = emptyList(),
    val latestMentalHealthResult: MentalHealthResult? = null,
    val savedActivities: List<ActivityEntity> = emptyList(),
    val dailyQuote: String = "Loading your daily quote...",
    val dailyQuoteAuthor: String = "",
    val isQuoteLoading: Boolean = true,
    val quoteError: Boolean = false,
    val firebaseSynced: Boolean = false
)

class MoodSpaceViewModel(
    private val repository: ActivityRepository,
    private val firestoreRepository: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoodSpaceUiState())
    val uiState: StateFlow<MoodSpaceUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allActivities.collect { entities ->
                _uiState.update { it.copy(savedActivities = entities) }
            }
        }
        fetchDailyQuote()
    }

    fun fetchDailyQuote() {
        viewModelScope.launch {
            _uiState.update { it.copy(isQuoteLoading = true, quoteError = false) }
            try {
                val response = QuoteApi.service.getRandomQuote()
                _uiState.update {
                    it.copy(
                        dailyQuote = "\"${response.content}\"",
                        dailyQuoteAuthor = "— ${response.author}",
                        isQuoteLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        dailyQuote = "\"Take care of your mind as you would your body. You deserve peace.\"",
                        dailyQuoteAuthor = "— MoodSpace",
                        isQuoteLoading = false,
                        quoteError = true
                    )
                }
            }
        }
    }

    fun onShakeDetected() {
        _uiState.update { it.copy(showShakePopup = true) }
    }

    fun dismissShakePopup() {
        _uiState.update { it.copy(showShakePopup = false) }
    }

    fun setDisplayedName(name: String) {
        _uiState.update { it.copy(displayedName = name) }
    }

    fun setMoodDone(mood: String) {
        _uiState.update { it.copy(moodDone = true, selectedMood = mood) }
        viewModelScope.launch {
            repository.insert(ActivityEntity(title = "Mood: $mood", time = getCurrentTime(), type = "mood"))
        }
        checkAllGoalsDone()
    }

    fun setGratitudeDone() {
        _uiState.update { it.copy(gratitudeDone = true) }
        viewModelScope.launch {
            repository.insert(ActivityEntity(title = "Gratitude", time = getCurrentTime(), type = "gratitude"))
        }
        checkAllGoalsDone()
    }

    fun setSleepDone(inBed: String, outOfBed: String, duration: String) {
        _uiState.update {
            it.copy(sleepDone = true, inBedTime = inBed, outOfBedTime = outOfBed, sleepDuration = duration)
        }
        viewModelScope.launch {
            repository.insert(ActivityEntity(title = "Sleep: $duration ($inBed - $outOfBed)", time = getCurrentTime(), type = "sleep"))
        }
        checkAllGoalsDone()
    }

    fun markMessageRead(index: Int) {
        _uiState.update { it.copy(readMessages = it.readMessages + index) }
    }

    fun markAllRead() {
        _uiState.update { it.copy(readMessages = setOf(0, 1, 2)) }
    }

    fun dismissAllGoalsPopup() {
        val result = calculateMentalHealth()
        _uiState.update {
            it.copy(
                showAllGoalsPopup = false,
                showMentalHealthPopup = true,
                latestMentalHealthResult = result,
                mentalHealthHistory = it.mentalHealthHistory + result
            )
        }
        viewModelScope.launch {
            repository.insert(ActivityEntity(title = "${result.emoji} ${result.status}", time = result.time, type = "mental_health"))
            // Save to Firebase
            val s = _uiState.value
            val synced = firestoreRepository.saveMoodEntry(
                FirestoreMoodEntry(
                    mood = s.selectedMood,
                    mentalHealthStatus = result.status,
                    date = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault()).format(java.util.Date()),
                    time = result.time
                )
            )
            _uiState.update { it.copy(firebaseSynced = synced) }
        }
    }

    fun dismissMentalHealthPopup() {
        _uiState.update { it.copy(showMentalHealthPopup = false) }
    }

    fun addActivity(title: String, time: String) {
        _uiState.update { it.copy(activities = it.activities + SimpleActivity(title, time)) }
        viewModelScope.launch {
            repository.insert(ActivityEntity(title = title, time = time, type = "simple"))
        }
    }

    private fun checkAllGoalsDone() {
        val s = _uiState.value
        if (s.moodDone && s.gratitudeDone && s.sleepDone) {
            _uiState.update { it.copy(showAllGoalsPopup = true) }
        }
    }

    private fun calculateMentalHealth(): MentalHealthResult {
        val s = _uiState.value
        val moodScore = when (s.selectedMood) { "Great" -> 3; "Good" -> 2; "OK" -> 1; else -> 0 }
        val sleepScore = when {
            parseSleepHours(s.sleepDuration) >= 8 -> 3
            parseSleepHours(s.sleepDuration) >= 7 -> 2
            parseSleepHours(s.sleepDuration) >= 6 -> 1
            else -> 0
        }
        val total = moodScore + sleepScore + 1
        val time = getCurrentTime()
        return when {
            total >= 6 -> MentalHealthResult("You're Doing Great! 🌟", "😊",
                "Based on your mood, sleep, and gratitude today, you appear to be in a positive mental state. Keep up the great work!",
                "Continue your healthy habits. Regular check-ins like this help maintain good mental well-being.", "green", time)
            total >= 4 -> MentalHealthResult("You're Doing Okay 🌤", "🙂",
                "Your mental well-being seems generally stable today, but there are small signs of stress or low energy. That's completely normal.",
                "Try to get a little more sleep tonight and take a moment to appreciate the small things. You're doing better than you think.", "yellow", time)
            total >= 2 -> MentalHealthResult("Low Mood Detected 🌧", "😔",
                "Based on today's check-in, your mood and sleep suggest you may be experiencing some emotional difficulty. This is common and you are not alone.",
                "Consider talking to someone you trust. Small steps like a short walk, drinking water, or calling a friend can make a difference.", "orange", time)
            else -> MentalHealthResult("Signs of Distress 💙", "😢",
                "Your responses today suggest you may be going through a difficult time. Please know that what you're feeling is valid, and help is available.",
                "You don't have to face this alone. Please reach out to someone you trust, or contact a mental health helpline. Speaking to a counsellor or therapist can make a real difference.", "red", time)
        }
    }

    private fun parseSleepHours(s: String) = try { s.trim().split(" ")[0].toInt() } catch (e: Exception) { 7 }

    private fun getCurrentTime(): String {
        val sdf = java.text.SimpleDateFormat("h:mm a", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }

    companion object {
        fun factory(repository: ActivityRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return MoodSpaceViewModel(repository) as T
                }
            }
        }
    }
}