package com.example.simpleToDo.utils.analytics

interface AnalyticsEvent {
	/**
	 * Имя события
	 * */
	val name: String
	
	/**
	 * Список параметров события
	 * */
	val arguments: Map<String, Any?>
}
