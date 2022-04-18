package com.pyramitec.secretfriend.infra.webservice

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SecretFriendService {

    @POST("/v1/secret-friend/keeper")
    suspend fun keeper(@Body friends: List<String>): Response<HashMap<String, String>>

}