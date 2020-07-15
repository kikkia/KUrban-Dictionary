package models

import java.time.Instant

data class Definition(val word: String,
                      val definition: String,
                      val example: String,
                      val permalink: String,
                      val thumbsUp: Int,
                      val thumbsDown: Int,
                      val author: String,
                      val defId: Int,
                      val currentVote: String,
                      val created: Instant,
                      val linkedDefinitions: List<String>)