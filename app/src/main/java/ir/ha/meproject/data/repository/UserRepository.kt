package ir.ha.meproject.data.repository

import ir.ha.meproject.data.model.Users
import ir.ha.meproject.data.remote.UserWebService
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