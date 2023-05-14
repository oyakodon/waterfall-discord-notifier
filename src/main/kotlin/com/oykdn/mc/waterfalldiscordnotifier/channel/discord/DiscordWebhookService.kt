package com.oykdn.mc.waterfalldiscordnotifier.channel.discord

import com.oykdn.mc.waterfalldiscordnotifier.model.DiscordWebhookPayload
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface DiscordWebhookService {
    @Headers(
        "Accept: application/json", "Content-type: application/json"
    )
    @POST("{id}/{token}")
    fun post(
        @Path("id") webhookId: String,
        @Path("token") webhookToken: String,
        @Body message: DiscordWebhookPayload,
    ): Call<ResponseBody>
}
