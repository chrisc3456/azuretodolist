package com.azure.todolist.model

data class Result<T>(
    val state: State,
    val resultData: T?,
    val message: String?
) {

    enum class State {
        LOADING,
        COMPLETE_SUCCESS,
        COMPLETE_ERROR
    }

    companion object {
        fun <T> completeWithSuccess(resultData: T?): Result<T> {
            return Result(
                State.COMPLETE_SUCCESS,
                resultData,
                null
            )
        }

        fun <T> completeWithError(message: String, resultData: T? = null): Result<T> {
            return Result(
                State.COMPLETE_ERROR,
                resultData,
                message
            )
        }

        fun <T> loading(resultData: T?): Result<T> {
            return Result(
                State.LOADING,
                resultData,
                null
            )
        }
    }
}