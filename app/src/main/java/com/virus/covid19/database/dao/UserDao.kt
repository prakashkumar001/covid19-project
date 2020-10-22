package com.virus.covid19.database.dao

import androidx.room.*
import com.virus.covid19.database.entities.User


@Dao
interface UserDao {
        @Query("SELECT * FROM user ORDER BY ID")
        fun loadAllUser(): List<User?>?

        @Insert
        fun insertPerson(user: User?)

        @Update
        fun updatePerson(user: User?)

        @Delete
        fun delete(user: User?)

        @Query("SELECT * FROM user WHERE id = :id")
        fun loadUserById(id: Int): User?

        @Query("SELECT * FROM user WHERE email = :email")
        fun loadUserByEmail(email: String): User?

        @Query("SELECT * FROM user WHERE email = :email AND password = :password")
        fun getUserInfo(email: String,password: String): User?

        @Query("SELECT * FROM user WHERE email = :email AND isSocialLogin = :issocial")
        fun getUserInfo(email: String,issocial: Boolean): User?

        @Query("SELECT * FROM user")
        fun getUser(): User?

}