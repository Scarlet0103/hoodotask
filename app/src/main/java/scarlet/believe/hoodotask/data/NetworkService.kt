package scarlet.believe.hoodotask.data

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import scarlet.believe.hoodotask.data.model.CommentResponse
import scarlet.believe.hoodotask.data.model.Response

interface NetworkService {

    @Headers("app-id: 6052f77f148f194d2b6e707e")
    @GET("post")
    fun getPosts(
        @Query("page") page: Int
    ): Single<Response>

    @Headers("app-id: 6052f77f148f194d2b6e707e")
    @GET("post/{postID}/comment")
    fun getComments(
            @Path("postID") postID : String, @Query("page") page: Int
    ):Single<CommentResponse>

    companion object {
        fun getService(): NetworkService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dummyapi.io/data/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }

}