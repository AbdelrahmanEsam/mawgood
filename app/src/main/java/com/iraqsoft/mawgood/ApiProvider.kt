package com.iraqsoft.mawgood

//import com.code_zone.ahmal_agent.db.model.CountriesData
import com.iraqsoft.mawgood.db.model.CheckInAndOutResponse
import com.iraqsoft.mawgood.db.model.GetResponse
import com.iraqsoft.mawgood.db.model.LoginCompanyResponse
import retrofit2.Response
import retrofit2.http.*


    interface ApiProvider {



        @FormUrlEncoded
        @POST("/company/login")
        suspend fun login(@Field("username") user : String , @Field("password") password :String): Response<LoginCompanyResponse>


        @GET("/users")
        suspend fun getEmployees(@Query("company") company : String, @Query("branch") branch :String ): Response<GetResponse>

        @FormUrlEncoded
        @POST("/reports/check")
        suspend fun check(@Field("employeeId") empId : String ): Response<CheckInAndOutResponse>



    }

