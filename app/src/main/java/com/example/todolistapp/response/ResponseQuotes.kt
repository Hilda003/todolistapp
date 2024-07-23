package com.example.todolistapp.response

import com.google.gson.annotations.SerializedName

data class ResponseQuotes(

	@field:SerializedName("ResponseQuotes")
	val responseQuotes: List<ResponseQuotesItem>
)

data class ResponseQuotesItem(

	@field:SerializedName("authorSlug")
	val authorSlug: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("length")
	val length: Int,

	@field:SerializedName("dateModified")
	val dateModified: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("dateAdded")
	val dateAdded: String,

	@field:SerializedName("tags")
	val tags: List<String>
)
