package com.example.todolistapp.data

import java.io.Serializable


data class ModelQuote(
    var _id: String,
    var content: String,
    var author: String,
    var authorSlug: String,
    var length: Int,
): Serializable