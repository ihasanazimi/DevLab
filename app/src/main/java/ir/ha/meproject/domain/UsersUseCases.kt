package ir.ha.meproject.domain

import ir.ha.meproject.data.model.Users
import ir.ha.meproject.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserUseCase{

    suspend fun getUsers() : Flow<Users>

}


class UserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UserUseCase {


    override suspend fun getUsers(): Flow<Users> {
        return userRepository.getUsers()
    }



}