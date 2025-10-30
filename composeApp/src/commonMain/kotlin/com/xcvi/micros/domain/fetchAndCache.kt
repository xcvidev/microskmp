package com.xcvi.micros.domain

suspend fun <FallbackRequest, ApiResult, DbResult> fetchAndCache(
    apiCall: suspend () -> ApiResult?,
    cacheCall: suspend (ApiResult) -> Unit,
    dbCall: suspend (ApiResult) -> DbResult?,
    fallbackRequest: FallbackRequest,
    fallbackDbCall: suspend (FallbackRequest) -> DbResult?,
    isEmptyResult: (DbResult) -> Boolean = { false },
    notFoundFailure: Failure = Failure.EmptyResult,
    networkFailure: Failure = Failure.Network,
): Response<DbResult> {
    val apiResult: Response<ApiResult> = try {
        apiCall()?.let { Response.Success(it) } ?: Response.Error(notFoundFailure)
    } catch (e: Exception) {
        Response.Error(networkFailure)
    }

    when (apiResult) {
        is Response.Success -> {
            val dbResult = try {
                cacheCall(apiResult.data)
                dbCall(apiResult.data)
            } catch (e: Exception) {
                null
            }

            return if (dbResult != null && !isEmptyResult(dbResult)) {
                Response.Success(dbResult)
            } else {
                Response.Error(notFoundFailure)
            }
        }

        is Response.Error -> {
            val fallbackResult = try {
                fallbackDbCall(fallbackRequest)
            } catch (e: Exception) {
                null
            }

            return if (fallbackResult != null && !isEmptyResult(fallbackResult)) {
                Response.Success(fallbackResult)
            } else {
                Response.Error(apiResult.error)
            }
        }
    }
}
