package ir.ha.mylibrary.data.repository

import ir.ha.mylibrary.data.model.Users
import ir.ha.mylibrary.data.remote.UserWebService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

interface UserRepository {

    suspend fun getUsers() : Flow<Users>

}


class UserRepositoryImpl @Inject constructor(
    private val usersWebServices : UserWebService
) : UserRepository {


    override suspend fun getUsers(): Flow<Users> = flow {
       try {
           emit(usersWebServices.getUsersWebService())
       }catch (e : IOException){
           emit(Users())
       }
    }



}