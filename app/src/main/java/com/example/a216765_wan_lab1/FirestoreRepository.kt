package com.example.a216765_wan_lab1

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class FirestoreMoodEntry(
    val userId: String = "A216765",
    val mood: String = "",
    val mentalHealthStatus: String = "",
    val date: String = "",
    val time: String = ""
)

class FirestoreRepository {
    private val db = Firebase.firestore
    private val collection = db.collection("mood_entries")

    suspend fun saveMoodEntry(entry: FirestoreMoodEntry): Boolean {
        return try {
            collection.add(entry).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getAllEntries(): List<FirestoreMoodEntry> {
        return try {
            val snapshot = collection
                .whereEqualTo("userId", "A216765")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(FirestoreMoodEntry::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}